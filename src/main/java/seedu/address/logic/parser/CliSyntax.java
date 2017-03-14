package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DEADLINE = new Prefix("BY ");
    public static final Prefix PREFIX_TIMEINTERVAL_END = new Prefix("TO ");
    public static final Prefix PREFIX_TIMEINTERVAL_START = new Prefix("FROM ");
    public static final Prefix PREFIX_LABEL = new Prefix("#");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
