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
    public static final String[] PARAMETER_NAME = {"name", "n"};
    public static final String[] PARAMETER_START_DATE = {"start_date", "startdate", "sd"};
    public static final String[] PARAMETER_END_DATE = {"end_date", "enddate", "ed"};
    public static final String[] PARAMETER_START_TIME = {"start_time", "starttime", "st"};
    public static final String[] PARAMETER_END_TIME = {"end_time", "endtime", "et"};
    public static final String[] PARAMETER_TAG = {"tag", "t"};

    /* List filter definitions */
    public static final String FILTER_COMPLETED = "completed";
    public static final String FILTER_UNCOMPLETED = "uncompleted";
    public static final String FILTER_ALL = "all";
    //@@author A0142255M
    public static final String FILTER_TIMED = "timed";
    public static final String FILTER_FLOATING = "floating";
    //@@author
    //@@author A0139961U
    public static final String FILTER_DUE_TODAY = "today";
    public static final String FILTER_DUE_THIS_WEEK = "this week";
    public static final String FILTER_OVERDUE = "overdue";
    //@@author

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
