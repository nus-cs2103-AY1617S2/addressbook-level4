package seedu.opus.sync;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.Before;
import org.junit.Test;

import seedu.opus.sync.exceptions.SyncException;

public class SyncServiceGtaskTest {
    private SyncManager mockSyncManager;
    private SyncServiceGtask syncServiceGtask;

    private static File DATA_STORE_CREDENTIAL;

    private static File DATA_STORE_TEST_CREDENTIALS;

    @Before
    public void setUp() throws SyncException {
        DATA_STORE_TEST_CREDENTIALS = new File("cred/StoredCredential_1");
        DATA_STORE_CREDENTIAL = new File("data/credentials/StoredCredential");

        if(!Files.exists(DATA_STORE_CREDENTIAL.toPath())) {
            try {
                Files.createDirectories(DATA_STORE_CREDENTIAL.toPath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

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
            Path path = DATA_STORE_CREDENTIAL.toPath();
            Files.copy(DATA_STORE_TEST_CREDENTIALS.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCredential() {
        if (DATA_STORE_CREDENTIAL.exists()) {
            DATA_STORE_CREDENTIAL.delete();
        }
    }
}
