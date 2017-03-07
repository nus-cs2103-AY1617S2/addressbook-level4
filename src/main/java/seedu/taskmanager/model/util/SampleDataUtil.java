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
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new TaskName("Alex Yeoh"), new Deadline("87438807"), new EndTime("1400"), new Date("alexyeoh@gmail.com")
                    /*new UniqueTagList("friends")*/),
                new Task(new TaskName("Bernice Yu"), new Deadline("99272758"), new EndTime("1600"), new Date("berniceyu@gmail.com")
                    /*new UniqueTagList("colleagues", "friends")*/),
                new Task(new TaskName("Charlotte Oliveiro"), new Deadline("93210283"), new EndTime("1800"), new Date("charlotte@yahoo.com")
                    /*new UniqueTagList("neighbours")*/),
                new Task(new TaskName("David Li"), new Deadline("91031282"), new EndTime("0700"), new Date("lidavid@google.com")
                    /*new UniqueTagList("family")*/),
                new Task(new TaskName("Irfan Ibrahim"), new Deadline("92492021"), new EndTime("2100"), new Date("irfan@outlook.com")
                    /*new UniqueTagList("classmates")*/),
                new Task(new TaskName("Roy Balakrishnan"), new Deadline("92624417"), new EndTime("0100"), new Date("royb@gmail.com")
                    /*new UniqueTagList("colleagues")*/)
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
