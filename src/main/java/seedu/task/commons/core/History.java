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

    public String getBackupFilePath() {
        return backupDirectory + backupFilePaths[currentFileIndex];
    }

    private void increaseCurrentFileIndex() {
        currentFileIndex = (currentFileIndex + 1) % (MAX_NUM_UNDO + 1);
    }

    private void decreaseCurrentFileIndex() {
        if (currentFileIndex <= 0) {
            currentFileIndex = MAX_NUM_UNDO;
        } else {
            currentFileIndex--;
        }
    }

    //returns path to load from for undo
    public String getUndoFilePath() {
        assert undoCount != 0;
        if (currentFileIndex <= 0) {
            return backupDirectory + backupFilePaths[MAX_NUM_UNDO];
        } else {
            return backupDirectory + backupFilePaths[currentFileIndex - 1];
        }
    }

    //returns path to load from for redo
    public String getRedoFilePath() {
        assert redoCount != 0;
        if (currentFileIndex < MAX_NUM_UNDO) {
            return backupDirectory + backupFilePaths[currentFileIndex + 1];
        } else {
            return backupDirectory + backupFilePaths[0];
        }
    }

    //this method means a command that modifies taskmanager have been executed
    public void handleTaskManagerChanged(String backupFilePath) {
        if (!backupFilePath.trim().equals("")) {
            increaseUndoCount();
            redoCount = 0;
            increaseCurrentFileIndex();
        }
    }

    //This methods is called by undo after it changed the data
    public void handleUndo() {
        assert undoCount != 0;
        decreaseUndoCount();
        increaseRedoCount();
        decreaseCurrentFileIndex();
    }

    //This methods is called by redo after it changed the data
    public void handleRedo() {
        assert redoCount != 0;
        decreaseRedoCount();
        increaseUndoCount();
        increaseCurrentFileIndex();
    }

    //used for Junit test
    public void test_setBackupDirectory(String backupDirectory) {
        this.backupDirectory = backupDirectory;
    }

    //used for Junit test
    public static History test_resetInstance() {
        instance = new History();
        return instance;
    }
}
