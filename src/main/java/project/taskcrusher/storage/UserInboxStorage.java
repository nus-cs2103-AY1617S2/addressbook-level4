package project.taskcrusher.storage;

import java.io.IOException;
import java.util.Optional;

import project.taskcrusher.commons.exceptions.DataConversionException;
import project.taskcrusher.model.ReadOnlyUserInbox;

/**
 * Represents a storage for {@link project.taskcrusher.model.UserInbox}.
 */
public interface UserInboxStorage {

    /**
     * Returns the file path of the data file.
     */
    String getUserInboxFilePath();

    /**
     * Returns UserInbox data as a {@link ReadOnlyUserInbox}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUserInbox> readUserInbox() throws DataConversionException, IOException;

    /**
     * @see #getUserInboxFilePath()
     */
    Optional<ReadOnlyUserInbox> readUserInbox(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyUserInbox} to the storage.
     * @param userInbox cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserInbox(ReadOnlyUserInbox userInbox) throws IOException;

    /**
     * @see #saveUserInbox(ReadOnlyUserInbox)
     */
    void saveUserInbox(ReadOnlyUserInbox userInbox, String filePath) throws IOException;

}
