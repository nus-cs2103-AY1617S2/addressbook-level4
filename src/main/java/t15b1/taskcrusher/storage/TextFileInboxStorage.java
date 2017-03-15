package t15b1.taskcrusher.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import t15b1.taskcrusher.commons.core.LogsCenter;
import t15b1.taskcrusher.commons.exceptions.DataConversionException;
import t15b1.taskcrusher.commons.util.FileUtil;
import t15b1.taskcrusher.model.ReadOnlyUserInbox;
import t15b1.taskcrusher.model.UserInbox;

public class TextFileInboxStorage implements UserInboxStorage {

    private static final Logger logger = LogsCenter.getLogger(TextFileInboxStorage.class);

    private String filePath;

    public TextFileInboxStorage(String filePath){
        this.filePath = filePath;
    }

    public String getUserInboxFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlyUserInbox> readUserInbox() throws DataConversionException, IOException {
        return readUserInbox(filePath);
    }

    public Optional<ReadOnlyUserInbox> readUserInbox(String filePath) throws DataConversionException, IOException {
        assert filePath != null;

        File userInboxFile = new File(filePath);

        if (!userInboxFile.exists()) {
            logger.info("UserInbox file "  + userInboxFile + " not found");
            return Optional.empty();
        }

        ReadOnlyUserInbox userInboxOptional = TextFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(userInboxOptional);
    }

    public void saveUserInbox(ReadOnlyUserInbox userInbox) throws IOException {
        saveUserInbox(userInbox, filePath);

    }

    public void saveUserInbox(ReadOnlyUserInbox userInbox, String filePath) throws IOException {
        assert userInbox != null;
        assert filePath != null;


        File file = new File(filePath);
        FileUtil.createIfMissing(file);

        TextFileStorage.saveDataToFile(file, new UserInbox(userInbox));

    }

}
