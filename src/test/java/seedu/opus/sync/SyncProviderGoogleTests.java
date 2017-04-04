package seedu.opus.sync;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Task;


//@@author A0148087W-reused
/**
 *
 * Credits: burnflare
 */
public class SyncProviderGoogleTests {
    private static final File DATA_STORE_CREDENTIAL = new File(SyncServiceGtask.DATA_STORE_DIR + "StoredCredential");

    private static File DATA_STORE_TEST_CREDENTIALS;

    private static final SyncServiceGtask syncProviderGoogle = spy(new SyncServiceGtask());
    private static final SyncManager mockSyncManager = mock(SyncManager.class);
    private static final Task mockTask = mock(Task.class);

    @BeforeClass
    public static void setUp() {
        DATA_STORE_TEST_CREDENTIALS = new File("data/StoredCredential");
        copyTestCredentials();

        try {
            Optional<DateTime> fakeTime = Optional.of(new DateTime(LocalDateTime.now()));
            Name fakeName = new Name("fakename");
            int minId = 99999;
            int maxId = 9999999;
            Random r = new Random();

            when(mockTask.getEndTime()).thenReturn(fakeTime);
            when(mockTask.getName()).thenReturn(fakeName);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        syncProviderGoogle.start();
    }

    @AfterClass
    public static void tearDown() {
        deleteCredential();
    }

    public static void copyTestCredentials() {
        try {
            deleteCredential();
            Files.copy(getTestCredential().toPath(), DATA_STORE_CREDENTIAL.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCredential() {
        DATA_STORE_CREDENTIAL.delete();
    }

    private static File getTestCredential() {
        return DATA_STORE_TEST_CREDENTIALS;
    }

    @Test
    public void syncProviderGoogleStartTest() {
        reset(syncProviderGoogle);
        syncProviderGoogle.start();

        verify(syncProviderGoogle, atLeastOnce()).start();
    }


    @Test
    public void syncProviderGoogle_stop_successful() {
        reset(mockSyncManager);
        syncProviderGoogle.stop();

        // Verify sync status changed
        verify(syncProviderGoogle).stop();
    }

    @Test
    public void syncProviderGoogle_addEvent_successful() {
        LinkedList<Task> list = new LinkedList<Task>();
        list.add(mockTask);
        syncProviderGoogle.updateTaskList(list);

    }
}
