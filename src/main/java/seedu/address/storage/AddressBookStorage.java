package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyToDoList;

/**
 * Represents a storage for {@link seedu.address.model.ToDoList}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyToDoList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyToDoList> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyToDoList> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyToDoList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyToDoList addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyToDoList)
     */
    void saveAddressBook(ReadOnlyToDoList addressBook, String filePath) throws IOException;

}
