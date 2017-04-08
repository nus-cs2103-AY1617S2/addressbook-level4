package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import edu.emory.mathcs.backport.java.util.Arrays;
import project.taskcrusher.commons.util.FileUtil;
import project.taskcrusher.logic.commands.LoadCommand;
import project.taskcrusher.model.UserInbox;
import project.taskcrusher.storage.XmlUserInboxStorage;
import project.taskcrusher.testutil.TestEventCard;
import project.taskcrusher.testutil.TestTaskCard;
import project.taskcrusher.testutil.TypicalTestUserInbox;

public class LoadCommandTest extends TaskcrusherGuiTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    public static final String EXISTING_FILE = "existing.xml";

    @Before
    public void setUp() throws Exception {
        String filePath = getTempFilePath(EXISTING_FILE);
        FileUtil.createIfMissing(new File(filePath));
        TypicalTestUserInbox testUserInbox = new TypicalTestUserInbox();
        UserInbox original = testUserInbox.getTypicalUserInbox();
        XmlUserInboxStorage xmlUserInboxStorage = new XmlUserInboxStorage(filePath);
        xmlUserInboxStorage.saveUserInbox(original, filePath);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void loadNonExistingFile() {
        String filename = "nonexisting.xml";
        commandBox.runCommand("load " + filename);
        assertResultMessage(String.format(LoadCommand.MESSAGE_FILE_NONEXISTENT, filename));
    }

    @Test
    public void loadExistingFileAsNewFile() {
        commandBox.runCommand("load new " + EXISTING_FILE);
        assertResultMessage(String.format(LoadCommand.MESSAGE_FILE_ALREADY_EXISTS, EXISTING_FILE));
    }

    @Test
    public void loadNonXmlFile() {
        commandBox.runCommand("load new file.xmlaa");
        assertResultMessage(LoadCommand.MESSAGE_INVALID_EXTENSION);
    }

    @Test
    public void loadNewFile() throws Exception {
        String filepath = getTempFilePath("newfile.xml");
        commandBox.runCommand("load new " + filepath);
        assertTrue(FileUtil.isFileExists(new File(filepath)));
    }

    @Test
    public void loadExistingFileSuccess() {
        String filePath = getTempFilePath(EXISTING_FILE);
        commandBox.runCommand("clear");
        commandBox.runCommand("load " + filePath);
        TestTaskCard[] expectedTasks = new TypicalTestUserInbox().getTypicalTasks();
        Arrays.sort(expectedTasks);
        TestEventCard[] expectedEvents = new TypicalTestUserInbox().getTypicalEvents();
        Arrays.sort(expectedEvents);

        assertTrue(userInboxPanel.isListMatching(expectedTasks));
        assertTrue(userInboxPanel.isListMatching(expectedEvents));
    }
}
