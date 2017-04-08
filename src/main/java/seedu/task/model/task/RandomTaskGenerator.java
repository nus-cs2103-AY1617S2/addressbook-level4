package seedu.task.model.task;

import java.util.Optional;
import java.util.Random;

import seedu.task.commons.exceptions.IllegalValueException;

public class RandomTaskGenerator {
    private static int nameIndex = 0;
    private static double TASK_DATE_PERCENTAGE = .8;
    private static double TASK_TIME_PERCENTAGE = .8;
    private static double TASK_DESCRIPTION_PERCENTAGE = 1;
    private static Random rand = new Random();
    private static final String[] TASK_NAME_LIST = { "Go to class", "Go to store", "Go to gym" };
    private static final String[] CLASS_LIST = { "MATH", "NETWORKING", "PSYCHOLOGY", "CRYPTOGRAPHY", "PHILOSOPHY",
        "FAT STUDIES", "CHINESE", "PHYSICAL EDUCATION", "PHYSICS", "HEAT TRANSFER" };
    private static final String[] BUILDING_LIST = { "Love", "Woodruff", "MRDC", "CRC", "Klaus", "Knight", "Howey" };
    private static final String[] THINGS_TO_BUY = { "peanut butter", "milk", "soy milk", "snacks", "fruit", "bananas",
        "century eggs", "isotonic" };
    private static final String[] WORKOUT_DAYS = { "Chest day", "Back day", "Leg day", "Secondary chest day",
        "arm day" };

    public static Task generateTask() {
        boolean generated = false;
        while (!generated) {
            try {
                TaskName taskName = generateTaskName();
                Optional<TaskDate> taskDate = Optional.ofNullable(generateTaskDate());
                Optional<TaskTime> taskStartTime = Optional.ofNullable(generateTaskTime());
                Optional<TaskTime> taskEndTime = Optional.ofNullable(generateTaskTime());
                Optional<String> taskDescription = Optional.ofNullable(generateTaskDescription(taskName.fullTaskName));
                Task retTask = new Task(taskName, taskDate, taskStartTime, taskEndTime, taskDescription,
                        new TaskStatus(TaskStatus.MESSAGE_INCOMPLETE));
                return retTask;
            } catch (Exception e) {

            }
        }
        return null;

    }

    private static TaskTime generateTaskTime() throws IllegalValueException {
        if (!isFieldPresent(TASK_TIME_PERCENTAGE)) {
            return null;
        }
        int minute = rand.nextInt(4) * 15;
        int hour = rand.nextInt(16) + 8;
        if (minute == 0) {
            return new TaskTime(hour + "00");
        }
        return new TaskTime(hour + "" + minute);
    }

    private static TaskDate generateTaskDate() throws IllegalValueException {
        if (!isFieldPresent(TASK_DATE_PERCENTAGE)) {
            return null;
        }
        int day = rand.nextInt(31) + 1;
        int month = rand.nextInt(12) + 1;
        int year = 17;
        String retString = "";
        if (day < 10) {
            retString += "0" + day;
        } else {
            retString += day;
        }
        if (month < 10) {
            retString += "0" + month;
        } else {
            retString += month;
        }
        retString += year;
        return new TaskDate(retString);
    }

    private static TaskName generateTaskName() throws IllegalValueException {
        nameIndex = rand.nextInt(TASK_NAME_LIST.length);
        return new TaskName(TASK_NAME_LIST[nameIndex]);
    }

    private static boolean isFieldPresent(double fieldPresentRate) {
        if (fieldPresentRate > rand.nextDouble()) {
            return true;
        } else {
            return false;
        }
    }

    private static String generateTaskDescription(String taskName) {
        if (!isFieldPresent(TASK_DESCRIPTION_PERCENTAGE)) {
            return null;
        }
        if (nameIndex == 0) {
            String retString = "";
            retString = retString += CLASS_LIST[rand.nextInt(CLASS_LIST.length)];
            retString = retString + " " + (1000 + rand.nextInt(3000));
            retString = retString + " at ";
            retString = retString + BUILDING_LIST[rand.nextInt(BUILDING_LIST.length)];
            retString = retString + (rand.nextInt(5) + 1) + "" + rand.nextInt(10) + "" + rand.nextInt(10);
            return retString;
        } else if (nameIndex == 1) {
            String retString = "Remember to pickup ";
            for (int i = 0; i < THINGS_TO_BUY.length; i++) {
                if (rand.nextDouble() > 0.5) {
                    retString = retString += THINGS_TO_BUY[i] + ", ";
                }
            }
            if (retString.equals("Remember to pickup ")) {
                return null;
            } else {
                return retString.substring(0, retString.length() - 2);
            }
        } else {
            return WORKOUT_DAYS[rand.nextInt(WORKOUT_DAYS.length)];
        }
    }

}
