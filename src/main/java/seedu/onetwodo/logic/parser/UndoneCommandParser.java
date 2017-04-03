package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.IncorrectCommand;
import seedu.onetwodo.logic.commands.UndoneCommand;
import seedu.onetwodo.model.task.TaskType;

/**
 * Parses input arguments and creates a new UndoneCommand object
 */
public class UndoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoneCommand
     * and returns an UndoneCommand object for execution.
     */
    public Command parse(String args) {
        String argsTrimmed = args.trim();
        if (argsTrimmed.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneCommand.MESSAGE_USAGE));
        }
        char taskType = argsTrimmed.charAt(0);
        Optional<Integer> index = ParserUtil.parseIndex(argsTrimmed.substring(1));
        if (!index.isPresent() || TaskType.getTaskTypeFromChar(taskType) == null) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneCommand.MESSAGE_USAGE));
        }

        return new UndoneCommand(taskType, index.get());
    }
}
