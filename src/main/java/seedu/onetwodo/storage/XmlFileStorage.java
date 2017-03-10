package seedu.onetwodo.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.onetwodo.commons.exceptions.DataConversionException;
import seedu.onetwodo.commons.util.XmlUtil;

/**
 * Stores toDoList data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given toDoList data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableToDoList toDoList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, toDoList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns toDo List in the file or an empty toDo List
     */
    public static XmlSerializableToDoList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableToDoList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
