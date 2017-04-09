package seedu.doist.logic.parser;

import java.io.File;
import java.io.IOException;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.SaveAtCommand;

//@@author A0140887W
/**
 * Parses input arguments and creates a new SaveAtCommand object
 */
public class SaveAtCommandParser {
    public Command parse(String argument) {
        // Remove trailing whitespace
        String processedArgument = argument.trim();
        // remove all leading spaces, new line characters etc
        processedArgument = processedArgument.replaceAll("^\\s+", "");
        // cannot be empty path
        if (processedArgument.isEmpty()) {
            return new IncorrectCommand(String.format(SaveAtCommand.MESSAGE_INVALID_PATH, SaveAtCommand.MESSAGE_USAGE));
        }
        File file = new File(processedArgument);
        try {
            String path = file.getCanonicalPath();
            return new SaveAtCommand(new File(path));
        } catch (IOException e) {
            return new IncorrectCommand(String.format(SaveAtCommand.MESSAGE_INVALID_PATH, SaveAtCommand.MESSAGE_USAGE));
        } catch (SecurityException e) {
            return new IncorrectCommand(String.format(SaveAtCommand.MESSAGE_INVALID_PATH, SaveAtCommand.MESSAGE_USAGE));
        }
    }
}
