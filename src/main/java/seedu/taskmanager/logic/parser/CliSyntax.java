package seedu.taskmanager.logic.parser;

import java.util.regex.Pattern;

import seedu.taskmanager.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_STARTDATE = new Prefix("p/");
    public static final Prefix PREFIX_ENDDATE = new Prefix("e/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
