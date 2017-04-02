package seedu.task.logic.parser;

import java.util.regex.Pattern;

import seedu.task.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix string definitions */
    public static final String PREFIX_STRING_DATE = "for:";
    public static final String PREFIX_STRING_PRIORITY = "priority:";
    public static final String PREFIX_STRING_INSTRUCTION = "note:";
    public static final String PREFIX_STRING_TAG = "#";

    /* Prefix definitions */
    public static final Prefix PREFIX_DATE = new Prefix(PREFIX_STRING_DATE);
    public static final Prefix PREFIX_PRIORITY = new Prefix(PREFIX_STRING_PRIORITY);
    public static final Prefix PREFIX_INSTRUCTION = new Prefix(PREFIX_STRING_INSTRUCTION);
    public static final Prefix PREFIX_TAG = new Prefix(PREFIX_STRING_TAG);

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
