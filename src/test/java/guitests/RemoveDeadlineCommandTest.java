package guitests;

import static org.junit.Assert.assertEquals;
import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.RemoveDeadlineCommand;
import typetask.testutil.TaskBuilder;
import typetask.testutil.TestTask;

//@@author A0139926R
public class RemoveDeadlineCommandTest extends AddressBookGuiTest {

    private static final String REMOVEDEADLINE_COMMAND = "removedeadline";
    private static final String ADD_DEADLINE_TO_EXISTING = "edit 1 by: tmr";

    @Test
    public void removedeadline_success() throws IllegalValueException {
        int editIndex = 1;
        commandBox.runCommand(ADD_DEADLINE_TO_EXISTING);
        assertRemovedeadlineSuccess(editIndex);
    }
    @Test
    public void removedeadline_fail() {
        commandBox.runCommand(REMOVEDEADLINE_COMMAND);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveDeadlineCommand.MESSAGE_USAGE));
    }

    private void assertRemovedeadlineSuccess(int editedIndex) throws IllegalValueException {
        String emptyDate = "";
        TestTask expectedResult =
                new TaskBuilder().withName("Alice Pauline").withDate("").withEndDate("").withCompleted(false).withPriority("Low").build();

        commandBox.runCommand(REMOVEDEADLINE_COMMAND + " " + editedIndex);
        assertResultMessage(String.format(RemoveDeadlineCommand.MESSAGE_EDIT_TASK_SUCCESS, expectedResult));
        assertEquals(personListPanel.getPerson(editedIndex).getDate().value, emptyDate);
        assertEquals(personListPanel.getPerson(editedIndex).getEndDate().value, emptyDate);
    }
}
