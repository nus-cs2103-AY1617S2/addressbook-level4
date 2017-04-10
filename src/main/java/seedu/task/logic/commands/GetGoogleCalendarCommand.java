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
import seedu.task.logic.util.LogicHelper;
import seedu.task.model.task.Task;

// @@author A0140063X
/**
 * Grabs upcoming events from google and save them as tasks.
 */
public class GetGoogleCalendarCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);
    public static final String COMMAND_WORD_1 = "getgoogle";
    public static final String COMMAND_WORD_2 = "gg";
    public static final String MESSAGE_SUCCESS = "Upcoming events obtained from Google successfully!\n"
            + "Note that duplicate events or events without names are ignored.";
    public static final String MESSAGE_USAGE = COMMAND_WORD_2
            + ": Gets your events from your Google Calendar and add them to KIT."
            + " Please note that this will only get upcoming events and"
            + " duplicate events or events without names are ignored.\n" + "Example: " + COMMAND_WORD_2;

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
                    tasks.add(LogicHelper.createTaskFromEvent(event));
                    logger.info("New event from google calendar sucessfully added.");
                } catch (IllegalValueException ive) {
                    logger.info(ive.getMessage());
                    // Continue to add next event even if one fails.
                }
            }
        } catch (IOException e) {
            return new CommandResult(GoogleCalendar.CONNECTION_FAIL_MESSAGE);
        }

        addMultipleTaskToModel(tasks);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * This method makes the query to google calendar and returns the list of events.
     * Will only get upcoming and non recurring events.
     *
     * @return List of obtained events.
     * @throws IOException  If connection failed.
     */
    private List<Event> getEventsFromGoogle() throws IOException {
        com.google.api.services.calendar.Calendar service = GoogleCalendar.getCalendarService();
        DateTime now = new DateTime(new java.util.Date());
        Events events = service.events().list(GoogleCalendar.CALENDAR_ID)
                .setTimeMin(now)
                .setSingleEvents(true)
                .execute();

        return events.getItems();
    }

    /**
     * This method adds the given list of tasks to model, sort and list them.
     *
     * @param tasks    The list of tasks to add.
     */
    private void addMultipleTaskToModel(ArrayList<Task> tasks) {
        assert model != null;
        model.addMultipleTasks(tasks);
        model.sortTaskList();
        model.updateFilteredListToShowAll();
    }
}
