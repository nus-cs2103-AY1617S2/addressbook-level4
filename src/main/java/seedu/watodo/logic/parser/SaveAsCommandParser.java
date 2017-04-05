package seedu.watodo.logic.parser;

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
        if (!checkCorrectFileExtension(newFilePath)) {
            return new IncorrectCommand(SaveAsCommand.MESSAGE_INVALID_FILE_PATH_EXTENSION);
        }
        return new SaveAsCommand(newFilePath);
    }

    private boolean checkCorrectFileExtension(String newFilePath) {
        return (newFilePath.endsWith(".xml")) ? true : false;
    }

}
