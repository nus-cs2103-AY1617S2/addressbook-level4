package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.UncheckCommand;
import seedu.task.model.UndoManager;

public class UncheckedCommandParser {
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncheckCommand.MESSAGE_USAGE));
        }

        // Add the undo entry after the UncheckCommand is successfully parsed.
        UndoManager.pushCommand(UncheckCommand.COMMAND_WORD);

        return new UncheckCommand(index.get());
    }
}
