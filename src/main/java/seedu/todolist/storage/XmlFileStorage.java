package seedu.todolist.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.XmlUtil;

/**
 * Stores todo list data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given todo list data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTodoList todoList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, todoList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns address book in the file or an empty address book
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
