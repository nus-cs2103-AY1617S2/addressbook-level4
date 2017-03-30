package seedu.bulletjournal.logic.parser;

import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.bulletjournal.logic.commands.ChangePathCommand;
import seedu.bulletjournal.logic.commands.Command;
import seedu.bulletjournal.logic.commands.DeleteCommand;
import seedu.bulletjournal.logic.commands.IncorrectCommand;

public class ChangePathCommandParser {
    public Command parse(String args) {
        return new ChangePathCommand(args.trim());
    }
}
