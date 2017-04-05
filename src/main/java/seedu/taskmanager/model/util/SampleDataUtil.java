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
                new Task(new TaskName("Eat breakfast with mom"), new StartDate("03/03/17"), new StartTime("1000"),
                        new EndDate("03/03/17"), new EndTime("1100"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("Eat dinner with my girl friend"), new StartDate("09/03/17"),
                        new StartTime("1800"), new EndDate("09/03/17"), new EndTime("2000"), Boolean.FALSE,
                        new UniqueCategoryList("just", "friends")),
                new Task(new TaskName("Start on the CS2103 project"), new StartDate("03/03/17"),
                        new StartTime("1400"), new EndDate("03/04/17"), new EndTime("1800"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Give up on CS2103 project"), new StartDate("04/04/17"),
                        new StartTime("1400"), new EndDate("05/04/17"), new EndTime("1500"), Boolean.FALSE,
                        new UniqueCategoryList("lepak")),
                new Task(new TaskName("Try again for CS2103"), new StartDate("05/04/17"), new StartTime("1500"),
                        new EndDate("05/05/17"), new EndTime("1600"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Time to relax a little"), new StartDate("06/05/17"), new StartTime("1400"),
                        new EndDate("06/05/17"), new EndTime("1800"), Boolean.FALSE,
                        new UniqueCategoryList("lepak")),
                new Task(new TaskName("Event1"), new StartDate("30/03/17"), new StartTime("0800"),
                        new EndDate("30/03/17"), new EndTime("1000"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Event2"), new StartDate("30/03/17"), new StartTime("1400"),
                        new EndDate("31/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Event3"), new StartDate("30/03/17"), new StartTime("1800"),
                        new EndDate("01/04/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Event4"), new StartDate("26/03/17"), new StartTime("1200"),
                        new EndDate("30/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Event5"), new StartDate("25/03/17"), new StartTime("0659"),
                        new EndDate("30/03/17"), new EndTime("2341"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Event6"), new StartDate("01/03/17"), new StartTime("0001"),
                        new EndDate("30/03/17"), new EndTime("0002"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Event7"), new StartDate("01/04/17"), new StartTime("0030"),
                        new EndDate("02/04/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("Event8"), new StartDate("02/04/17"), new StartTime("0800"),
                        new EndDate("02/04/17"), new EndTime("0830"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("Event9"), new StartDate("02/04/17"), new StartTime("0930"),
                        new EndDate("02/04/17"), new EndTime("1000"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("Event10"), new StartDate("02/04/17"), new StartTime("1030"),
                        new EndDate("02/04/17"), new EndTime("1100"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("MoreEvent1"), new StartDate("05/05/17"), new StartTime("0000"),
                        new EndDate("06/05/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("MoreEvent2"), new StartDate("07/05/17"), new StartTime("0000"),
                        new EndDate("08/05/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("MoreEvent3"), new StartDate("09/05/17"), new StartTime("0000"),
                        new EndDate("10/05/17"), new EndTime("2359"), Boolean.FALSE,
                        new UniqueCategoryList("friends")),
                new Task(new TaskName("Eat lunch at techno"), new StartDate("EMPTY_FIELD"),
                        new StartTime("EMPTY_FIELD"), new EndDate("04/03/17"), new EndTime("1400"),
                        Boolean.FALSE, new UniqueCategoryList("no", "friends")),
                new Task(new TaskName("Get it done"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("06/05/17"), new EndTime("1700"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Finish CS2103"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("30/03/17"), new EndTime("1100"), Boolean.FALSE,
                        new UniqueCategoryList("joke")),
                new Task(new TaskName("Deadline1"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("30/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Deadline2"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("31/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Deadline3"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("01/04/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Deadline4"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("02/04/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Deadline5"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("03/04/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Deadline6"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("29/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Deadline7"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("28/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Deadline8"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("27/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Deadline9"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("30/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("fun")),
                new Task(new TaskName("Deadline10"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("30/03/17"), new EndTime("1400"), Boolean.FALSE,
                        new UniqueCategoryList("fun")),
                new Task(new TaskName("MoreDeadline1"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("07/06/17"), new EndTime("2358"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("MoreDeadline2"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("07/07/17"), new EndTime("2357"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("MoreDeadline3"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("07/08/20"), new EndTime("2356"), Boolean.FALSE,
                        new UniqueCategoryList("friends")),
                new Task(new TaskName("Chiong all day everyday"), new StartDate("EMPTY_FIELD"),
                        new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"),
                        Boolean.FALSE, new UniqueCategoryList("work")),
                new Task(new TaskName("Endless cycles of regret"), new StartDate("EMPTY_FIELD"),
                        new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"),
                        Boolean.FALSE, new UniqueCategoryList("lepak")),
                new Task(
                        new TaskName("Unncessary Floating Task Name to show how long this task name can actually"
                                + "get and as you can see it can go really long like seriously"
                                + "it goes on and on and on"),
                        new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"),
                        new EndTime("EMPTY_FIELD"), Boolean.FALSE, new UniqueCategoryList("joke")),
                new Task(new TaskName("Task1"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("Task2"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Task3"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("school")),
                new Task(new TaskName("Task4"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Task5"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("school")),
                new Task(new TaskName("Task6"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Task7"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("school", "work")),
                new Task(new TaskName("Task8"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("Task9"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("work", "school")),
                new Task(new TaskName("Task10"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("MoreTask1"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("work")),
                new Task(new TaskName("MoreTask2"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("family")),
                new Task(new TaskName("MoreTask3"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                        new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"), Boolean.FALSE,
                        new UniqueCategoryList("friends")) };
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
