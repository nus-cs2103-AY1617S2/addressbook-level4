package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)");

    /* Add Command key words */
    public static final String WILDCARD = ".*";

    public static final String WITH_DEADLINE_ONLY = "\\bdue\\b";

    public static final String WITH_DEADLINE_AND_STARTING_TIME = "\\bfrom.*to\\b";

    public static final String WITH_STARTING_TIME = "\\bfrom\\b";
    public static final String WITH_DEADLINE = "\\bto\\b";

    public static final String WITH_TAGS = "\\btag\\b";
}
