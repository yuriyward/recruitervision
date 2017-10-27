package vision.service;

import javafx.scene.Cursor;
import net.arnx.jsonic.JSON;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vision.Start;
import vision.models.CV;
import vision.models.Filed;
import vision.repository.FiledRepository;
import vision.repository.SelectionRepository;
import vision.utils.CommonUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yuriy on 27.10.2017.
 */
@Service
public class JSONCreator {
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
    private final FiledRepository filedRepository;
    private final static Logger logger = LoggerFactory.getLogger(JSONCreator.class);

    @Autowired
    public JSONCreator(FiledRepository filedRepository) {
        this.filedRepository = filedRepository;
    }

    public boolean createJsonFile(String path) {
        Start.getScene().setCursor(Cursor.WAIT);
        JSONObject json = new JSONObject();
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            JSONObject candidateObject = new JSONObject();
            candidateObject.put("language", filed.getLanguage());
            candidateObject.put("candidate",
                    CommonUtils.getIfNullEmptyString(filed.getExtractedData().getCandidateName()) + " "
                            + CommonUtils.getIfNullEmptyString(filed.getExtractedData().getCandidateMiddleName()) + " "
                            + CommonUtils.getIfNullEmptyString(filed.getExtractedData().getCandidateSurname())
            );
            JSONObject resume = new JSONObject();
            addContent(resume, cv);
            candidateObject.put("resume", resume);
            json.put(filed.getFile().getName(), candidateObject);
        }
        File file = new File(path + CommonUtils.DELIMITER + "Resume candidates list. Created by RecruiterVision [" + dateFormat.format(new Date()) + "].json");
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    logger.info("New .json file created");
                }
            }
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(json.toString());
            }
        } catch (IOException e) {
            logger.info("Error during creation of resume list in .json");
            return false;
        }
        logger.info("Data saved to candidates list file");
        Start.getScene().setCursor(Cursor.DEFAULT);
        return true;
    }

    private void addContent(JSONObject json, CV cv) {
        if (!SelectionRepository.noBaseData())
            addBase(json, cv);
        if (SelectionRepository.summary)
            json.put("summary", CommonUtils.getIfNullEmptyString(cv.getSummarySection()));
        if (!SelectionRepository.noExperience())
            addWorkExperience(json, cv);
        if (!SelectionRepository.noSkills())
            addSkills(json, cv);
        if (!SelectionRepository.noAccomplishments())
            addAccomplishments(json, cv);
        if (SelectionRepository.languages)
            json.put("languages", CommonUtils.getIfNullEmptyString(cv.getLanguagesSection()));
        if (SelectionRepository.education)
            json.put("education", CommonUtils.getIfNullEmptyString(cv.getEducationSection()));
        if (SelectionRepository.additionalInfo)
            json.put("additionalInfo", CommonUtils.getIfNullEmptyString(cv.getAdditionalInfo()));
        if (SelectionRepository.interests)
            json.put("interests", CommonUtils.getIfNullEmptyString(cv.getInterests()));
    }

    private void addBase(JSONObject json, CV cv) {
        JSONObject baseData = new JSONObject();
        if (SelectionRepository.nameSurname) {
            baseData.put("name", CommonUtils.getIfNullEmptyString(cv.getCandidateName()));
            baseData.put("middleName", CommonUtils.getIfNullEmptyString(cv.getCandidateMiddleName()));
            baseData.put("surname", CommonUtils.getIfNullEmptyString(cv.getCandidateSurname()));
        }
        if (SelectionRepository.gender)
            baseData.put("gender", CommonUtils.getIfNullEmptyString(cv.getGender()));
        if (SelectionRepository.email) {
            JSONArray emails = new JSONArray();
            fillJsonArray(emails, cv.getEmails());
            baseData.put("emails", emails);
        }
        if (SelectionRepository.cityCountry) {
            baseData.put("city", CommonUtils.getIfNullEmptyString(cv.getCity()));
            baseData.put("country", CommonUtils.getIfNullEmptyString(cv.getCountry()));
        }
        if (SelectionRepository.phones) {
            JSONArray phones = new JSONArray();
            fillJsonArray(phones, cv.getPhones());
            baseData.put("phones", phones);
        }
        if (SelectionRepository.jobTitles) {
            JSONArray jobTitles = new JSONArray();
            fillJsonArray(jobTitles, cv.getCandidateJobTitles());
            baseData.put("jobTitles", jobTitles);
        }
        if (SelectionRepository.urls) {
            JSONArray urls = new JSONArray();
            fillJsonArray(urls, cv.getURLs());
            baseData.put("urls", urls);
        }
        json.put("baseData", baseData);
    }

    private void addWorkExperience(JSONObject json, CV cv) {
        JSONObject experience = new JSONObject();
        if (SelectionRepository.experiencesTitles) {
            JSONArray titles = new JSONArray();
            fillJsonArray(titles, cv.getExperienceMain());
            experience.put("experienceTitles", titles);
        }
        if (SelectionRepository.experience) {
            JSONArray experienceContent = new JSONArray();
            fillJsonArray(experienceContent, cv.getExperienceText());
            experience.put("experienceContent", experienceContent);
        }
        json.put("experience", experience);
    }

    private void addSkills(JSONObject json, CV cv) {
        JSONObject skills = new JSONObject();
        if (SelectionRepository.skills) {
            JSONArray skillsContent = new JSONArray();
            fillJsonArray(skillsContent, cv.getSkillsSection());
            skills.put("skillsContent", skillsContent);
        }
        if (SelectionRepository.programmingLanguages) {
            JSONArray programmingLanguages = new JSONArray();
            fillJsonArray(programmingLanguages, cv.getProgrammingLanguages());
            skills.put("programmingLanguages", programmingLanguages);
        }
        if (SelectionRepository.programmingSkills) {
            JSONArray programingSkills = new JSONArray();
            fillJsonArray(programingSkills, cv.getProgrammingLanguages());
            skills.put("programingSkills", programingSkills);
        }
        json.put("skills", skills);
    }

    private void addAccomplishments(JSONObject json, CV cv) {
        JSONObject accomplishments = new JSONObject();
        if (SelectionRepository.accomplishments) {
            JSONArray accomplishmentsContent = new JSONArray();
            fillJsonArray(accomplishmentsContent, cv.getAccomplishments());
            accomplishments.put("accomplishmentsContent", accomplishmentsContent);
        }
        if (SelectionRepository.awards) {
            JSONArray awards = new JSONArray();
            fillJsonArray(awards, cv.getAwards());
            accomplishments.put("awards", awards);
        }
        if (SelectionRepository.credibility) {
            JSONArray credibility = new JSONArray();
            fillJsonArray(credibility, cv.getAccomplishments());
            accomplishments.put("credibility", credibility);
        }
        json.put("accomplishments", accomplishments);
    }


    private void fillJsonArray(JSONArray jsonArray, Iterable<String> arr) {
        if (arr != null && arr.iterator().hasNext()) {
            for (String element : arr) {
                jsonArray.put(element);
            }
        }
    }


}
