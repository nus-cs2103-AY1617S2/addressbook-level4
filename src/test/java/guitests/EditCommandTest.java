//@@author A0113795Y
package guitests;
import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.EditCommand;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.RecurringFrequency;
import seedu.task.model.task.RecurringTaskOccurrence;
import seedu.task.model.task.Task;
import seedu.task.model.task.Timing;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends AddressBookGuiTest {

    @Test
     public void editAllFieldsSpecifiedSuccessForNormalTask() throws Exception {
        TestTask[] expectedTasksList = td.getTypicalTasks();
        String detailsToEdit = "Bobby p/2 sd/08/04/2017 ed/09/04/2017 t/date";
        int taskListIndex = 1;

        commandBox.runCommand("clear");
        commandBox.runCommand(expectedTasksList[0].getAddCommand());
        TestTask editedTask = new
                 TaskBuilder().withDescription("Bobby")
                 .withPriority("2")
                 .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                 .withFrequency(null)
                 .withStartTiming("08/04/2017")
                 .withEndTiming("09/04/2017")
                 .withTags("date")
                 .build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit,
                 editedTask, expectedTasksList);
    }

    @Test
    public void editAllFieldsSepcifiedSuccessForRecurringTask() throws Exception {
        String detailsToEdit = "Bobby p/2 sd/08/04/2017 ed/09/04/2017 t/date";
        TestTask taskToEdit = td.recMonth;
        commandBox.runCommand("clear");
        commandBox.runCommand(taskToEdit.getAddCommand());
        commandBox.runCommand("editthis 1 " + detailsToEdit);

        Task editedTask = new Task(new Description("Bobby"), new Priority("2"),
                new Timing("08/04/2017"), new Timing("09/04/2017"),
                new UniqueTagList("date"), false, new RecurringFrequency(null));

        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getDescription().description);
        assertMatching(editedTask, editedCard);

        commandBox.runCommand("find 01/01/2017");
        assertResultMessage("0 tasks listed!");

        // Check the first occurrence
        commandBox.runCommand("find 01/03/2017");
        assertResultMessage("1 tasks listed!");

        // Check the second occurrence
        commandBox.runCommand("find 01/05/2017");
        assertResultMessage("1 tasks listed!");

        // Check the third occurrence
        commandBox.runCommand("find 01/07/2017");
        assertResultMessage("1 tasks listed!");

        // Check the fourth occurrence
        commandBox.runCommand("find 01/09/2017");
        assertResultMessage("1 tasks listed!");

        // Check the fifth occurrence
        commandBox.runCommand("find 01/11/2017");
        assertResultMessage("1 tasks listed!");

        // Check the sixth occurrence
        commandBox.runCommand("find 01/01/2018");
        assertResultMessage("1 tasks listed!");
    }

    @Test
    public void completeRecurring() throws DuplicateTagException, IllegalValueException {
        TestTask taskToComplete = td.recMonth;
        commandBox.runCommand("clear");
        commandBox.runCommand(taskToComplete.getAddCommand());
        commandBox.runCommand("complete 1");

        Task editedTask = new Task(taskToComplete.getDescription(), taskToComplete.getPriority(),
                taskToComplete.getStartTiming(), taskToComplete.getEndTiming(),
                new UniqueTagList("complete"), false, new RecurringFrequency(null));

        commandBox.runCommand("find 01/01/2017");
        assertResultMessage("1 tasks listed!");
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getDescription().description);
        assertMatching(editedTask, editedCard);

        // Check the first occurrence
        commandBox.runCommand("find 01/03/2017");
        assertResultMessage("1 tasks listed!");

        // Check the second occurrence
        commandBox.runCommand("find 01/05/2017");
        assertResultMessage("1 tasks listed!");

        // Check the third occurrence
        commandBox.runCommand("find 01/07/2017");
        assertResultMessage("1 tasks listed!");

        // Check the fourth occurrence
        commandBox.runCommand("find 01/09/2017");
        assertResultMessage("1 tasks listed!");

        // Check the fifth occurrence
        commandBox.runCommand("find 01/11/2017");
        assertResultMessage("1 tasks listed!");

        // Check the sixth occurrence
        commandBox.runCommand("find 01/01/2018");
        assertResultMessage("1 tasks listed!");
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int taskListIndex = 1;

        commandBox.runCommand("clear");
        TestTask[] expectedTasksList = td.getTypicalTasks();
        for (int i = 0; i < expectedTasksList.length; i++) {
            commandBox.runCommand(expectedTasksList[i].getAddCommand());
        }
        TestTask taskToEdit = expectedTasksList[taskListIndex - 1];
        TestTask editedTask = new
                 TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit,
                 editedTask, expectedTasksList);
    }

    @Test
     public void edit_missingPersonIndex_failure() {
        commandBox.runCommand("clear");
        TestTask taskToEdit = td.fiona;
        commandBox.runCommand(taskToEdit.getAddCommand());
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                 EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidPersonIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    /**
    * Checks whether the edited person has the correct updated details.
    *
    * @param filteredTaskListIndex index of person to edit in filtered list
    * @param taskListIndex index of person to edit in the address book.
    * Must refer to the same person as {@code filteredPersonListIndex}
    * @param detailsToEdit details to edit the person with as input to the
    edit command
    * @param editedPerson the expected person after editing the person's
    details
    */
    private void assertEditSuccess(int filteredTaskListIndex, int
             taskListIndex, String detailsToEdit, TestTask editedTask,
             TestTask[] expectedTasksList) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " +
                 detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard =
                 taskListPanel.navigateToTask(editedTask.getDescription().description);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous persons plus the person
        // with updated details
        expectedTasksList[taskListIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS,
                 editedTask));
    }
}
