package savvytodo.logic.parser;

import java.util.regex.Pattern;

import savvytodo.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public interface CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_DATE_TIME = new Prefix("dt/");
    public static final Prefix PREFIX_RECURRENCE = new Prefix("r/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
