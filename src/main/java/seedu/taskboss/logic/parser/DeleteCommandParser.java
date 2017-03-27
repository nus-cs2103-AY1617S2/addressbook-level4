package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.DeleteCommand;
import seedu.taskboss.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        Set<Integer> index = parseIndex(args);

        if (index.get(0) == null)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index);
    }

    private Set<Integer> parseIndex(String indexList) {
        Set<Integer> taskIndex = new HashSet<Integer>();
        String trimmedList = indexList.trim();
        String[] indexes = trimmedList.split(" ");

        for (String index : indexes) {
            taskIndex.add(Integer.parseInt(index));
        }

        return taskIndex;
    }
}
