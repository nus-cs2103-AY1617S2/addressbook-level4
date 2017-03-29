package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.IncorrectCommand;
import seedu.onetwodo.model.task.TaskType;

/**
 * Parses input arguments and creates a new DoneCommand object
 */
public class DoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns an DoneCommand object for execution.
     */
    public Command parse(String args) {
        String argsTrimmed = args.trim();
        if (argsTrimmed.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
        char taskType = argsTrimmed.charAt(0);
        Optional<Integer> index = ParserUtil.parseIndex(argsTrimmed.substring(1));
        if (!index.isPresent() || TaskType.getTaskTypeFromChar(taskType) == null) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(taskType, index.get());
    }
}
