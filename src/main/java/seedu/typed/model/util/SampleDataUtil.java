package seedu.typed.model.util;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.ReadOnlyTaskManager;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            int taskNumber = 6;
            Task[] tasks = new Task[6];
            String[] names = new String[] {"Meet Alex Yeoh", "Meet Bernice Yu", "Meet Charlotte Oliveiro",
                "Meet David Li", "Meet Irfan Ibrahim", "Meet Roy Balakrishnan"};
            String[] dates = new String[] {"20/01/2017", "21/01/2017", "22/01/2017", "23/01/2017",
                "24/01/2017", "25/01/2017"};
            String[] tags = new String[] {"friends", "colleagues", "neighbours", "family", "classmates",
                "colleagues"};
            for (int i = 0; i < taskNumber; i++) {
                tasks[i] = new Task.TaskBuilder()
                        .setName(names[i])
                        .setDate(dates[i])
                        .addTags(tags[i])
                        .build();
            }
            return tasks;
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleTM = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleTM.addTask(sampleTask);
            }
            return sampleTM;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
