package org.teamstbf.yats.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import org.teamstbf.yats.commons.core.LogsCenter;
import org.teamstbf.yats.commons.exceptions.DataConversionException;
import org.teamstbf.yats.commons.util.FileUtil;
import org.teamstbf.yats.model.ReadOnlyTaskManager;

/**
 * A class to access TaskManager data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private static String filePath;

    public XmlTaskManagerStorage(String filePath) {
	XmlTaskManagerStorage.filePath = filePath;
    }

    @Override
    public String getTaskManagerFilePath() {
	return filePath;
    }

    public static String getFilePath() {
	return filePath;
    }

    public static void setTaskManagerFilePath(String newFilePath) {
	XmlTaskManagerStorage.filePath = newFilePath;
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
	return readTaskManager(filePath);
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
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath)
	    throws DataConversionException, FileNotFoundException {
	assert filePath != null;

	File addressBookFile = new File(filePath);

	if (!addressBookFile.exists()) {
	    logger.info("TaskManager file " + addressBookFile + " not found");
	    return Optional.empty();
	}

	ReadOnlyTaskManager addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

	return Optional.of(addressBookOptional);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
	saveTaskManager(taskManager, filePath);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyTaskManager)}
     *
     * @param filePath
     *            location of the data. Cannot be null
     */
    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
	assert taskManager != null;
	assert filePath != null;

	File file = new File(filePath);
	FileUtil.createIfMissing(file);
	XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
    }

}
