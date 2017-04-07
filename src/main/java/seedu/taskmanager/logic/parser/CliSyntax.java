package seedu.taskmanager.logic.parser;

import java.util.regex.Pattern;

import seedu.taskmanager.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_STARTDATE = new Prefix("s/");
    public static final Prefix PREFIX_ENDDATE = new Prefix("e/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    // @@author A0140032E
    public static final Prefix PREFIX_REPEAT = new Prefix("r/");
    // @@author
    public static final Prefix PREFIX_TAG = new Prefix("#");

    /* Alternative prefix definitions for natural variations of user input*/
//    public static final Prefix ALTERNATIVE_PREFIX_STARTDATE = new Prefix("start on ");
//    public static final Prefix ALTERNATIVE_PREFIX_ENDDATE = new Prefix("end on ");
//    public static final Prefix ALTERNATIVE_PREFIX_DESCRIPTION = new Prefix("with description ");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    // File Path allows numbers, "/.-", space, lowercase and uppercase letters
    public static final Pattern FILEPATH_ARGS_FORMAT = Pattern.compile("([ 0-9a-zA-Z/_.-])+");
}
