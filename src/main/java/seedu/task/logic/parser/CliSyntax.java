package seedu.task.logic.parser;

import java.util.regex.Pattern;

import seedu.task.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DUEDATE = new Prefix("due/");
    public static final Prefix PREFIX_END = new Prefix("ends/");
    public static final Prefix PREFIX_START = new Prefix("starts/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    /* List command definitions */
    //@@author A0163744B
    public static final String LIST_COMMAND_COMPLETE = "complete";
    public static final String LIST_COMMAND_INCOMPLETE = "incomplete";
    public static final String LIST_COMMAND_ADDED = "by added";
    public static final String LIST_COMMAND_DUE = "by due";
    public static final String LIST_COMMAND_START = "by starts";
    public static final String LIST_COMMAND_END = "by ends";
    //@@author
}
