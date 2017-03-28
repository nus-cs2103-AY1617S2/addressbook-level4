package seedu.taskboss.logic.parser;

import java.util.regex.Pattern;

import seedu.taskboss.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");

    //@@author A0144904H
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");

    //@@author
    public static final Prefix PREFIX_START_DATE = new Prefix("sd/");
    public static final Prefix PREFIX_END_DATE = new Prefix("ed/");
    public static final Prefix PREFIX_INFORMATION = new Prefix("i/");
    public static final Prefix PREFIX_RECURRENCE = new Prefix("r/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_KEYWORD = new Prefix("k/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
