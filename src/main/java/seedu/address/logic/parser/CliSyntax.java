package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    // TODO don't think we need the prefixes anymore
    //public static final Prefix PREFIX_START_DATETIME = new Prefix("from ");
    //public static final Prefix PREFIX_END_DATETIME = new Prefix("to "); // TODO conflicts with tomorrow so space added
    //public static final Prefix PREFIX_DEADLINE_DATETIME = new Prefix("by ");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
