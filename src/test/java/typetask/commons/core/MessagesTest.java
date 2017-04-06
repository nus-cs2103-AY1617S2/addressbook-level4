package typetask.commons.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessagesTest {

    @Test
    public void messageUnknownCommand_success() {
        assertEquals(Messages.MESSAGE_UNKNOWN_COMMAND, "Unknown command");
    }
    @Test
    public void messageInvalidCommandFormat_success() {
        assertEquals(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "Invalid command format! \n%1$s");
    }
    @Test
    public void messageInvalidTaskDisplayedIndex_success() {
        assertEquals(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, "The task index provided is invalid");
    }
    @Test
    public void messageTasksListedOverview_success() {
        assertEquals(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, "%1$d task(s) listed!");
    }
    @Test
    public void messageConfigError_success() {
        assertEquals(Messages.MESSAGE_CONFIG_ERROR,
                "Issues encountered when reading config file.\nPlease restart TypeTask.");
    }
    @Test
    public void messageInvalidStartAndEndDate_success() {
        assertEquals(Messages.MESSAGE_INVALID_START_AND_END_DATE,
                "End date and time should not be before Start date and time");
    }
    @Test
    public void messageInvalidDateFormatForStartDate_success() {
        assertEquals(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_START_DATE, "Start date is invalid");
    }
    @Test
    public void messageInvalidDateFormatForEndDate_success() {
        assertEquals(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_END_DATE, "End date is invalid");
    }
    @Test
    public void messageInvalidDateFormatForDate_success() {
        assertEquals(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE, "Deadline is invalid");
    }
}
