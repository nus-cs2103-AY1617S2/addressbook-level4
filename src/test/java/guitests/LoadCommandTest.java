// @@author A0138909R
package guitests;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.doit.commons.exceptions.DataConversionException;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.LoadCommand;
import seedu.doit.logic.commands.SaveCommand;
import seedu.doit.testutil.TestTask;
import seedu.doit.testutil.TestUtil;
import seedu.doit.testutil.TypicalTestTasks;

public class LoadCommandTest extends TaskManagerGuiTest {
    private static final String GUI_ALTERNATE_XML_FILE = "guitest1.xml";
    private static final String ALTERNATE_XML = TestUtil.SANDBOX_FOLDER + GUI_ALTERNATE_XML_FILE;

    @Test
    public void load() throws DataConversionException, IOException, IllegalValueException {
        TestTask[] currentList = this.td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        this.commandBox.runCommand(taskToAdd.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        this.commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + ALTERNATE_XML);
        this.commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + getDataFileLocation());

        TestTask[] expectedList = currentList;
        assertAllPanelsMatch(currentList);

        // add another task
        taskToAdd = TypicalTestTasks.getDeadlineTestTask();
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        this.commandBox.runCommand(taskToAdd.getAddCommand());
        this.commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + ALTERNATE_XML);
        assertAllPanelsMatch(expectedList);

        // add another event
        taskToAdd = TypicalTestTasks.getEventTestTask();
        this.commandBox.runCommand(taskToAdd.getAddCommand());
        this.commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + getDataFileLocation());
        assertAllPanelsMatch(currentList);
        File file = new File(ALTERNATE_XML);
        file.delete();
    }
}
