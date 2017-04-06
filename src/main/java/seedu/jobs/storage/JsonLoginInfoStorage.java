package seedu.jobs.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.jobs.commons.exceptions.DataConversionException;
import seedu.jobs.commons.util.JsonUtil;
import seedu.jobs.model.LoginInfo;

public class JsonLoginInfoStorage implements LoginInfoStorage {
    
    private String filePath;

    public JsonLoginInfoStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<LoginInfo> readLoginInfo() throws DataConversionException, IOException {
        return readLoginInfo(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<LoginInfo> readLoginInfo(String loginInfoFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(loginInfoFilePath, LoginInfo.class);
    }

    @Override
    public void saveLoginInfo(LoginInfo loginInfo) throws IOException {
        JsonUtil.saveJsonFile(loginInfo, filePath);
    }
        
}
