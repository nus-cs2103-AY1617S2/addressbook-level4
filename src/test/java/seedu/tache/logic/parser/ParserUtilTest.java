//@@author A0139925U
package seedu.tache.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;


public class ParserUtilTest {

    @Test
    public void parserUtil_invalidParameter_failure() {
        assertFalse(ParserUtil.isValidParameter("sdfsd"));
        assertFalse(ParserUtil.isValidParameter("sdkl"));
        assertFalse(ParserUtil.isValidParameter("sd jkjk"));
        assertFalse(ParserUtil.isValidParameter("sd ed"));
        assertFalse(ParserUtil.isValidParameter("tggy"));
        assertFalse(ParserUtil.isValidParameter("ccc"));
    }

    @Test
    public void parserUtil_validParameter_success() {
        assertTrue(ParserUtil.isValidParameter("tag"));
        assertTrue(ParserUtil.isValidParameter("t"));
        assertTrue(ParserUtil.isValidParameter("start_date"));
        assertTrue(ParserUtil.isValidParameter("enddate"));
        assertTrue(ParserUtil.isValidParameter("ri"));
    }

    @Test
    public void parserUtil_parseIndex_failure() {
        assertEquals(ParserUtil.parseIndex("sdfsd"), Optional.empty());
        assertEquals(ParserUtil.parseIndex("123ddsfs"), Optional.empty());
        assertEquals(ParserUtil.parseIndex("sdf 1"), Optional.empty());
    }

    @Test
    public void parserUtil_hasDate_failure() {
        assertFalse(ParserUtil.hasDate("sdfsd"));
        assertFalse(ParserUtil.hasDate("7 april"));
        assertFalse(ParserUtil.hasDate("10am"));
    }

    @Test
    public void parserUtil_hasDate_success() {
        assertTrue(ParserUtil.hasDate("04/04/17"));
        assertTrue(ParserUtil.hasDate("10-04-17"));
    }

    @Test
    public void parserUtil_hasTime_failure() {
        assertFalse(ParserUtil.hasTime("sdfsd"));
        assertFalse(ParserUtil.hasTime("7 april"));
        assertFalse(ParserUtil.hasTime("10--am"));
    }

    @Test
    public void parserUtil_hasTime_success() {
        assertTrue(ParserUtil.hasTime("9pm"));
        assertTrue(ParserUtil.hasTime("8.00pm"));
        assertTrue(ParserUtil.hasTime("10am"));
    }

}
