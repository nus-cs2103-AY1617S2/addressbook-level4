package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.onetwodo.MainApp;
import seedu.onetwodo.commons.exceptions.DataConversionException;
import seedu.onetwodo.commons.util.FileUtil;
import seedu.onetwodo.logic.commands.ExportCommand;
import seedu.onetwodo.logic.commands.ImportCommand;
import seedu.onetwodo.logic.commands.SaveToCommand;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.storage.StorageManager;
import seedu.onetwodo.storage.ToDoListStorage;
import seedu.onetwodo.storage.XmlToDoListStorage;

//@@author A0139343E
/**
 * Test for all file transfer commands, such as SaveToCommand, ImportCommand and ExportCommand
 *
 */
public class FileTransferCommandTest extends ToDoListGuiTest {

    private static final String TEST_SAVE_FOLDER = FileUtil.getPath("./src/test/data/SaveToTest.xml");
    private static final String TEST_EXPORT_FOLDER = FileUtil.getPath("./src/test/data/FileTransferToTest.xml");

    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    // SaveToCommand tests

    public void save_invalidFileName_exceptionThrown() throws Exception {
        resetStorages();
        
        thrown.expect(IOException.class);
        commandBox.runCommand("save test/data/(\"=^_^=\".xml");
    }
    
    public void save_fileAlreadyExist_exceptionThrown() throws Exception {
        resetStorages();
        commandBox.runCommand(TEST_SAVE_FOLDER);
        thrown.expect(IOException.class);
        commandBox.runCommand(TEST_SAVE_FOLDER);
    }
    
    @Test
    public void save_incorrectInput_failureResultMessage() {
        resetStorages();
        
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
        commandBox.runCommand("save overwrite SaveToTest.xml SaveToTest2.xml");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // not "overwrite" word
        commandBox.runCommand("save overwrites " + TEST_SAVE_FOLDER);
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // file type is not xml
        commandBox.runCommand("save SaveToTest.html");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // file has no type
        commandBox.runCommand("save SaveToTest");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_FAILURE, 
                SaveToCommand.MESSAGE_USAGE));
        
        // ImportCommand and ExportCommand tests
    }
    
    
    @Test
    public void save_correctInput_successResultMessage() throws Exception {
        StorageManager storageManager = (StorageManager) MainApp.getInstance().getStorage();
        ToDoListStorage toDoListStorage = storageManager.getToDoListStorage();
        ReadOnlyToDoList toDoList = toDoListStorage.readToDoList().get();
        resetStorages();

        commandBox.runCommand(TEST_SAVE_FOLDER);
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_SUCCESS,
                TEST_SAVE_FOLDER));
        
        ReadOnlyToDoList newToDoList = toDoListStorage.readToDoList().get();
        assertEquals(newToDoList, new ToDoList(toDoList));
    }
    
    
    @Test
    public void save_correctOverwriteInput_successResultMessage() throws Exception {
        StorageManager storageManager = (StorageManager) MainApp.getInstance().getStorage();
        ToDoListStorage toDoListStorage = storageManager.getToDoListStorage();
        ReadOnlyToDoList toDoList = toDoListStorage.readToDoList().get();
        resetStorages();

        commandBox.runCommand("save overwrite " + TEST_SAVE_FOLDER);
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_SUCCESS,
                TEST_SAVE_FOLDER));
        
        ReadOnlyToDoList newToDoList = toDoListStorage.readToDoList().get();
        assertEquals(newToDoList, new ToDoList(toDoList));
    }
    
    
    private void resetStorages() {
        
        // saveToFile is file usd for save command test
        File saveToFile = new File(TEST_SAVE_FOLDER);
        if(saveToFile.exists() || saveToFile.isDirectory()) {
            saveToFile.delete();
        }
        
        // fileTransferFile is file used for import and export command test
        File fileTransferFile = new File(TEST_EXPORT_FOLDER);
        if(fileTransferFile.exists() || fileTransferFile.isDirectory()) {
            fileTransferFile.delete();
        }
    }
    

}
