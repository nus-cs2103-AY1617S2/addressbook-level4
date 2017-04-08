package project.taskcrusher.logic.parser;

import java.util.regex.Pattern;

import project.taskcrusher.logic.parser.ArgumentTokenizer.Prefix;

//@@author A0163962X
/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for tasks*/
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");

    /* Prefix definitions for events*/
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");

    /* Prefix definitions for both tasks and events*/
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("//");
    public static final Prefix PREFIX_OPTION = new Prefix("--");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
