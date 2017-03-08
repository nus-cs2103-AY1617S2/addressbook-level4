package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserInbox;

public class TextFileStorage implements AddressBookStorage {

    @Override
    public String getAddressBookFilePath() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<ReadOnlyUserInbox> readAddressBook() throws DataConversionException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<ReadOnlyUserInbox> readAddressBook(String filePath) throws DataConversionException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveAddressBook(ReadOnlyUserInbox addressBook) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveAddressBook(ReadOnlyUserInbox addressBook, String filePath) throws IOException {
        // TODO Auto-generated method stub

    }

}
