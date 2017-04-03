package seedu.onetwodo.logic.parser;

import java.util.regex.Pattern;

import seedu.onetwodo.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_START_DATE = new Prefix("s/");
    public static final Prefix PREFIX_END_DATE = new Prefix("e/");
    public static final Prefix PREFIX_RECUR = new Prefix("r/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_AFTER = PREFIX_START_DATE;
    public static final Prefix PREFIX_BEFORE = PREFIX_END_DATE;
    public static final Prefix PREFIX_ORDER = new Prefix("o/");
    public static final Prefix PREFIX_REVERSE = new Prefix("rev/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
    // or
    // more
    // keywords
    // separated
    // by
    // whitespace

}
