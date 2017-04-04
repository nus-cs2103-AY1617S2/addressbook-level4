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
import java.util.Arrays;
import java.util.List;
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


    private static final List<File> DATA_STORE_TEST_CREDENTIALS = Arrays.asList(
            new File("data/StoredCredential_1"),
            new File("data/StoredCredential_2"),
            new File("data/StoredCredential_3")
            );

    public static final File DATA_TEST_CREDENTIAL = new File("data/testCredential");
    public static final java.io.File DATA_STORE_DIR = new java.io.File("data/credentials");

    private static final SyncServiceGtask syncProviderGoogle = spy(new SyncServiceGtask());
    private static final SyncManager mockSyncManager = mock(SyncManager.class);
    private static final Task mockTask = mock(Task.class);

    @BeforeClass
    public static void setUp() {
        //copyTestCredentials();

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
            Files.copy(new File("data/testCredential").toPath(), new File("data/credentials").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCredential() {
        DATA_STORE_DIR.delete();
    }

    private static File getRandomCredential() {
        int r = new Random().nextInt(DATA_STORE_TEST_CREDENTIALS.size());
        return DATA_STORE_TEST_CREDENTIALS.get(r);
    }

    @Test
    public void syncProviderGoogleStartTest() {
        reset(syncProviderGoogle);
        syncProviderGoogle.start();

        verify(syncProviderGoogle, atLeastOnce()).start();
    }


    @Test
    public void syncProviderGoogleStopTest() {
        reset(mockSyncManager);
        syncProviderGoogle.stop();

        // Verify sync status changed
        verify(syncProviderGoogle).stop();
    }

}