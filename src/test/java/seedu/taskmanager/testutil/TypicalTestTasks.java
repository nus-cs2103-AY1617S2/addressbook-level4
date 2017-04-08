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

    public TestTask eatbreakfast, eatlunch, eatdinner, doCS, giveupCS, tryagainCS, regret, sampleEvent,
            sampleFloatingTask, sampleDeadline, recurTestDay, recurTestDayOnce, recurTestDayTwice, recurTestDayThrice,
            recurTestWeek, recurTestWeekOnce, recurTestWeekTwice, recurTestWeekThrice, recurTestMonth,
            recurTestMonthOnce, recurTestMonthTwice, recurTestMonthThrice, recurTestYear, recurTestYearOnce,
            recurTestYearTwice, recurTestYearThrice;

    public TypicalTestTasks() {
        try {
            eatbreakfast = new TaskBuilder().withTaskName("Eat breakfast with mom").withStartDate("03/03/17")
                    .withStartTime("1000").withEndDate("03/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build(); // event
            doCS = new TaskBuilder().withTaskName("Start on the CS2103 project").withStartDate("03/03/17")
                    .withStartTime("1400").withEndDate("03/04/17").withEndTime("1800").withCompletion(false)
                    .withCategories("work").build(); // event
            eatdinner = new TaskBuilder().withTaskName("Eat dinner with my only 2 friends").withStartDate("09/03/17")
                    .withStartTime("1800").withEndDate("09/03/17").withEndTime("2000").withCompletion(false).build();
            // event

            giveupCS = new TaskBuilder().withTaskName("Give up on CS2103 project").withStartDate("04/04/17")
                    .withStartTime("1400").withEndDate("05/04/17").withEndTime("1500").withCompletion(false)
                    .withCategories("lepak").build(); // event
            tryagainCS = new TaskBuilder().withTaskName("Try again for CS2103").withStartDate("05/04/17")
                    .withStartTime("1500").withEndDate("05/05/17").withEndTime("1600").withCompletion(false)
                    .withCategories("work").build(); // event
            eatlunch = new TaskBuilder().withTaskName("Eat lunch at techno").withStartDate("EMPTY_FIELD")
                    .withStartTime("EMPTY_FIELD").withEndDate("04/03/17").withEndTime("1400").withCompletion(false)
                    .withCategories("no", "friends").build(); // deadline
            regret = new TaskBuilder().withTaskName("Endless cycles of regret").withStartDate("EMPTY_FIELD")
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
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public void TypicalRecurringEventsForDays() {
        try {
            recurTestDay = new TaskBuilder().withTaskName("I'm recurring").withStartDate("03/03/17")
                    .withStartTime("1000").withEndDate("03/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestDayOnce = new TaskBuilder().withTaskName("I'm recurring").withStartDate("04/03/17")
                    .withStartTime("1000").withEndDate("04/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestDayTwice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("05/03/17")
                    .withStartTime("1000").withEndDate("05/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestDayThrice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("06/03/17")
                    .withStartTime("1000").withEndDate("06/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public void TypicalRecurringEventsForWeeks() {
        try {
            recurTestWeek = new TaskBuilder().withTaskName("I'm recurring").withStartDate("03/03/17")
                    .withStartTime("1000").withEndDate("03/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestWeekOnce = new TaskBuilder().withTaskName("I'm recurring").withStartDate("10/03/17")
                    .withStartTime("1000").withEndDate("10/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestWeekTwice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("17/03/17")
                    .withStartTime("1000").withEndDate("17/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestWeekThrice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("21/03/17")
                    .withStartTime("1000").withEndDate("21/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public void TypicalRecurringEventsForMonths() {
        try {
            recurTestMonth = new TaskBuilder().withTaskName("I'm recurring").withStartDate("03/03/17")
                    .withStartTime("1000").withEndDate("03/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestMonthOnce = new TaskBuilder().withTaskName("I'm recurring").withStartDate("03/04/17")
                    .withStartTime("1000").withEndDate("03/04/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestMonthTwice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("05/05/17")
                    .withStartTime("1000").withEndDate("03/05/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestMonthThrice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("03/06/17")
                    .withStartTime("1000").withEndDate("03/06/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public void TypicalRecurringEventsForYears() {
        try {
            recurTestYear = new TaskBuilder().withTaskName("I'm recurring").withStartDate("03/03/17")
                    .withStartTime("1000").withEndDate("03/03/17").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestYearOnce = new TaskBuilder().withTaskName("I'm recurring").withStartDate("03/03/18")
                    .withStartTime("1000").withEndDate("03/03/18").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestYearTwice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("03/03/19")
                    .withStartTime("1000").withEndDate("03/03/19").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
            recurTestYearThrice = new TaskBuilder().withTaskName("I'm recurring").withStartDate("03/03/20")
                    .withStartTime("1000").withEndDate("03/03/20").withEndTime("1100").withCompletion(false)
                    .withCategories("just", "friends").build();
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
        return new TestTask[] { eatbreakfast, eatlunch, eatdinner, doCS, giveupCS, regret, tryagainCS };
    }

    public TestTask[] getTypicalRecurringTasksForDays() {
        return new TestTask[] { recurTestDay, recurTestDayOnce, recurTestDayTwice, recurTestDayThrice };
    }

    public TestTask[] getTypicalRecurringTasksForWeeks() {
        return new TestTask[] { recurTestWeek, recurTestWeekOnce, recurTestWeekTwice, recurTestWeekThrice };
    }

    public TestTask[] getTypicalRecurringTasksForMonths() {
        return new TestTask[] { recurTestMonth, recurTestMonthOnce, recurTestMonthTwice, recurTestMonthThrice };
    }

    public TestTask[] getTypicalRecurringTasksForYears() {
        return new TestTask[] { recurTestYear, recurTestYearOnce, recurTestYearTwice, recurTestYearThrice };
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
