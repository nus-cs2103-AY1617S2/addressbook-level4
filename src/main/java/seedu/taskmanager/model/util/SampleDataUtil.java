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
                    // new Task(new TaskName("eat lunch"), new
                    // StartDate("03/03/17"), new StartTime("1400"),
                    // new EndDate("03/03/17"), new EndTime("1600"), new
                    // Boolean(false),
                    // new UniqueCategoryList("essential")), // event
                    // new Task(new TaskName("eat breakfast"), new
                    // StartDate("23/11/17"), new StartTime("1400"),
                    // new EndDate("23/11/17"), new EndTime("1600"), new
                    // Boolean(false),
                    // new UniqueCategoryList("essential", "friends")), // event
                    // new Task(new TaskName("eat some more"), new
                    // StartDate("13/01/17"), new StartTime("2100"),
                    // new EndDate("14/01/17"), new EndTime("2200"), new
                    // Boolean(true),
                    // new UniqueCategoryList("todo")), // event
                    // new Task(new TaskName("omg stop eating"), new
                    // StartDate("03/02/17"), new StartTime("1400"),
                    // new EndDate("03/02/50"), new EndTime("1600"), new
                    // Boolean(true),
                    // new UniqueCategoryList("important")), // event
                    // new Task(new TaskName("i cant stop eating"), new
                    // StartDate("06/03/17"), new StartTime("1200"),
                    // new EndDate("06/03/17"), new EndTime("2300"), new
                    // Boolean(true),
                    // new UniqueCategoryList("problem", "important")), // event
                    // new Task(new TaskName("ded"), new
                    // StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                    // new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"),
                    // new Boolean(false),
                    // new UniqueCategoryList("essential")) }; // floating
                    // task
                    new Task(new TaskName("Eat breakfast with mom"), new StartDate("03/03/17"), new StartTime("1000"),
                            new EndDate("03/03/17"), new EndTime("1100"), new Boolean(false),
                            new UniqueCategoryList("just", "friends")),
                    new Task(new TaskName("Eat lunch at techno"), new StartDate("EMPTY_FIELD"),
                            new StartTime("EMPTY_FIELD"), new EndDate("04/03/17"), new EndTime("1400"),
                            new Boolean(false), new UniqueCategoryList("no", "friends")),
                    new Task(new TaskName("Eat dinner with my only 2 friends"), new StartDate("09/03/17"),
                            new StartTime("1800"), new EndDate("09/03/17"), new EndTime("2000"), new Boolean(false),
                            new UniqueCategoryList()),
                    new Task(new TaskName("Start on the CS2103 project"), new StartDate("03/03/17"),
                            new StartTime("1400"), new EndDate("03/04/17"), new EndTime("1800"), new Boolean(false),
                            new UniqueCategoryList("work")),
                    new Task(new TaskName("Give up on CS2103 project"), new StartDate("04/04/17"),
                            new StartTime("1400"), new EndDate("05/04/17"), new EndTime("1500"), new Boolean(false),
                            new UniqueCategoryList("lepak")),
                    new Task(new TaskName("Try again for CS2103"), new StartDate("05/04/17"), new StartTime("1500"),
                            new EndDate("05/05/17"), new EndTime("1600"), new Boolean(false),
                            new UniqueCategoryList("work")),
                    new Task(new TaskName("Endless cycles of regret"), new StartDate("EMPTY_FIELD"),
                            new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"),
                            new Boolean(false), new UniqueCategoryList("lepak")),
                    new Task(new TaskName("Time to relax a little"), new StartDate("06/05/17"), new StartTime("1400"),
                            new EndDate("06/05/17"), new EndTime("1800"), new Boolean(false),
                            new UniqueCategoryList("lepak")),
                    new Task(new TaskName("Chiong all day everyday"), new StartDate("EMPTY_FIELD"),
                            new StartTime("EMPTY_FIELD"), new EndDate("EMPTY_FIELD"), new EndTime("EMPTY_FIELD"),
                            new Boolean(false), new UniqueCategoryList("work")),
                    new Task(new TaskName("Get it done"), new StartDate("EMPTY_FIELD"), new StartTime("EMPTY_FIELD"),
                            new EndDate("06/05/17"), new EndTime("1700"), new Boolean(false),
                            new UniqueCategoryList("work")) };
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
