package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ConfirmCommand;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0162877N
public class ConfirmCommandTest extends TaskManagerGuiTest {

    public static final String COMMAND_WORD = "confirm";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms the bookings of the task identified "
            + "by the index number used in the last task listing. "
            + "Other booking slots will be removed.\n"
            + "Parameters: INDEX (index of the task in the current task list and must be a positive integer)\n"
            + "Parameters: INDEX (index of the time slot to confirm and must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 1";
    public static final String MESSAGE_NO_SUCH_BOOKING = "Index provided is invalid.\n" + MESSAGE_USAGE;
    public static final String MESSAGE_TASK_NO_BOOKING = "This task does not have bookings to confirm.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    @Test
    public void confirm_isMutating_ReturnTrue() throws IllegalDateTimeValueException,
            IllegalValueException, CommandException {
        ConfirmCommand cc = new ConfirmCommand(1, 1);
        assertTrue(cc.isMutating());
    }

    @Test
    public void confirm_invalidCommand_ReturnTrue() {
        commandBox.runCommand("confirm ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
    }

    @Test
    public void confirm_invalidCommand() throws IllegalDateTimeValueException, IllegalValueException, CommandException {
        commandBox.runCommand("confirm -1 -2");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        commandBox.runCommand("confirm a b");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void confirm_validOutOfRangeIndex() throws IllegalDateTimeValueException,
            IllegalValueException, CommandException {
        commandBox.runCommand("confirm 18 2");
        assertResultMessage(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);

        commandBox.runCommand("confirm 12 2");
        assertResultMessage(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);
    }

    @Test
    public void confirm_ValidConfirmCommand_ReturnTrue() {
        commandBox.runCommand("book Complete booking #friends on 19 May 2017 2pm to 5pm, 12 May 2017 2pm to 5pm");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Complete booking");
        assertTrue(addedCard.getTitle().equals("Complete booking"));
        System.out.println(taskListPanel.getNumberOfTasks());

        commandBox.runCommand("confirm 1 2");
        TaskCardHandle confirmedCard = taskListPanel.navigateToTask("Complete booking");
        System.out.println(confirmedCard.getStartTime());
        System.out.println(confirmedCard.getDeadline());
        assertTrue(!confirmedCard.getStartTime().equals(""));
        assertTrue(!confirmedCard.getDeadline().equals(""));

        commandBox.runCommand("undo");
        confirmedCard = taskListPanel.navigateToTask("Complete booking");
        System.out.println(confirmedCard.getStartTime());
        System.out.println(confirmedCard.getDeadline());
        assertTrue(confirmedCard.getStartTime().equals(""));
        assertTrue(confirmedCard.getDeadline().equals(""));
    }
}
