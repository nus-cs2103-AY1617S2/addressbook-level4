package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SaveCommand;

public class SaveCommandParser {

    public Command parse(String args) {
    	if (args.length() > 2) {
    		return new IncorrectCommand(
    				String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    	}
    	
    	args = args.trim();
        return new SaveCommand(args);
    }

}
