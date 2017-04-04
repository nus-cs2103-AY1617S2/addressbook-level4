package seedu.todolist.logic.parser;

import java.util.regex.Pattern;

import seedu.todolist.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_START_TIME = new Prefix("s/");
    public static final Prefix PREFIX_END_TIME = new Prefix("e/");
    public static final Prefix PREFIX_TAG_ADD = new Prefix("ta/");
    public static final Prefix PREFIX_COMPLETE_TIME = new Prefix("c/");
    public static final Prefix PREFIX_TODO_TYPE = new Prefix("type/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
