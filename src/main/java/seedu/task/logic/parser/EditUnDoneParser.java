package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditUnDoneCommand;
import seedu.task.logic.commands.IncorrectCommand;


public class EditUnDoneParser {

    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditUnDoneCommand.MESSAGE_USAGE));
        }

        return new EditUnDoneCommand(index.get());
    }
}
