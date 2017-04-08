package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.doist.commons.util.FileUtil;
import seedu.doist.commons.util.XmlUtil;
import seedu.doist.logic.commands.LoadCommand;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.storage.XmlSerializableTodoList;

//@@author A0140887W
public class LoadCommandTest extends DoistGUITest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/LoadCommandTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File NOT_XML_FORMAT = new File(TEST_DATA_FOLDER + "notXmlFormat.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validTodoList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void load() throws Exception {
        assertLoadSuccess(VALID_FILE.getAbsolutePath(),
                XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableTodoList.class));

        //invalid file, unable to load
        assertLoadFailure(EMPTY_FILE.getAbsolutePath(), LoadCommand.MESSAGE_INVALID_FILE);

        //invalid file
        assertLoadFailure(NOT_XML_FORMAT.getAbsolutePath(), LoadCommand.MESSAGE_INVALID_FILE);

        // not a file, unable to load
        assertLoadFailure(MISSING_FILE.getAbsolutePath(), LoadCommand.MESSAGE_NOT_FILE);
    }

    @Test
    public void loadWithEmpty() {
        commandBox.runCommand("load ");
        assertResultMessage(String.format(LoadCommand.MESSAGE_INVALID_PATH,
                                            LoadCommand.MESSAGE_USAGE));

        commandBox.runCommand("load");
        assertResultMessage(String.format(LoadCommand.MESSAGE_INVALID_PATH,
                                              LoadCommand.MESSAGE_USAGE));
    }

    private void assertLoadSuccess(String path, ReadOnlyTodoList loadedList) {
        commandBox.runCommand("load " + path);

        //confirm the list now contains the loaded tasks
        ReadOnlyTask[] expectedList = loadedList.getTaskList()
                .toArray(new ReadOnlyTask[loadedList.getTaskList().size()]);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private void assertLoadFailure(String path, String expectedMessage) {
        commandBox.runCommand("load " + path);
        assertResultMessage(expectedMessage);
    }
}

