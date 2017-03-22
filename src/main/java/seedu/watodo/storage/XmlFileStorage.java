package seedu.watodo.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.watodo.commons.exceptions.DataConversionException;
import seedu.watodo.commons.util.XmlUtil;

/**
 * Stores tasklist data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given tasklist data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskList taskList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, taskList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns address book in the file or an empty address book
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
