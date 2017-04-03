//@@author A0138909R
package guitests;

import java.util.Arrays;

import org.junit.Test;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.DoneCommand;
import seedu.doit.logic.commands.ListCommand;
import seedu.doit.logic.commands.MarkCommand;
import seedu.doit.model.comparators.TaskNameComparator;
import seedu.doit.testutil.TaskBuilder;
import seedu.doit.testutil.TestTask;

public class ListCommandTest extends TaskManagerGuiTest {
    TestTask[] currentList = this.td.getTypicalTasks();

    @Test
    public void list_Success() throws IllegalValueException {
        assertListSuccess();
    }

    public void assertListSuccess() throws IllegalValueException {

        this.commandBox.runCommand(DoneCommand.COMMAND_WORD);
        this.commandBox.runCommand(ListCommand.COMMAND_WORD);
        TestTask taskToMark = this.currentList[MarkCommandTest.INDEX_MARK_VALID - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withIsDone(true).build();
        assertMarkSuccess(MarkCommandTest.INDEX_MARK_VALID, MarkCommandTest.INDEX_MARK_VALID, markedTask);
    }

    private void assertMarkSuccess(int filteredTaskListIndex, int taskManagerIndex, TestTask markedTask) {

        this.commandBox.runCommand(MarkCommandTest.MESSAGE_MARK_COMMAND + filteredTaskListIndex);

        // confirm the list now contains all previous tasks plus the task with
        // updated isDone variable
        this.currentList[taskManagerIndex - 1] = markedTask;
        Arrays.sort(this.currentList, new TaskNameComparator());

        assertAllPanelsMatch(this.currentList);
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, markedTask));

    }
}
