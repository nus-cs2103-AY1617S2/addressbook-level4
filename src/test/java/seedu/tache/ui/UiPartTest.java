package seedu.tache.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import javafx.fxml.FXML;
import seedu.tache.MainApp;

public class UiPartTest {

    private static final String MISSING_FILE_PATH = "UiPartTest/missingFile.fxml";
    private static final String INVALID_FILE_PATH = "UiPartTest/invalidFile.fxml";
    private static final String VALID_FILE_PATH = "UiPartTest/validFile.fxml";
    private static final TestFxmlObject VALID_FILE_ROOT = new TestFxmlObject("Hello World!");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void constructorNullFileUrlThrowsAssertionError() {
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>((URL) null);
        fail();
    }

    @Test
    public void constructorMissingFileUrlThrowsAssertionError() throws Exception {
        URL missingFileUrl = new URL(testFolder.getRoot().toURI().toURL(), MISSING_FILE_PATH);
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>(missingFileUrl);
        fail();
    }

    @Test
    public void constructorInvalidFileUrlThrowsAssertionError() {
        URL invalidFileUrl = getTestFileUrl(INVALID_FILE_PATH);
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>(invalidFileUrl);
        fail();
    }

    @Test
    public void constructorValidFileUrlLoadsFile() {
        URL validFileUrl = getTestFileUrl(VALID_FILE_PATH);
        assertEquals(VALID_FILE_ROOT, new TestUiPart<TestFxmlObject>(validFileUrl).getRoot());
    }

    @Test
    public void constructorNullFileNameThrowsAssertionError() {
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>((String) null);
        fail();
    }

    @Test
    public void constructorMissingFileNameThrowsAssertionError() {
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>(MISSING_FILE_PATH);
        fail();
    }

    @Test
    public void constructorInvalidFileNameThrowsAssertionError() {
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>(INVALID_FILE_PATH);
        fail();
    }

    private URL getTestFileUrl(String testFilePath) {
        String testFilePathInView = "/view/" + testFilePath;
        URL testFileUrl = MainApp.class.getResource(testFilePathInView);
        assertNotNull(testFilePathInView + " does not exist.", testFileUrl);
        return testFileUrl;
    }

    /**
     * UiPart used for testing.
     * It should only be used with invalid FXML files or the valid file located at {@link VALID_FILE_PATH}.
     */
    private static class TestUiPart<T> extends UiPart<T> {

        @FXML
        private TestFxmlObject validFileRoot; // Check that @FXML annotations work

        TestUiPart(URL fxmlFileUrl) {
            super(fxmlFileUrl);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

        TestUiPart(String fxmlFileName) {
            super(fxmlFileName);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

    }

}
