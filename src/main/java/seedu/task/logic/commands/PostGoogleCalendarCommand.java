package seedu.task.logic.commands;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.task.commons.core.GoogleCalendar;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Post selected task to GoogleCalendar.
 */
public class PostGoogleCalendarCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);
    public static final String COMMAND_WORD_1 = "postgoogle";
    public static final String COMMAND_WORD_2 = "pg";
    public static final String MESSAGE_SUCCESS = "task posted: %1$s\n";
    public static final String MESSAGE_FAIL = "Unable to post to Google Calendar.";
    public static final String MESSAGE_MISSING_DATE = "Both start and end dates are required"
            + " to post to Google Calendar";
    public static final String MESSAGE_USAGE = COMMAND_WORD_2
            + ": Posts the selected event to your Google Calendar.\n"
            + "Example: " + COMMAND_WORD_2;

    private final int filteredTaskListIndex;

    //@@author A0140063X-reused
    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     */
    public PostGoogleCalendarCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;
        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    //@@author A0140063X
    @Override
    public CommandResult execute() throws CommandException {

        ReadOnlyTask taskToPost = getTaskToPost();
        
        if (taskToPost.getStartDate().isNull() || taskToPost.getEndDate().isNull()) {
            return new CommandResult(MESSAGE_MISSING_DATE);
        }
        
        Event event = createEventFromTask(taskToPost);

        try {
            com.google.api.services.calendar.Calendar service = GoogleCalendar.getCalendarService();
            event = service.events().insert(GoogleCalendar.calendarId, event).execute();

        } catch (IOException e) {
            logger.info("Failure due to " + e.getMessage());
            return new CommandResult(MESSAGE_FAIL);
        }

        logger.info(String.format("Event created: %s\n", event.getHtmlLink()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToPost));
    }

    //@@author A0140063X
    private ReadOnlyTask getTaskToPost() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        return lastShownList.get(filteredTaskListIndex);
    }

    //@@author A0140063X
    private Event createEventFromTask(ReadOnlyTask taskToPost) {
        assert taskToPost != null;
        assert taskToPost.getStartDate() != null;
        assert taskToPost.getEndDate() != null;
        
        Event event = new Event()
                .setSummary(taskToPost.getName().fullName)
                .setLocation(taskToPost.getLocation().value)
                .setDescription(taskToPost.getRemark().value);

        DateTime startDateTime = new DateTime(taskToPost.getStartDate().getDateValue());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Singapore");
        event.setStart(start);       

        DateTime endDateTime = new DateTime(taskToPost.getEndDate().getDateValue());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Singapore");
        event.setEnd(end);
        
        return event;
    }

}
