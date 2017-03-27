package seedu.doist.logic.parser;

import java.util.regex.Pattern;

import seedu.doist.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_FROM = new Prefix("\\from");
    public static final Prefix PREFIX_TO = new Prefix("\\to");
    public static final Prefix PREFIX_REMIND = new Prefix("\\remind_at");
    public static final Prefix PREFIX_EVERY = new Prefix("\\every");
    public static final Prefix PREFIX_AS = new Prefix("\\as");
    public static final Prefix PREFIX_UNDER = new Prefix("\\under");
    public static final Prefix PREFIX_BY = new Prefix("\\by");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("\\desc");
    public static final Prefix PREFIX_IN = new Prefix("\\in");
    public static final Prefix PREFIX_FOR = new Prefix("\\for");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    public static final Prefix[] ALL_PREFICES = {
        PREFIX_FROM,
        PREFIX_TO,
        PREFIX_REMIND,
        PREFIX_EVERY,
        PREFIX_AS,
        PREFIX_UNDER,
        PREFIX_BY,
        PREFIX_DESCRIPTION,
        PREFIX_IN,
        PREFIX_FOR
    };
}
