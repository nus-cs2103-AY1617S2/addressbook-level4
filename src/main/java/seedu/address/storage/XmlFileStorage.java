package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores toDoApp data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given toDoApp data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableToDoApp toDoApp)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, toDoApp);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns toDoApp in the file or an empty toDoApp
     */
    public static XmlSerializableToDoApp loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableToDoApp.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
