package seedu.doit.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.exceptions.DataConversionException;
import seedu.doit.commons.util.FileUtil;
import seedu.doit.model.ReadOnlyItemManager;

/**
 * A class to access TaskManager data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private String filePath;

    public XmlTaskManagerStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getTaskManagerFilePath() {
        return this.filePath;
    }

    @Override
    public void setTaskManagerFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<ReadOnlyItemManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(this.filePath);
    }

    /**
     * Similar to {@link #readTaskManager()}
     *
     * @param filePath
     *            location of the data. Cannot be null
     * @throws DataConversionException
     *             if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyItemManager> readTaskManager(String filePath)
            throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskManagerFile = new File(filePath);

        if (!taskManagerFile.exists()) {
            logger.info("TaskManager file " + taskManagerFile + " not found");
            return Optional.empty();
        }

        ReadOnlyItemManager taskManagerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskManagerOptional);
    }

    @Override
    public void saveTaskManager(ReadOnlyItemManager taskManager) throws IOException {
        saveTaskManager(taskManager, this.filePath);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyItemManager)}
     *
     * @param filePath
     *            location of the data. Cannot be null
     */
    @Override
    public void saveTaskManager(ReadOnlyItemManager taskManager, String filePath) throws IOException {
        assert taskManager != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
    }

    // @@author A0138909R
    @Override
    public void copyTaskManager(String oldPath, String newPath) throws IOException {
        assert oldPath != null;
        assert newPath != null;
        try {
            logger.info("Copying file.");
            Files.copy(Paths.get(oldPath), Paths.get(newPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            logger.info("I/O Exception when copying file.");
            throw new IOException("Error when copying file.");
        }
    }
    // @@author
}
