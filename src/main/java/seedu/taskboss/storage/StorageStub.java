package seedu.taskboss.storage;

import java.io.IOException;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.UserPrefs;

/**
 * StorageStub class for dependency injection
 */
public class StorageStub extends StorageManager {

    private TaskBossStorage taskBoss;
    private String filepath;

    public StorageStub(String filepath) {
        this.filepath = filepath;
    }
    
    @Override
    public void StorageManager(TaskBossStorage taskBossStorage, UserPrefsStorage userPrefsStorage) {
        this.taskBossStorage = taskBossStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    @Override
    public void saveTaskBoss(ReadOnlyTaskBoss taskBoss) throws IOException; {
        this.taskBoss = taskBoss;
    }

    @Override
    public String getTaskBossFilePath() {
        return this.filepath;
    }
}
