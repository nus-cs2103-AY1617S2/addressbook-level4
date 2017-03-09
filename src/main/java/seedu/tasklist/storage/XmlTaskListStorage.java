package seedu.tasklist.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.exceptions.DataConversionException;
import seedu.tasklist.commons.util.FileUtil;
import seedu.tasklist.model.ReadOnlyTaskList;

/**
 * A class to access TaskList data stored as an xml file on the hard disk.
 */
public class XmlTaskListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskListStorage.class);

    private String filePath;

    public XmlTaskListStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getTaskListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return readTaskList(filePath);
    }

    /**
     * Similar to {@link #readTaskList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("TaskList file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskList addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList addressBook) throws IOException {
        saveTaskList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveTaskList(ReadOnlyTaskList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskList(ReadOnlyTaskList addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskList(addressBook));
    }

}
