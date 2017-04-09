package seedu.task.model.util;

import java.util.Random;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.task.EndDate;
import seedu.task.model.task.StartDate;

//@@author A0163848R
public class TaskDateGenerator {

    private static final int MAX_DAYS_TO_ADD = 255;

    private static final String[] STARTDATES = {
        "today",
        "tomorrow",
        "yesterday",
        "day after tomorrow",
        "day before yesterday",
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
        "next monday",
        "next tuesday",
        "next wednesday",
        "next thursday",
        "next friday",
        "next saturday",
        "next sunday",
        "last monday",
        "last tuesday",
        "last wednesday",
        "last thursday",
        "last friday",
        "last saturday",
        "last sunday",
        "00-00-0000",
        "00-00-1000",
        "12-12-1234",
        "15-05-1977",
        "22-09-2024",
        "05-02-2199",
        "06.04.12017",
    };

    private static Random r;

    private static StartDate lastStartDate;

    public static StartDate getStartDate(Random r) {
        TaskDateGenerator.r = r;

        try {
            lastStartDate = new StartDate(CollectionUtil.getRandom(STARTDATES, r));
        } catch (IllegalValueException e) {
            return getStartDate(r);
        }

        return lastStartDate;
    }

    public static EndDate getEndDate(Random r) {
        if (lastStartDate == null) {
            getStartDate(r);
        }

        try {
            return new EndDate(r.nextInt(MAX_DAYS_TO_ADD + 1) + " days after " + lastStartDate.toString());
        } catch (IllegalValueException e) {
            getStartDate(r);
            return getEndDate(r);
        }
    }
}
