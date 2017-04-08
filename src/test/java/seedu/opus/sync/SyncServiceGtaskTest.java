package seedu.opus.sync;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

//@@author A0148087W
public class SyncServiceGtaskTest {

    public static final String TEST_CREDENTIAL_DIRECTORY = "cred/StoredCredential_1";
    public static final String STORED_CREDENTIAL_DIRECTORY = "data/credentials/StoredCredential";

    private static SyncManager mockSyncManager;
    private static SyncServiceGtask syncServiceGtask;

    private static File dataStoreCredential;
    private static File dataStoreTestCredential;

    @Before
    public void setUp() throws SyncException {
        copyTestCredentials();
        mockSyncManager = mock(SyncManager.class);
        syncServiceGtask = spy(new SyncServiceGtask());
        syncServiceGtask.setSyncManager(mockSyncManager);
        syncServiceGtask.start();
    }

    @Test
    public void syncServiceGtaskStartSuccessful() throws SyncException {
        assertNotNull(syncServiceGtask);
        reset(syncServiceGtask);
        syncServiceGtask.start();
        verify(syncServiceGtask).start();
    }

    @Test
    public void syncServiceGtaskStopSuccessful() throws SyncException {
        assertNotNull(syncServiceGtask);
        reset(syncServiceGtask);
        syncServiceGtask.stop();
        verify(syncServiceGtask).stop();
    }

    @Test
    public void syncServiceGtaskUpdateTaskListSuccessful() throws SyncException {
        assertNotNull(syncServiceGtask);
        reset(syncServiceGtask);

        Task mockTask = mock(Task.class);
        Optional<DateTime> mockStartDateTime = Optional.ofNullable(null);
        Optional<DateTime> mockEndDateTime = Optional.of(mock(DateTime.class));
        when(mockTask.getStartTime()).thenReturn(mockStartDateTime);
        when(mockTask.getEndTime()).thenReturn(mockEndDateTime);

        List<Task> list = new ArrayList<Task>();
        list.add(mockTask);

        syncServiceGtask.start();
        syncServiceGtask.updateTaskList(list);

        verify(syncServiceGtask).updateTaskList(list);
    }

    public static void copyTestCredentials() {
        dataStoreTestCredential = new File(TEST_CREDENTIAL_DIRECTORY);
        dataStoreCredential = new File(STORED_CREDENTIAL_DIRECTORY);

        if (!Files.exists(dataStoreCredential.toPath())) {
            try {
                Files.createDirectories(dataStoreCredential.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            deleteCredential();
            Path path = dataStoreCredential.toPath();
            Files.copy(dataStoreTestCredential.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCredential() {
        dataStoreCredential = new File("data/credentials/StoredCredential");
        if (dataStoreCredential.exists()) {
            dataStoreCredential.delete();
        }
    }
}
