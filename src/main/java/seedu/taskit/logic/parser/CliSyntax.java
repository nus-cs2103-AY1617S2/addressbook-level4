package seedu.taskit.logic.parser;

import java.util.regex.Pattern;

import seedu.taskit.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TAG = new Prefix(" tag ");
    public static final Prefix PREFIX_BY= new Prefix(" by ");
    public static final Prefix PREFIX_FROM= new Prefix(" from ");
    public static final Prefix PREFIX_TO= new Prefix(" to ");
    public static final Prefix PREFIX_PRIORITY= new Prefix(" priority ");
    public static final Prefix PREFIX_TITLE= new Prefix("title");
    public static final Prefix PREFIX_START= new Prefix("start");
    public static final Prefix PREFIX_END= new Prefix("end");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
