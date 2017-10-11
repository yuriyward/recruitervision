package vision.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.Cursor;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yuriy on 08.10.2017.
 */
@Service
public class PDFCreator {
    private final static Font fileNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.ORANGE);
    private final static Font sectionNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
    private final static Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK);
    private final static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC);
    private final static Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    private final static Logger logger = LoggerFactory.getLogger(PDFCreator.class);

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");

    private final FiledRepository filedRepository;

    @Autowired
    public PDFCreator(FiledRepository filedRepository) {
        this.filedRepository = filedRepository;
    }

    public boolean createDocument(String path) {
        try {
            Start.getScene().setCursor(Cursor.WAIT);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(path + CommonUtils.DELIMITER + "Resume candidates list. Created by RecruiterVision [" + dateFormat.format(new Date()) + "].pdf"));
            document.open();
            addMetaData(document);
            addContent(document);
            document.close();
            logger.info("Data saved to candidates list file");
            Start.getScene().setCursor(Cursor.DEFAULT);
            return true;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addMetaData(Document document) {
        document.addTitle("Resume candidates list");
        document.addSubject("Resume candidate list was created automatically by RecruiterVision powered by Yuriy Babyak");
        document.addAuthor("RecruiterVision powered by Yuriy Babyak");
        document.addCreator("RecruiterVision powered by Yuriy Babyak");
    }

    private void addContent(Document document) {
        try {
            PdfPTable table = new PdfPTable(2);
            table.setWidths(new int[]{2, 6});
            table.setWidthPercentage(100);
            Paragraph paragraph = new Paragraph("Candidates list created by RecruiterVision [powered by Yuriy Babyak] " + dateFormat.format(new Date()), paragraphFont);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);
            addContent(table);
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addContent(PdfPTable table) {
        if (!SelectionRepository.noBaseData())
            addBaseData(table);
        if (SelectionRepository.summary)
            addSummary(table);
        if (!SelectionRepository.noExperience())
            addWorkExperience(table);
        if (!SelectionRepository.noSkills())
            addSkills(table);
        if (!SelectionRepository.noAccomplishments())
            addAccomplishments(table);
        if (SelectionRepository.languages)
            addLanguages(table);
        if (SelectionRepository.education)
            addEducation(table);
        if (SelectionRepository.additionalInfo)
            addAdditionalInfo(table);
        if (SelectionRepository.interests)
            addInterests(table);
    }

    private void addSectionName(PdfPTable table, String sectionName) {
        PdfPCell cell = new PdfPCell(new Phrase(sectionName, sectionNameFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
    }

    private void addSubsection(PdfPTable table, String subSectionName) {
        table.addCell("");
        table.addCell(new PdfPCell(new Phrase(subSectionName, headerFont)));
    }

    private void addFileWithSectionValue(PdfPTable table, String fileName, String content) {
        PdfPCell cell = new PdfPCell(new Phrase(fileName, fileNameFont));
        cell.setPaddingBottom(5f);
        table.addCell(cell);
        PdfPCell cell2 = new PdfPCell(new Phrase(content, contentFont));
        cell2.setPaddingBottom(5f);
        table.addCell(cell2);
    }

    private void addFileWithSectionValue(PdfPTable table, String fileName, String content, boolean highlighted) {
        PdfPCell cell = new PdfPCell(new Phrase(fileName, fileNameFont));
        cell.setPaddingBottom(5f);
        table.addCell(cell);
        PdfPCell cell2 = new PdfPCell(new Phrase(content, contentFont));
        cell2.setPaddingBottom(5f);
        cell2.setBackgroundColor(BaseColor.YELLOW);
        table.addCell(cell2);
    }

    private void addBaseData(PdfPTable table) {
        addSectionName(table, "Base data");
        if (SelectionRepository.nameSurname)
            addNameMiddleSurname(table);
        if (SelectionRepository.gender)
            addGender(table);
        if (SelectionRepository.email)
            addEmail(table);
        if (SelectionRepository.cityCountry)
            addCityAndCountry(table);
        if (SelectionRepository.phones)
            addPhones(table);
        if (SelectionRepository.jobTitles)
            addJobTitles(table);
        if (SelectionRepository.urls)
            addUrls(table);
    }

    private void addSummary(PdfPTable table) {
        addSectionName(table, "Summary");
        addSubsection(table, "Content");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getSummarySection());
            containsSummaryAndAdd(table, filed, value);
        }
    }

    private void addWorkExperience(PdfPTable table) {
        addSectionName(table, "Work experience");
        if (SelectionRepository.experiencesTitles)
            addTitles(table);
        if (SelectionRepository.experience)
            addWorkExperienceContent(table);
    }

    private void addSkills(PdfPTable table) {
        addSectionName(table, "Skills");
        if (SelectionRepository.skills)
            addSkillsContent(table);
        if (SelectionRepository.programmingLanguages)
            addProgramingLanguages(table);
        if (SelectionRepository.programmingSkills)
            addProgrammingSkills(table);
    }

    private void addAccomplishments(PdfPTable table) {
        addSectionName(table, "Accomplishments");
        if (SelectionRepository.accomplishments)
            addAccomplishmentsContent(table);
        if (SelectionRepository.awards)
            addAwards(table);
        if (SelectionRepository.credibility)
            addCredibility(table);
    }

    private void addLanguages(PdfPTable table) {
        addSectionName(table, "Languages");
        addSubsection(table, "Content");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getLanguagesSection());
            containsLanguagesAndAdd(table, filed, value);
        }
    }

    private void addEducation(PdfPTable table) {
        addSectionName(table, "Education");
        addSubsection(table, "Content");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getEducationSection());
            containsEducationAndAdd(table, filed, value);
        }
    }

    private void addAdditionalInfo(PdfPTable table) {
        addSectionName(table, "Additional info");
        addSubsection(table, "Content");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getAdditionalInfo());
            containsAdditionalInfoAndAdd(table, filed, value);
        }
    }

    private void addInterests(PdfPTable table) {
        addSectionName(table, "Interests");
        addSubsection(table, "Content");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getInterests());
            containsInterestsAndAdd(table, filed, value);
        }
    }

    private void addNameMiddleSurname(PdfPTable table) {
        addSubsection(table, "Name + Middle name + Surname");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getCandidateName()) + " " + CommonUtils.getIfNullEmptyString(cv.getCandidateMiddleName()) + " " + CommonUtils.getIfNullEmptyString(cv.getCandidateSurname());
            containsBaseDataAndAdd(table, filed, value);
        }
    }

    private void addGender(PdfPTable table) {
        addSubsection(table, "Gender");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getGender());
            containsBaseDataAndAdd(table, filed, value);
        }
    }

    private void addEmail(PdfPTable table) {
        addSubsection(table, "Email");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getEmails());
            containsBaseDataAndAdd(table, filed, value);
        }
    }

    private void addCityAndCountry(PdfPTable table) {
        addSubsection(table, "City and country");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getCity()) + " " + CommonUtils.getIfNullEmptyString(cv.getCountry());
            containsBaseDataAndAdd(table, filed, value);
        }
    }

    private void addPhones(PdfPTable table) {
        addSubsection(table, "Phones");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getPhones());
            containsBaseDataAndAdd(table, filed, value);
        }
    }

    private void addJobTitles(PdfPTable table) {
        addSubsection(table, "Job titles");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getCandidateJobTitles());
            containsBaseDataAndAdd(table, filed, value);
        }
    }

    private void addUrls(PdfPTable table) {
        addSubsection(table, "URLs");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getURLs());
            containsBaseDataAndAdd(table, filed, value);
        }
    }

    private void addTitles(PdfPTable table) {
        addSubsection(table, "Titles");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getExperienceMain());
            containsWorkExperienceAndAdd(table, filed, value);
        }
    }

    private void addWorkExperienceContent(PdfPTable table) {
        addSubsection(table, "Content");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getExperienceText());
            containsWorkExperienceAndAdd(table, filed, value);
        }
    }

    private void addSkillsContent(PdfPTable table) {
        addSubsection(table, "Content");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getSkillsSection());
            containsSkillsAndAdd(table, filed, value);
        }
    }

    private void addProgramingLanguages(PdfPTable table) {
        addSubsection(table, "Programming languages");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getProgrammingLanguages());
            containsSkillsAndAdd(table, filed, value);
        }
    }

    private void addProgrammingSkills(PdfPTable table) {
        addSubsection(table, "Programming skills");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getProgrammingSkills());
            containsSkillsAndAdd(table, filed, value);
        }
    }

    private void addAccomplishmentsContent(PdfPTable table) {
        addSubsection(table, "Content");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getAccomplishments());
            containsAccomplishmentsAndAdd(table, filed, value);
        }
    }

    private void addAwards(PdfPTable table) {
        addSubsection(table, "Awards");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getAwards());
            containsAccomplishmentsAndAdd(table, filed, value);
        }
    }

    private void addCredibility(PdfPTable table) {
        addSubsection(table, "Credibility");
        for (Filed filed : filedRepository.getFiledList()) {
            CV cv = filed.getExtractedData();
            String value = CommonUtils.getIfNullEmptyString(cv.getCredibility());
            containsAccomplishmentsAndAdd(table, filed, value);
        }
    }

    private void containsBaseDataAndAdd(PdfPTable table, Filed filed, String value) {
        boolean contains = false;
        if (SelectionRepository.advancedBase != null)
            for (String val : SelectionRepository.advancedBase) {
                if (value.contains(val)) {
                    contains = true;
                }
            }
        if (contains) {
            addFileWithSectionValue(table, filed.getFile().getName(), value, true);
        } else {
            addFileWithSectionValue(table, filed.getFile().getName(), value);
        }
    }

    private void containsSummaryAndAdd(PdfPTable table, Filed filed, String value) {
        boolean contains = false;
        if (SelectionRepository.advancedSummary != null)
            for (String val : SelectionRepository.advancedSummary) {
                if (value.contains(val)) {
                    contains = true;
                }
            }
        if (contains) {
            addFileWithSectionValue(table, filed.getFile().getName(), value, true);
        } else {
            addFileWithSectionValue(table, filed.getFile().getName(), value);
        }
    }

    private void containsWorkExperienceAndAdd(PdfPTable table, Filed filed, String value) {
        boolean contains = false;
        if (SelectionRepository.advancedWork != null)
            for (String val : SelectionRepository.advancedWork) {
                if (value.contains(val)) {
                    contains = true;
                }
            }
        if (contains) {
            addFileWithSectionValue(table, filed.getFile().getName(), value, true);
        } else {
            addFileWithSectionValue(table, filed.getFile().getName(), value);
        }
    }

    private void containsSkillsAndAdd(PdfPTable table, Filed filed, String value) {
        boolean contains = false;
        if (SelectionRepository.advancedSkills != null)
            for (String val : SelectionRepository.advancedSkills) {
                if (value.contains(val)) {
                    contains = true;
                }
            }
        if (contains) {
            addFileWithSectionValue(table, filed.getFile().getName(), value, true);
        } else {
            addFileWithSectionValue(table, filed.getFile().getName(), value);
        }
    }

    private void containsAccomplishmentsAndAdd(PdfPTable table, Filed filed, String value) {
        boolean contains = false;
        if (SelectionRepository.advancedAccomplishments != null)
            for (String val : SelectionRepository.advancedAccomplishments) {
                if (value.contains(val)) {
                    contains = true;
                }
            }
        if (contains) {
            addFileWithSectionValue(table, filed.getFile().getName(), value, true);
        } else {
            addFileWithSectionValue(table, filed.getFile().getName(), value);
        }
    }

    private void containsLanguagesAndAdd(PdfPTable table, Filed filed, String value) {
        boolean contains = false;
        if (SelectionRepository.advancedLanguages != null)
            for (String val : SelectionRepository.advancedLanguages) {
                if (value.contains(val)) {
                    contains = true;
                }
            }
        if (contains) {
            addFileWithSectionValue(table, filed.getFile().getName(), value, true);
        } else {
            addFileWithSectionValue(table, filed.getFile().getName(), value);
        }
    }

    private void containsEducationAndAdd(PdfPTable table, Filed filed, String value) {
        boolean contains = false;
        if (SelectionRepository.advancedEducation != null)
            for (String val : SelectionRepository.advancedEducation) {
                if (value.contains(val)) {
                    contains = true;
                }
            }
        if (contains) {
            addFileWithSectionValue(table, filed.getFile().getName(), value, true);
        } else {
            addFileWithSectionValue(table, filed.getFile().getName(), value);
        }
    }

    private void containsAdditionalInfoAndAdd(PdfPTable table, Filed filed, String value) {
        boolean contains = false;
        if (SelectionRepository.advancedAdditionalInfo != null)
            for (String val : SelectionRepository.advancedAdditionalInfo) {
                if (value.contains(val)) {
                    contains = true;
                }
            }
        if (contains) {
            addFileWithSectionValue(table, filed.getFile().getName(), value, true);
        } else {
            addFileWithSectionValue(table, filed.getFile().getName(), value);
        }
    }

    private void containsInterestsAndAdd(PdfPTable table, Filed filed, String value) {
        boolean contains = false;
        if (SelectionRepository.advancedInterests != null)
            for (String val : SelectionRepository.advancedInterests) {
                if (value.contains(val)) {
                    contains = true;
                }
            }
        if (contains) {
            addFileWithSectionValue(table, filed.getFile().getName(), value, true);
        } else {
            addFileWithSectionValue(table, filed.getFile().getName(), value);
        }
    }
}
