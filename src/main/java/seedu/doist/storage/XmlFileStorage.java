package seedu.doist.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.commons.util.XmlUtil;

/**
 * Stores data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given list of data to the specified file.
     */
    // TODO: limit the allowed classes to only AliasListMap and TodoList
    public static <T> void saveDataToFile(File file, T list)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, list);
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

    /**
     * Returns aliasListMap in the file or an empty aliasListMap map
     */
    public static XmlSerializableAliasListMap loadAliasDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAliasListMap.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
