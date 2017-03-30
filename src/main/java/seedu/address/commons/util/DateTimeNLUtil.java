package seedu.address.commons.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author A0110491U
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
        today, tmr
    }

    enum AbsDay {
        mon, tue, wed, thu, fri, sat, sun
    }

    public DateTimeNLUtil() {
        today = LocalDate.now();
        modmap.put(Modifier.next, 1);
        modmap.put(Modifier.following, 2);
        relativedaymap.put(RelativeDay.today, 0);
        relativedaymap.put(RelativeDay.tmr, 1);
        absdaymap.put(AbsDay.mon, "MONDAY");
        absdaymap.put(AbsDay.tue, "TUESDAY");
        absdaymap.put(AbsDay.wed, "WEDNESDAY");
        absdaymap.put(AbsDay.thu, "THURSDAY");
        absdaymap.put(AbsDay.fri, "FRIDAY");
        absdaymap.put(AbsDay.sat, "SATURDAY");
        absdaymap.put(AbsDay.sun, "SUNDAY");
    }

    public String getDate(String arg) throws IllegalNLException {
        String[] args = arg.split(" ");
        if (args.length == 1) {
            System.out.println("ARGS 0 : " + args[0]);
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

    public static boolean isARelativeDay(String arg) {
        for (RelativeDay each : RelativeDay.values()) {
            if (each.name().equals(arg)) {
                return true;
            }
        }
        return false;
    }

    public static RelativeDay getRelativeEnum(String arg) {
        for (RelativeDay each : RelativeDay.values()) {
            if (each.name().equals(arg)) {
                return each;
            }
        }
        return null;
    }

    public static boolean isAnAbsDay(String arg) {
        for (AbsDay day : AbsDay.values()) {
            if (day.name().equals(arg)) {
                return true;
            }
        }
        return false;
    }

    public static AbsDay getAbsDay(String arg) {
        for (AbsDay day : AbsDay.values()) {
            if (day.name().equals(arg)) {
                return day;
            }
        }
        return null;
    }

    public static boolean isAModifier(String arg) {
        for (Modifier mod : Modifier.values()) {
            if (mod.name().equals(arg)) {
                return true;
            }
        }
        return false;
    }

    public static Modifier getModifier(String arg) {
        for (Modifier mod : Modifier.values()) {
            if (mod.name().equals(arg)) {
                return mod;
            }
        }
        return null;
    }

    public class IllegalNLException extends IllegalValueException {

        public IllegalNLException(String message) {
            super(message);
        }

    }
}
//@@author

