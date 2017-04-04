# A0140887W-reused
###### \java\seedu\doist\logic\parser\UnfinishCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnfinishCommand object
 */
public class UnfinishCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the UnfinishCommand
     * and returns an UnfinishCommand object for execution.
     */
    public Command parse(String args) {

        int[] indices = ParserUtil.parseStringToIntArray(args);
        if (indices == null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                      UnfinishCommand.MESSAGE_USAGE));
        }
        return new UnfinishCommand(indices);
    }
}
```
###### \java\seedu\doist\storage\AliasListMapStorage.java
``` java
/**
 * Represents a storage for {@link seedu.doist.model.AliasListMap}.
 */
public interface AliasListMapStorage {

    /**
     * Returns AliasListMap data as a {@link ReadOnlyAliasListMap}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAliasListMap> readAliasListMap() throws DataConversionException, IOException;

    /**
     * @see #readAliasListMap()
     * @see #getAliasListFilePath()
     */
    Optional<ReadOnlyAliasListMap> readAliasListMap(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAliasListMap} to the storage.
     * @param aliasList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAliasListMap(ReadOnlyAliasListMap aliasList) throws IOException;

    /**
     * @see #saveAliasListMap(ReadOnlyAliasListMap)
     */
    void saveAliasListMap(ReadOnlyAliasListMap aliasList, String filePath) throws IOException;

    /**
     * Returns the file path of the data file.
     */
    String getAliasListMapFilePath();

    /**
     * Sets the file path of the data file.
     */
    void setAliasListMapFilePath(String path);

}
```
###### \java\seedu\doist\storage\XmlAliasListMapStorage.java
``` java
/**
 * A class to access AliasListMap data stored as an xml file on the hard disk.
 */
public class XmlAliasListMapStorage implements AliasListMapStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTodoListStorage.class);

    private String filePath;

    public XmlAliasListMapStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getAliasListMapFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAliasListMap> readAliasListMap() throws DataConversionException, IOException {
        return readAliasListMap(filePath);
    }

    /**
     * Similar to {@link #readAliasListMap()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAliasListMap> readAliasListMap(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File aliasListMapFile = new File(filePath);

        if (!aliasListMapFile.exists()) {
            logger.info("AliasList Map file "  + aliasListMapFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAliasListMap aliasListMapOptional = XmlFileStorage.loadAliasDataFromSaveFile(new File(filePath));

        return Optional.of(aliasListMapOptional);
    }

    @Override
    public void saveAliasListMap(ReadOnlyAliasListMap aliasList) throws IOException {
        saveAliasListMap(aliasList, filePath);
    }

    /**
     * Similar to {@link #saveAliasListMap(AliasListMap)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAliasListMap(ReadOnlyAliasListMap aliasListMap, String filePath) throws IOException {
        assert aliasListMap != null;
        assert filePath != null;
        logger.fine("XMLAliasListMapStorage: Trying to save alias list map...");

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAliasListMap(aliasListMap));
    }

    @Override
    public void setAliasListMapFilePath(String path) {
        this.filePath = path;
    }

}
```
