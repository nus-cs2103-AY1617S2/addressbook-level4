package werkbook.task.logic.parser;

import java.util.regex.Pattern;

import werkbook.task.logic.parser.ArgumentTokenizer.Prefix;

//@@author A0139903B
/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_STARTDATETIME = new Prefix("from", true, true);
    public static final Prefix PREFIX_ENDDATETIME = new Prefix("to", true, false);
    public static final Prefix PREFIX_DEADLINE = new Prefix("by", true, false);
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
