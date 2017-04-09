package org.teamstbf.yats.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.teamstbf.yats.commons.exceptions.DataConversionException;
import org.teamstbf.yats.commons.util.XmlUtil;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {
	/**
	 * Saves the given addressbook data to the specified file.
	 */
	public static void saveDataToFile(File file, XmlSerializableTaskManager addressBook) throws FileNotFoundException {
		try {
			XmlUtil.saveDataToFile(file, addressBook);
		} catch (JAXBException e) {
			assert false : "Unexpected exception " + e.getMessage();
		}
	}

	/**
	 * Returns address book in the file or an empty address book
	 */
	public static XmlSerializableTaskManager loadDataFromSaveFile(File file)
			throws DataConversionException, FileNotFoundException {
		try {
			return XmlUtil.getDataFromFile(file, XmlSerializableTaskManager.class);
		} catch (JAXBException e) {
			throw new DataConversionException(e);
		}
	}

}
