package guitests;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;
import seedu.ezdo.testutil.TypicalTestTasks;

public class SortCommandTest extends EzDoGuiTest {

    protected EzDo getInitialData() {
        EzDo ez = new EzDo();
        TypicalTestTasks.loadEzDoWithSampleData(ez);
        try {
            ez.addTask(new Task(td.kappa));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
        return ez;
    }

    @Test
    public void sortByFields() {
        TestTask[] expectedList;

        // sort by start date
        commandBox.runCommand("sort s");
        expectedList = new TestTask[]{td.elle, td.george, td.daniel, td.carl, td.benson, td.fiona, td.alice, td.kappa};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // sort by due date
        commandBox.runCommand("s d");
        expectedList = new TestTask[]{td.carl, td.elle, td.george, td.daniel, td.fiona, td.benson, td.alice, td.kappa};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // sort by name
        commandBox.runCommand("sort n");
        expectedList = new TestTask[]{td.alice, td.benson, td.carl, td.daniel, td.elle, td.fiona, td.george, td.kappa};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // sort by priority
        commandBox.runCommand("s p");
        expectedList = new TestTask[]{td.alice, td.benson, td.carl, td.daniel, td.fiona, td.elle, td.george, td.kappa};
        assertTrue(taskListPanel.isListMatching(expectedList));

        //invalid command
        commandBox.runCommand("sort blah");
        assertResultMessage(SortCommand.MESSAGE_INVALID_FIELD);
    }

    @Test
    public void maintainSortOrder() {
        TestTask[] expectedList;

        commandBox.runCommand("sort d");

        // check that Jack is in the right place after adding him
        commandBox.runCommand(td.jack.getAddCommand(false));
        expectedList = new TestTask[]{td.carl, td.elle, td.george, td.jack, td.daniel, td.fiona, td.benson, td.alice,
                                      td.kappa};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // check that Carl is in the right place after editing his due date
        commandBox.runCommand("edit 1 d/12/12/2099");
        String expectedName = taskListPanel.getTask(7).getName().toString();
        assertTrue(expectedName.equals(td.carl.getName().toString()));
    }
}
