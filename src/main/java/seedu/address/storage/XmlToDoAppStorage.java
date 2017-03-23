package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyToDoApp;

/**
 * A class to access ToDoApp data stored as an xml file on the hard disk.
 */
public class XmlToDoAppStorage implements ToDoAppStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlToDoAppStorage.class);

    private String filePath;

    public XmlToDoAppStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getToDoAppFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyToDoApp> readToDoApp() throws DataConversionException, IOException {
        return readToDoApp(filePath);
    }

    /**
     * Similar to {@link #readToDoApp()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyToDoApp> readToDoApp(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File toDoAppFile = new File(filePath);

        if (!toDoAppFile.exists()) {
            logger.info("ToDoApp file "  + toDoAppFile + " not found");
            return Optional.empty();
        }

        ReadOnlyToDoApp toDoAppOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(toDoAppOptional);
    }

    @Override
    public void saveToDoApp(ReadOnlyToDoApp toDoApp) throws IOException {
        saveToDoApp(toDoApp, filePath);
    }

    /**
     * Similar to {@link #saveToDoApp(ReadOnlyToDoApp)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveToDoApp(ReadOnlyToDoApp toDoApp, String filePath) throws IOException {
        assert toDoApp != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableToDoApp(toDoApp));
    }

}
