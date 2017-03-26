package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;

//@@author A0146789H
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        // Add the undo entry after the DeleteCommand is successfully parsed.
        UndoManager.pushCommand(DeleteCommand.COMMAND_WORD);

        return new DeleteCommand(index.get());
    }

}
