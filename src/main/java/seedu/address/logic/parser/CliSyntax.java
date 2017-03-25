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
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern
            .compile("(?<keywords>\\S+(?:\\s+\\S+)*)");
    public static final String DATE = "^|\\s(\\d{1,2}/\\d{1,2}/\\d{2,4})(?=$|\\s)";

    /* Add Command key words */
    public static final String WILDCARD = ".*";
    public static final String END_OF_A_WORD = "\\b";
    public static final String END_OF_A_WORD_REVERSE = "b\\";

    public static final String DEADLINE_ONLY = "due";

    public static final String[] STARTINGTIME_AND_DEADLINE = { "from", "to" };
    public static final int INDEX_OF_STARTINGTIME = 1;
    public static final int INDEX_OF_DEADLINE = 0;
    public static final String STARTINGTIME_AND_DEADLINE_REVERSE_REGEX = "(?<deadlineReverse>.*)\\b+ot\\b+"
            + "(?<startingTimeReverse>.*)\\b+morf\\b+(?<rest>.*)";
    public static final String[] CAPTURE_GROUPS_OF_EVENT = { "deadlineReverse",
            "startingTimeReverse" };

    public static final String STARTING_TIME = "from";
    public static final String DEADLINE = "to";

    public static final String TAGS = "(?:^|\\s)(#\\S*)";

    public static final String DEFAULT_DEADLINE = " at 2359";
    public static final String DEFAULT_STARTING_TIME = " at 0000";

    /* Find Command Keywords */
    public static final String FIND_NAME = "title";
    public static final String FIND_DEADLINE = "due";
    public static final String FIND_PERIOD = "during";
    public static final String FIND_TAG = "tag";
}
