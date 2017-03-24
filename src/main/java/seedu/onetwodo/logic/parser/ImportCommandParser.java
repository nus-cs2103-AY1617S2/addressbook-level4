package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.ImportCommand;
import seedu.onetwodo.logic.commands.IncorrectCommand;

//@@author A0139343E
/**
 * Parses input arguments and creates a new Import object
 */
public class ImportCommandParser extends FileTransferCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the Import Command
     * and returns an ImportCommand object for execution.
     */
    public Command parse(String argument) {
        assert argument != null;
        String args = argument.trim().toLowerCase();
        ImportCommand command;
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ImportCommand.MESSAGE_USAGE));
        }

        // check if the number of word in the input is correct
        String[] argArray = args.split(PATH_SPLIT_REGEX);
        int argSize = argArray.length;
        if (argSize != SIZE_ONE) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ImportCommand.MESSAGE_USAGE));
        }

        // check if overwrite exist in the string
        String pathInput;
        pathInput = argArray[INDEX_ZERO];
        command = new ImportCommand(pathInput);

        // checks if path is valid
        if (!isValidPath(pathInput)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ImportCommand.MESSAGE_USAGE));
        } else {
            return command;
        }
    }

}
