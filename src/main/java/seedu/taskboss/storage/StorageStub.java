package seedu.taskboss.storage;

import java.io.IOException;
import seedu.taskboss.model.ReadOnlyTaskBoss;

/**
 * StorageStub class for dependency injection
 */
public class StorageStub extends Storage {

    private TaskBossStorage taskBoss;
    private String tbFilePath;

    public StorageStub(String tbFilePath) {
        this.tbFilePath = tbFilePath;
    }

    @Override
    public void saveTaskBoss(ReadOnlyTaskBoss taskBoss) throws IOException; {
        this.taskBoss = taskBoss;
    }

    @Override
    public String getTaskBossFilePath() {
        return this.tbFilePath;
    }
}
