package seedu.task.commons.util;

import java.util.Date;

import org.junit.Test;

public class NattyDateUtilTest {

    @Test
    public void nattyutil_basicDate() {
        Date date = NattyDateUtil.parseSingleDate("03/10/17");
        System.out.println(date);
    }
}
