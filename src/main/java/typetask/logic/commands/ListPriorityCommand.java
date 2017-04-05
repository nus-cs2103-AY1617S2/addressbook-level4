package typetask.logic.commands;

import typetask.model.task.Priority;

//@@author A0144902L
/**
 * Lists tasks with High Priority in the TaskManager to the user.
 */
public class ListPriorityCommand extends Command {
    public static final String COMMAND_WORD = "list*";

    public static final String MESSAGE_SUCCESS = "High priority task(s) listed!";

    Priority priority;

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(priority);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
