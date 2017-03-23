package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyActivity;

/**
 * Selects an activity identified using it's last displayed index from WhatsLeft.
 */
public class SelectCommand extends Command {

    public final int targetIndex;
    public final String type;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the activity identified by the index number used in the last activity listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_EVENT_SUCCESS = "Selected Event: %1$s";
    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";
    //@@author A0110491U
    public SelectCommand(int targetIndex, String type) {
        this.targetIndex = targetIndex;
        this.type = type;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();

        if (type.equals("ev")) {
            if (lastShownEventList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1, type));
            return new CommandResult(String.format(MESSAGE_SELECT_EVENT_SUCCESS, targetIndex));
        }
        if (type.equals("ts")) {
            if (lastShownTaskList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1, type));
            return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));
        }
    }

}
