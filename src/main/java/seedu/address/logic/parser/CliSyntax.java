package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_BOOK_ADD_DATE = new Prefix("add");
    public static final Prefix PREFIX_BOOK_CHANGE_DATE = new Prefix("change");
    public static final Prefix PREFIX_BOOK_DATE = new Prefix(" on ");
    public static final Prefix PREFIX_BOOK_DATE_DELIMITER = new Prefix(",");
    public static final Prefix PREFIX_BOOK_REMOVE_DATE = new Prefix("remove");
    public static final Prefix PREFIX_DEADLINE = new Prefix("by ");
    public static final Prefix PREFIX_TIMEINTERVAL_END = new Prefix("to ");
    public static final Prefix PREFIX_TIMEINTERVAL_START = new Prefix("from ");
    public static final Prefix PREFIX_LABEL = new Prefix("#");
    public static final Prefix PREFIX_STATUS_COMPLETED = new Prefix("completed");
    public static final Prefix PREFIX_STATUS_INCOMPLETE = new Prefix("incomplete");
    public static final Prefix PREFIX_CLEAR_DATES = new Prefix("clear dates");
    public static final Prefix PREFIX_RECURRENCE = new Prefix("repeat every ");
    public static final Prefix PREFIX_REMOVE_RECURRENCE = new Prefix("stop repeat");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
