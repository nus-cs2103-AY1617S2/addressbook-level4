package seedu.taskboss.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.taskboss.commons.core.LogsCenter;
import seedu.taskboss.commons.exceptions.DataConversionException;
import seedu.taskboss.commons.util.FileUtil;
import seedu.taskboss.model.ReadOnlyTaskBoss;

/**
 * A class to access TaskBoss data stored as an xml file on the hard disk.
 */
public class XmlTaskBossStorage implements TaskBossStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskBossStorage.class);

    private String filePath;

    public XmlTaskBossStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getTaskBossFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskBoss> readTaskBoss() throws DataConversionException, IOException {
        return readTaskBoss(filePath);
    }

    /**
     * Similar to {@link #readTaskBoss()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskBoss> readTaskBoss(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File taskBossFile = new File(filePath);

        if (!taskBossFile.exists()) {
            logger.info("TaskBoss file "  + taskBossFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskBoss taskBossOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskBossOptional);
    }

    @Override
    public void saveTaskBoss(ReadOnlyTaskBoss taskBoss) throws IOException {
        saveTaskBoss(taskBoss, filePath);
    }

    /**
     * Similar to {@link #saveTaskBoss(ReadOnlyTaskBoss)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskBoss(ReadOnlyTaskBoss taskBoss, String filePath) throws IOException {
        assert taskBoss != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskBoss(taskBoss));
    }

}
