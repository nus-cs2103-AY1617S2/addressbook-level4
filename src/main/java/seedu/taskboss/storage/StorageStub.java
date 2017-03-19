package seedu.taskboss.storage;

import java.io.IOException;
import seedu.taskboss.model.ReadOnlyTaskBoss;

/**
 * StorageStub class for dependency injection
 */
public class StorageStub extends Storage {

    private TaskBoss taskBoss;
    private String filepath;
    
    public StorageStub(String filepath) {
        this.filepath = filepath;
    }
    
    @Override
    public void saveTaskBoss(ReadOnlyTaskBoss taskBoss) throws IOException;

    @Override
    public String getFilePath() {
        return this.filepath;
    }
}
