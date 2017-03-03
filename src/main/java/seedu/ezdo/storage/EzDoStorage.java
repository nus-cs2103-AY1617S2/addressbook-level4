package seedu.ezdo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.model.ReadOnlyEzDo;

/**
 * Represents a storage for {@link seedu.ezdo.model.EzDo}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyEzDo}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEzDo> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyEzDo> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEzDo} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyEzDo addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyEzDo)
     */
    void saveAddressBook(ReadOnlyEzDo addressBook, String filePath) throws IOException;

}
