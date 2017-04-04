package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCompleteCommand;
import seedu.task.logic.commands.IncorrectCommand;
//@@author A0139161J
public class DeleteCompleteCommandParser {

    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand (
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCompleteCommand.MESSAGE_USAGE));
        }
        return new DeleteCompleteCommand(index.get());
    }
}
