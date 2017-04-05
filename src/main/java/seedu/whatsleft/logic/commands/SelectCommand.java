package seedu.whatsleft.logic.commands;

import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.commons.core.UnmodifiableObservableList;
import seedu.whatsleft.commons.events.ui.JumpToCalendarEventEvent;
import seedu.whatsleft.commons.events.ui.JumpToCalendarTaskEvent;
import seedu.whatsleft.commons.events.ui.JumpToEventListRequestEvent;
import seedu.whatsleft.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;

/**
 * Selects an activity identified using it's last displayed index from
 * WhatsLeft.
 */
public class SelectCommand extends Command {

    public final int targetIndex;
    public final String type;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the activity identified by the index number used in the last activity listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_EVENT_SUCCESS = "Selected Event: %1$s";
    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    // @@author A0110491U
    public SelectCommand(int targetIndex, String type) {
        this.targetIndex = targetIndex;
        this.type = type;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();
        model.storePreviousCommand("");

        if (type.equals("ev")) {
            return selectingEvent(lastShownEventList);
        }
        if (type.equals("ts")) {
            return selectingTask(lastShownTaskList);
        }
        return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    //@@author A0110491U
    /**
     * @param lastShownTaskList
     * @return CommandResult success if the index of event is valid
     * @throws CommandException if the index given is invalid
     */
    private CommandResult selectingTask(UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList)
            throws CommandException {
        if (lastShownTaskList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex - 1));
        EventsCenter.getInstance().post(new JumpToCalendarTaskEvent(lastShownTaskList.get(targetIndex - 1)));
        ReadOnlyTask selected = lastShownTaskList.get(targetIndex - 1);
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, selected));
    }

    //@@author A0110491U
    /**
     * @param lastShownEventList
     * @return CommandResult success if the index of task is valid
     * @throws CommandException if the index given is invalid
     */
    private CommandResult selectingEvent(UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList)
            throws CommandException {
        if (lastShownEventList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new JumpToEventListRequestEvent(targetIndex - 1));
        if (!lastShownEventList.get(targetIndex - 1).isOver()) {
            EventsCenter.getInstance().post(new JumpToCalendarEventEvent(lastShownEventList.get(targetIndex - 1)));
        }
        ReadOnlyEvent selected = lastShownEventList.get(targetIndex - 1);
        return new CommandResult(String.format(MESSAGE_SELECT_EVENT_SUCCESS, selected));
    }

}
