// @@author A0139399J
package guitests;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.Test;

import seedu.doit.commons.core.Messages;
import seedu.doit.logic.commands.MarkCommand;
import seedu.doit.model.comparators.TaskNameComparator;
import seedu.doit.testutil.TaskBuilder;
import seedu.doit.testutil.TestTask;

public class MarkCommandTest extends TaskManagerGuiTest {

    public static final String MESSAGE_MARK_COMMAND = MarkCommand.COMMAND_WORD + " ";
    public static final String MESSAGE_TEST_FIND_COMMAND = "find n/Elle";

    public static final int INDEX_MARK_VALID = 2;
    public static final int INDEX_MARK_INVALID = -2;

    public static final int INDEX_FIND_VALID = 5;
    public static final int INDEX_FIND_VALID_FILTERED = 1;
    public static final int INDEX_FIND_INVALID_FILTERED = 5;

    // The list of tasks in the task list panel is expected to match this list.
    private TestTask[] expectedTasksList = this.td.getTypicalTasks();

    @Test
    public void mark_task_success() throws Exception {
        TestTask taskToMark = this.expectedTasksList[INDEX_MARK_VALID - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withIsDone(true).build();
        assertMarkSuccess(INDEX_MARK_VALID, INDEX_MARK_VALID, markedTask);
    }

    @Test
    public void mark_task_invalid_index_failure() throws Exception {
        this.commandBox.runCommand(MESSAGE_MARK_COMMAND + INDEX_MARK_INVALID);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void mark_findThenMark_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_FIND_COMMAND);
        TestTask taskToMark = this.expectedTasksList[INDEX_FIND_VALID - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withIsDone(true).build();
        assertMarkSuccess(INDEX_FIND_VALID_FILTERED, INDEX_FIND_VALID, markedTask);
    }

    @Test
    public void mark_findThenMark_failure() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_FIND_COMMAND);
        this.commandBox.runCommand(MESSAGE_MARK_COMMAND + INDEX_FIND_INVALID_FILTERED);

        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertMarkSuccess(int filteredTaskListIndex, int taskManagerIndex, TestTask markedTask) {

        this.commandBox.runCommand(MESSAGE_MARK_COMMAND + filteredTaskListIndex);

        // confirm the list now contains all previous tasks plus the task with
        // updated isDone variable
        this.expectedTasksList[taskManagerIndex - 1] = markedTask;
        Arrays.sort(this.expectedTasksList, new TaskNameComparator());

        assertAllPanelsMatch(this.expectedTasksList);
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, markedTask));

    }

}
