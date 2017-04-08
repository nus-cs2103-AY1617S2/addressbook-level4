package seedu.task.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import seedu.task.commons.exceptions.IllegalValueException;

//@@author A0163845X
public class TaskTime {

    private List<Date> dates;
    private Date time;
    private SimpleDateFormat formatter;
    private final String OUTPUT_FORMAT = " hh:mm a ";

    public final String value;
    private int hour;
    private int minute;
    private String hourStr;
    private String minuteStr;
    public final String TIME_DELIMITER = ":";
    public final int MINUTE_ARRAY_INDEX = 0;
    public final int HOUR_ARRAY_INDEX = 1;

    public static final String MESSAGE_INVALID_TIME_FORMAT = "Invalid time format, be more prcise or try "
            + "hhmm, hh:mm, or h:mm";

    public TaskTime(String input) throws IllegalValueException {

        value = input.trim();

        try {
            int[] timeArray = timeFormatConverter(input);
            setMinute(timeArray[MINUTE_ARRAY_INDEX]);
            setHour(timeArray[HOUR_ARRAY_INDEX]);
            time = null;
        } catch (Exception e) {
            formatter = new SimpleDateFormat(OUTPUT_FORMAT);
            NattyParser natty = new NattyParser();
            dates = natty.parse(input);
            if (dates == null) {
                throw new IllegalValueException(MESSAGE_INVALID_TIME_FORMAT);
            }
            time = dates.get(0);
        }

    }

    @Override
    public String toString() {
        if (time == null) {
            return hourStr + TIME_DELIMITER + minuteStr;
        } else {
            return formatter.format(dates.get(0));
        }

    }

    public void setHour(int hour) throws IllegalValueException {
        if (0 <= hour && hour <= 23) {
            this.hour = hour;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TIME_FORMAT);
        }
    }

    public void setMinute(int minute) throws IllegalValueException {
        if (0 <= minute && minute <= 59) {
            this.minute = minute;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TIME_FORMAT);
        }
    }

    public int[] timeFormatConverter(String time) throws IllegalValueException {
        if (time.length() < 3 || time.length() > 5) {
            throw new IllegalValueException(MESSAGE_INVALID_TIME_FORMAT);
        } else if (time.length() == 3) {
            this.minuteStr = time.substring(1, 3);
            this.hourStr = time.substring(0, 1);
        } else if (time.length() == 4) {
            if (time.substring(1, 2).equals(TIME_DELIMITER)) {
                this.minuteStr = time.substring(2, 4);
                this.hourStr = "0" + time.substring(0, 1);
            } else {
                this.minuteStr = time.substring(2, 4);
                this.hourStr = time.substring(0, 2);
            }
        } else {
            if (!time.substring(2, 3).equals(TIME_DELIMITER)) {
                throw new IllegalValueException(MESSAGE_INVALID_TIME_FORMAT);
            }
            this.minuteStr = time.substring(3, 5);
            this.hourStr = time.substring(0, 2);
        }
        int minute = Integer.parseInt(minuteStr);
        int hour = Integer.parseInt(hourStr);
        int[] timeArray = { minute, hour };
        return timeArray;
    }

    public int compareTo(TaskTime other) {
        return ((this.hour * 60 + this.minute) - (other.hour * 60 + other.minute));
    }

    public static void main(String[] args) {
        try {
            TaskTime t = new TaskTime("0204");
            System.out.println(t);
            t = new TaskTime("305");
            System.out.println(t);
            t = new TaskTime("4:10");
            System.out.println(t);
            t = new TaskTime("12:30");
            System.out.println(t);
            t = new TaskTime("0:01");
            System.out.println(t);
        } catch (IllegalValueException ive) {

        }
    }
}
