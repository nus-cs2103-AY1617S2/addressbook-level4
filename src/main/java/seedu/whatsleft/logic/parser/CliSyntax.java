package seedu.whatsleft.logic.parser;

import java.util.regex.Pattern;

import seedu.whatsleft.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_STARTTIME = new Prefix("st/");
    public static final Prefix PREFIX_STARTDATE = new Prefix("sd/");
    public static final Prefix PREFIX_ENDTIME = new Prefix("et/");
    public static final Prefix PREFIX_ENDDATE = new Prefix("ed/");
    public static final Prefix PREFIX_BYTIME = new Prefix("bt/");
    public static final Prefix PREFIX_BYDATE = new Prefix("bd/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_TAG = new Prefix("ta/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
