package savvytodo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import savvytodo.commons.core.LogsCenter;
import savvytodo.commons.exceptions.DataConversionException;
import savvytodo.commons.util.FileUtil;
import savvytodo.model.ReadOnlyTaskManager;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private String filePath;

    public XmlTaskManagerStorage(String filePath) {
        this.filePath = filePath;
    }

    public String gettaskManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManaager() throws DataConversionException, IOException {
        return taskManagerBook(filePath);
    }

    /**
     * Similar to {@link #readTaskManaager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskManager> taskManagerBook(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskManager addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager addressBook) throws IOException {
        saveTaskManager(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskManager(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

}
