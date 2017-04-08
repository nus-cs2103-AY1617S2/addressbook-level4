package onlythree.imanager.logic.commands;

import onlythree.imanager.model.TaskList;

/**
 * Clears the task list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task list has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskList());
        //@@author A0148052L-reused
        model.pushCommand(COMMAND_WORD);
        model.pushStatus(model.getTaskList());
        //@@author
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
