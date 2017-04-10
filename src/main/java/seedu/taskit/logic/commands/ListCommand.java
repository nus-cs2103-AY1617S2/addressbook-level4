package seedu.taskit.logic.commands;

import static seedu.taskit.logic.parser.CliSyntax.ALL;
import static seedu.taskit.logic.parser.CliSyntax.TODAY;

//@@author A0141872E
/**
 * Lists all tasks in TaskIt to the user based on given parameters.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all the existing tasks in TaskIt \n"
            + "Parameters: [all, done, undone, overdue, today,low,medium,high]\n"
            + "Example: " + COMMAND_WORD + " undone\n" + COMMAND_WORD + " today\n";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_SPECIFIC = "Listed all relevant tasks for %1$s";
    public static final String MESSAGE_NO_TASK_TODAY = "There is no incomplete task for today! Great";

    private String parameter;

    /**
     * List all relevant tasks in TaskIt.
     *
     * @param parameter the requested parameter
     */
    public ListCommand (String parameter) {
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute() {

        int taskListSize;
        switch (parameter) {
        case ALL:
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_ALL);

        case TODAY:
            taskListSize = model.updateFilteredTaskList(parameter);
            if(taskListSize == 0){
                return new CommandResult(MESSAGE_NO_TASK_TODAY);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC, parameter));

        default:
            model.updateFilteredTaskList(parameter);// for all other valid parameters
            return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC, parameter));
        }
    }




    //@@author A0141011J
    @Override
    public boolean isUndoable() {
        return true;
    }
}
