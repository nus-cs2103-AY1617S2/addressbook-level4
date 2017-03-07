package seedu.taskmanager.model.util;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Time;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new TaskName("Alex Yeoh"), new Time("87438807"), new Date("alexyeoh@gmail.com")
                    /*new UniqueTagList("friends")*/),
                new Task(new TaskName("Bernice Yu"), new Time("99272758"), new Date("berniceyu@gmail.com")
                    /*new UniqueTagList("colleagues", "friends")*/),
                new Task(new TaskName("Charlotte Oliveiro"), new Time("93210283"), new Date("charlotte@yahoo.com")
                    /*new UniqueTagList("neighbours")*/),
                new Task(new TaskName("David Li"), new Time("91031282"), new Date("lidavid@google.com")
                    /*new UniqueTagList("family")*/),
                new Task(new TaskName("Irfan Ibrahim"), new Time("92492021"), new Date("irfan@outlook.com")
                    /*new UniqueTagList("classmates")*/),
                new Task(new TaskName("Roy Balakrishnan"), new Time("92624417"), new Date("royb@gmail.com")
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
