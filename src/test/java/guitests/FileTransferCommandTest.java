package guitests;

import static org.junit.Assert.assertEquals;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import org.junit.Test;

import seedu.onetwodo.MainApp;
import seedu.onetwodo.commons.util.FileUtil;
import seedu.onetwodo.logic.commands.ExportCommand;
import seedu.onetwodo.logic.commands.ImportCommand;
import seedu.onetwodo.logic.commands.SaveToCommand;
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

    private static final String TEST_SAVE_FOLDER = FileUtil.getPath("test/data/SaveToTest.xml");
    private static final String TEST_EXPORT_FOLDER = FileUtil.getPath("test/data/FileTransferToTest.xml");
    private static final String TEST_INVALID_NAME_FOLDER = FileUtil.getPath("test/data/(\"=^_^=\".xml");

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
        assertEquals(new ToDoList(newToDoList), new ToDoList(toDoList));
    }

    @Test
    public void save_fileAlreadyExist_exceptionThrown() throws Exception {
        resetStorages();

        commandBox.runCommand("save " + TEST_SAVE_FOLDER);
        commandBox.runCommand("save " + TEST_SAVE_FOLDER);
        String result = String.format(SaveToCommand.MESSAGE_OVERWRITE_WARNING, TEST_SAVE_FOLDER).toString();
        assertResultMessage(result);
    }

    @Test
    public void save_incorrectInput_failureResultMessage() {
        resetStorages();

        // no file path
        commandBox.runCommand("save");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SaveToCommand.MESSAGE_USAGE));

        // empty string file path
        commandBox.runCommand("save ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SaveToCommand.MESSAGE_USAGE));

        // too many arguments
        commandBox.runCommand("save overwrite SaveToTest.xml SaveToTest2.xml");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SaveToCommand.MESSAGE_USAGE));

        // not "overwrite" word
        commandBox.runCommand("save overwrites " + TEST_SAVE_FOLDER);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SaveToCommand.MESSAGE_USAGE));

        // file type is not xml
        commandBox.runCommand("save SaveToTest.html");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_SHOULD_BE_XML,
                SaveToCommand.MESSAGE_USAGE));

        // file has no type
        commandBox.runCommand("save SaveToTest");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_SHOULD_BE_XML,
                SaveToCommand.MESSAGE_USAGE));

        // file has no name
        commandBox.runCommand("save .xml");
        assertResultMessage(String.format(SaveToCommand.MESSAGE_SAVETO_SHOULD_BE_XML,
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

        // overwrite and save
        commandBox.runCommand("export overwrite " + TEST_EXPORT_FOLDER);
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS,
                TEST_EXPORT_FOLDER));

        ReadOnlyToDoList newToDoList = toDoListStorage.readToDoList().get();
        assertEquals(new ToDoList(newToDoList), new ToDoList(toDoList));
    }

    @Test
    public void export_fileAlreadyExist_exceptionThrown() throws Exception {
        resetStorages();
        commandBox.runCommand("export " + TEST_EXPORT_FOLDER);
        commandBox.runCommand("export " + TEST_EXPORT_FOLDER);

        String result = String.format(ExportCommand.MESSAGE_OVERWRITE_WARNING,
                TEST_EXPORT_FOLDER).toString();
        assertResultMessage(result);
    }

    @Test
    public void fileTransfer_incorrectInput_failureResultMessage() throws Exception {
        resetStorages();

        // no file path
        commandBox.runCommand("export");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
        commandBox.runCommand("import");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE));

        // empty string file path
        commandBox.runCommand("export ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
        commandBox.runCommand("import ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE));


        // too many arguments
        commandBox.runCommand("export overwrite ExportTest1.xml ExportTest2.xml");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
        commandBox.runCommand("import ImportTest1.xml ImportTest2.xml");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE));

        // not "overwrite" word
        commandBox.runCommand("export overwrites " + TEST_EXPORT_FOLDER);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));

        // file type is not xml
        commandBox.runCommand("export ExportTestFile.html");
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_SHOULD_BE_XML,
                ExportCommand.MESSAGE_USAGE));
        commandBox.runCommand("import ImportTestFile.html");
        assertResultMessage(String.format(ImportCommand.MESSAGE_IMPORT_SHOULD_BE_XML,
                ImportCommand.MESSAGE_USAGE));

        // file has no type
        commandBox.runCommand("export ExportTestFile");
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_SHOULD_BE_XML,
                ExportCommand.MESSAGE_USAGE));
        commandBox.runCommand("import ImportTestFile");
        assertResultMessage(String.format(ImportCommand.MESSAGE_IMPORT_SHOULD_BE_XML,
                ImportCommand.MESSAGE_USAGE));

        // file has no name
        commandBox.runCommand("export .xml");
        assertResultMessage(String.format(ExportCommand.MESSAGE_EXPORT_SHOULD_BE_XML,
                ExportCommand.MESSAGE_USAGE));
        commandBox.runCommand("import .xml");
        assertResultMessage(String.format(ImportCommand.MESSAGE_IMPORT_SHOULD_BE_XML,
                ImportCommand.MESSAGE_USAGE));

    }

    private void resetStorages() {

        // saveToFile is file used for save command test
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

    //@@author A0139343E-unused

    //These tests only work in junit, but not in travis (suspects due to OS in travis too slow for creating files).
/*
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void save_fileAlreadyExist_exceptionThrown() throws Exception {
        resetStorages();
        commandBox.runCommand("save " + TEST_SAVE_FOLDER);
        thrown.expect(CommandException.class);
        commandBox.runCommand("save " + TEST_SAVE_FOLDER);
    }

    @Test
    public void save_invalidFileName_exceptionThrown() throws Exception {
        resetStorages();

        commandBox.runCommand("save " + TEST_INVALID_NAME_FOLDER);
        assertResultMessage(SaveToCommand.MESSAGE_SAVETO_MAKE_FILE_FAIL);
    }

    @Test
    public void import_fileNotExist_exceptionThrown() throws Exception {
        resetStorages();

        commandBox.runCommand("import " + TEST_INVALID_NAME_FOLDER);
        String result = ImportCommand.MESSAGE_IMPORT_FILE_MISSING
                + String.format(ImportCommand.MESSAGE_IMPORT_FAILURE, TEST_INVALID_NAME_FOLDER).toString();
        assertResultMessage(result);
    }

    @Test
    public void export_invalidFileName_exceptionThrown() throws Exception {
        resetStorages();

        commandBox.runCommand("export " + TEST_INVALID_NAME_FOLDER);
        assertResultMessage(ExportCommand.MESSAGE_EXPORT_MAKE_FILE_FAIL);
    }
*/
}
