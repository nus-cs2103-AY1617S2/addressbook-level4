package seedu.address.model.util;

import java.util.Date;
import java.util.Random;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.DateUtil;

//@@author A0163848R
public class TaskDateGenerator {

    private static final int MAX_DAYS_TO_ADD = 255;

    private static final String[] STARTDATES = {
            "today",
            "tomorrow",
            "yesterday",
            "day after tomorrow",
            "next year",
            "last year",
            "this year",
            "april 1",
            "may 28",
            "monday",
            "tuesday",
            "wednesday",
            "thursday",
            "friday",
            "saturday",
            "sunday",
            "0000-00-00",
            "1000-00-00",
            "1234-12-12",
            "1977-05-15",
            "2024-09-22",
            "2199-02-05",
            "12017.04.06",
    };
    
    private static Random r;
    
    private static Date lastStartDate;
    
    public static String getStartDate(Random r) {
        TaskDateGenerator.r = r;
        
        try {
            lastStartDate = DateUtil.parse(CollectionUtil.getRandom(STARTDATES, r));
        } catch (IllegalValueException e) {
            return getStartDate(r);
        }
        
        return lastStartDate.toString();
    }
    
    public static String getEndDate(Random r) {
        if (lastStartDate == null) {
            getStartDate(r);
        }
        
        return DateUtil.add(lastStartDate, r.nextInt(MAX_DAYS_TO_ADD + 1)).toString();
    }
}
