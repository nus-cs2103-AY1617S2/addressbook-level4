package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.FindCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;

//@@author A0139343E
public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertNoFindResults(FindCommand.COMMAND_WORD + " Nemo", TaskType.DEADLINE);
        assertFindResult(FindCommand.COMMAND_WORD + " change", td.taskH.getTaskType(), td.taskH, td.taskI);
    }

    @Test
    public void find_afterDelete_foundItems() {
        //find after deleting one result taskH
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " t2");
        assertFindResult(FindCommand.COMMAND_WORD + " change", td.taskH.getTaskType(), td.taskI);
    }

    @Test
    public void find_tagKeywords_foundItems() {
        assertFindResult(FindCommand.COMMAND_WORD + " army", td.taskA.getTaskType(), td.taskA);
    }

    @Test
    public void find_descriptionKeyWords_foundItems() {
        assertFindResult(FindCommand.COMMAND_WORD + " cheap", td.taskG.getTaskType(), td.taskG);
    }

    @Test
    public void find_multipleKeywords_foundItems() {
        //find from main key words which include task name, desciption and tags.
        //also check if whitespace and comma can be detected
        commandBox.runCommand(FindCommand.COMMAND_WORD + " cheap, army submit");
        TestTask[] expectedTasks = { td.taskA, td.taskD, td.taskG };
        assertListSize(expectedTasks.length);
        assertResultMessage(expectedTasks.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(TaskType.EVENT, new TestTask[]{td.taskA}));
        assertTrue(taskListPanel.isListMatching(TaskType.DEADLINE, new TestTask[]{td.taskD}));
        assertTrue(taskListPanel.isListMatching(TaskType.TODO, new TestTask[]{td.taskG}));
    }

    @Test
    public void find_emptyList_notFound() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertNoFindResults(FindCommand.COMMAND_WORD + " Dory", TaskType.DEADLINE);
    }


    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("finds something");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertNoFindResults(String command, TaskType type) {
        assertFindResult(command, type);
    }

    private void assertFindResult(String command, TaskType type, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(type, expectedHits));
    }

}
