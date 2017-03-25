package seedu.doist.logic.parser;



import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.SaveAtCommand;

//@@author A0140887W
public class SaveAtCommandParser {
    public Command parse(String argument) {
        // Remove trailing whitespace
        String processedArgument = argument.trim();
        Path path = null;
        try {
            path = Paths.get(processedArgument);
        } catch (InvalidPathException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveAtCommand.MESSAGE_USAGE));
        }
        return new SaveAtCommand(path.toAbsolutePath());
    }
}
