package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_BY= new Prefix("by");
    public static final Prefix PREFIX_FROM= new Prefix("from");
    public static final Prefix PREFIX_TO= new Prefix("to");
    
    public static final String FIELDWORD_TITLE= new String("title");
    public static final String FIELDWORD_TAG= new String("tag");
    public static final String FIELDWORD_START= new String("start");
    public static final String FIELDWORD_END= new String("end");
    public static final String FIELDWORD_DEADLINE= new String("deadline");
    
    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
