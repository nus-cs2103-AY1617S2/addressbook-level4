package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_BOOK_DATE = new Prefix("on ");
    public static final Prefix PREFIX_BOOK_DATE_DELIMITER = new Prefix(",");
    public static final Prefix PREFIX_DEADLINE = new Prefix("by ");
    public static final Prefix PREFIX_TIMEINTERVAL_END = new Prefix("to ");
    public static final Prefix PREFIX_TIMEINTERVAL_START = new Prefix("from ");
    public static final Prefix PREFIX_LABEL = new Prefix("#");
    public static final Prefix PREFIX_STATUS_COMPLETED = new Prefix("completed");
    public static final Prefix PREFIX_STATUS_INCOMPLETE = new Prefix("incomplete");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
