package seedu.opus.sync;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

//@author A0148087W
public class SyncManagerTest {
    private SyncManager syncManager;
    private SyncService mockService;

    @Before
    public void setUp() {
        mockService = mock(SyncServiceGtask.class);
        syncManager = new SyncManager(mockService);
    }

    @Test
    public void syncManagerStartSyncExpectSyncServiceStart() throws SyncException {
        assertNotNull(syncManager);
        syncManager.startSync();
        verify(mockService).start();
    }

    @Test
    public void syncManagerStopSyncExpectSyncServiceStop() throws SyncException {
        assertNotNull(syncManager);
        syncManager.stopSync();
        verify(mockService).stop();
    }

    @Test
    public void syncManagerUpdateTaskListWithValidTaskExpectSyncServiceUpdateListSuccessful() {
        assertNotNull(syncManager);
        
        Task mockTask = mock(Task.class);
        Optional<DateTime> mockStartDateTime = Optional.ofNullable(null);
        Optional<DateTime> mockEndDateTime = Optional.of(mock(DateTime.class));
        when(mockTask.getStartTime()).thenReturn(mockStartDateTime);
        when(mockTask.getEndTime()).thenReturn(mockEndDateTime);

        List<Task> list = new ArrayList<Task>();
        list.add(mockTask);

        syncManager.updateTaskList(list);
        verify(mockService).updateTaskList(list);
    }
}
