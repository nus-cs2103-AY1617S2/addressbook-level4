package seedu.doist.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.SaveAtCommand;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.ConfigManager;

//@@author A0140887W
public class SaveAsParserTest {

    @Test
    public void saveAsParser() {
        // a normal relative path
        assertCorrectPath("hello");
        // with new line and space
        assertCorrectPath("/nhello   ");
        // relative path that goes back to drive
        assertCorrectPath("/hello");
        // relative path that goes back to parent folder
        assertCorrectPath("../hello");
        //absolute path
        assertCorrectPath("C:/hello");
        //path with symbols file name
        assertCorrectPath("e@_()()2h2jnuxjdjFso");

        //existing file
        File file = null;
        try {
            file = File.createTempFile("hello", "");
            file.deleteOnExit();
            assertFileExists(file.getAbsolutePath());
        } catch (IOException e) {
            fail();
        }

        //Empty path
        assertIncorrectPath("");
        // spaces and new line
        assertIncorrectPath("  \n");
    }


    public void assertCorrectPath(String path) {
        assertPath(path, false, false);
    }

    public void assertIncorrectPath(String path) {
        assertPath(path, true, false);
    }

    public void assertFileExists(String path) {
        assertPath(path, true, true);
    }

    /**
     * Uses SaveAtCommandParser to parse the path then calls {@link #assertFeedback}
     */
    public void assertPath(String path, boolean isCommandExceptionExpected, boolean isFileExist) {
        SaveAtCommandParser parser = new SaveAtCommandParser();
        Command command = parser.parse(path);
        if (isFileExist) {
            assertFeedback(command, SaveAtCommand.MESSAGE_FILE_EXISTS, isCommandExceptionExpected);
        } else {
            assertFeedback(command, String.format(SaveAtCommand.MESSAGE_INVALID_PATH,
                    SaveAtCommand.MESSAGE_USAGE), isCommandExceptionExpected);
        }
    }

    /**
     * Executes the command, confirms that a CommandException is thrown if expected or not thrown if not expected
     * and that the result message is correct.
     */
    public void assertFeedback(Command returnedCommand, String message, boolean isCommandExceptionExpected) {
        //We need to initialise config manager to be the default
        returnedCommand.setData(null, null, new ConfigManager());
        try {
            returnedCommand.execute();
            assertFalse("CommandException should have been thrown", isCommandExceptionExpected);
        } catch (CommandException e) {
            assertTrue("CommandException should not have been thrown", isCommandExceptionExpected);
            assertEquals(message, e.getMessage());
        }
    }
}
