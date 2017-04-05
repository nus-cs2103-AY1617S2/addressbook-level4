package seedu.whatsleft.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.commons.exceptions.DataConversionException;
import seedu.whatsleft.commons.util.FileUtil;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;

/**
 * A class to access WhatsLeft data stored as an xml file on the hard disk.
 */
public class XmlWhatsLeftStorage implements WhatsLeftStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlWhatsLeftStorage.class);

    private String filePath;

    public XmlWhatsLeftStorage(String filePath) {
        this.filePath = filePath;
    }

    public void setWhatsLeftFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getWhatsLeftFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWhatsLeft> readWhatsLeft() throws DataConversionException, IOException {
        return readWhatsLeft(filePath);
    }

    /**
     * Similar to {@link #readWhatsLeft()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWhatsLeft> readWhatsLeft(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File whatsLeftFile = new File(filePath);

        if (!whatsLeftFile.exists()) {
            logger.info("WhatsLeft file "  + whatsLeftFile + " not found");
            return Optional.empty();
        }

        ReadOnlyWhatsLeft whatsLeftOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(whatsLeftOptional);
    }

    @Override
    public void saveWhatsLeft(ReadOnlyWhatsLeft whatsLeft) throws IOException {
        saveWhatsLeft(whatsLeft, filePath);
    }

    /**
     * Similar to {@link #saveWhatsLeft(ReadOnlyWhatsLeft)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveWhatsLeft(ReadOnlyWhatsLeft whatsLeft, String filePath) throws IOException {
        assert whatsLeft != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableWhatsLeft(whatsLeft));
    }
}
