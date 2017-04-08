package seedu.today.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

//@@author A0144422R
public class DateTimeTest {
    @Test
    public void dateTimeTest() {
        DateTime dateTime = new DateTime(
                new PrettyTimeParser().parse("30 APRIL 2017").get(0));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        assertTrue(df.format(dateTime.getDate()).equals("30/04/2017"));
        Date date1 = new PrettyTimeParser().parse("31 May 2017").get(0);
        assertFalse(dateTime.isSameDay(date1));
        dateTime.set(date1);
        assertTrue(df.format(dateTime.getDate()).equals("31/05/2017"));
        Date date2 = new PrettyTimeParser().parse("31 May 2017 3.45pm").get(0);
        assertTrue(dateTime.isSameDay(date2));
        Date date3 = new PrettyTimeParser().parse("5 seconds ago ").get(0);
        dateTime.set(date3);
        assertTrue(/*
                    * dateTime.toString().equals("Ƭ��֮ǰ") ||
                    */ dateTime.toString().equals("moments ago"));
    }
}
