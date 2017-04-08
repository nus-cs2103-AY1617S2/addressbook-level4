package seedu.opus.sync;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;

import seedu.opus.sync.exceptions.SyncException;

public class SyncServiceGtaskTest {
    private SyncManager mockSyncManager;
    private SyncServiceGtask syncServiceGtask;

    private static final File DATA_STORE_CREDENTIAL = new File("data/credentials/StoredCredential");

    private static final File DATA_STORE_TEST_CREDENTIALS = new File("cred/StoredCredential_1");

    @Before
    public void setUp() throws SyncException {
        copyTestCredentials();
        mockSyncManager = mock(SyncManager.class);
        syncServiceGtask = spy(new SyncServiceGtask());
        syncServiceGtask.setSyncManager(mockSyncManager);
    }

    @Test
    public void syncServiceGtaskStart() throws SyncException {
        syncServiceGtask.start();
        verify(syncServiceGtask).start();
    }

    public static void copyTestCredentials() {
        try {
            deleteCredential();
            Files.copy(DATA_STORE_TEST_CREDENTIALS.toPath(), DATA_STORE_CREDENTIAL.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCredential() {
        DATA_STORE_CREDENTIAL.delete();
    }
}
