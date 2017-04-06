package seedu.whatsleft.commons.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
//@@author A0110491U
/**
 * This class is a Date-Time Natural Language Utility class that supports the natural language processing
 * for the date attributes implemented by the Events and Tasks classes. Natural Language for this is defined
 * as any String that does not fully contain digits. e.g. {"\\d+"} will not be considered natural language
 *
 */
public class DateTimeNLUtil {

    public static final String MESSAGE_DATELANG_CONSTRAINTS = "natural language function only supports"
            + "modifiers like next, following as well as relative days like today, tmr as well as absolute"
            + "days like mon, tue, wed, thu, fri, sat, sun";
    LocalDate today;
    EnumMap<Modifier, Integer> modmap = new EnumMap<Modifier, Integer>(Modifier.class);
    EnumMap<RelativeDay, Integer> relativedaymap = new EnumMap<RelativeDay, Integer>(RelativeDay.class);
    EnumMap<AbsDay, String> absdaymap = new EnumMap<AbsDay, String>(AbsDay.class);

    enum Modifier {
        next, following
    }

    enum RelativeDay {
        today, tmr, tomorrow
    }

    enum AbsDay {
        mon, tue, wed, thu, fri, sat, sun, monday, tuesday, wednesday, thursday, friday, saturday,
        sunday, tues, weds, thurs
    }

    /**
     * Constructor sets up the default values in the EnumMaps for instance of this class
     */
    public DateTimeNLUtil() {
        today = LocalDate.now();
        modmap.put(Modifier.next, 1);
        modmap.put(Modifier.following, 2);
        relativedaymap.put(RelativeDay.today, 0);
        relativedaymap.put(RelativeDay.tmr, 1);
        relativedaymap.put(RelativeDay.tomorrow, 1);
        absdaymap.put(AbsDay.mon, "MONDAY");
        absdaymap.put(AbsDay.tue, "TUESDAY");
        absdaymap.put(AbsDay.wed, "WEDNESDAY");
        absdaymap.put(AbsDay.thu, "THURSDAY");
        absdaymap.put(AbsDay.fri, "FRIDAY");
        absdaymap.put(AbsDay.sat, "SATURDAY");
        absdaymap.put(AbsDay.sun, "SUNDAY");
        absdaymap.put(AbsDay.monday, "MONDAY");
        absdaymap.put(AbsDay.tuesday, "TUESDAY");
        absdaymap.put(AbsDay.wednesday, "WEDNESDAY");
        absdaymap.put(AbsDay.thursday, "THURSDAY");
        absdaymap.put(AbsDay.friday, "FRIDAY");
        absdaymap.put(AbsDay.saturday, "SATURDAY");
        absdaymap.put(AbsDay.sunday, "SUNDAY");
        absdaymap.put(AbsDay.tues, "TUESDAY");
        absdaymap.put(AbsDay.weds, "WEDNESDAY");
        absdaymap.put(AbsDay.thurs, "THURSDAY");
    }

    /**
     * This method takes in a natural language String argument, and processes it to determine
     * if it has the required format to be converted to a date in String (DDMMYY)
     * @return 6 digit String in the format (DDMMYY)
     * @throws IllegalNLException if the natural language
     */
    public String getDate(String arg) throws IllegalNLException {
        arg = arg.toLowerCase();
        String[] args = arg.split(" ");
        if (args.length == 1) {
            if (isARelativeDay(args[0])) {
                int relative = relativedaymap.get(getRelativeEnum(args[0]));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
                String date = today.plusDays(relative).format(formatter);
                return date;
            } else if (isAnAbsDay(args[0])) {
                String day = absdaymap.get(getAbsDay(args[0]));
                DayOfWeek targetdow = DayOfWeek.valueOf(day);
                DayOfWeek currdow = today.getDayOfWeek();
                int daysleft = 0;
                while (targetdow != currdow) {
                    daysleft++;
                    currdow = currdow.plus(1);
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
                String date = today.plusDays(daysleft).format(formatter);
                return date;
            }
        } else if (args.length == 2) {
            if (isAModifier(args[0]) && isAnAbsDay(args[1])) {
                int mod = modmap.get(getModifier(args[0]));
                String day = absdaymap.get(getAbsDay(args[1]));
                DayOfWeek targetdow = DayOfWeek.valueOf(day);
                DayOfWeek currdow = today.getDayOfWeek();
                int daysleft = 0;
                while (targetdow != currdow) {
                    daysleft++;
                    currdow = currdow.plus(1);
                }
                daysleft = daysleft + (mod * 7);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
                String date = today.plusDays(daysleft).format(formatter);
                return date;
            }
        } else {
            throw new IllegalNLException(MESSAGE_DATELANG_CONSTRAINTS);
        }
        return null;
    }

    /**
     * Determines if this String argument is in the RelativeDay Enum
     */
    public static boolean isARelativeDay(String arg) {
        for (RelativeDay each : RelativeDay.values()) {
            if (each.name().equals(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns the Enum value in RelativeDay Enum that matches the given String argument
     */
    public static RelativeDay getRelativeEnum(String arg) {
        for (RelativeDay each : RelativeDay.values()) {
            if (each.name().equals(arg)) {
                return each;
            }
        }
        return null;
    }

    /**
     * Determines if this String argument is in the AbsDay Enum
     */
    public static boolean isAnAbsDay(String arg) {
        for (AbsDay day : AbsDay.values()) {
            if (day.name().equals(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns the Enum value in AbsDay Enum that matches the given String argument
     */
    public static AbsDay getAbsDay(String arg) {
        for (AbsDay day : AbsDay.values()) {
            if (day.name().equals(arg)) {
                return day;
            }
        }
        return null;
    }

    /**
     * Determines if this String argument is in the Modifier Enum
     */
    public static boolean isAModifier(String arg) {
        for (Modifier mod : Modifier.values()) {
            if (mod.name().equals(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns the Enum value in Modifier Enum that matches the given String argument
     */
    public static Modifier getModifier(String arg) {
        for (Modifier mod : Modifier.values()) {
            if (mod.name().equals(arg)) {
                return mod;
            }
        }
        return null;
    }

    /**
     * Represents the IllegalValueException for a Natural Language that was parsed
     *
     */
    public class IllegalNLException extends IllegalValueException {

        public IllegalNLException(String message) {
            super(message);
        }

    }
}
//@@author

