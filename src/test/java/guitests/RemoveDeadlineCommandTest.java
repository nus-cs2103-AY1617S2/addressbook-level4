package guitests;

import static org.junit.Assert.assertEquals;
import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import typetask.commons.core.Messages;
import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.RemoveDeadlineCommand;
import typetask.testutil.TaskBuilder;
import typetask.testutil.TestTask;

//@@author A0139926R
public class RemoveDeadlineCommandTest extends TypeTaskGuiTest {

    private static final String REMOVEDEADLINE_COMMAND = "removedeadline";
    private static final String REMOVEDEADLINE_COMMAND_SHORT = "rd";
    private static final String ADD_DEADLINE_TO_EXISTING = "edit 1 by: tmr";

    @Test
    public void removedeadline_success() throws IllegalValueException {
        int editIndex = 1;
        commandBox.runCommand(ADD_DEADLINE_TO_EXISTING);
        assertRemovedeadlineSuccess(editIndex);
    }
    @Test
    public void removedeadlineWithNoIndex_fail() {
        commandBox.runCommand(REMOVEDEADLINE_COMMAND_SHORT);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveDeadlineCommand.MESSAGE_USAGE));
    }
    @Test
    public void removedeadlineWithInvalidIndex_fail() {
        commandBox.runCommand(REMOVEDEADLINE_COMMAND_SHORT + " 500");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertRemovedeadlineSuccess(int editedIndex) throws IllegalValueException {
        String emptyDate = "";
        TestTask expectedResult =
                new TaskBuilder().withName("Alice Pauline").withDate("").withEndDate("").withCompleted(false).
                        withPriority("Low").build();

        commandBox.runCommand(REMOVEDEADLINE_COMMAND + " " + editedIndex);
        assertResultMessage(String.format(RemoveDeadlineCommand.MESSAGE_EDIT_TASK_SUCCESS, expectedResult));
        assertEquals(taskListPanel.getTask(editedIndex).getDate().value, emptyDate);
        assertEquals(taskListPanel.getTask(editedIndex).getEndDate().value, emptyDate);
    }
}
