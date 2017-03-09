package seedu.taskboss.logic.parser;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.task.DateTime;

/*
 * Parses date
 */
public class DateParser {

    private com.joestelmach.natty.Parser nattyParser;

    public DateParser() {
        this.nattyParser = new com.joestelmach.natty.Parser();
    }

    public DateTime parseStartDate(String date) throws IllegalValueException {

        if (Locale.getDefault().equals(Locale.US)) {
            date = date.replaceAll("(\\d{1,2})-(\\d{1,2})-((?:\\d\\d){1,2})", "$2-$1-$3");
        }

        List<DateGroup> dateGroupList = this.nattyParser.parse(date);
        int numDates = countDates(dateGroupList);
        System.out.println(numDates + "HELLO " + date);

        if (numDates == 0) {
            throw new IllegalValueException("START DATE: Failed to understand give date.");
        }

        if (numDates > 1) {
            throw new IllegalValueException("START DATE: Please only enter a single date.");
        }

        DateGroup dateGroup = dateGroupList.get(0);

        return new DateTime(dateGroup.getDates().get(0), dateGroup.isDateInferred(), dateGroup.isTimeInferred());

    }

    public DateTime parseEndDate(String date) throws IllegalValueException {

        if (Locale.getDefault().equals(Locale.US)) {
            date = date.replaceAll("(\\d{1,2})-(\\d{1,2})-((?:\\d\\d){1,2})", "$2-$1-$3");
        }

        List<DateGroup> dateGroupList = this.nattyParser.parse(date);
        int numDates = countDates(dateGroupList);
        System.out.println(numDates + "HELLO " + date);

        if (numDates == 0) {
            throw new IllegalValueException("END DATE: Failed to understand given date.");
        }

        if (numDates > 1) {
            throw new IllegalValueException("END DATE: Please only enter a single date.");
        }

        DateGroup dateGroup = dateGroupList.get(0);

        return new DateTime(dateGroup.getDates().get(0), dateGroup.isDateInferred(), dateGroup.isTimeInferred());

    }

    public Optional<String> convert(Optional<DateTime> dateTime) {
        return dateTime.isPresent() ? Optional.of(dateTime.toString()) : Optional.empty();
    }

    private int countDates(List<DateGroup> dateGroups) {
        int numTotalDates = 0;
        for (DateGroup dateGroup : dateGroups) {
            numTotalDates += dateGroup.getDates().size();
        }
        return numTotalDates;
    }
}