package seedu.taskmanager.logic.parser;

import java.util.regex.Pattern;

import seedu.taskmanager.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_FROM = new Prefix("FROM");
    public static final Prefix PREFIX_TO = new Prefix("TO");
    public static final Prefix PREFIX_ON = new Prefix("ON");
    public static final Prefix PREFIX_BY = new Prefix("BY");
    public static final Prefix PREFIX_CATEGORY = new Prefix("CATEGORY");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
                                                                                                          // or
                                                                                                          // more
                                                                                                          // keywords
                                                                                                          // separated
                                                                                                          // by
                                                                                                          // whitespace
}
