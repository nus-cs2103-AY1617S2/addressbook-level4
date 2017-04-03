package seedu.taskit.logic.parser;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.IncorrectCommand;
import seedu.taskit.logic.commands.ListCommand;
import seedu.taskit.logic.commands.SaveCommand;

//@@author A0141872E
public class ListCommandParser {
    
    private static final List<String> PARAMETERS = Arrays.asList("all", "done", "undone", "overdue", "today", "low", "medium", "high" );
    
    public Command parse(String args) {
        Optional<String> parameters = Optional.of(args);
        if (!parameters.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        String parameter = parameters.get().trim();
        if(!isValidParameter(parameter)){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(parameter);
    }
    
    private boolean isValidParameter(String parameter) {
        return PARAMETERS.contains(parameter);
    }

}
