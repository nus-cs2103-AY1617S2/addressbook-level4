package seedu.whatsleft.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.whatsleft.commons.exceptions.DataConversionException;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;

/**
 * Represents a storage for {@link seedu.whatsleft.model.WhatsLeft}.
 */
public interface WhatsLeftStorage {

    /**
     * Returns the file path of the data file.
     */
    String getWhatsLeftFilePath();

    //@@author A0121668A
    /**
     * Sets the new storage location of the data file
     */
    void setWhatsLeftFilePath(String filepath);
  //@@author
    /**
     * Returns WhatsLeft data as a {@link ReadOnlyWhatsLeft}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */

    Optional<ReadOnlyWhatsLeft> readWhatsLeft() throws DataConversionException, IOException;

    /**
     * @see #getWhatsLeftFilePath()
     */
    Optional<ReadOnlyWhatsLeft> readWhatsLeft(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWhatsLeft} to the storage.
     * @param whatsLeft cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWhatsLeft(ReadOnlyWhatsLeft whatsLeft) throws IOException;

    /**
     * @see #saveWhatsLeft(ReadOnlyWhatsLeft)
     */
    void saveWhatsLeft(ReadOnlyWhatsLeft whatsLeft, String filePath) throws IOException;

}
