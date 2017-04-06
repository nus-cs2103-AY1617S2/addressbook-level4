package seedu.bulletjournal.logic.parser;

import java.util.regex.Pattern;

import seedu.bulletjournal.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    //@@author A0105748B
    /* Prefix definitions */
    public static final Prefix PREFIX_DEADLINE = new Prefix("d/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_BEGINTIME = new Prefix("b/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    //@@author A0105748B
    /* Patterns definition for show command */
    public static final Pattern KEYWORDS_SHOW_FORMAT =
            Pattern.compile("(done|undone)", Pattern.CASE_INSENSITIVE);

    /* Patterns definition for finish command args */
    public static final Pattern FINISH_ARGS_FORMAT =
            Pattern.compile("^\\d+$");
}
