//@@author A0139217E
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CompleteCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.task.Task;

/**
 * Parses input arguments and creates a new CompleteCommand object
 */
public class CompleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteCommand
     * and returns an CompleteCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        Optional<Integer> index = ParserUtil.parseIndex(args.trim());
        Optional<String> listName = ParserUtil.parseListName(args);
        if (!index.isPresent() || !listName.isPresent() || listName.get().equals(Task.TASK_NAME_COMPLETED)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
        String listNameString = listName.get();
        if (!listName.isPresent()) {
            listNameString = Task.TASK_NAME_NON_FLOATING;
        }

        return new CompleteCommand(listNameString, index.get());
    }

}
