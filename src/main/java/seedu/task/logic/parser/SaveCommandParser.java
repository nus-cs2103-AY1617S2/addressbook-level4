package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SaveCommand;

public class SaveCommandParser {

    public Command parse(String args) {
    	args = args.trim();
        return new SaveCommand(args);
    }

}
