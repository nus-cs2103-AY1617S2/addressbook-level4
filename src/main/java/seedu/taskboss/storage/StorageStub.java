package seedu.taskboss.storage;

import java.io.IOException;
import seedu.taskboss.model.ReadOnlyTaskBoss;

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
    public void saveTaskBoss(ReadOnlyTaskBoss taskBoss) throws IOException; {
        this.taskBoss = taskBoss;
    }

    @Override
    public String getTaskBossFilePath() {
        return this.filepath
    }
}
