package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

//@@author A0164440M
public class PathCommandTest extends TaskBookGuiTest {

    @Test
    public void saveToCorrectPath() {
        assertCorrectPathResult("path", "a.xml");
        assertCorrectPathResult("path", "a.txt");
    }

    @Test
    public void saveToInvalidPath() {

        commandBox.runCommand("path");
        assertResultMessage("Invalid command format! \npath: Change save path. "
                + "Parameters: path [filename] \nExample: path taskbook.xml");
    }

    private void assertCorrectPathResult(String command, String path) {
        commandBox.runCommand(command + " " + path);
        assertTrue(fileExist(path));
        assertResultMessage("Save path has been successfully updated \n");
    }

    private void assertWrongPathResult(String command, String path) {
        commandBox.runCommand(command + " " + path);
        assertTrue(!fileExist(path));
        assertResultMessage("This path is invalid");
    }

    private boolean fileExist(String path) {
        File f = new File(path);
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }
}
