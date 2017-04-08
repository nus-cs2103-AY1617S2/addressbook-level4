package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import t09b1.today.commons.core.Messages;
import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.logic.commands.EditCommand;
import t09b1.today.model.tag.UniqueTagList;
import t09b1.today.model.task.Name;
import t09b1.today.model.task.Task;
import t09b1.today.testutil.TestUtil;

// @author A0093999Y
public class EditCommandTest extends TaskManagerGuiTest {

    @Test
    public void edit() throws Exception {
        // Edit Today Task with Tags
        commandBox.runCommand("edit T3 #lol #wp");
        Task taskToEdit = Task.createTask(td.todayListDeadline);
        taskToEdit.setTags(new UniqueTagList("lol", "wp"));
        todayList = TestUtil.replaceTaskFromList(todayList, taskToEdit, 2);

        assertTodayEditSuccess(taskToEdit, todayList, futureList, completedList);

        // Edit Future Task with Name and Tag
        commandBox.runCommand("edit F1 Find Bobby #child");
        taskToEdit = Task.createTask(td.futureListFloat);
        taskToEdit.setName(new Name("Find Bobby"));
        taskToEdit.setTags(new UniqueTagList("child"));
        futureList = TestUtil.replaceTaskFromList(futureList, taskToEdit, 0);

        assertFutureEditSuccess(taskToEdit, todayList, futureList, completedList);

        // Edit Completed Task, remove tags
        commandBox.runCommand("edit C1 #");
        taskToEdit = Task.createTask(td.completedListFloat);
        taskToEdit.setTags(new UniqueTagList());
        completedList = TestUtil.replaceTaskFromList(completedList, taskToEdit, 0);

        assertCompletedEditSuccess(taskToEdit, todayList, futureList, completedList);
    }

    @Test
    public void edit_fail() throws Exception {
        // ~~~ Failure Tests ~~~
        commandBox.runCommand("edit Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_COMMAND_FORMAT);

        commandBox.runCommand("edit F8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("edit F1");
        assertResultMessage(Messages.MESSAGE_INVALID_COMMAND_FORMAT);

        commandBox.runCommand("edit F1 " + td.todayListFloat.getName().toString() + " #helo");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);

        commandBox.runCommand("edit F1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit F1 #*&");
        assertResultMessage(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find do");
        commandBox.runCommand("edit T2 Dancing time #party #fun");
        commandBox.runCommand("list");
        Task taskToEdit = Task.createTask(td.todayListToday);
        taskToEdit.setName(new Name("Dancing time"));
        taskToEdit.setTags(new UniqueTagList("party", "fun"));
        todayList = TestUtil.replaceTaskFromList(todayList, taskToEdit, 4);

        assertTodayEditSuccess(taskToEdit, todayList, futureList, completedList);

    }

    // ----- Helper -----

    private void assertTodayEditSuccess(Task taskToEdit, Task[] expectedTodayList, Task[] expectedFutureList,
            Task[] expectedCompletedList) throws IllegalArgumentException, IllegalValueException {
        // confirm the new card contains the right data
        TaskCardHandle addedCard = todayTaskListPanel.navigateToTask(taskToEdit.getName().toString());
        assertMatching(taskToEdit, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        assertAllListsMatching(expectedTodayList, expectedFutureList, expectedCompletedList);
    }

    private void assertFutureEditSuccess(Task taskToEdit, Task[] expectedTodayList, Task[] expectedFutureList,
            Task[] expectedCompletedList) throws IllegalArgumentException, IllegalValueException {
        // confirm the new card contains the right data
        TaskCardHandle addedCard = futureTaskListPanel.navigateToTask(taskToEdit.getName().toString());
        assertMatching(taskToEdit, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        assertAllListsMatching(expectedTodayList, expectedFutureList, expectedCompletedList);
    }

    private void assertCompletedEditSuccess(Task taskToEdit, Task[] expectedTodayList, Task[] expectedFutureList,
            Task[] expectedCompletedList) throws IllegalArgumentException, IllegalValueException {
        // confirm the new card contains the right data
        commandBox.runCommand("listcompleted");
        TaskCardHandle addedCard = completedTaskListPanel.navigateToTask(taskToEdit.getName().toString());
        assertMatching(taskToEdit, addedCard);
        commandBox.runCommand("list");

        // confirm the list now contains all previous tasks plus the new task
        assertAllListsMatching(expectedTodayList, expectedFutureList, expectedCompletedList);
    }

}
