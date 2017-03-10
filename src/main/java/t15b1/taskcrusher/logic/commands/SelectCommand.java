package t15b1.taskcrusher.logic.commands;

import t15b1.taskcrusher.commons.core.EventsCenter;
import t15b1.taskcrusher.commons.core.Messages;
import t15b1.taskcrusher.commons.core.UnmodifiableObservableList;
import t15b1.taskcrusher.commons.events.ui.JumpToListRequestEvent;
import t15b1.taskcrusher.logic.commands.exceptions.CommandException;
import t15b1.taskcrusher.model.task.ReadOnlyTask;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex));

    }

}
