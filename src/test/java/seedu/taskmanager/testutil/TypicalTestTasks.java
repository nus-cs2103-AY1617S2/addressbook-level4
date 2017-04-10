package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList;

// @@author A0141102H
/**
 *
 */
public class TypicalTestTasks {

    public TestTask eatBreakfast, eatLunch, eatDinner, doCS, tryHarderCS, tryAgainCS, getFit, sampleEvent,
            sampleFloatingTask, sampleDeadline, sampleClashBetweenOneDayEvent, sampleClashBetweenMultipleDaysEvent,
            sampleClashAcrossMultipleDaysEvent, sampleClashStartOfMultipleDaysEvent, sampleClashEndOfMultipleDaysEvent,
            sampleNoClashSameDayEvent, sampleNoClashSeparateDayEvent, recurTestDay, recurTestDayOnce, recurTestDayTwice,
            recurTestDayThrice, recurTestWeek, recurTestWeekOnce, recurTestWeekTwice, recurTestWeekThrice,
            recurTestMonth, recurTestMonthOnce, recurTestMonthTwice, recurTestMonthThrice, recurTestYear,
            recurTestYearOnce, recurTestYearTwice, recurTestYearThrice, eventTestMon, eventTestTuesThurs,
            eventTestThurs, eventTestFriSat, updateEventFromIndexBeforeToAfter, updateEventFromIndexBeforeToBefore,
            updateEventFromIndexAfterToBefore, updateEventFromIndexAfterToAfter, updateEventFromClashToNoClash,
            completedEatBreakfast, fromToEventWithoutTime, onToEventWithEndTime, byDeadlineWithoutTime,
            byDeadlineWithTime;

    public TypicalTestTasks() {
        try {
            eatBreakfast = new TaskBuilder().withTaskName("Eat breakfast with mom").withStartDate("03/03/17")
                    .withStartTime("1000").withEndDate("03/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build(); // event
            doCS = new TaskBuilder().withTaskName("Start on the CS2103 project").withStartDate("03/03/17")
                    .withStartTime("1400").withEndDate("03/04/17").withEndTime("1800").withCompletion(false)
                    .withCategories("work").build(); // event
            eatDinner = new TaskBuilder().withTaskName("Eat dinner with my only 2 friends").withStartDate("09/03/17")
                    .withStartTime("1800").withEndDate("09/03/17").withEndTime("2000").withCompletion(false).build();
            tryHarderCS = new TaskBuilder().withTaskName("Try harder for CS2103 project").withStartDate("04/04/17")
                    .withStartTime("1400").withEndDate("05/04/17").withEndTime("1500").withCompletion(false)
                    .withCategories("lepak").build(); // event
            tryAgainCS = new TaskBuilder().withTaskName("Try even harder for CS2103").withStartDate("05/04/17")
                    .withStartTime("1500").withEndDate("05/05/17").withEndTime("1600").withCompletion(false)
                    .withCategories("work").build(); // event
            eatLunch = new TaskBuilder().withTaskName("Eat lunch at techno").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("04/03/17").withEndTime("1400").withCompletion(false)
                    .withCategories("no", "friends").build(); // deadline
            getFit = new TaskBuilder().withTaskName("Run 2.4km in 10 mins").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("EMPTY_FIELD").withEndTime("EMPTY_FIELD")
                    .withCompletion(false).withCategories("lepak").build(); // floating
                                                                            // task

            // Manually added
            sampleEvent = new TaskBuilder().withTaskName("Time to relax a little").withStartDate("06/05/17")
                    .withStartTime("1400").withEndDate("06/05/17").withEndTime("1800").withCompletion(false)
                    .withCategories("lepak").build();
            sampleFloatingTask = new TaskBuilder().withTaskName("Chiong all day everyday").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("EMPTY_FIELD").withEndTime("EMPTY_FIELD")
                    .withCompletion(false).withCategories("work").build();
            sampleDeadline = new TaskBuilder().withTaskName("Get it done").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("06/05/17").withEndTime("1700").withCompletion(false)
                    .withCategories("work").build();
            fromToEventWithoutTime = new TaskBuilder().withTaskName("Hell week").withStartDate("08/04/17")
                    .withStartTime("0000").withEndDate("11/04/17").withEndTime("2359").withCompletion(false)
                    .withCategories("rekt").build();
            onToEventWithEndTime = new TaskBuilder().withTaskName("Hell week").withStartDate("08/04/17")
                    .withStartTime("0000").withEndDate("08/04/17").withEndTime("1200").withCompletion(false)
                    .withCategories("rekt").build();
            byDeadlineWithoutTime = new TaskBuilder().withTaskName("Deadline").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("08/04/17").withEndTime("2359").withCompletion(false)
                    .withCategories("rekt").build();
            byDeadlineWithTime = new TaskBuilder().withTaskName("DedLine").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("08/04/17").withEndTime("1200").withCompletion(false)
                    .withCategories("rekt").build();

            // blocking time slots test tasks
            sampleClashBetweenOneDayEvent = new TaskBuilder().withTaskName("Escape Reality").withStartDate("13/04/17")
                    .withStartTime("1130").withEndDate("13/04/17").withEndTime("1800").withCompletion(false)
                    .withCategories("CS", "NO").build();
            sampleClashBetweenMultipleDaysEvent = new TaskBuilder().withTaskName("Midweek blues")
                    .withStartDate("12/04/17").withStartTime("1900").withEndDate("12/04/17").withEndTime("2200")
                    .withCompletion(false).withCategories("no", "dance").build();
            sampleClashAcrossMultipleDaysEvent = new TaskBuilder().withTaskName("No gap").withStartDate("10/04/17")
                    .withStartTime("1200").withEndDate("14/04/17").withEndTime("1000").withCompletion(false)
                    .withCategories("thigh", "gap").build();
            sampleClashStartOfMultipleDaysEvent = new TaskBuilder().withTaskName("Sighpie").withStartDate("11/04/17")
                    .withStartTime("1500").withEndDate("11/04/17").withEndTime("1800").withCompletion(false)
                    .withCategories("approaching", "submission").build();
            sampleClashEndOfMultipleDaysEvent = new TaskBuilder().withTaskName("Fixing Travis")
                    .withStartDate("13/04/17").withStartTime("0700").withEndDate("13/04/17").withEndTime("1100")
                    .withCompletion(false).withCategories("NoTravis").build();
            sampleNoClashSameDayEvent = new TaskBuilder().withTaskName("Reality hits hard").withStartDate("13/04/17")
                    .withStartTime("1200").withEndDate("14/04/17").withEndTime("1100").withCompletion(false)
                    .withCategories("CS", "rekt").build();
            sampleNoClashSeparateDayEvent = new TaskBuilder().withTaskName("Coding in Progress")
                    .withStartDate("16/04/17").withStartTime("2330").withEndDate("17/04/17").withEndTime("0030")
                    .withCompletion(false).withCategories("code", "work").build();

            eventTestMon = new TaskBuilder().withTaskName("Monday Blues").withStartDate("10/04/17")
                    .withStartTime("1000").withEndDate("10/04/17").withEndTime("1100").withCompletion(false)
                    .withCategories("not", "red").build();
            eventTestTuesThurs = new TaskBuilder().withTaskName("TwoDays").withStartDate("11/04/17")
                    .withStartTime("1700").withEndDate("13/04/17").withEndTime("0800").withCompletion(false)
                    .withCategories("not", "one").build();
            eventTestThurs = new TaskBuilder().withTaskName("Depressing Thursday").withStartDate("13/04/17")
                    .withStartTime("1100").withEndDate("13/04/17").withEndTime("1200").withCompletion(false)
                    .withCategories("CS2013", "impossible").build();
            eventTestFriSat = new TaskBuilder().withTaskName("Weekend").withStartDate("14/04/17").withStartTime("1230")
                    .withEndDate("15/04/17").withEndTime("1900").withCompletion(false).withCategories("still", "coding")
                    .build();

            updateEventFromIndexBeforeToBefore = new TaskBuilder().withTaskName("Eat breakfast with mom")
                    .withStartDate("03/03/17").withStartTime("1000").withEndDate("03/03/17").withEndTime("1500")
                    .withCompletion(false).withCategories("just", "friends").build();
            updateEventFromIndexBeforeToAfter = new TaskBuilder().withTaskName("Start on the CS2103 project")
                    .withStartDate("09/03/17").withStartTime("1900").withEndDate("11/03/17").withEndTime("1100")
                    .withCompletion(false).withCategories("work").build();
            updateEventFromIndexAfterToBefore = new TaskBuilder().withTaskName("Start on the CS2103 project")
                    .withStartDate("08/03/17").withStartTime("1000").withEndDate("11/03/17").withEndTime("1100")
                    .withCompletion(false).withCategories("work").build();
            updateEventFromIndexAfterToAfter = new TaskBuilder().withTaskName("Try even harder for CS2103")
                    .withStartDate("05/04/17").withStartTime("1430").withEndDate("05/04/17").withEndTime("1530")
                    .withCompletion(false).withCategories("work").build();
            updateEventFromClashToNoClash = new TaskBuilder().withTaskName("Salvage CS2103").withStartDate("05/04/17")
                    .withStartTime("1700").withEndDate("14/04/17").withEndTime("1100").withCompletion(false)
                    .withCategories("work").build();

            // completedTask
            completedEatBreakfast = new TaskBuilder().withTaskName("Eat breakfast with mom").withStartDate("03/03/17")
                    .withStartTime("1000").withEndDate("03/03/17").withEndTime("1100").withCompletion(true)
                    .withCategories("just", "friends").build(); // event

            // Recurring task
            recurTestDay = new TaskBuilder().withTaskName("I'm recurring").withStartDate("30/12/17")
                    .withStartTime("1000").withEndDate("30/12/17").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestDayOnce = new TaskBuilder().withTaskName("I'm recurring").withStartDate("31/12/17")
                    .withStartTime("1000").withEndDate("31/12/17").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestDayTwice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("01/01/18")
                    .withStartTime("1000").withEndDate("01/01/18").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestDayThrice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("02/01/18")
                    .withStartTime("1000").withEndDate("02/01/18").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestWeek = new TaskBuilder().withTaskName("I'm recurring").withStartDate("17/12/17")
                    .withStartTime("1000").withEndDate("17/12/17").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestWeekOnce = new TaskBuilder().withTaskName("I'm recurring").withStartDate("24/12/17")
                    .withStartTime("1000").withEndDate("24/12/17").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestWeekTwice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("31/12/17")
                    .withStartTime("1000").withEndDate("31/12/17").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestWeekThrice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("07/01/18")
                    .withStartTime("1000").withEndDate("07/01/17").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestMonth = new TaskBuilder().withTaskName("I'm recurring").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("03/11/17").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestMonthOnce = new TaskBuilder().withTaskName("I'm recurring").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("03/12/17").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestMonthTwice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("03/01/18").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestMonthThrice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("03/02/18").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestYear = new TaskBuilder().withTaskName("I'm recurring").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("03/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestYearOnce = new TaskBuilder().withTaskName("I'm recurring").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("03/03/18").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestYearTwice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("03/03/19").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();
            recurTestYearThrice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("03/03/20").withEndTime("1100").withCompletion(false)
                    .withCategories("Test").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public void TypicalTestEventsForBlockingTimeSlots() {
        try {
            // add command
            eventTestMon = new TaskBuilder().withTaskName("Monday Blues").withStartDate("10/04/17")
                    .withStartTime("1000").withEndDate("10/04/17").withEndTime("1100").withCompletion(false)
                    .withCategories("not", "red").build();
            eventTestTuesThurs = new TaskBuilder().withTaskName("TwoDays").withStartDate("11/04/17")
                    .withStartTime("1700").withEndDate("13/04/17").withEndTime("0800").withCompletion(false)
                    .withCategories("not", "one").build();
            eventTestThurs = new TaskBuilder().withTaskName("Depressing Thursday").withStartDate("13/04/17")
                    .withStartTime("1100").withEndDate("13/04/17").withEndTime("1200").withCompletion(false)
                    .withCategories("CS2013", "impossible").build();
            eventTestFriSat = new TaskBuilder().withTaskName("Weekend").withStartDate("14/04/17").withStartTime("1230")
                    .withEndDate("15/04/17").withEndTime("1900").withCompletion(false).withCategories("still", "coding")
                    .build();

            // update command

            updateEventFromIndexBeforeToBefore = new TaskBuilder().withTaskName("Eat breakfast with mom")
                    .withStartDate("03/03/17").withStartTime("1000").withEndDate("03/03/17").withEndTime("1500")
                    .withCompletion(false).withCategories("just", "friends").build();

            updateEventFromIndexBeforeToAfter = new TaskBuilder().withTaskName("Start on the CS2103 project")
                    .withStartDate("09/03/17").withStartTime("1900").withEndDate("11/03/17").withEndTime("1100")
                    .withCompletion(false).withCategories("work").build();

            updateEventFromIndexAfterToBefore = new TaskBuilder().withTaskName("Start on the CS2103 project")
                    .withStartDate("08/03/17").withStartTime("1000").withEndDate("11/03/17").withEndTime("1100")
                    .withCompletion(false).withCategories("not", "red").build();

            updateEventFromIndexAfterToAfter = new TaskBuilder().withTaskName("Try even harder for CS2103")
                    .withStartDate("05/04/17").withStartTime("1430").withEndDate("05/04/17").withEndTime("1530")
                    .withCompletion(false).withCategories("work").build();

            updateEventFromClashToNoClash = new TaskBuilder().withTaskName("Salvage CS2103").withStartDate("05/04/17")
                    .withStartTime("1700").withEndDate("14/04/17").withEndTime("1100").withCompletion(false)
                    .withCategories("work").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                tm.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { eatBreakfast, doCS, eatDinner, tryHarderCS, tryAgainCS, eatLunch, getFit };
    }

    public TestTask[] getTypicalRecurringEventTasksForDays() {
        return new TestTask[] { recurTestDay, recurTestDayOnce, recurTestDayTwice, recurTestDayThrice };
    }

    public TestTask[] getTypicalRecurringEventTasksForWeeks() {
        return new TestTask[] { recurTestWeek, recurTestWeekOnce, recurTestWeekTwice, recurTestWeekThrice };
    }

    public TestTask[] getTypicalRecurringDeadlineTasksForMonths() {
        return new TestTask[] { recurTestMonth, recurTestMonthOnce, recurTestMonthTwice, recurTestMonthThrice };
    }

    public TestTask[] getTypicalRecurringDeadlineTasksForYears() {
        return new TestTask[] { recurTestYear, recurTestYearOnce, recurTestYearTwice, recurTestYearThrice };
    }

    public TestTask[] getTypicalTestEventsForBlockingTimeSlots() {
        return new TestTask[] { eventTestMon, eventTestTuesThurs, eventTestThurs, eventTestFriSat };
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
