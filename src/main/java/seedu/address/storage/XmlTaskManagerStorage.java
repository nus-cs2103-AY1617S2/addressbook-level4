package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

<<<<<<< HEAD:src/main/java/seedu/address/storage/XmlTaskManagerStorage.java
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskManager;
=======
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.FileUtil;
import seedu.task.model.ReadOnlyTaskManager;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/XmlAddressBookStorage.java

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private String filePath;

    public XmlTaskManagerStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getTaskManagerFilePath() {
        return filePath;
    }

    @Override
<<<<<<< HEAD:src/main/java/seedu/address/storage/XmlTaskManagerStorage.java
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(filePath);
=======
    public Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/XmlAddressBookStorage.java
    }

    /**
     * Similar to {@link #readTaskManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
<<<<<<< HEAD:src/main/java/seedu/address/storage/XmlTaskManagerStorage.java
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException,
=======
    public Optional<ReadOnlyTaskManager> readAddressBook(String filePath) throws DataConversionException,
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/XmlAddressBookStorage.java
                                                                                 FileNotFoundException {
        assert filePath != null;

        File taskManagerFile = new File(filePath);

        if (!taskManagerFile.exists()) {
            logger.info("TaskManager file "  + taskManagerFile + " not found");
            return Optional.empty();
        }

<<<<<<< HEAD:src/main/java/seedu/address/storage/XmlTaskManagerStorage.java
        ReadOnlyTaskManager taskManagerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
=======
        ReadOnlyTaskManager addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/XmlAddressBookStorage.java

        return Optional.of(taskManagerOptional);
    }

    @Override
<<<<<<< HEAD:src/main/java/seedu/address/storage/XmlTaskManagerStorage.java
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, filePath);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        assert taskManager != null;
=======
    public void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
        assert addressBook != null;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/XmlAddressBookStorage.java
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
    }

}
