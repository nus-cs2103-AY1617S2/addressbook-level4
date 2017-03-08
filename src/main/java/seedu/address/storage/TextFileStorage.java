package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyUserInbox;

public class TextFileStorage implements AddressBookStorage {
    
    private static final Logger logger = LogsCenter.getLogger(TextFileStorage.class);

    private String filePath;
    
    public TextFileStorage(String filePath){
        this.filePath = filePath;
    }
    
    public String getAddressBookFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlyUserInbox> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    public Optional<ReadOnlyUserInbox> readAddressBook(String filePath) throws DataConversionException, IOException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }
        
        //TODO Update this so that it parses data from text file
        ReadOnlyUserInbox addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    public void saveAddressBook(ReadOnlyUserInbox addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);

    }

    public void saveAddressBook(ReadOnlyUserInbox addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

      
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        
        //TODO Update this so that it saves data to text file
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));

    }

}
