package seedu.taskboss.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.taskboss.commons.exceptions.DataConversionException;
import seedu.taskboss.commons.util.XmlUtil;

/**
 * Stores TaskBoss data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given TaskBoss data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskBoss taskBoss)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, taskBoss);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns TaskBoss in the file or an empty TaskBoss
     */
    public static XmlSerializableTaskBoss loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTaskBoss.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
