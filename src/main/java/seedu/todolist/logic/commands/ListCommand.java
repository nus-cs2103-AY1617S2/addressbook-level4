package seedu.todolist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

//@@author A0139633B
/**
 * Lists all tasks in the to-do list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String TYPE_DEFAULT = "";
    public static final String TYPE_ALL = "all";
    public static final String TYPE_INCOMPLETE = "incomplete";
    public static final String TYPE_COMPLETE = "complete";
    public static final String TYPE_OVERDUE = "overdue";

    private static ArrayList<String> validCommands = new ArrayList<String>(Arrays.asList(
            TYPE_DEFAULT, TYPE_ALL, TYPE_INCOMPLETE, TYPE_COMPLETE, TYPE_OVERDUE
            ));

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the type of tasks specified.\n"
            + "Parameters: TYPE\n"
            + "Example: " + COMMAND_WORD + " incomplete";

    public static final String MESSAGE_SUCCESS = "Listed tasks";

    private final String taskType;

    public ListCommand(String taskType) {
        this.taskType = taskType;
    }

    public static boolean isValidCommand(String command) {
        return validCommands.contains(command);
    }

    @Override
    public CommandResult execute() {
        switch(taskType) {

        case TYPE_INCOMPLETE:
            model.getFilteredIncompleteTaskList();
            break;

        case TYPE_COMPLETE:
            model.getFilteredCompleteTaskList();
            break;

        case TYPE_OVERDUE:
            model.getFilteredOverdueTaskList();
            break;

        default:
            model.updateFilteredListToShowAll();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean isMutating() {
        return false;
    }

    @Override
    public String getCommandText() {
        return MESSAGE_SUCCESS;
    }
}
