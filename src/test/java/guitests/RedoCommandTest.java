package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.jobs.logic.commands.RedoCommand.MESSAGE_SUCCESS;
import static seedu.jobs.logic.commands.RedoCommand.MESSAGE_FAILURE;

import org.junit.Test;
import java.util.EmptyStackException;
import java.util.Stack;

import guitests.guihandles.TaskCardHandle;
import javafx.collections.ObservableList;
import seedu.jobs.commons.core.Messages;
import seedu.jobs.logic.commands.RedoCommand;
import seedu.jobs.model.task.UniqueTaskList;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.model.FixedStack;
import seedu.jobs.testutil.TestTask;
import seedu.jobs.testutil.TestUtil;

//@@author A0140055W

public class RedoCommandTest extends TaskBookGuiTest {
	private final Stack<TestTask> testStack = new Stack<TestTask>();
	//private final FixedStack<ObservableList<Task>> testUndoStack = UniqueTaskList.getUndoStack();
	@Test
	public void redo() throws IllegalArgumentException, IllegalTimeException, EmptyStackException {
		commandBox.runCommand("redo");
		assertResultMessage(MESSAGE_FAILURE);
		TestTask[] currentList = td.getTypicalTasks();		
		// add a task for undoing to enable redoing
		TestTask taskToAdd = td.CS4101;
		commandBox.runCommand(td.CS4101.getAddCommand());
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);
		undoForRedo(currentList);
		assertRedoSuccess(currentList);
		undoForRedo(currentList);
		// add another task
		taskToAdd = td.CS4102;
		commandBox.runCommand(td.CS4102.getAddCommand());
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);
		assertRedoSuccess(currentList);
		assertRedoSuccess(currentList);
	}
	private void undoForRedo(TestTask[] currentList)
			throws IllegalArgumentException, IllegalTimeException, EmptyStackException {
		TestTask taskUndone = currentList[currentList.length - 1];
		testStack.push(taskUndone);
		commandBox.runCommand("undo");
	}
	private void assertRedoSuccess(TestTask[] currentList)
			throws IllegalArgumentException, IllegalTimeException, EmptyStackException {
		TestTask taskToBeRedone = testStack.pop();
		//commandBox.runCommand(taskToBeRedone.toString());
		TestTask[] expectedResult = TestUtil.addTasksToList(currentList, taskToBeRedone);
		commandBox.runCommand("redo");
		//commandBox.runCommand("current:"+currentList[currentList.length-1].toString());
		//commandBox.runCommand("expected:"+expectedResult[currentList.length-1].toString());
		// confirm the resultant list after redoing matches the original
		assertTrue(taskListPanel.isListMatching(expectedResult));
		// confirm that the result message is correct
        assertResultMessage(String.format(MESSAGE_SUCCESS, taskToBeRedone));
	}
}