package todolist.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import todolist.commons.core.LogsCenter;
import todolist.commons.exceptions.DataConversionException;
import todolist.commons.util.FileUtil;
import todolist.model.ReadOnlyToDoList;

/**
 * A class to access ToDoList data stored as an xml file on the hard disk.
 */
public class XmlToDoListStorage implements ToDoListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlToDoListStorage.class);

    private String filePath;

    public XmlToDoListStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getToDoListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException {
        return readToDoList(filePath);
    }

    /**
     * Similar to {@link #readToDoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyToDoList> readToDoList(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File todoListFile = new File(filePath);

        if (!todoListFile.exists()) {
            logger.info("To-do List file "  + todoListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyToDoList todoListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        logger.info("load data from " + filePath);

        return Optional.of(todoListOptional);
    }

    @Override
    public void saveToDoList(ReadOnlyToDoList todoList) throws IOException {
        saveToDoList(todoList, filePath);
        logger.info("saved data to " + filePath);
    }

    /**
     * Similar to {@link #saveToDoList(ReadOnlyToDoList)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveToDoList(ReadOnlyToDoList todoList, String filePath) throws IOException {
        assert todoList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableToDoList(todoList));
    }

}
