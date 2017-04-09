package seedu.taskmanager.model.util;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0141102H
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new TaskName("Start on Quiz 6"), new StartDate("01/03/17"), new StartTime("0001"),
                        new EndDate("30/03/17"), new EndTime("0002"), Boolean.FALSE,
                        new UniqueCategoryList("IE2150", "High", "NUS")),
                new Task(new TaskName("Eat breakfast with mom"), new StartDate("03/03/17"), new StartTime("1000"),
                        new EndDate("03/03/17"), new EndTime("1100"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("Start on the CS2103 project"), new StartDate("03/03/17"),
                        new StartTime("1400"), new EndDate("03/04/17"), new EndTime("1800"), Boolean.FALSE,
                        new UniqueCategoryList("CS2103", "High", "NUS")),
                new Task(new TaskName("Eat dinner with friends"), new StartDate("09/03/17"),
                        new StartTime("1800"), new EndDate("09/03/17"), new EndTime("2000"), Boolean.FALSE,
                        new UniqueCategoryList("just", "friends", "low")),
                new Task(new TaskName("Finish assignment 5"), new StartDate("25/03/17"), new StartTime("0659"),
                        new EndDate("30/03/17"), new EndTime("2341"), Boolean.FALSE,
                        new UniqueCategoryList("IE2100", "NUS")),
                new Task(new TaskName("Revise for midterms"), new StartDate("26/03/17"), new StartTime("1200"),
                        new EndDate("30/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("IE2130", "NUS")),
                new Task(new TaskName("Meet prof"), new StartDate("30/03/17"), new StartTime("0800"),
                        new EndDate("30/03/17"), new EndTime("1000"), Boolean.FALSE,
                        new UniqueCategoryList("IE2150", "NUS")),
                new Task(new TaskName("Make sense of topic 3"), new StartDate("30/03/17"), new StartTime("1400"),
                        new EndDate("31/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("IE2100", "NUS")),
                new Task(new TaskName("Buy a cat"), new StartDate("30/03/17"), new StartTime("1800"),
                        new EndDate("01/04/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("family", "High")),
                new Task(new TaskName("Rest for the day"), new StartDate("01/04/17"), new StartTime("0030"),
                        new EndDate("02/04/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("lepak")),
                new Task(new TaskName("Eat breakfast"), new StartDate("02/04/17"), new StartTime("0800"),
                        new EndDate("02/04/17"), new EndTime("0830"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("Hand in assignment"), new StartDate("02/04/17"), new StartTime("0930"),
                        new EndDate("02/04/17"), new EndTime("1000"), Boolean.FALSE,
                        new UniqueCategoryList("IE2100", "NUS")),
                new Task(new TaskName("Do up minutes"), new StartDate("02/04/17"), new StartTime("1030"),
                        new EndDate("02/04/17"), new EndTime("1100"), Boolean.FALSE,
                        new UniqueCategoryList("IE2150", "NUS")),
                new Task(new TaskName("Try hard for CS2103 project"), new StartDate("04/04/17"),
                        new StartTime("1400"), new EndDate("05/04/17"), new EndTime("1500"), Boolean.FALSE,
                        new UniqueCategoryList("CS2103", "NUS")),
                new Task(new TaskName("Try very hard for CS2103"), new StartDate("05/04/17"), new StartTime("1500"),
                        new EndDate("05/05/17"), new EndTime("1600"), Boolean.FALSE,
                        new UniqueCategoryList("CS2103", "NUS")),
                new Task(new TaskName("Meet @ Hv"), new StartDate("05/05/17"), new StartTime("0000"),
                        new EndDate("06/05/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("lepak")),
                new Task(new TaskName("Take a quick nap"), new StartDate("06/05/17"), new StartTime("1400"),
                        new EndDate("06/05/17"), new EndTime("1800"), Boolean.FALSE,
                        new UniqueCategoryList("lepak")),
                new Task(new TaskName("Meet boss"), new StartDate("07/05/17"), new StartTime("0000"),
                        new EndDate("08/05/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Regret taking this job"), new StartDate("09/05/17"), new StartTime("0000"),
                        new EndDate("10/05/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Eat lunch at techno"), new StartDate("EMPTY_FIELD"),
                        new StartTime("EMPTY_FIELD"), new EndDate("04/03/17"), new EndTime("1400"),
                        Boolean.FALSE, new UniqueCategoryList("no", "friends", "NUS")),
                new Task(new TaskName("Assignment 2"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("27/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("IE2150", "NUS", "High")),
                new Task(new TaskName("Assignment 4"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("28/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("IE2130", "NUS", "High")),
                new Task(new TaskName("Assignment 3"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("29/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("IE2100", "NUS", "High")),
                new Task(new TaskName("Finish v0.4"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("30/03/17"), new EndTime("1100"), Boolean.FALSE,
                        new UniqueCategoryList("CS2103", "NUS", "High")),
                new Task(new TaskName("Hand in proposal to Dr Soh"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("30/03/17"), new EndTime("1200"), Boolean.FALSE,
                        new UniqueCategoryList("IE2150", "Medium")),
                new Task(new TaskName("Sign up for energy conference"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("30/03/17"), new EndTime("1300"), Boolean.FALSE,
                        new UniqueCategoryList("NUS", "Low")),
                new Task(new TaskName("Email prof for answers"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("30/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("IE2130", "High")),
                new Task(new TaskName("Assignment 5"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("31/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("IE2100", "NUS")),
                new Task(new TaskName("Prank the world"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("01/04/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("fun")),
                new Task(new TaskName("Finish Bryson Science Challenge"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("02/04/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("GEH1033", "NUS")),
                new Task(new TaskName("Find AA personnel"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("03/04/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("NUS")),
                new Task(new TaskName("Ready for work"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("06/05/17"), new EndTime("1700"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Hand in report"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("07/06/17"), new EndTime("2358"), Boolean.FALSE,
                        new UniqueCategoryList("High")),
                new Task(new TaskName("Sell myself"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("07/07/17"), new EndTime("2357"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Get ready for school"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("07/08/20"), new EndTime("2356"), Boolean.FALSE,
                        new UniqueCategoryList("NUS")),
                new Task(new TaskName("Buy a cat"), new StartDate("EMPTY_FIELD"),
                        new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"),
                        Boolean.FALSE, new UniqueCategoryList("work")),
                new Task(new TaskName("Buy a dog"), new StartDate("EMPTY_FIELD"),
                        new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"),
                        Boolean.FALSE, new UniqueCategoryList("lepak")),
                new Task(
                        new TaskName("Unnecessary Floating Task Name to show how long this task name can actually"
                                + "get and as you can see it can go really long like seriously"
                                + "it goes on and on and on"),
                        new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"),
                        new EndTime("EMPTY_FIELD"), Boolean.FALSE, new UniqueCategoryList("test")),
                new Task(new TaskName("Find a boyfriend/girlfriend"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Get serious"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Propose"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("BTO"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Fail to find BTO"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Try again for BTO"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Get married"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Think about having kids"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Argue about kids"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Argue about married life"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Had enough of married life"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Divorce"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")),
                new Task(new TaskName("Netflix"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("story")) };
            // CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            throw new AssertionError("Sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("Sample data cannot contain duplicate tasks", e);
        }
    }
}
