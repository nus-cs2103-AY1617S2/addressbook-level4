package seedu.ezdo.logic.parser;

import seedu.ezdo.logic.commands.Command;

/**
 * An interface for the command parsers in ezDo.
 */
public interface CommandParser {
    /** Parses the given string */
    Command parse(String args);
}
