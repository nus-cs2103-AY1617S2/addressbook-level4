//@@author A0139221N
package seedu.tasklist.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IntegerUtilTest {

    @Test
    public void isSingleDigitTest() {
        //negative integers
        assertTrue(IntegerUtil.isSingleDigit(-9));
        assertFalse(IntegerUtil.isSingleDigit(-10));
        //positive integers
        assertTrue(IntegerUtil.isSingleDigit(9));
        assertFalse(IntegerUtil.isSingleDigit(10));
        //zero
        assertTrue(IntegerUtil.isSingleDigit(0));
    }
}
