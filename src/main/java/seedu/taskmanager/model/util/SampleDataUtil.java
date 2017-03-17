package seedu.taskmanager.model.util;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.TaskManager;
//import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                    new Task(new TaskName("eat lunch"), new Date("03/03/17"), new StartTime("1400"), new EndTime("1600")
                    /* new UniqueCategoryList("friends") */), new Task(new TaskName("eat breakfast"), new Date("23/11/17")
                            , new StartTime("1400"), new EndTime("1600")
                    /* new UniqueCategoryList("colleagues", "friends") */), new Task(new TaskName("eat some more")
                            , new Date("13/01/17"), new StartTime("2100"), new EndTime("2200")
                    /* new UniqueCategoryList("neighbours") */), new Task(new TaskName("omg stop eating")
                            , new Date("03/02/17"), new StartTime("1400"), new EndTime("1600")
                    /* new UniqueCategoryList("family") */), new Task(new TaskName("i can't stop eating")
                            ,new Date("06/03/17"), new StartTime("1200"), new EndTime("2300")
                    /* new UniqueCategoryList("classmates") */), new Task(new TaskName("ded"), new Date("03/04/17")
                            , new StartTime("0800"), new EndTime("1200")
                    /* new UniqueCategoryList("colleagues") */) };
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
