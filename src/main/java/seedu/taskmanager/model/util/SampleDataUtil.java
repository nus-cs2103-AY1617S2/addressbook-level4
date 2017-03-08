package seedu.taskmanager.model.util;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Deadline;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new TaskName("Alex Yeoh"), /* new Date("alexyeoh@gmail.com"), new EndTime("1400"),*/ new Deadline("thursday")
                /*    new UniqueCategoryList("friends")*/),
                new Task(new TaskName("Bernice Yu"), /* new Date("berniceyu@gmail.com"), new EndTime("1600"),*/ new Deadline("99272758")
                /*    new UniqueCategoryList("colleagues", "friends")*/),
                new Task(new TaskName("Charlotte Oliveiro"), /* new Date("charlotte@yahoo.com"), new EndTime("1800"),*/ new Deadline("93210283")
                /*    new UniqueCategoryList("neighbours")*/),
                new Task(new TaskName("David Li"), /* new Date("lidavid@google.com"), new EndTime("0700"),*/ new Deadline("91031282")
                /*    new UniqueCategoryList("family")*/),
                new Task(new TaskName("Irfan Ibrahim"), /* new Date("irfan@outlook.com"), new EndTime("2100"),*/ new Deadline("92492021")
                /*    new UniqueCategoryList("classmates")*/),
                new Task(new TaskName("Roy Balakrishnan"), /* new Date("royb@gmail.com"), new EndTime("0100"),*/ new Deadline("92624417")
                /*    new UniqueCategoryList("colleagues")*/)
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
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
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
