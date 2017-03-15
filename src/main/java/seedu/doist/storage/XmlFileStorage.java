package seedu.doist.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.commons.util.XmlUtil;

/**
 * Stores TodoList data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given to-do list data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTodoList addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns todoList in the file or an empty todoList list
     */
    public static XmlSerializableTodoList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTodoList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
