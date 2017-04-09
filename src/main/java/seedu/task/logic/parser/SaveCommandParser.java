package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SaveCommand;

//@@author A0141928B
/**
 * Parses input argument and changes the save location
 */
public class SaveCommandParser {

    public static final String XML_EXTENSION = ".xml";

    /**
     * Parses the given argument in the context of the SaveCommand and
     * returns a SaveCommand object for execution
     */
    public Command parse(String args) {
        assert args != null;

        try {
            Paths.get(args); //Checks if path is valid
        } catch (NullPointerException npe) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveCommand.MESSAGE_USAGE));
        } catch (InvalidPathException ipe) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveCommand.MESSAGE_INVALID_FILE_PATH));
        }

        if (!args.endsWith(XML_EXTENSION)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveCommand.MESSAGE_INVALID_FILE_TYPE));
        }

        return new SaveCommand(args);
    }

}
