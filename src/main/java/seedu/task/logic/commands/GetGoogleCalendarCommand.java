package seedu.task.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import seedu.task.commons.core.GoogleCalendar;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.Task;

// @@author A0140063X
/**
 * Grabs upcoming events from google and save them as tasks.
 */
public class GetGoogleCalendarCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);
    public static final String COMMAND_WORD_1 = "getgoogle";
    public static final String COMMAND_WORD_2 = "ggc";
    public static final String MESSAGE_SUCCESS = "Upcoming events obtained from Google successfully!\n"
            + "Note that duplicate events or events without names are ignored.";
    public static final String MESSAGE_USAGE = COMMAND_WORD_2
            + ": Gets your events from your Google Calendar and add them to KIT."
            + " Please note that this will only get upcoming events.\n" + "Example: " + COMMAND_WORD_2;

    @Override
    public CommandResult execute() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            List<Event> events = getEventsFromGoogle();

            if (events.size() == 0) {
                return new CommandResult("No events found");
            }
            logger.info("Events retrieved from Google Calendar. Attempting to add.");

            for (Event event : events) {
                try {
                    tasks.add(createTaskFromEvent(event));
                    logger.info("New event from google calendar sucessfully added.");
                } catch (IllegalValueException ive) {
                    logger.info(ive.getMessage());
                }
            }

        } catch (IOException e) {
            return new CommandResult(GoogleCalendar.CONNECTION_FAIL_MESSAGE);
        }

        updateModel(tasks);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    private List<Event> getEventsFromGoogle() throws IOException {
        com.google.api.services.calendar.Calendar service = GoogleCalendar.getCalendarService();
        DateTime now = new DateTime(new java.util.Date());
        Events events = service.events().list(GoogleCalendar.CALENDAR_ID)
                .setTimeMin(now)
                .setSingleEvents(true)
                .execute();

        return events.getItems();
    }

    private void updateModel(ArrayList<Task> tasks) {
        assert model != null;
        model.addMultipleTasks(tasks);
        model.sortTaskList();
        model.updateFilteredListToShowAll();
    }
}
