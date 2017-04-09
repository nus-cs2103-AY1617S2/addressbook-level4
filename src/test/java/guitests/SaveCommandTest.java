package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import org.junit.Test;

import seedu.whatsleft.logic.commands.SaveCommand;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0121668A
public class SaveCommandTest extends WhatsLeftGuiTest {
    @Test
    public void saveCommand_validFilePath_saveSuccessful() {

        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.getFilteredTestEvents(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.getFilteredTestTasks(currentTaskList);

        /** Save a file from the designated filepath */
        assertSaveWhatsLeftSuccessful("./src/test/data/SaveCommandTest/TypicalWhatsLeft.xml",
                currentTaskList, currentEventList);
    }

    @Test
    public void saveCommand_invalidFilePath_invalidCommandMessageShown() {
        assertInvalidSaveCommandMessageShown(""); // no filename
        assertInvalidSaveCommandMessageShown("./Data"); // filename without .xml
        assertInvalidSaveCommandMessageShown("./Data/^%&^.xml"); // invalid //
                                                              // filename
    }

    /**
     * Assert Saving a WhatsLeft file is unsuccessful
     *
     * @param filePath
     */
    private void assertInvalidSaveCommandMessageShown(String filePath) {
        commandBox.runCommand("save " + filePath);
        String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE);
        ;
        assertEquals(expected, resultDisplay.getText());
    }

    /**
     * Assert saving a file to a location is successful and delete the saved
     * file
     *
     * @param filePathToSave
     * @param currentTaskList
     * @param currentEventList
     */
    private void assertSaveWhatsLeftSuccessful(String filePathToSave, TestTask[] currentTaskList,
            TestEvent[] currentEventList) {
        commandBox.runCommand("save " + filePathToSave);
        commandBox.runCommand("read " + filePathToSave);
        assertTrue(eventListPanel.isListMatching(currentEventList));
        assertTrue(taskListPanel.isListMatching(currentTaskList));
        File file = new File(filePathToSave);
        file.delete();
    }
}
