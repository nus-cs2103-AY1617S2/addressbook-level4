package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

<<<<<<< HEAD:src/main/java/seedu/address/storage/TaskManagerStorage.java
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskManager;
=======
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskManager;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/AddressBookStorage.java

/**
 * Represents a storage for {@link seedu.task.model.TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskManagerFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTaskManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
<<<<<<< HEAD:src/main/java/seedu/address/storage/TaskManagerStorage.java
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;
=======
    Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/AddressBookStorage.java

    /**
     * @see #getTaskManagerFilePath()
     */
<<<<<<< HEAD:src/main/java/seedu/address/storage/TaskManagerStorage.java
    Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManager} to the storage.
     * @param taskManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * @see #saveTaskManager(ReadOnlyTaskManager)
     */
    void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException;
=======
    Optional<ReadOnlyTaskManager> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyTaskManager)
     */
    void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/AddressBookStorage.java

}
