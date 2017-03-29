package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BookCommand;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0162877N
public class BookCommandTest extends TaskManagerGuiTest {

    public static final String COMMAND_WORD = "book";
    public static final String MESSAGE_DUPLICATE_BOOKING = "This booking already exists in the task manager";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Books time slots for a task. "
            + "Parameters: TITLE [#LABEL] on DATE STARTTIME to ENDTIME, DATE STARTTIME to ENDTIME...\n"
            + "Example: " + COMMAND_WORD
            + " Meet John Doe #friends #owesMoney on 31 Mar 2017 2pm to 5pm,"
            + " 01 Oct 2017 2pm to 5pm, 30 Oct 2017 1pm to 2pm";
    public static final String MESSAGE_BOOKING_CONSTRAINTS =
            "Input dates are in the wrong format, please try again!";

    @Test
    public void book_isMutating_ReturnTrue() throws IllegalDateTimeValueException,
            IllegalValueException, CommandException {
        Set<String> emptySet = new HashSet<String>();
        BookCommand bc = new BookCommand("valid", emptySet, "");
        assertTrue(bc.isMutating());
    }

    @Test
    public void book_ValidBookCommand_ReturnTrue() {
        commandBox.runCommand("book Complete booking #friends on 10-10-2017 2pm to 5pm,"
                        + " 11-10-2017 2pm to 5pm, 12-10-2017 2pm to 5pm");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Complete booking");
        assertTrue(addedCard.getTitle().equals("Complete booking"));
    }

    @Test
    public void book_DuplicateBooking() {
        commandBox.runCommand("book Complete booking #friends on 10-10-2017 2pm to 5pm,"
                + " 11-10-2017 2pm to 5pm, 12-10-2017 2pm to 5pm");
        commandBox.runCommand("book Complete booking #friends on 10-10-2017 2pm to 5pm,"
                + " 11-10-2017 2pm to 5pm, 12-10-2017 2pm to 5pm");

        assertResultMessage(MESSAGE_DUPLICATE_BOOKING);
    }

    @Test
    public void book_InvalidCommand() {
        commandBox.runCommand("book Complete booking #friends on 10 Oct 2017 2pm,");
        assertResultMessage(MESSAGE_BOOKING_CONSTRAINTS);

        commandBox.runCommand("book Complete booking #friends 10 Oct 2017 2pm,");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

}
