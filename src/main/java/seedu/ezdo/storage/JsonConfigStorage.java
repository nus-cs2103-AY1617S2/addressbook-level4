package seedu.ezdo.storage;

/**
 * A class to access Config stored in the hard disk as a json file
 */
public class JsonConfigStorage implements ConfigStorage {

    private String filePath;

    public JsonConfigStorage(String filePath) {
        this.filePath = filePath;
    }

}
