package seedu.ezdo.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

public class ParserUtilTest {

    @Test
    public void parseSortCriteria_noMatch() {
        assertEquals(Optional.empty(), ParserUtil.parseSortCriteria(""));
    }

}
