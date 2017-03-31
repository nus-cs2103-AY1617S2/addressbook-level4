package seedu.task.commons.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author A0140063X
public class HistoryTest {
    private History history;

    @Test
    public void handleTaskManagerChanged() {
        history = History.test_resetInstance();

        //after 11 taskmanager changing events
        simulateTaskManagerChanged(11);

        //undo count should still maintain max of 10
        assertEquals(History.MAX_NUM_UNDO, history.getUndoCount());

        //redo count should always be 0 after any taskmanager changes
        assertEquals(0, history.getRedoCount());

        //path to save backup should loop back and continue at backup0
        assertEquals("data/temp/backup0.xml", history.getBackupFilePath());

        //path to load from for undo remains at backup10
        assertEquals("data/temp/backup10.xml", history.getUndoFilePath());
    }


    private void simulateTaskManagerChanged(int num) {
        for (int i = 0; i < num; i++) {
            history.handleTaskManagerChanged("dummy");
        }
    }


    @Test
    public void handleUndo() {
        history = History.test_resetInstance();

        history.handleTaskManagerChanged("1");
        history.handleUndo();

        assertEquals(0, history.getUndoCount());
        assertEquals(1, history.getRedoCount());
        assertEquals("data/temp/backup0.xml", history.getBackupFilePath());

        //11 events, max undo 10
        simulateTaskManagerChanged(11);
        simulateUndo(10);

        assertEquals(0, history.getUndoCount());
        assertEquals(10, history.getRedoCount());
        assertEquals("data/temp/backup1.xml", history.getBackupFilePath());
    }

    private void simulateUndo(int num) {
        for (int i = 0; i < num; i++) {
            history.handleUndo();
        }
    }
}
