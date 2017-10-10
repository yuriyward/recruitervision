package vision.repository;

import javafx.collections.ObservableList;

/**
 * @author Yuriy on 09.10.2017.
 */
public class SelectionRepository {
    public static boolean nameSurname = true;
    public static boolean email = true;
    public static boolean jobTitles = true;
    public static boolean cityCountry = true;
    public static boolean urls = true;
    public static boolean gender = true;
    public static boolean phones = true;
    //
    public static boolean experiencesTitles = true;
    public static boolean experience = true;
    //
    public static boolean skills = true;
    public static boolean programmingLanguages = true;
    public static boolean programmingSkills = true;
    //
    public static boolean education = true;
    //
    public static boolean summary = true;
    //
    public static boolean languages = true;
    //
    public static boolean accomplishments = true;
    public static boolean awards = true;
    public static boolean credibility = true;
    //
    public static boolean interests = true;
    //
    public static boolean additionalInfo = true;
    //
    //
    public static ObservableList<String> advancedBase;
    public static ObservableList<String> advancedWork;
    public static ObservableList<String> advancedEducation;
    public static ObservableList<String> advancedSkills;
    public static ObservableList<String> advancedSummary;
    public static ObservableList<String> advancedInterests;
    public static ObservableList<String> advancedAccomplishments;
    public static ObservableList<String> advancedLanguages;
    public static ObservableList<String> advancedAdditionalInfo;
    //

    public static boolean noBaseData() {
        return !nameSurname && !email && !jobTitles && !cityCountry && !urls && !gender && !phones;
    }

    public static boolean noExperience() {
        return !experiencesTitles && !experience;
    }

    public static boolean noSkills() {
        return !skills && !programmingLanguages && !programmingSkills;
    }

    public static boolean noAccomplishments() {
        return !accomplishments && !awards && !credibility;
    }
}
