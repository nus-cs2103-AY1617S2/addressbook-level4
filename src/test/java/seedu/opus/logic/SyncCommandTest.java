package seedu.opus.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.opus.logic.commands.CommandResult;
import seedu.opus.logic.commands.SyncCommand;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.Model;
import seedu.opus.model.ModelManager;
import seedu.opus.storage.StorageManager;
import seedu.opus.sync.SyncServiceGtaskTest;
import seedu.opus.sync.exceptions.SyncException;

//@@author A0148087W
public class SyncCommandTest {

    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Logic logic;
    private Model model = spy(new ModelManager());

    @Before
    public void setUp() {
        String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
    }

    @Test
    public void executeSyncCommandWithValidOnArgumentSuccess() throws CommandException, SyncException {
        SyncServiceGtaskTest.copyTestCredentials();

        CommandResult result = logic.execute("sync on");
        assertEquals(result.feedbackToUser, SyncCommand.MESSAGE_SYNC_ON_SUCCESS);
        verify(model).startSync();

        SyncServiceGtaskTest.deleteCredential();
    }

    @Test
    public void executeSyncCommandWithValidOffArgumentSuccess() throws CommandException, SyncException {
        CommandResult result = logic.execute("sync off");
        assertEquals(result.feedbackToUser, SyncCommand.MESSAGE_SYNC_OFF_SUCCESS);
        verify(model).stopSync();
    }

    @Test
    public void executeSyncCommandWithInvalidArgumentRaiseException() throws CommandException {
        assertNotNull(logic);
        thrown.expect(CommandException.class);
        thrown.expectMessage(SyncCommand.MESSAGE_USAGE);
        logic.execute("sync invalid args");
    }

    @Test
    public void executeSyncCommandWithValidArgumentsAndSyncCommandRaised() throws SyncException, CommandException {
        doThrow(new SyncException(null)).when(model).startSync();
        assertNotNull(logic);
        thrown.expect(CommandException.class);
        logic.execute("sync on");
    }
}
