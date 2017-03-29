package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditBookingCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

//@@author A0162877N
public class EditBookingCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void editbooking_isMutating_ReturnTrue()
            throws IllegalDateTimeValueException, IllegalValueException, CommandException {
        EditBookingCommand ebc = new EditBookingCommand(1, 1, "");
        assertTrue(ebc.isMutating());
    }

    @Test
    public void editbooking_addBookingDates_success() throws Exception {
        TestTask taskToAdd = (new TaskBuilder())
                .withTitle("Complete booking")
                .withDeadline("")
                .withStartTime("")
                .withLabels("friends")
                .withBookings("10-10-2017 2pm to 5pm",
                "11 Oct 2017 2pm to 5pm",
                "12 Oct 2017 2pm to 5pm",
                "13 Oct 2017 2pm to 5pm",
                "14 Oct 2017 2pm to 5pm",
                "16 Oct 2017 2pm to 5pm")
                .build();

        commandBox.runCommand("book Complete booking #friends on 10 Oct 2017 2pm to 5pm,"
                + " 11 Oct 2017 2pm to 5pm, 12 Oct 2017 2pm to 5pm");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Complete booking");
        assertTrue(addedCard.getTitle().equals("Complete booking"));

        commandBox.runCommand("editbooking 8 add 13 Oct 2017 2pm to 5pm,"
                + " 14 Oct 2017 2pm to 5pm, 16 Oct 2017 2pm to 5pm");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        System.out.println("Comparing");
        assertMatching(taskToAdd, addedCard);
    }

    @Test
    public void editbooking_invalidAddBookingCommand_ReturnsError() throws Exception {
        commandBox.runCommand("book Complete booking #friends on 10 Oct 2017 2pm to 5pm,"
                + " 11 Oct 2017 2pm to 5pm, 12 Oct 2017 2pm to 5pm");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Complete booking");
        assertTrue(addedCard.getTitle().equals("Complete booking"));

        commandBox.runCommand("editbooking 9 add 13 Oct 2017 2pm,"
                + " 14 Oct 2017 7pm, 16 ");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking 0 add 13 Oct 2017 2pm,"
                + " 14 Oct 2017 7pm, 16 ");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking -1 add 13 Oct 2017 2pm,"
                + " 14 Oct 2017 7pm, 16 ");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookingCommand.MESSAGE_USAGE));
    }

    @Test
    public void editbooking_removeBookingDates_success() throws Exception {
        TestTask taskToAdd = (new TaskBuilder())
                .withTitle("Complete booking")
                .withDeadline("")
                .withStartTime("")
                .withLabels("friends")
                .withBookings("11 Oct 2017 2pm to 5pm",
                "12 Oct 2017 2pm to 5pm",
                "13 Oct 2017 2pm to 5pm",
                "14 Oct 2017 2pm to 5pm",
                "16 Oct 2017 2pm to 5pm")
                .build();

        commandBox.runCommand("book Complete booking #friends on 10 Oct 2017 2pm to 5pm,"
                + " 11 Oct 2017 2pm to 5pm, 12 Oct 2017 2pm to 5pm,"
                + " 13 Oct 2017 2pm to 5pm, 14 Oct 2017 2pm to 5pm,"
                + " 16 Oct 2017 2pm to 5pm");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Complete booking");
        assertTrue(addedCard.getTitle().equals("Complete booking"));

        commandBox.runCommand("editbooking 8 remove 1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        System.out.println("Comparing");
        assertMatching(taskToAdd, addedCard);
    }

    @Test
    public void editbooking_invalidRemoveBookingCommand_ReturnsError() throws Exception {
        commandBox.runCommand("book Complete booking #friends on 10 Oct 2017 2pm to 5pm,"
                + " 11 Oct 2017 2pm to 5pm, 12 Oct 2017 2pm to 5pm,"
                + " 13 Oct 2017 2pm to 5pm, 14 Oct 2017 2pm to 5pm,"
                + " 16 Oct 2017 2pm to 5pm");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Complete booking");
        assertTrue(addedCard.getTitle().equals("Complete booking"));

        commandBox.runCommand("editbooking 10 remove 1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);

        commandBox.runCommand("editbooking 0 remove 1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);

        commandBox.runCommand("editbooking -1 remove 1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking 8 remove 7");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(EditBookingCommand.MESSAGE_NO_SUCH_BOOKING);

        commandBox.runCommand("editbooking 8 remove 0");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(EditBookingCommand.MESSAGE_NO_SUCH_BOOKING);

        commandBox.runCommand("editbooking 8 remove -1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));
    }

    @Test
    public void editbooking_changeBookingDates_success() throws Exception {
        TestTask taskToAdd = (new TaskBuilder())
                .withTitle("Complete booking")
                .withDeadline("")
                .withStartTime("")
                .withLabels("friends")
                .withBookings("10-10-2017 2pm to 5pm",
                "11 Oct 2017 2pm to 5pm",
                "12 Oct 2017 2pm to 5pm",
                "13 Oct 2017 2pm to 5pm",
                "14 Oct 2017 2pm to 5pm",
                "16 Oct 2017 2pm to 5pm")
                .build();

        commandBox.runCommand("book Complete booking #friends on 10 Oct 2017 2pm to 5pm,"
                + " 11 Oct 2017 2pm to 5pm, 12 Oct 2017 2pm to 5pm");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Complete booking");
        assertTrue(addedCard.getTitle().equals("Complete booking"));

        commandBox.runCommand("editbooking 8 add 13 Oct 2017 2pm to 5pm,"
                + " 14 Oct 2017 2pm to 5pm, 16 Oct 2017 2pm to 5pm");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        System.out.println("Comparing");
        assertMatching(taskToAdd, addedCard);

        TestTask editedTask = (new TaskBuilder())
                .withTitle("Complete booking")
                .withDeadline("")
                .withStartTime("")
                .withLabels("friends")
                .withBookings("11 Oct 2017 2pm to 5pm",
                "12 Oct 2017 2pm to 5pm",
                "13 Oct 2017 2pm to 5pm",
                "14 Oct 2017 2pm to 5pm",
                "16 Oct 2017 2pm to 5pm",
                "17 Oct 2017 2pm to 5pm")
                .build();

        commandBox.runCommand("editbooking 8 change 1 17 Oct 2017 2pm to 5pm");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        System.out.println("Comparing");
        assertMatching(editedTask, addedCard);
    }

    @Test
    public void editbooking_invalidChangeBookingCommand_ReturnsError() throws Exception {
        commandBox.runCommand("book Complete booking #friends on 10 Oct 2017 2pm to 5pm,"
                + " 11 Oct 2017 2pm to 5pm, 12 Oct 2017 2pm to 5pm,"
                + " 13 Oct 2017 2pm to 5pm, 14 Oct 2017 2pm to 5pm,"
                + " 16 Oct 2017 2pm to 5pm");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Complete booking");
        assertTrue(addedCard.getTitle().equals("Complete booking"));

        commandBox.runCommand("editbooking 10 change 1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking 0 change 1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking -1 change 1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking 8 change 7");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking 8 change 0");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking 8 change -1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));
    }

    @Test
    public void editbooking_invalidEditBookingCommand_ReturnsError() throws Exception {
        commandBox.runCommand("book Complete booking #friends on 10 Oct 2017 2pm to 5pm,"
                + " 11 Oct 2017 2pm to 5pm, 12 Oct 2017 2pm to 5pm,"
                + " 13 Oct 2017 2pm to 5pm, 14 Oct 2017 2pm to 5pm,"
                + " 16 Oct 2017 2pm to 5pm");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Complete booking");
        assertTrue(addedCard.getTitle().equals("Complete booking"));

        commandBox.runCommand("editbooking 10 anyhow");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking 0cccsc");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking 7");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking change");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));

        commandBox.runCommand("editbooking 8 invalid 1");
        addedCard = taskListPanel.navigateToTask("Complete booking");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBookingCommand.MESSAGE_USAGE));
    }
}
