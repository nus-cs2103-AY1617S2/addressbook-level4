package project.taskcrusher.logic.parser;

import java.util.regex.Pattern;

import project.taskcrusher.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for tasks*/
    public static final Prefix PREFIX_DEADLINE = new Prefix("d/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");

    /* Prefix definitions for both tasks and events*/
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("//");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
