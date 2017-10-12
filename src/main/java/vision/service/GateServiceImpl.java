package vision.service;

import gate.*;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vision.Start;
import vision.models.CV;
import vision.models.Filed;
import vision.push_notification.animations.Animations;
import vision.push_notification.notification.NotificationController;
import vision.push_notification.notification.Notifications;
import vision.repository.FiledRepository;
import vision.utils.CommonUtils;
import vision.utils.Props;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Yuriy on 18.08.2017.
 */
@Service
public class GateServiceImpl implements GateService {
    private final static Logger logger = LoggerFactory.getLogger(GateServiceImpl.class);
    private final FiledRepository filedRepository;
    private final Props props;
    private CorpusController corpusController;
    private Corpus corpus;

    @Autowired
    public GateServiceImpl(FiledRepository filedRepository, Props props) {
        this.filedRepository = filedRepository;
        this.props = props;
        addSubscription();
    }

    private void addSubscription() {
        filedRepository.getOnRemove().subscribe(this::deleteFileFromCorpus);
        filedRepository.getOnClear().subscribe(filed -> clearCorpus());
    }

    @Override
    public void initPlugins() {
        File annieGapp = new File(Gate.getGateHome(), "recruiterVision.gapp");
        try {
            corpusController = (CorpusController) PersistenceManager.loadObjectFromFile(annieGapp);
        } catch (PersistenceException | IOException | ResourceInstantiationException e) {
            e.printStackTrace();
        }
        logger.info("Annie and plugins inited");
    }

    @Override
    public void initNewCorpus() {
        try {
            corpus = Factory.newCorpus("Gate CV Corpus");
        } catch (ResourceInstantiationException e) {
            e.printStackTrace();
        }
        logger.info("New corpus inited");
    }

    @Override
    public void addFileToCorpus(Filed filed) {
        try {
            Document document;
            if (props.isPARSE_FILE_BY_TIKA()) {
                document = Factory.newDocument(filed.getParsed());
                document.setSourceUrl(CommonUtils.getFileUrl(filed.getFile()));
            } else {
                document = Factory.newDocument(CommonUtils.getFileUrl(filed.getFile()));
            }
            corpus.add(document);
            filed.setFileNameGate(corpus.get(corpus.size() - 1).getName());
        } catch (ResourceInstantiationException e) {
            e.printStackTrace();
        }
        logger.info("File added to corpus [" + filed.getFileNameGate() + "]");
    }

    @Override
    public void deleteFileFromCorpus(Filed filed) {
        corpus.remove(getDocumentFromCorpus(filed.getFileNameGate()));
        logger.info("File removed from corpus [" + filed.getFileNameGate() + "]");
    }

    private Document getDocumentFromCorpus(String name) {
        for (Document document : corpus) {
            if (document.getName().equals(name)) {
                return document;
            }
        }
        return null;
    }

    @Override
    public void initGate() {
        if (Gate.getGateHome() == null) {
            Gate.setGateHome(CommonUtils.getFileByPath());
        }
        if (Gate.getPluginsHome() == null)
            Gate.setPluginsHome(CommonUtils.getFileByPath("plugins"));
        try {
            Gate.init();
        } catch (GateException e) {
            e.printStackTrace();
        }
        logger.info("Gate inited");
    }

    @Override
    public void executeController() {
        try {
            Start.getScene().setCursor(Cursor.WAIT);
            corpusController.setCorpus(corpus);
            corpusController.execute();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        logger.info("Controller executed");
    }

    @Override
    public void extractData() {
        long startTime = System.nanoTime();
        for (Document document : corpus) {
            fillCVdata(document);
        }
        long endTime = System.nanoTime();
        Platform.runLater(() -> {
            NotificationController notification = new NotificationController("All files extracted", "Now you can check content of all CV files or create candidates list", Notifications.SUCCESS);
            notification.setAnimation(Animations.POPUP);
            notification.showAndDismiss(Duration.seconds(5));
        });
        Start.getScene().setCursor(Cursor.DEFAULT);
        logger.info("Execution time: " + (endTime - startTime) + " [ns]");
        logger.info("Data extracted");
        filedRepository.refreshFiledRepository();
    }

    private void fillCVdata(Document document) {
        Filed filed = filedRepository.getFiledByPath(document.getSourceUrl().getPath());
        if (!filed.getExtractedStatus().equals("OK")) {
            try {
                CV cv = new CV();
                AnnotationSet annotations = document.getAnnotations();
                Annotation annotation;

                AnnotationSet annotationSet =
                        annotations.get("_Individual");
                if (annotationSet != null && annotationSet.size() > 0) {
                    annotation
                            = annotationSet.iterator().next();

                    cv.setCandidateName((String) annotation.getFeatures().get("firstName"));
                    cv.setCandidateMiddleName((String) annotation.getFeatures().get("middleName"));
                    cv.setCandidateSurname((String) annotation.getFeatures().get("surname"));
                    cv.setGender((String) annotation.getFeatures().get("gender"));
                }

                annotationSet =
                        annotations.get("_Email");
                HashSet<String> emailList = new HashSet<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        emailList.add(Utils.stringFor(document, ann));
                    }
                    cv.setEmails(emailList);
                }

                annotationSet =
                        annotations.get("_Address");
                List<String> addressList = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        addressList.add(Utils.stringFor(document, ann));
                    }
                    cv.setAddresses(addressList);
                }

                annotationSet =
                        annotations.get("_Phone");
                HashSet<String> phonesList = new HashSet<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        phonesList.add(Utils.stringFor(document, ann));
                    }
                    cv.setPhones(phonesList);
                }

                annotationSet =
                        annotations.get("_Country");
                if (annotationSet.size() > 0) {
                    annotation = annotationSet.iterator().next();
                    cv.setCountry(Utils.stringFor(document, annotation));
                }

                annotationSet =
                        annotations.get("_City");
                if (annotationSet.size() > 0) {
                    annotation = annotationSet.iterator().next();
                    cv.setCity(Utils.stringFor(document, annotation));
                }

                annotationSet =
                        annotations.get("_JobTitle");
                List<String> jobTitles = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        jobTitles.add(Utils.stringFor(document, ann));
                    }
                    cv.setCandidateJobTitles(jobTitles);
                }

                annotationSet =
                        annotations.get("_URL");
                HashSet<String> URLs = new HashSet<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        URLs.add(Utils.stringFor(document, ann));
                    }
                    cv.setURLs(URLs);
                }

                annotationSet =
                        annotations.get("_SectionName");
                List<String> sectionNames = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        sectionNames.add(Utils.stringFor(document, ann));
                    }
                    cv.setSectionNames(sectionNames);
                }

                annotationSet =
                        annotations.get("_Summary");
                if (annotationSet.size() > 0) {
                    annotation = annotationSet.iterator().next();
                    cv.setSummarySection(Utils.stringFor(document, annotation));
                }

                annotationSet =
                        annotations.get("_Skills");
                List<String> skillsSectionList = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        skillsSectionList.add(Utils.stringFor(document, ann));
                    }
                    cv.setSkillsSection(skillsSectionList);
                }

                annotationSet =
                        annotations.get("_Experience");
                List<String> experinceNames = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        experinceNames.add(Utils.stringFor(document, ann));
                    }
                    cv.setExperienceMain(experinceNames);
                }

                annotationSet =
                        annotations.get("_ExperienceText");
                List<String> experienceText = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        experienceText.add(Utils.stringFor(document, ann));
                    }
                    cv.setExperienceText(experienceText);
                }

                annotationSet =
                        annotations.get("_Education");
                List<String> educationSectionList = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        educationSectionList.add(Utils.stringFor(document, ann));
                    }
                    cv.setEducationSection(educationSectionList);
                }

                annotationSet =
                        annotations.get("_Languages");
                if (annotationSet.size() > 0) {
                    annotation = annotationSet.iterator().next();
                    cv.setLanguagesSection(Utils.stringFor(document, annotation));
                }

                annotationSet =
                        annotations.get("_Interests");
                List<String> interestsList = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        interestsList.add(Utils.stringFor(document, ann));
                    }
                    cv.setInterests(interestsList);
                }

                annotationSet =
                        annotations.get("_AdditionalInfo");
                List<String> additionalInfoList = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        additionalInfoList.add(Utils.stringFor(document, ann));
                    }
                    cv.setAdditionalInfo(additionalInfoList);
                }

                annotationSet =
                        annotations.get("_Credibility");
                List<String> credibility = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        String text;
                        text = Utils.stringFor(document, ann);
                        credibility.add(text);
                    }
                    cv.setCredibility(credibility);
                }

                annotationSet =
                        annotations.get("_Awards");
                List<String> awards = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        awards.add(Utils.stringFor(document, ann));
                    }
                    cv.setAwards(awards);
                }

                annotationSet =
                        annotations.get("_Accomplishments");
                List<String> accomplishments = new ArrayList<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        accomplishments.add(Utils.stringFor(document, ann));
                    }
                    cv.setAccomplishments(accomplishments);
                }

                annotationSet =
                        annotations.get("_ProgrammingLanguage");
                HashSet<String> programmingLanguages = new HashSet<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        programmingLanguages.add(Utils.stringFor(document, ann));
                    }
                    cv.setProgrammingLanguages(programmingLanguages);
                }

                annotationSet =
                        annotations.get("_ProgrammingSkill");
                HashSet<String> programmingSkills = new HashSet<>();
                if (annotationSet.size() > 0) {
                    for (Annotation ann : annotationSet) {
                        programmingSkills.add(Utils.stringFor(document, ann));
                    }
                    cv.setProgrammingSkills(programmingSkills);
                }

                filed.setExtractedData(cv);
                filed.setExtractedStatus("OK");
                logger.info("File [" + filed.getFile().getName() + "] extracted");
            } catch (Exception ex) {
                logger.info("Error during extracting data " + ex);
                filed.setExtractedStatus("Error");
            }
        } else {
            logger.info("File already extracted");
        }
    }

    @Override
    public void clearCorpus() {
        corpus.clear();
        logger.info("Corpus cleared");
    }
}
