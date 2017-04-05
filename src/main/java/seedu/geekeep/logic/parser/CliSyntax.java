package seedu.geekeep.logic.parser;

import seedu.geekeep.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_STARTING_DATETIME = new Prefix("s/");
    public static final Prefix PREFIX_ENDING_DATETIME = new Prefix("e/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    //@@author A0148037E
    public static final Prefix PREFIX_BEFORE_DATETIME = new Prefix("b/");
    public static final Prefix PREFIX_AFTER_DATETIME = new Prefix("a/");
    //@@author

}
