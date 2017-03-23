package seedu.taskit.logic.parser;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.apache.commons.lang.ArrayUtils;

import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.IncorrectCommand;
import seedu.taskit.logic.commands.ListCommand;

public class ListCommandParser {
    
    private static final String[] parameters = {"done", "undone", "overdue", "today", "week"};
    
    public Command parse(String args) {
        String parameter = args.trim();
        if(!isValidParameter(parameter)){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(parameter);
    }
    
    private boolean isValidParameter(String parameter) {
        if(ArrayUtils.contains(parameters, parameter)){
            return true;
        }
        return false;
    }

}
