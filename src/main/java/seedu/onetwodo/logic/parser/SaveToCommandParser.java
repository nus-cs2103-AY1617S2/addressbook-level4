package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.IncorrectCommand;
import seedu.onetwodo.logic.commands.SaveToCommand;

//@@author A0139343E
/**
 * Parses input arguments and creates a new SaveTo object
 */
public class SaveToCommandParser extends FileTransferCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveToCommand
     * and returns an SaveToCommand object for execution.
     */
    public Command parse(String argument) {
        assert argument != null;
        String args = argument.trim().toLowerCase();
        SaveToCommand command;
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        }

        // check if the number of word in the input is correct
        String[] argArray = args.split(PATH_SPLIT_REGEX);
        int argSize = argArray.length;
        if (argSize != SIZE_ONE && argSize != SIZE_TWO) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        }

        // check if overwrite exist in the string
        String overwriteWord, pathInput;
        if (argSize == SIZE_ONE) {
            pathInput = argArray[INDEX_ZERO];
            command = new SaveToCommand(pathInput);
            command.setIsOverWriting(false);
        } else {    // has 2 arguments
            pathInput = argArray[INDEX_ONE];
            overwriteWord = argArray[INDEX_ZERO];
            command = new SaveToCommand(pathInput);
            if (!isOverWrittingFormat(overwriteWord)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SaveToCommand.MESSAGE_USAGE));
            } else {
                command.setIsOverWriting(true);
            }
        }

        // checks if path is valid
        if (!isValidPath(pathInput)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else {
            return command;
        }
    }

}
