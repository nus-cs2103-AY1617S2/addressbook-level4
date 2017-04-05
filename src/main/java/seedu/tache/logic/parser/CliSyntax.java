package seedu.tache.logic.parser;

import java.util.regex.Pattern;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    //@@author A0139925U
    /* Parameter delimiter definitions */
    public static final String PARAMETER_DELIMITER = new String(";");
    public static final String INDEX_DELIMITER = new String(",");
    public static final String EDIT_PARAMETER_DELIMITER = new String(" ");

    /* Natural Language Processing definitions */
    public static final String EDIT_PARAMETER_KEYWORD = " change ";
    public static final String EDIT_PARAMETER_VALUE_KEYWORD = " to ";
    public static final String EDIT_MULTI_PARAMETER_KEYWORD = " and ";
    public static final String EDIT_MULTI_PARAMETER_KEYWORDS = " and change ";

    /* Parameter names definitions */
    public static final String NAME_PARAMETER = "name";
    public static final String NAME_PARAMETER_2 = "n";
    public static final String START_DATE_PARAMETER = "start_date";
    public static final String START_DATE_PARAMETER_2 = "startdate";
    public static final String START_DATE_PARAMETER_3 = "sd";
    public static final String END_DATE_PARAMETER = "end_date";
    public static final String END_DATE_PARAMETER_2 = "enddate";
    public static final String END_DATE_PARAMETER_3 = "ed";
    public static final String START_TIME_PARAMETER = "start_time";
    public static final String START_TIME_PARAMETER_2 = "starttime";
    public static final String START_TIME_PARAMETER_3 = "st";
    public static final String END_TIME_PARAMETER = "end_time";
    public static final String END_TIME_PARAMETER_2 = "endtime";
    public static final String END_TIME_PARAMETER_3 = "et";
    public static final String TAG_PARAMETER = "tag";
    public static final String TAG_PARAMETER_2 = "t";

    /* List filter definitions */
    public static final String COMPLETED_FILTER = "completed";
    public static final String UNCOMPLETED_FILTER = "uncompleted";
    public static final String ALL_FILTER = "all";
    //@@author A0142255M
    public static final String TIMED_FILTER = "timed";
    public static final String FLOATING_FILTER = "floating";
    //@@author
    //@@author A0139961U
    public static final String DUE_TODAY_FILTER = "today";
    public static final String DUE_THIS_WEEK_FILTER = "this week";
    public static final String OVERDUE_FILTER = "overdue";
    //@@author

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
