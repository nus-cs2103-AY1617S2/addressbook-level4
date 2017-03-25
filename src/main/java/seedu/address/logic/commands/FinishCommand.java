package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniqueEventList.EventNotFoundException;
import seedu.address.model.person.UniqueTaskList.TaskNotFoundException;

//@@ author A0121668A
/*
 * Mark a Task as complete
 */
public class FinishCommand extends Command {

    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": mark the task identified by index number used in the last respective listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 5";

    public static final String MESSAGE_FINISH_TASK_SUCCESS = "Finished task: %1$s";

    private final int filteredActivityListIndex;

    // @@author A0121668A
    public FinishCommand(int filteredActivityListIndex, String targetType) {
        assert filteredActivityListIndex > 0;
        this.filteredActivityListIndex = filteredActivityListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();

        if (filteredActivityListIndex >= lastShownTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToComplete = lastShownTaskList.get(filteredActivityListIndex);
        try {
            model.MarkTaskAsComplete(filteredActivityListIndex - 1);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_FINISH_TASK_SUCCESS, taskToComplete));
    }
}
