package t15b1.taskcrusher.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import t15b1.taskcrusher.commons.core.LogsCenter;
import t15b1.taskcrusher.commons.exceptions.DataConversionException;
import t15b1.taskcrusher.commons.util.FileUtil;
import t15b1.taskcrusher.model.ReadOnlyUserInbox;

/**
 * A class to access userInbox data stored as an xml file on the hard disk.
 */
public class XmlUserInboxStorage implements UserInboxStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUserInboxStorage.class);

    private String filePath;

    public XmlUserInboxStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getUserInboxFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserInbox> readUserInbox() throws DataConversionException, IOException {
        return readUserInbox(filePath);
    }

    /**
     * Similar to {@link #readUserInbox()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyUserInbox> readUserInbox(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File userInboxFile = new File(filePath);

        if (!userInboxFile.exists()) {
            logger.info("User Inbox file "  + userInboxFile + " not found");
            return Optional.empty();
        }

        ReadOnlyUserInbox userInboxOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(userInboxOptional);
    }

    @Override
    public void saveUserInbox(ReadOnlyUserInbox userInbox) throws IOException {
        saveUserInbox(userInbox, filePath);
    }

    /**
     * Similar to {@link #saveUserInbox(ReadOnlyUserInbox)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveUserInbox(ReadOnlyUserInbox userInbox, String filePath) throws IOException {
        assert userInbox != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableUserInbox(userInbox));
    }

}
