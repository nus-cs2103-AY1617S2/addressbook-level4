package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.ExportCommand;
import seedu.onetwodo.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new Export object
 */
public class ExportCommandParser extends FileTransferCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the Export Command
     * and returns an ExportCommand object for execution.
     */
    public Command parse(String argument) {
        assert argument != null;
        String args = argument.trim().toLowerCase();
        ExportCommand command;
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportCommand.MESSAGE_USAGE));
        }

        // check if the number of word in the input is correct
        String[] argArray = args.split(PATH_SPLIT_REGEX);
        int argSize = argArray.length;
        if (argSize != SIZE_ONE && argSize != SIZE_TWO) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportCommand.MESSAGE_USAGE));
        }

        // check if overwrite exist in the string
        String overwriteWord, pathInput;
        if (argSize == SIZE_ONE) {
            pathInput = argArray[INDEX_ZERO];
            command = new ExportCommand(pathInput);
            command.setIsOverWriting(false);
        } else {    // has 2 arguments
            pathInput = argArray[INDEX_ONE];
            overwriteWord = argArray[INDEX_ZERO];
            command = new ExportCommand(pathInput);
            if (!isOverWrittingFormat(overwriteWord)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportCommand.MESSAGE_USAGE));
            } else {
                command.setIsOverWriting(true);
            }
        }

        // checks if path is valid
        if (!isValidPath(pathInput)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportCommand.MESSAGE_USAGE));
        } else {
            return command;
        }
    }

}
