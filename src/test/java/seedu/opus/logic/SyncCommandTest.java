package seedu.opus.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.opus.logic.commands.CommandResult;
import seedu.opus.logic.commands.SyncCommand;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.Model;
import seedu.opus.storage.StorageManager;

public class SyncCommandTest {

    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Logic logic;
    private Model mockModel = mock(Model.class);

    @Before
    public void setUp() {
        String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(mockModel, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
    }

    @Test
    public void executeSyncCommandWithValidOnArgumentSuccess() throws CommandException {
        CommandResult result = logic.execute("sync on");
        assertEquals(result.feedbackToUser, SyncCommand.MESSAGE_SYNC_ON_SUCCESS);
    }

    @Test
    public void executeSyncCommandWithValidOffArgumentSuccess() throws CommandException {
        CommandResult result = logic.execute("sync off");
        assertEquals(result.feedbackToUser, SyncCommand.MESSAGE_SYNC_OFF_SUCCESS);
    }

    @Test
    public void executeSyncCommandWithInvalidArgumentRaiseException() throws CommandException {
        assertNotNull(logic);
        thrown.expect(CommandException.class);
        thrown.expectMessage(SyncCommand.MESSAGE_USAGE);
        logic.execute("sync invalid args");
    }
}
