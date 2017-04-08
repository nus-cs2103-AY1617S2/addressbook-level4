package seedu.task.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/FileUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validTaskManager.xml");
    private static final File TEMP_FILE = new File(TEST_DATA_FOLDER + "tempTaskManager.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getPath() {

        // valid case
        assertEquals("folder" + File.separator + "sub-folder", FileUtil.getPath("folder/sub-folder"));

        // null parameter -> assertion failure
        thrown.expect(AssertionError.class);
        FileUtil.getPath(null);

        // no forwards slash -> assertion failure
        thrown.expect(AssertionError.class);
        FileUtil.getPath("folder");
    }

    @Test
    public void createDir_success() throws IOException {
        FileUtil.createDirs(TEMP_FILE);
    }

    @Test
    public void createDir_fail() throws IOException {
        thrown.expect(IOException.class);
        FileUtil.createDirs(new File("#$%^&*"));
    }

    @Test
    public void creatFile_success() throws IOException {
        File file = new File(TEST_DATA_FOLDER + "CREATED.xml");
        assertTrue(FileUtil.createFile(file));
        file.delete();

    }

    @Test
    public void creatFile_fail() throws IOException {
        // creating an existing file
        assertFalse(FileUtil.createFile(EMPTY_FILE));

    }

    @Test
    public void isFileFormatCorrect_success() {
        assertTrue(FileUtil.isFileFormatCorrect(VALID_FILE));
    }

    @Test
    public void isFileFormatCorrect_fail() {
        assertFalse(FileUtil.isFileFormatCorrect(MISSING_FILE));

    }

}
