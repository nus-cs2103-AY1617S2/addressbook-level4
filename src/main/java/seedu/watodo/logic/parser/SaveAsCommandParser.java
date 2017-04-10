package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.logic.commands.SaveAsCommand;

//@@author A0141077L
/**
 * Parses input arguments and creates a new SaveAsCommand object
 */
public class SaveAsCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveAsCommand
     * and returns a SaveAsCommand object for execution.
     */
    public Command parse(String newFilePath) {
        newFilePath.trim();

        try {
            checkFilePathExists(newFilePath);
            checkCorrectFileFormat(newFilePath);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        return new SaveAsCommand(newFilePath);
    }

    private void checkFilePathExists(String newFilePath) throws IllegalValueException {
        if (newFilePath.isEmpty()) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveAsCommand.MESSAGE_USAGE));
        }
    }

    private void checkCorrectFileFormat(String newFilePath) throws IllegalValueException {
        if (!newFilePath.endsWith(".xml")) {
            throw new IllegalValueException(SaveAsCommand.MESSAGE_INVALID_FILE_PATH_EXTENSION);
        }
    }

}
