package typetask.logic.parser;

import java.util.regex.Pattern;

import typetask.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_DATE = new Prefix("by:");
    public static final Prefix PREFIX_TIME = new Prefix("@");
    public static final Prefix PREFIX_START_DATE = new Prefix("from:");
    public static final Prefix PREFIX_END_DATE = new Prefix("to:");
    public static final Prefix PREFIX_START_TIME = new Prefix("t/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
