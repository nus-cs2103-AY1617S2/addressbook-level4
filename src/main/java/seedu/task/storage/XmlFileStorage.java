package seedu.task.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.XmlUtil;

/**
 * Stores task list data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given task list data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskList tasklist)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, tasklist);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns task list in the file or an empty task list
     */
    public static XmlSerializableTaskList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTaskList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
