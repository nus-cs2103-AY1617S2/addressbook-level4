package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.logic.commands.RenameCategoryCommand;
import seedu.taskboss.logic.parser.RenameCategoryCommandParser;
import seedu.taskboss.model.task.Recurrence.Frequency;
import seedu.taskboss.testutil.TaskBuilder;
import seedu.taskboss.testutil.TestTask;

//@@author A0144904H
public class RenameCategoryCommandTest extends TaskBossGuiTest {

    @Test
    public void renameCategory_Long_Command_success() throws IllegalValueException {
        assertRenameCategoryResult("rename friends Project");
    }

    @Test
    public void renameCategory_Short_Command_success() throws IllegalValueException {
        assertRenameCategoryResult("r friends Project");
    }

    private void assertRenameCategoryResult(String command) throws IllegalValueException {
        TestTask sampleA;
        TestTask sampleB;
        sampleA = new TaskBuilder().withName("Attend wedding")
                .withInformation("123, Jurong West Ave 6, #08-111")
                .withPriorityLevel("Yes")
                .withStartDateTime("Feb 18, 2017 5pm")
                .withEndDateTime("Mar 28, 2017 5pm")
                .withRecurrence(Frequency.NONE)
                .withCategories(AddCommand.DEFAULT_ALL_TASKS, "Project").build();
        sampleB = new TaskBuilder().withName("Birthday party")
                .withInformation("311, Clementi Ave 2, #02-25")
                .withPriorityLevel("No")
                .withStartDateTime("Feb 23, 2017 10pm")
                .withEndDateTime("Jun 28, 2017 5pm")
                .withRecurrence(Frequency.NONE)
                .withCategories("owesMoney", "Project").build();

        TestTask[] taskListExpected = {sampleA, sampleB};
        commandBox.runCommand(command);
        assertResultMessage(RenameCategoryCommand.MESSAGE_SUCCESS);
        assertTrue(taskListPanel.isListMatching(taskListExpected));
    }


    @Test
    public void rename_unsuccessful() {

        //old category name == new category name
        commandBox.runCommand("rename friends friends");
        assertResultMessage(RenameCategoryCommandParser.ERROR_SAME_FIELDS);

        //invalid number of fields
        commandBox.runCommand("rename friends bestfriends forever");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameCategoryCommand.MESSAGE_USAGE));

        //category name with a single non-alphanumerical character
        commandBox.runCommand("rename owesMoney myMoney!");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RenameCategoryCommandParser.ERROR_NON_ALPHANUMERIC));

        //category name with all non-alphanumerical characters
        commandBox.runCommand("rename owesMoney !!!");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RenameCategoryCommandParser.ERROR_NON_ALPHANUMERIC));

        //category name does not exist
        commandBox.runCommand("rename superman batman");
        assertResultMessage("[superman] " + RenameCategoryCommand.MESSAGE_DOES_NOT_EXIST_CATEGORY);

        //category name is AllTasks
        commandBox.runCommand("rename AllTasks batman");
        assertResultMessage(RenameCategoryCommand.MESSAGE_ALL_TASK_CATEGORY_CANNOT_RENAME);

        //category name is Done
        commandBox.runCommand("rename Done batman");
        assertResultMessage(RenameCategoryCommand.MESSAGE_DONE_CATEGORY_CANNOT_RENAME);

        //rename category to Done
        commandBox.runCommand("rename owesMoney Done");
        assertResultMessage(RenameCategoryCommand.MESSAGE_CATEGORY_CANNOT_RENAME_TO_DONE);

        //rename category to AllTasks
        commandBox.runCommand("rename owesMoney AllTasks");
        assertResultMessage(RenameCategoryCommand.MESSAGE_CATEGORY_CANNOT_RENAME_TO_ALL_TASKS);

        //duplicate category
        commandBox.runCommand("rename owesMoney friends");
        assertResultMessage(RenameCategoryCommand.MESSAGE_DUPLICATE_CATEGORY);
    }
}
