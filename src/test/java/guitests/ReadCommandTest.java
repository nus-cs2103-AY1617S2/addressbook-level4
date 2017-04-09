package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.logic.commands.ReadCommand;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0121668A
public class ReadCommandTest extends WhatsLeftGuiTest {

    @Test
    public void readWhatsLeftSuccess() {

        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.getFilteredTestEvents(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.getFilteredTestTasks(currentTaskList);

        /** Read a file from the designated filepath */
        readCommand_validFilePath_readSuccessful("./src/test/data/ReadCommandTest/TypicalWhatsLeft.xml",
                currentTaskList, currentEventList);
    }

    @Test
    public void readWhatsLeftFailure() {
        readCommand_invalidFilePath_invalidCommandMessageShown(""); // no filename
        readCommand_invalidFilePath_invalidCommandMessageShown("./Data"); // filename without .xml
        readCommand_invalidFilePath_invalidCommandMessageShown("./Data/^%&^.xml"); // invalid
                                                              // filename
        readCommand_invalidFormat_dataConversionFailure("./src/test/data/ReadCommandTest/WrongFormat.xml");
    }

    /**
     * Assert reading a WhatsLeft file in wrong format is unsuccessful
     *
     * @param filePathToRead
     */
    private void readCommand_invalidFormat_dataConversionFailure(String filePathToRead) {
        commandBox.runCommand("read " + filePathToRead);
        String expected = Messages.MESSAGE_DATA_CONVERSION_FAILURE;
        assertEquals(expected, resultDisplay.getText());
    }

    /**
     * Assert reading a WhatsLeft file from invalid filepath is unsuccessful
     *
     * @param filePath
     */
    private void readCommand_invalidFilePath_invalidCommandMessageShown(String filePathToRead) {
        commandBox.runCommand("read " + filePathToRead);
        String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadCommand.MESSAGE_USAGE);
        assertEquals(expected, resultDisplay.getText());
    }

    /**
     * Assert reading a WhatsLeft file from designated filepath is successful
     *
     * @param filePath
     *            to read from
     * @param tasklist
     * @param eventslist
     */
    private void readCommand_validFilePath_readSuccessful(String filePathToRead,
            TestTask[] tasklist, TestEvent[] eventslist) {
        TestTask[] expectedTasks = TestUtil.removeTaskFromList(tasklist, 1);
        TestEvent[] expectedEvents = eventslist;

        commandBox.runCommand("read " + filePathToRead);
        assertTrue(eventListPanel.isListMatching(expectedEvents));
        assertTrue(taskListPanel.isListMatching(expectedTasks));
    }
}
