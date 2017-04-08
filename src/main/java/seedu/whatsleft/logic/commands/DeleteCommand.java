package seedu.whatsleft.logic.commands;

import java.util.logging.Logger;

import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.commons.core.UnmodifiableObservableList;
import seedu.whatsleft.commons.events.ui.JumpToEventListRequestEvent;
import seedu.whatsleft.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.ModelManager;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.activity.UniqueEventList.EventNotFoundException;
import seedu.whatsleft.model.activity.UniqueTaskList.TaskNotFoundException;

//@@author A0148038A
/**
 * Deletes an activity identified using it's last displayed index from WhatsLeft.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the activity identified by the type and index number used in the last respective listing.\n"
            + "Parameters: TYPE (ev represents event or ts represents task) and INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " ev" + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;
    public final String targetType;
    private final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    //@@author A0110491U
    public DeleteCommand(int targetIndex, String targetType) {
        this.targetIndex = targetIndex;
        this.targetType = targetType;
    }
    //@@author A0124377A
    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();

        if (targetType.equals("ev")) {
            //@@author A0148038A
            logger.info("-------[Executing DeleteEventCommand] " + this.toString());

            if (lastShownEventList.size() < targetIndex) {
                logger.info("-------[Failed execution of DeleteEventCommand] " + this.toString());
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }

            //@@author A0124377A
            ReadOnlyEvent eventToDelete = lastShownEventList.get(targetIndex - 1);
            try {
                //store for undo operation
                ReadOnlyWhatsLeft currState = model.getWhatsLeft();
                ModelManager.setPreviousState(currState);
                EventsCenter.getInstance().post(new JumpToEventListRequestEvent(targetIndex - 1));
                model.deleteEvent(eventToDelete);
                model.storePreviousCommand("delete");
            } catch (EventNotFoundException enfe) {
                assert false : "The target event cannot be missing";
            }
            return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
        }

        if (targetType.equals("ts")) {
            //@@author A0148038A
            logger.info("-------[Executing DeleteTaskCommand] " + this.toString());

            if (lastShownTaskList.size() < targetIndex) {
                logger.info("-------[Failed execution of DeleteTaskCommand] " + this.toString());
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            //@@author A0124377A
            ReadOnlyTask taskToDelete = lastShownTaskList.get(targetIndex - 1);
            try {
                //store for undo operation
                ReadOnlyWhatsLeft currState = model.getWhatsLeft();
                ModelManager.setPreviousState(currState);
                EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex - 1));
                model.deleteTask(taskToDelete);
                model.storePreviousCommand("delete");
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        }
        return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

    }

}
