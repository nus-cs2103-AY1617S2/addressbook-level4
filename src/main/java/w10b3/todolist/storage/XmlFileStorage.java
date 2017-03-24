package w10b3.todolist.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import w10b3.todolist.commons.exceptions.DataConversionException;
import w10b3.todolist.commons.util.XmlUtil;

/**
 * Stores ToDoList data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given ToDoList data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableToDoList todoList)
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
    public static XmlSerializableToDoList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableToDoList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
