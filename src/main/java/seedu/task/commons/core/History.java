package seedu.task.commons.core;

// @@author A0140063X
public class History {

    public static final int MAX_NUM_UNDO = 10;

    private static History instance;
    private int undoCount;
    private int redoCount;
    private String backupDirectory = "data/temp/";
    private final String backupFilePaths[] = {"backup0.xml", "backup1.xml", "backup2.xml", "backup3.xml", "backup4.xml",
        "backup5.xml", "backup6.xml", "backup7.xml", "backup8.xml", "backup9.xml", "backup10.xml"};
    private int currentFileIndex;

    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    private History() {
        this.undoCount = 0;
        this.redoCount = 0;
        this.currentFileIndex = 0;
    }

    public int getUndoCount() {
        return undoCount;
    }

    private void increaseUndoCount() {
        if (this.undoCount < 10) {
            this.undoCount++;
        } else {
            this.undoCount = MAX_NUM_UNDO;
        }
    }

    private void decreaseUndoCount() {
        if (this.undoCount > 0) {
            this.undoCount--;
        } else {
            this.undoCount = 0;
        }
    }

    public int getRedoCount() {
        return redoCount;
    }

    private void increaseRedoCount() {
        if (this.redoCount < 10) {
            this.redoCount++;
        } else {
            this.redoCount = MAX_NUM_UNDO;
        }
    }

    private void decreaseRedoCount() {
        if (this.redoCount > 0) {
            this.redoCount--;
        } else {
            this.redoCount = 0;
        }
    }

    private void increaseCurrentFileIndex() {
        currentFileIndex = (currentFileIndex + 1) % (MAX_NUM_UNDO + 1);
    }

    private void decreaseCurrentFileIndex() {
        assert currentFileIndex >= 0;
        if (currentFileIndex == 0) {
            currentFileIndex = MAX_NUM_UNDO;
        } else {
            currentFileIndex--;
        }
    }

    /**
     * This method returns the filePath to save back up into. This is based on currentFileIndex.
     *
     * @return File path to back up into.
     */
    public String getBackupFilePath() {
        return backupDirectory + backupFilePaths[currentFileIndex];
    }

    /**
     * This method returns the filePath for undo to load from.
     * This method is only used when undo command is called, therefore undoCount must not be 0.
     * currentFileIndex must never be negative since it corresponds to a file.
     *
     * @return File path for undo to load from.
     */
    public String getUndoFilePath() {
        assert undoCount != 0;
        assert currentFileIndex >= 0;
        if (currentFileIndex == 0) {
            return backupDirectory + backupFilePaths[MAX_NUM_UNDO];
        } else {
            return backupDirectory + backupFilePaths[currentFileIndex - 1];
        }
    }

    /**
     * This method returns the filePath for redo to load from.
     * This method is only used when redo command is called, therefore redoCount must not be 0.
     *
     * @return File path for redo to load from.
     */
    public String getRedoFilePath() {
        assert redoCount != 0;
        if (currentFileIndex < MAX_NUM_UNDO) {
            return backupDirectory + backupFilePaths[currentFileIndex + 1];
        } else {
            return backupDirectory + backupFilePaths[0];
        }
    }

    /**
     * This method is called when a command that modifies taskmanager have been executed.
     * If backupFilePath is not empty, it means backup is done. Updates values to maintain proper status.
     *
     * @param backupFilePath    File path that backup was saved into.
     */
    public void handleTaskManagerChanged(String backupFilePath) {
        if (!backupFilePath.trim().equals("")) {
            increaseUndoCount();
            redoCount = 0;
            increaseCurrentFileIndex();
        }
    }

    /**
     * This method is called by UndoCommand and indicates that undo was successfully executed.
     * Updates the necessary values. undoCount must not be 0 since UndoCommand was successfully executed.
     */
    public void handleUndo() {
        assert undoCount != 0;
        decreaseUndoCount();
        increaseRedoCount();
        decreaseCurrentFileIndex();
    }

    /**
     * This method is called by RedoCommand and indicates that redo was successfully executed.
     * Updates the necessary values. redoCount must not be 0 since RedoCommand was successfully executed.
     */
    public void handleRedo() {
        assert redoCount != 0;
        decreaseRedoCount();
        increaseUndoCount();
        increaseCurrentFileIndex();
    }

    /**
     * This resets undoCount to 0. Used when an I/O error occurs.
     */
    public void resetUndoCount() {
        this.undoCount = 0;
    }

    /**
     * This resets redoCount to 0. Used when an I/O error occurs.
     */
    public void resetRedoCount() {
        this.redoCount = 0;
    }

    /**
     * This method is used for Junit testing only.
     *
     * @param backupDirectory   Directory for history to use.
     */
    public void test_setBackupDirectory(String backupDirectory) {
        this.backupDirectory = backupDirectory;
    }

    /**
     * This method is used for Junit testing only.
     */
    public static History test_resetInstance() {
        instance = new History();
        return instance;
    }
}
