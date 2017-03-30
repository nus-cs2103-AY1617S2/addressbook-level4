package seedu.todolist.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.FileUtil;
import seedu.todolist.model.ReadOnlyTodoList;

/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class XmlTodoListStorage implements TodoListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTodoListStorage.class);

    private String filePath;

    public XmlTodoListStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getTodoListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException {
        return readTodoList(filePath);
    }

    /**
     * Similar to {@link #readTodoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTodoList> readTodoList(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File todoListFile = new File(filePath);

        if (!todoListFile.exists()) {
            logger.info("TodoList file "  + todoListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTodoList todoListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(todoListOptional);
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList todoList) throws IOException {
        saveTodoList(todoList, filePath);
    }

    /**
     * Similar to {@link #saveTodoList(ReadOnlyTodoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTodoList(ReadOnlyTodoList todoList, String filePath) throws IOException {
        assert todoList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTodoList(todoList));
    }

}
