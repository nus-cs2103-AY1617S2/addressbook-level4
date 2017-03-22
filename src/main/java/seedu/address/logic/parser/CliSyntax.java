package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_VENUE = new Prefix("/venue");
    public static final Prefix PREFIX_STARTTIME = new Prefix("/from:");
    public static final Prefix PREFIX_ENDTIME = new Prefix("/to:");
    public static final Prefix PREFIX_TAG = new Prefix("#");
    public static final Prefix PREFIX_URGENCYLEVEL = new Prefix("/level");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("/description");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
