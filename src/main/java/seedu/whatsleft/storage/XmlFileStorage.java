package seedu.whatsleft.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.whatsleft.commons.exceptions.DataConversionException;
import seedu.whatsleft.commons.util.XmlUtil;

/**
 * Stores WhatsLeft data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given WhatsLeft data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableWhatsLeft whatsLeft)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, whatsLeft);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns WhatsLeft in the file or an empty WhatsLeft
     */
    public static XmlSerializableWhatsLeft loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableWhatsLeft.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
