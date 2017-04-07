package seedu.task.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.api.services.calendar.model.Event;

import seedu.task.commons.core.GoogleCalendar;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.util.LogicHelper;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0140063X
/**
 * Post either selected or all task to Google Calendar.
 */
public class PostGoogleCalendarCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);
    public static final int NO_INDEX = -1;
    public static final String COMMAND_WORD_1 = "postgoogle";
    public static final String COMMAND_WORD_2 = "pg";
    public static final String MESSAGE_SUCCESS = "Task posted: %1$s\n";
    public static final String MESSAGE_SUCCESS_MULTIPLE = "All eligible task in current listing posted.";
    public static final String MESSAGE_MISSING_DATE = "Both start and end dates are required"
            + " to post to Google Calendar";
    public static final String MESSAGE_ALREADY_POSTED = "Task already posted to Google Calendar";
    public static final String MESSAGE_USAGE = COMMAND_WORD_2 + ": Posts events to your Google Calendar.\n"
            + "Post the task identified by INDEX, if no index specified then posts all task in current listing.\n"
            + "Parameters: [INDEX]\n"
            + "Example: " + COMMAND_WORD_2;

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     */
    public PostGoogleCalendarCommand(int filteredTaskListIndex) {
        if (filteredTaskListIndex == NO_INDEX) {
            this.filteredTaskListIndex = NO_INDEX;
        } else {
            this.filteredTaskListIndex = filteredTaskListIndex - 1;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (filteredTaskListIndex == NO_INDEX) {
            try {
                postMultipleEvents();
            } catch (IOException ioe) {
                return new CommandResult(ioe.getMessage());
            }

            return new CommandResult(MESSAGE_SUCCESS_MULTIPLE);
        } else {
            ReadOnlyTask taskToPost;
            try {
                taskToPost = postEvent(filteredTaskListIndex);
                //CommandException is thrown if wrong index
            } catch (IllegalValueException ive) {
                return new CommandResult(ive.getMessage());
            } catch (IOException ioe) {
                return new CommandResult(ioe.getMessage());
            } catch (TaskNotFoundException tnfe) {
                return new CommandResult(tnfe.getMessage());
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, taskToPost));
        }
    }

    private void postMultipleEvents() throws IOException {
        assert model != null;
        int max = model.getFilteredTaskList().size();

        for (int i = 0; i < max; i++) {
            try {
                postEvent(i);

            } catch (CommandException | TaskNotFoundException e) {
                logger.warning("Invalid index when posting multiple events to calendar. This should not happen!");
            } catch (IllegalValueException ive) {
                logger.info(ive.getMessage());
            }
            //continue to post next event even if one event fails
        }
    }

    private ReadOnlyTask postEvent(int index) throws CommandException, IllegalValueException,
                                                    IOException, TaskNotFoundException {
        ReadOnlyTask taskToPost = getTaskFromIndex(index);

        if (taskToPost.getStartDate().isNull() || taskToPost.getEndDate().isNull()) {
            throw new IllegalValueException(MESSAGE_MISSING_DATE);
        }

        Event event = LogicHelper.createEventFromTask(taskToPost);

        try {
            com.google.api.services.calendar.Calendar service = GoogleCalendar.getCalendarService();

            if (taskToPost.getEventId().trim().isEmpty()) {
                event = service.events().insert(GoogleCalendar.CALENDAR_ID, event).execute();
                setTaskEventId(taskToPost, event.getId());

                logger.info(String.format("Event created: %s\n", event.getHtmlLink()));
            } else {
                service.events().update(GoogleCalendar.CALENDAR_ID, event.getId(), event).execute();

                logger.info(String.format("Event updated: %s\n", event.getHtmlLink()));
            }
        } catch (IOException ioe) {
            logger.info("Failure due to " + ioe.getMessage());
            throw new IOException(GoogleCalendar.CONNECTION_FAIL_MESSAGE);
        }

        return taskToPost;
    }

    private void setTaskEventId(ReadOnlyTask taskToPost, String eventId)
            throws TaskNotFoundException, IllegalValueException {
        assert model != null;
        model.setTaskEventId(taskToPost, eventId);
    }

}
