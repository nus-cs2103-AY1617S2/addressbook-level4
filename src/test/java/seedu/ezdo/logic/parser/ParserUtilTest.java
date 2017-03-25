package seedu.ezdo.logic.parser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.regex.Matcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

public class ParserUtilTest {

    @Test
    public void parseSortCriteria_noMatch() {
        assertEquals(Optional.empty(), ParserUtil.parseSortCriteria(""));
    }

}
