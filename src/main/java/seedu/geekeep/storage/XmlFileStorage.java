package seedu.geekeep.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.commons.util.XmlUtil;

/**
 * Stores taskmanager data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given taskmanager data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskManager taskManager)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, taskManager);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns GeeKeep in the file or an empty GeeKeep
     */
    public static XmlSerializableTaskManager loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTaskManager.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
