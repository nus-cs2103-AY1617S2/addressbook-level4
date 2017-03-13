package seedu.toluist.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import seedu.toluist.testutil.TestUtil;

import java.io.File;
import java.io.IOException;

/**
 * Tests for FileUtil
 */
public class FileUtilTest {
    @Before
    public void clearSandboxFolder() {
        TestUtil.cleanSandboxFolder();
    }

    @Test
    public void createIfMissing() throws IOException {
        File file = new File(TestUtil.getFilePathInSandboxFolder("missing"));
        FileUtil.createIfMissing(file);
        assertTrue(file.exists());
    }

    @Test
    public void createParentDirOfFile() throws IOException {
        File file = new File(TestUtil.getFilePathInSandboxFolder("missingFolder/missing"));
        FileUtil.createParentDirsOfFile(file);
        assertTrue(file.getParentFile().exists());
    }

    @Test
    public void createFile_noMissingParentDir() throws IOException {
        File file = new File(TestUtil.getFilePathInSandboxFolder("file"));
        FileUtil.createFile(file);
        assertTrue(file.exists());
    }

    @Test
    public void createFile_missingParentDirs() throws IOException {
        File file = new File(TestUtil.getFilePathInSandboxFolder("file/file/file"));
        FileUtil.createFile(file);
        assertTrue(file.exists());
        assertTrue(file.getParentFile().exists());
        assertTrue(file.getParentFile().getParentFile().exists());
    }

    @Test
    public void writeFile() throws IOException {
        File file = new File(TestUtil.getFilePathInSandboxFolder("file/file/file"));
        String expected = "some content";
        FileUtil.writeToFile(file, expected);

        // Check that any missing parent dirs are created
        assertTrue(file.exists());
        assertTrue(file.getParentFile().exists());
        assertTrue(file.getParentFile().getParentFile().exists());

        // Check that the content of the file when read is the same
        assertEquals(FileUtil.readFromFile(file), expected);
    }
}
