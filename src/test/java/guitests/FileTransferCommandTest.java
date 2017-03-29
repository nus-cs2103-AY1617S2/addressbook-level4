package guitests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.onetwodo.MainApp;
import seedu.onetwodo.commons.util.FileUtil;
import seedu.onetwodo.logic.commands.ExportCommand;
import seedu.onetwodo.logic.commands.ImportCommand;
import seedu.onetwodo.logic.commands.SaveToCommand;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.storage.StorageManager;
import seedu.onetwodo.storage.ToDoListStorage;

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

    @Test
    public void save_correctInput_successResultMessage() throws Exception {
        resetStorages();

        StorageManager storageManager = (StorageManager) MainApp.getInstance().getStorage();
        ToDoListStorage toDoListStorage = storageManager.getToDoListStorage();
        ReadOnlyToDoList toDoList = toDoListStorage.readToDoList().get();

        // simple saving to another destination
        commandBox.runCommand("save " + TEST_SAVE_FOLDER);
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_SUCCESS,
                TEST_SAVE_FOLDER));

        // overwrite and save
        commandBox.runCommand("save overwrite " + TEST_SAVE_FOLDER);
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_SUCCESS,
                TEST_SAVE_FOLDER));

        ReadOnlyToDoList newToDoList = toDoListStorage.readToDoList().get();
        assertEquals(newToDoList, new ToDoList(toDoList));
    }

    public void save_invalidFileName_exceptionThrown() throws Exception {
        resetStorages();

        thrown.expect(IOException.class);
        commandBox.runCommand("save test/data/(\"=^_^=\".xml");
    }

    public void save_fileAlreadyExist_exceptionThrown() throws Exception {
        resetStorages();
        commandBox.runCommand("save " + TEST_SAVE_FOLDER);
        thrown.expect(CommandException.class);
        commandBox.runCommand("save " + TEST_SAVE_FOLDER);
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
    }

    // ImportCommand and ExportCommand tests

    @Test
    public void export_correctInput_successResultMessage() throws Exception {
        resetStorages();

        StorageManager storageManager = (StorageManager) MainApp.getInstance().getStorage();
        ToDoListStorage toDoListStorage = storageManager.getToDoListStorage();
        ReadOnlyToDoList toDoList = toDoListStorage.readToDoList().get();

        // simple saving to another destination
        commandBox.runCommand("export " + TEST_EXPORT_FOLDER);
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS,
                TEST_EXPORT_FOLDER));

        // overwrite and save
        commandBox.runCommand("export overwrite " + TEST_EXPORT_FOLDER);
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS,
                TEST_EXPORT_FOLDER));

        ReadOnlyToDoList newToDoList = toDoListStorage.readToDoList().get();
        assertEquals(newToDoList, new ToDoList(toDoList));
    }

    public void import_invalidFileName_exceptionThrown() throws Exception {
        resetStorages();

        thrown.expect(IOException.class);
        commandBox.runCommand("import test/data/(\"O_O\".xml");
    }

    public void export_invalidFileName_exceptionThrown() throws Exception {
        resetStorages();

        thrown.expect(IOException.class);
        commandBox.runCommand("export test/data/(\"X_X\".xml");
    }

    public void export_fileAlreadyExist_exceptionThrown() throws Exception {
        resetStorages();
        commandBox.runCommand("export " + TEST_EXPORT_FOLDER);
        thrown.expect(CommandException.class);
        commandBox.runCommand("export " + TEST_EXPORT_FOLDER);
    }

    @Test
    public void fileTransfer_incorrectInput_failureResultMessage() {
        resetStorages();

        // no file path
        commandBox.runCommand("export");
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));
        commandBox.runCommand("import");
        assertResultMessage(String.format(ImportCommand.MESSAGE_IMPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));

        // empty string file path
        commandBox.runCommand("export ");
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));
        commandBox.runCommand("import ");
        assertResultMessage(String.format(ImportCommand.MESSAGE_IMPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));

        // file has no name
        commandBox.runCommand("export .xml");
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));
        commandBox.runCommand("import .xml");
        assertResultMessage(String.format(ImportCommand.MESSAGE_IMPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));

        // too many arguments
        commandBox.runCommand("export overwrite ExportTest1.xml ExportTest2.xml");
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));
        commandBox.runCommand("import ImportTest1.xml ImportTest2.xml");
        assertResultMessage(String.format(ImportCommand.MESSAGE_IMPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));

        // not "overwrite" word
        commandBox.runCommand("export overwrites " + TEST_EXPORT_FOLDER);
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));

        // file type is not xml
        commandBox.runCommand("export ExportTestFile.html");
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));
        commandBox.runCommand("import ImportTestFile.html");
        assertResultMessage(String.format(ImportCommand.MESSAGE_IMPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));

        // file has no type
        commandBox.runCommand("export ExportTestFile");
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));
        commandBox.runCommand("import ImportTestFile");
        assertResultMessage(String.format(ImportCommand.MESSAGE_IMPORT_FAILURE,
                SaveToCommand.MESSAGE_USAGE));

    }

    private void resetStorages() {

        // saveToFile is file usd for save command test
        File saveToFile = new File(TEST_SAVE_FOLDER);
        if (saveToFile.exists() || saveToFile.isDirectory()) {
            saveToFile.delete();
        }

        // fileTransferFile is file used for import and export command test
        File fileTransferFile = new File(TEST_EXPORT_FOLDER);
        if (fileTransferFile.exists() || fileTransferFile.isDirectory()) {
            fileTransferFile.delete();
        }
    }

}
