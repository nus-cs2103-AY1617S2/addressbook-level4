# A0140887W-reused
###### \java\guitests\guihandles\TaskCardHandle.java
``` java
    private String prettyDate (Date date) {
        StringBuilder prettydate = new StringBuilder();
        prettydate.append(prettyMonth (date.getMonth() + 1));
        prettydate.append(" " + date.getDate() + ", ");
        prettydate.append((date.getYear() + 1900) + " at ");
        prettydate.append(prettyTime(date.getHours(), date.getMinutes()));
        return prettydate.toString();
    }

    private String prettyMonth (int month) {
        switch (month) {
        case 1 : return "January";
        case 2 : return "February";
        case 3 : return "March";
        case 4 : return "April";
        case 5 : return "May";
        case 6 : return "June";
        case 7 : return "July";
        case 8 : return "August";
        case 9 : return "September";
        case 10 : return "October";
        case 11 : return "November";
        case 12 : return "December";
        default : return null;
        }
    }

    private String prettyTime (int hours, int minutes) {
        String suffix = (hours <= 12) ? "am" : "pm";
        String hour = (hours <= 12) ? Integer.toString(hours) : Integer.toString(hours - 12);
        String minute = (minutes < 10) ? "0" + Integer.toString(minutes) : Integer.toString(minutes);
        return hour + ":" + minute + suffix;
    }

```
###### \java\seedu\doist\storage\XmlAliasListMapStorageTest.java
``` java
public class XmlAliasListMapStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAliasListMapStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAliasListMap_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readAliasListMap(null);
    }

    private java.util.Optional<ReadOnlyAliasListMap> readAliasListMap(String filePath) throws Exception {
        return new XmlAliasListMapStorage(filePath).readAliasListMap(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAliasListMap("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAliasListMap("NotXmlFormatAliasListMap.xml");
    }

    @Test
    public void readAndSaveTodoList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAliasListMap.xml";
        AliasListMap original = new AliasListMap();
        XmlAliasListMapStorage xmlAliasListMapStorage = new XmlAliasListMapStorage(filePath);

        //Save in new file and read back
        xmlAliasListMapStorage.saveAliasListMap(original, filePath);
        ReadOnlyAliasListMap readBack = xmlAliasListMapStorage.readAliasListMap(filePath).get();
        assertEquals(original, new AliasListMap(readBack));

        //Modify data, overwrite exiting file, and read back
        original.setAlias("hello", "add");
        xmlAliasListMapStorage.saveAliasListMap(original, filePath);
        readBack = xmlAliasListMapStorage.readAliasListMap(filePath).get();
        assertEquals(original, new AliasListMap(readBack));

        //Save and read without specifying file path
        original.setAlias("goodbye", "add");
        xmlAliasListMapStorage.saveAliasListMap(original); //file path not specified
        readBack = xmlAliasListMapStorage.readAliasListMap().get(); //file path not specified
        assertEquals(original, new AliasListMap(readBack));
    }

    @Test
    public void saveAliasListMap_nullTodoList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAliasListMap(null, "SomeFile.xml");
    }

    private void saveAliasListMap(ReadOnlyAliasListMap aliasListMap, String filePath) throws IOException {
        new XmlAliasListMapStorage(filePath).saveAliasListMap(aliasListMap, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAliasListMap_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAliasListMap(new AliasListMap(), null);
    }
}
```
