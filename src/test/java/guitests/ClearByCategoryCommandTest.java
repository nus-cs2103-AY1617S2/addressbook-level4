package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.ClearByCategoryCommand;
import seedu.taskboss.testutil.TestTask;

public class ClearByCategoryCommandTest  extends TaskBossGuiTest {

    @Test
    public void clearByCategory_existingCategory() {
        String categoryDetails = "c/friends";
        TestTask[] expectedTaskList =
                new TestTask[]{td.carl, td.daniel, td.elle, td.fiona, td.george};
        boolean isShortedCommand = false;
        assertClearSuccess(isShortedCommand, categoryDetails, expectedTaskList);
    }

    @Test
    public void clearByCategory_existingCategoryWithShortcut() {
        String categoryDetails = "c/friends";
        TestTask[] expectedTaskList =
                new TestTask[]{td.carl, td.daniel, td.elle, td.fiona, td.george};
        boolean isShortedCommand = true;
        assertClearSuccess(isShortedCommand, categoryDetails, expectedTaskList);
    }

    @Test
    public void clearByCategory_invalidCommand_fail() {
        commandBox.runCommand("clearare");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void clearByCategory_invalidCommandFormat_fail() {
        commandBox.runCommand("clear w/try");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearByCategoryCommand.MESSAGE_USAGE));
    }

    @Test
    public void clearByCategory_nonExistingCategory() {
        commandBox.runCommand("clear c/strange");
        assertResultMessage(String.format(ClearByCategoryCommand.MESSAGE_CATEGORY_NOT_FOUND));
    }

    //@@author A0147990R
    /**
     * Checks whether the edited task has the correct updated details.
     * @param isShortCommand
     * @param categoryDetails category to clear as input to the ClearCategoryCommand
     * @param expectedTaskList the expected task list after clearing tasks by category
     */
    private void assertClearSuccess(boolean isShortCommand, String categoryDetails,
            TestTask[] expectedTaskList) {
        if (isShortCommand) {
            commandBox.runCommand("c " + categoryDetails);
        } else {
            commandBox.runCommand("clear " + categoryDetails);
        }

        assertTrue(taskListPanel.isListMatching(expectedTaskList));
        assertResultMessage(String.format(ClearByCategoryCommand.MESSAGE_CLEAR_TASK_SUCCESS));
    }

}
