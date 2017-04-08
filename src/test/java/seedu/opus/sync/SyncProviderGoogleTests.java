package seedu.opus.sync;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

/*
//@@author A0148087W-reused
/**
 *
 * Credits: burnflare
 */
public class SyncProviderGoogleTests {

    private static final SyncServiceGtask syncProviderGoogle = spy(new SyncServiceGtask());
    private static final File dataTestCredential = new File("data/TestCredential");
    private static final Task mockTask = mock(Task.class);
    private static final SyncManager mockSyncManager = mock(SyncManager.class);

    @BeforeClass
    public static void setUp() throws SyncException {
        copyTestCredentials();

        try {
            Optional<DateTime> fakeTime = Optional.of(new DateTime(LocalDateTime.now()));
            Name fakeName = new Name("fakename");

            when(mockTask.getEndTime()).thenReturn(fakeTime);
            when(mockTask.getName()).thenReturn(fakeName);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        syncProviderGoogle.setSyncManager(mockSyncManager);
        syncProviderGoogle.start();
    }

    @AfterClass
    public static void tearDown() {
        deleteCredential();
    }

    public static void copyTestCredentials() {

        deleteCredential();
        try {
            java.nio.file.Files.copy(dataTestCredential.toPath(), SyncServiceGtask.DATA_STORE_FILE.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCredential() {
        SyncServiceGtask.DATA_STORE_FILE.delete();
    }

    /*
    @Test
    public void syncProviderGoogleStartTest() throws SyncException {
        reset(syncProviderGoogle);
        syncProviderGoogle.start();

        verify(syncProviderGoogle).start();
    }


    @Test
    public void syncProviderGoogleStopTest() {
        reset(mockSyncManager);
        syncProviderGoogle.stop();

        // Verify sync status changed
        verify(syncProviderGoogle).stop();
    }

    @Test
    public void test() {
        LinkedList<Task> list = new LinkedList<Task>();
        list.add(mockTask);
        syncProviderGoogle.updateTaskList(list);
    }
    */
}
