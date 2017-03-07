package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
<<<<<<< HEAD
import seedu.address.model.person.ReadOnlyTask;
=======
import seedu.address.model.task.ReadOnlyTask;
>>>>>>> a767941edae67662e99e1bfd4f1f28910f9d385f

/**
 * Selects a task identified using its last displayed index from the task manager.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

<<<<<<< HEAD
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
=======
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
>>>>>>> a767941edae67662e99e1bfd4f1f28910f9d385f

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

}
