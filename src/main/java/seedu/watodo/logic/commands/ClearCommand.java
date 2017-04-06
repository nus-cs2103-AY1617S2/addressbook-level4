package seedu.watodo.logic.commands;

import seedu.watodo.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All tasks have been cleared!";
    private TaskManager dataToClear;


    @Override
    public CommandResult execute() {
        assert model != null;
        dataToClear = new TaskManager(model.getTaskManager());
        System.out.print(dataToClear);

        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author A0139845R
    @Override
    public void unexecute() {
        assert model != null;
        model.resetData(dataToClear);

    }

    @Override
    public void redo() {
        assert model != null;
        this.execute();
        model.updateFilteredListToShowAll();

    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
    //@@author


}
