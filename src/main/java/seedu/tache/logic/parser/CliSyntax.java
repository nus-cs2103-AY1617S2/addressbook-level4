package seedu.tache.logic.parser;

import java.util.regex.Pattern;

import seedu.tache.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Parameter delimiter definitions */
    public static final String PARAMETER_DELIMITER = new String(";");
    public static final String EDIT_PARAMETER_DELIMITER = new String(" ");

    /* Parameter names definitions */
    public static final String NAME_PARAMETER = "name";
    public static final String START_DATE_PARAMETER = "start_date";
    public static final String END_DATE_PARAMETER = "end_date";
    public static final String START_TIME_PARAMETER = "start_time";
    public static final String END_TIME_PARAMETER = "end_time";
    public static final String TAG_PARAMETER = "tag";

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
