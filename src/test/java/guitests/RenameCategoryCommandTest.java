package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.RenameCategoryCommand;
import seedu.taskboss.logic.parser.RenameCategoryCommandParser;
import seedu.taskboss.testutil.TaskBuilder;
import seedu.taskboss.testutil.TestTask;

//@@author A0144904H
public class RenameCategoryCommandTest extends TaskBossGuiTest {

    @Test
    public void renameCategory_Long_Command_success() throws IllegalValueException {
        TestTask sampleA = td.alice;
        TestTask sampleB = td.benson;
        TestTask[] taskList= {sampleA, sampleB};
        assertRenameCategoryResult(false, "name friends Project", taskList);
    }

    @Test
    public void renameCategory_Short_Command_success() throws IllegalValueException {
        TestTask sampleA = td.alice;
        TestTask sampleB = td.benson;
        TestTask[] taskList = {sampleA, sampleB};
        assertRenameCategoryResult(true, "n owesMoney Project", taskList);
    }

    private void assertRenameCategoryResult(boolean isShort,
                   String command, TestTask[] taskList) throws IllegalValueException {
        TestTask sampleA;
        TestTask sampleB;
        if (!isShort) {
            sampleA = new TaskBuilder().withName("Alice Pauline")
                    .withInformation("123, Jurong West Ave 6, #08-111")
                    .withPriorityLevel("Yes")
                    .withStartDateTime("Feb 18, 2017 5pm")
                    .withEndDateTime("Mar 28, 2017 5pm")
                    .withCategories("Project").build();
            sampleB = new TaskBuilder().withName("Benson Meier")
                    .withInformation("311, Clementi Ave 2, #02-25")
                    .withPriorityLevel("No")
                    .withStartDateTime("Feb 23, 2017 10pm")
                    .withEndDateTime("Jun 28, 2017 5pm")
                    .withCategories("owesMoney", "Project").build();
        } else {
            sampleA = new TaskBuilder().withName("Alice Pauline")
                    .withInformation("123, Jurong West Ave 6, #08-111")
                    .withPriorityLevel("Yes")
                    .withStartDateTime("Feb 18, 2017 5pm")
                    .withEndDateTime("Mar 28, 2017 5pm")
                    .withCategories("friends").build();
            sampleB = new TaskBuilder().withName("Benson Meier")
                    .withInformation("311, Clementi Ave 2, #02-25")
                    .withPriorityLevel("No")
                    .withStartDateTime("Feb 23, 2017 10pm")
                    .withEndDateTime("Jun 28, 2017 5pm")
                    .withCategories("Project", "friends").build();
        }

        TestTask[] taskListExpected = {sampleA, sampleB};
        commandBox.runCommand(command);
        assertResultMessage(RenameCategoryCommand.MESSAGE_SUCCESS);
        assertTrue(taskListPanel.isListMatching(taskListExpected));
    }

    @Test
    public void rename_unsuccessful() {
        //old category name == new category name
        commandBox.runCommand("name friends friends");
        assertResultMessage(RenameCategoryCommandParser.ERROR_SAME_FIELDS);

        //invalid number of fields
        commandBox.runCommand("name friends bestfriends forever");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameCategoryCommand.MESSAGE_USAGE));

        //category name with a single non-alphanumerical character
        commandBox.runCommand("name owesMoney myMoney!");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RenameCategoryCommandParser.ERROR_NON_ALPHANUMERIC));

        //category name with all non-alphanumerical characters
        commandBox.runCommand("name owesMoney !!!");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RenameCategoryCommandParser.ERROR_NON_ALPHANUMERIC));

        //specified category to be re-named does not exist
        commandBox.runCommand("name superman batman");
        assertResultMessage("[superman] " + RenameCategoryCommand.MESSAGE_DOES_NOT_EXIST_CATEGORY);
    }
}
