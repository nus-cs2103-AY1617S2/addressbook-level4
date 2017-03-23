package seedu.taskit.logic.commands;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.apache.commons.lang.ArrayUtils;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.logic.commands.exceptions.CommandException;
import seedu.taskit.model.task.Title;

//@author A0141872E
/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all the existing tasks in TaskIt \n"
            + "Parameters: [done, undone, overdue, today or week\n" 
            + "Example: \n" + COMMAND_WORD + " \n"
            + COMMAND_WORD + " undone\n" + COMMAND_WORD + " today\n";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_SPECIFIC = "Listed all relevant tasks for %1$s";
    
    public static final String MESSAGE_NO_TASK_TODAY = "There is no incomplete task for today! Great";
    public static final String MESSAGE_NO_TASK_WEEK = "There is no incomplete task for the week! Keep it Up";

    private final String[] parameters = {"done", "undone", "overdue", "today", "week"};
    private String parameter;
    
    /**
     * List all relevant tasks in TaskIt.
     * 
     * @param args the requested parameter
     */
    public ListCommand (String parameter) {
        this.parameter = parameter;  
    }
    
    private boolean isValidParameter(String parameter) {
        if(ArrayUtils.contains(parameters, parameter)){
            return true;
        }
        return false;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS_ALL);
    }

}
