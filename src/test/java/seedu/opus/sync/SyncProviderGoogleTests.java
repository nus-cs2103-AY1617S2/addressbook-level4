package seedu.opus.sync;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Task;

/*
//@@author A0148087W-reused
/**
 *
 * Credits: burnflare
 */
public class SyncProviderGoogleTests {

    private static final SyncServiceGtask syncProviderGoogle = spy(new SyncServiceGtask());
    private static final Task mockTask = mock(Task.class);

    @BeforeClass
    public static void setUp() {
        copyTestCredentials();

        try {
            Optional<DateTime> fakeTime = Optional.of(new DateTime(LocalDateTime.now()));
            Name fakeName = new Name("fakename");

            when(mockTask.getEndTime()).thenReturn(fakeTime);
            when(mockTask.getName()).thenReturn(fakeName);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        //syncProviderGoogle.start();
    }

    @AfterClass
    public static void tearDown() {
        deleteCredential();
    }

    public static void copyTestCredentials() {
        deleteCredential();
        @SuppressWarnings("unused")
        File dataTestCredential = new File(SyncServiceGtask.DATA_STORE_DIR.getPath());
    }

    public static void deleteCredential() {
        SyncServiceGtask.DATA_STORE_DIR.delete();
    }

    /*
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

    /*
    @Test
    public void test() {
        LinkedList<Task> list = new LinkedList<Task>();
        list.add(mockTask);
        syncProviderGoogle.updateTaskList(list);
    }
    */
}
