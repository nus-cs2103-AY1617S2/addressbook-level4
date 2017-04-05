package seedu.task.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.FileUtil;
import seedu.task.model.ReadOnlyTaskList;

/**
 * A class to access TaskList data stored as an xml file on the hard disk.
 */
public class XmlTaskListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskListStorage.class);

    private String filePath;
    private boolean saved = false;
    private File savedFile = null;

    public XmlTaskListStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
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
    @Override
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException,
    FileNotFoundException {
        assert filePath != null;

        File taskListFile = new File(filePath);

        if (!taskListFile.exists()) {
            logger.info("TaskList file "  + taskListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskList taskListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskListOptional);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTaskList(taskList, filePath);
    }

    /**
     * Similar to {@link #saveTaskList(ReadOnlyTaskList)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        assert taskList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        assert file != null;
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskList(taskList));
        this.saved = true;
        this.savedFile = file;
    }

    //@@author A0163559U
    @Override
    public void saveTaskListInNewLocation(ReadOnlyTaskList taskList, File newFile) throws IOException {
        assert this.saved = true && this.savedFile != null;
        System.out.println("file is " + newFile.toString());
        System.out.println("file path is " + newFile.toPath().toString());
        //        System.out.println("saved file is " + savedFile.toString());
        //        System.out.println("saved file path is " + savedFile.toPath().toString());
        saveTaskList(taskList, filePath);
        try {

            newFile.delete(); //we are about to overwrite it
            Files.copy(savedFile.toPath(), newFile.toPath());
        } catch (FileAlreadyExistsException faee) {

        } catch (IOException ioe) {
            System.out.println("IO Exception in saveTaskListInNewLocation");
            ioe.printStackTrace();

        }
        updateState(newFile);
    }

    public void updateState(File file) {
        this.savedFile = file;
        this.filePath = file.toString();
    }

    @Override
    public Optional<ReadOnlyTaskList> loadTaskListFromNewLocation(ReadOnlyTaskList taskList, File loadFile)
            throws FileNotFoundException, DataConversionException {
        Optional<ReadOnlyTaskList> newTaskList = readTaskList(loadFile.toString());
        if (newTaskList.isPresent()) {
            updateState(loadFile);
        }
        return newTaskList;
    }
    //@@author


}
