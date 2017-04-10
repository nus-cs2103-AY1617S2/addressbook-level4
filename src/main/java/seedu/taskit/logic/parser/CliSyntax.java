package seedu.taskit.logic.parser;

import java.util.regex.Pattern;

import seedu.taskit.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TAG = new Prefix(" tag ");
    public static final Prefix PREFIX_BY = new Prefix(" by ");
    public static final Prefix PREFIX_FROM = new Prefix(" from ");
    public static final Prefix PREFIX_TO = new Prefix(" to ");
    public static final Prefix PREFIX_PRIORITY = new Prefix(" priority ");
    public static final Prefix PREFIX_TITLE = new Prefix(" title ");

    public static final String ALL = "all";
    public static final String DEADLINE = "deadline";
    public static final String FLOATING = "floating";
    public static final String EVENT = "event";
    public static final String TODAY = "today";
    public static final String OVERDUE = "overdue";
    public static final String PRIORITY_LOW = "low";
    public static final String PRIORITY_MEDIUM = "medium";
    public static final String PRIORITY_HIGH = "high";
    public static final String DONE = "done";
    public static final String UNDONE = "undone";

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
