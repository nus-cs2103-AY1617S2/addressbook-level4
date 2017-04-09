//@@author A0139925U
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.CompleteCommand;
import seedu.tache.testutil.TestTask;

public class CompleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void complete_allTasks_success() {
        commandBox.runCommand("list");
        commandBox.runCommand("complete 1,2,3,4,5");
        TestTask[] completedHits = td.getTypicalTasks();
        ArrayUtils.reverse(completedHits);
        assertCompleteMessage(completedHits);
        assertCompleteResult("list uncompleted", new TestTask[0]);
    }

    @Test
    public void complete_oneTask_success() {
        commandBox.runCommand("list");
        commandBox.runCommand("complete 1");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> expectedTasksList = new ArrayList<TestTask>();
        for (int i = 0; i < tempTasksList.length - 1; i++) {
            expectedTasksList.add(tempTasksList[i + 1]);
        }
        assertCompleteMessage(tempTasksList[0]);
        assertCompleteResult("list uncompleted", expectedTasksList.toArray(new TestTask[0]));
    }

    @Test
    public void complete_someTasks_success() {
        commandBox.runCommand("list");
        commandBox.runCommand("complete 1,3,5");
        TestTask[] tempTasksList = td.getTypicalTasks();
        ArrayList<TestTask> completedTasksList = new ArrayList<TestTask>();
        completedTasksList.add(tempTasksList[4]);
        completedTasksList.add(tempTasksList[2]);
        completedTasksList.add(tempTasksList[0]);
        ArrayList<TestTask> uncompletedTasksList = new ArrayList<TestTask>();
        uncompletedTasksList.add(tempTasksList[1]);
        uncompletedTasksList.add(tempTasksList[3]);
        assertCompleteMessage(completedTasksList.toArray(new TestTask[0]));
        assertCompleteResult("list uncompleted", uncompletedTasksList.toArray(new TestTask[0]));
    }

    @Test
    public void complete_noTasks_failure() {
        commandBox.runCommand("complete ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void complete_invalidIndex_failure() {
        commandBox.runCommand("complete a");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));

        commandBox.runCommand("complete -1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));

        commandBox.runCommand("complete 9999");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void complete_invalidCommand_failure() {
        commandBox.runCommand("completekkjksjkds");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    //@@author A0142255M
    @Test
    public void complete_shortCommand_success() {
        commandBox.runCommand(CompleteCommand.SHORT_COMMAND_WORD + " 1");
        assertCompleteMessage(td.payDavid);
        assertCompleteResult("list completed", td.payDavid);
    }

    //@@author A0139925U
    private void assertCompleteResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

    private void assertCompleteMessage(TestTask... expectedHits) {
        assert expectedHits != null;
        String successMessage = "";
        for (int i = 0; i < expectedHits.length; i++) {
            successMessage += expectedHits[i].toString();
        }
        assertResultMessage(String.format(CompleteCommand.MESSAGE_COMPLETED_TASK_SUCCESS, successMessage));
    }
}
