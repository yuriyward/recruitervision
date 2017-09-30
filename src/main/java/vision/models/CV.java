package vision.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;

/**
 * @author Yuriy on 25.08.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CV {
    private String candidateName;
    private String candidateMiddleName;
    private String candidateSurname;
    private String gender;
    private HashSet<String> emails;
    private List<String> addresses;
    private HashSet<String> phones;
    private String city;
    private String country;
    private List<String> candidateJobTitles;
    private HashSet<String> URLs;
    private List<String> sectionNames;
    private String summarySection;
    private List<String> skillsSection;
    private List<String> educationSection;
    private List<String> experienceMain;
    private List<String> experienceText;
    private String languagesSection;
    private List<String> interests;
    private List<String> additionalInfo;
    private List<String> credibility;
    private List<String> awards;
    private List<String> accomplishments;
    private HashSet<String> programmingLanguages;
    private HashSet<String> programmingSkills;


}
