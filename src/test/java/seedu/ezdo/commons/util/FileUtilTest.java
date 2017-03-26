package seedu.ezdo.commons.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileUtilTest {

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
    public void createFile_noExists_false() throws Exception {
        File mock = mock(File.class);
        when(mock.exists()).thenReturn(true);
        assertFalse(FileUtil.createFile(mock));
    }

}
