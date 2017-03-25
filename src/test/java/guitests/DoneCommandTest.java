package guitests;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.Test;

import seedu.doit.commons.core.Messages;
import seedu.doit.logic.commands.DoneCommand;
import seedu.doit.model.item.TaskNameComparator;
import seedu.doit.testutil.TaskBuilder;
import seedu.doit.testutil.TestTask;

//@@author A0139399J
public class DoneCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private TestTask[] expectedTasksList = this.td.getTypicalTasks();

    @Test
    public void mark_task_success() throws Exception {
        int taskManagerIndex = 2;

        TestTask taskToMark = this.expectedTasksList[taskManagerIndex - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withIsDone(true).build();

        assertDoneSuccess(taskManagerIndex, taskManagerIndex, markedTask);
    }

    @Test
    public void mark_task_failure() throws Exception {
        int taskManagerIndex = -2;
        this.commandBox.runCommand("done " + taskManagerIndex);

        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        this.commandBox.runCommand("find Elle");

        int filteredTaskListIndex = 1;
        int taskManagerIndex = 5;
        TestTask taskToMark = this.expectedTasksList[taskManagerIndex - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withIsDone(true).build();

        assertDoneSuccess(filteredTaskListIndex, taskManagerIndex, markedTask);
    }

    @Test
    public void edit_findThenEdit_failure() throws Exception {
        this.commandBox.runCommand("find Elle");
        int filteredTaskListIndex = 5;
        this.commandBox.runCommand("done " + filteredTaskListIndex);

        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }





    private void assertDoneSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                   TestTask markedTask) {

        this.commandBox.runCommand("done " + filteredTaskListIndex);


        // confirm the list now contains all previous tasks plus the task with
        // updated isDone variable
        this.expectedTasksList[taskManagerIndex - 1] = markedTask;
        Arrays.sort(this.expectedTasksList, new TaskNameComparator());

        assertAllPanelsMatch(this.expectedTasksList);
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, markedTask));

    }

}
