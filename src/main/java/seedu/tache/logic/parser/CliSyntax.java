package seedu.tache.logic.parser;

import java.util.regex.Pattern;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    //@@author A0139925U
    /* Parameter delimiter definitions */
    public static final String DELIMITER_PARAMETER = new String(";");
    public static final String DELIMITER_INDEX = new String(",");
    public static final String DELIMITER_EDIT_PARAMETER = new String(" ");

    /* Natural Language Processing definitions */
    public static final String KEYWORD_EDIT_PARAMETER = " change ";
    public static final String KEYWORD_EDIT_PARAMETER_VALUE = " to ";
    public static final String KEYWORD_EDIT_MULTI_PARAMETER = " and ";
    public static final String KEYWORDS_EDIT_MULTI_PARAMETER = " and change ";

    /* Parameter names definitions */
    public static final String PARAMETER_NAME = "name";
    public static final String PARAMETER_2_NAME = "n";
    public static final String PARAMETER_START_DATE = "start_date";
    public static final String PARAMETER_2_START_DATE = "startdate";
    public static final String PARAMETER_3_START_DATE = "sd";
    public static final String PARAMETER_END_DATE = "end_date";
    public static final String PARAMETER_2_END_DATE = "enddate";
    public static final String PARAMETER_3_END_DATE = "ed";
    public static final String PARAMETER_START_TIME = "start_time";
    public static final String PARAMETER_2_START_TIME = "starttime";
    public static final String PARAMETER_3_START_TIME = "st";
    public static final String PARAMETER_END_TIME = "end_time";
    public static final String PARAMETER_2_END_TIME = "endtime";
    public static final String PARAMETER_3_END_TIME = "et";
    public static final String PARAMETER_TAG = "tag";
    public static final String PARAMETER_2_TAG = "t";

    /* List filter definitions */
    public static final String FILTER_COMPLETED = "completed";
    public static final String FILTER_UNCOMPLETED = "uncompleted";
    public static final String FILTER_ALL = "all";
    //@@author A0142255M
    public static final String FILTER_TIMED = "timed";
    public static final String FILTER_FLOATING = "floating";
    //@@author A0139961U
    public static final String FILTER_DUE_TODAY = "today";
    public static final String FILTER_DUE_THIS_WEEK = "this week";
    public static final String FILTER_OVERDUE = "overdue";

    //@@author A0142255M
    /* Calendar show view options */
    public static final String VIEW_DAY = "day";
    public static final String VIEW_WEEK = "week";
    public static final String VIEW_MONTH = "month";
    //@@author

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
