package seedu.taskboss.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.joestelmach.natty.DateGroup;

public class DateTimeTest {

    private com.joestelmach.natty.Parser nattyParser = new com.joestelmach.natty.Parser();

    @Test
    public void isValidDateTime() {
        // invalid dateTime
        List<DateGroup> dateGroupList = this.nattyParser.parse(" "); // spaces only
        int numDates = countDates(dateGroupList);
        boolean isSpaceValid = true;
        if (numDates != 1) {
            isSpaceValid = false;
        }
        assertFalse(isSpaceValid);

        List<DateGroup> dateGroupList2 = this.nattyParser.parse("dateTime"); // invalid dateTime
        int numDates2 = countDates(dateGroupList2);
        boolean isRandomValid = true;
        if (numDates2 != 1) {
            isRandomValid = false;
        }
        assertFalse(isRandomValid);

        List<DateGroup> dateGroupList3 = this.nattyParser.parse("this friday to next monday"); // not a single date
        int numDates3 = countDates(dateGroupList3);
        boolean isMultiplesValid = true;
        if (numDates3 != 1) {
            isMultiplesValid = false;
        }
        assertFalse(isMultiplesValid);

        // valid dateTime
        List<DateGroup> dateGroupList4 = this.nattyParser.parse("next wednesday 8.59pm");
        int numDates4 = countDates(dateGroupList4);
        boolean isNaturalValid = true;
        if (numDates4 != 1) {
            isNaturalValid = false;
        }
        assertTrue(isNaturalValid);

        List<DateGroup> dateGroupList5 = this.nattyParser.parse("11/03/2017");
        int numDates5 = countDates(dateGroupList5);
        boolean isSlashValid = true;
        if (numDates5 != 1) {
            isSlashValid = false;
        }
        assertTrue(isSlashValid);

        List<DateGroup> dateGroupList6 = this.nattyParser.parse("Feb 29 3am");
        int numDates6 = countDates(dateGroupList6);
        boolean isWordValid = true;
        if (numDates6 != 1) {
            isWordValid = false;
        }
        assertTrue(isWordValid);

        List<DateGroup> dateGroupList7 = this.nattyParser.parse("30-3-2015");
        int numDates7 = countDates(dateGroupList7);
        boolean isDashValid = true;
        if (numDates7 != 1) {
            isDashValid = false;
        }
        assertTrue(isDashValid);
    }

    private int countDates(List<DateGroup> dateGroups) {
        int numTotalDates = 0;
        for (DateGroup dateGroup : dateGroups) {
            numTotalDates += dateGroup.getDates().size();
        }
        return numTotalDates;
    }
}
