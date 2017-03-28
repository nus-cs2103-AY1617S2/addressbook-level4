package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import seedu.onetwodo.logic.commands.ExportCommand;
import seedu.onetwodo.logic.commands.ImportCommand;
import seedu.onetwodo.logic.commands.SaveToCommand;

//@@author A0139343E
/**
 * Test for all file transfer commands, such as SaveToCommand, ImportCommand and ExportCommand
 *
 */
public class FileTransferCommandTest extends ToDoListGuiTest {

    @Test
    public void path() {
        
        // SaveToCommand tests
        
        // saveToFile is file used for save command test
        File saveToFile = new File("test/data/SaveToTest.xml");

        if(saveToFile.exists() || saveToFile.isDirectory()) {
            saveToFile.delete();
        }
        
        commandBox.runCommand("save test/data/SaveToTest.xml");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_SUCCESS,
                "test/data/SaveToTest.xml"));
        // TODO: test if todolist is the same when read
        
        // no file path
        commandBox.runCommand("save");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // empty string file path
        commandBox.runCommand("save ");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // file has no name
        commandBox.runCommand("save .xml");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // too many arguments
        commandBox.runCommand("save overwrite test/data/SaveToTest.xml test/data/SaveToTest2.xml");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // not "overwrite" word
        commandBox.runCommand("save overwrites test/data/SaveToTest.xml");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // file name contains invalid character
        commandBox.runCommand("save test/data/(\"=^_^=\".xml");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // file type is not xml
        commandBox.runCommand("save test/data/SaveToTest.html");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // file has no type
        commandBox.runCommand("save test/data/SaveToTest");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        
        
        // ImportCommand and ExportCommand tests
        
        // fileTransferFile is file used for import and export command test
        File fileTransferFile = new File("test/data/FileTransferTest.xml");
        
        if(fileTransferFile.exists() || fileTransferFile.isDirectory()) {
            fileTransferFile.delete();
        }
    }
    

}
