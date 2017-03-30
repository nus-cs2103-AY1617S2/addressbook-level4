package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        Task[] tasks;
        try {
            tasks = new Task[] {
                new Task(new Title("Meet Prof Alex Yeoh"),
                    Optional.ofNullable(new Deadline("Tomorrow 1600")),
                    Optional.ofNullable(new Deadline("Tomorrow 2300")),
                    true,
                    new UniqueLabelList("school"),
                    false,
                    Optional.empty()),
                new Task(new Title("Dinner with family"),
                    Optional.ofNullable(new Deadline("Sunday 1800")),
                    Optional.ofNullable(new Deadline("Sunday 2300")),
                    false,
                    new UniqueLabelList("family"),
                    false,
                    Optional.empty()),
                new Task(new Title("CS2103 Project Meeting"),
                    Optional.ofNullable(new Deadline("Saturday 1600")),
                    Optional.ofNullable(new Deadline("Saturday 1900")),
                    false,
                    new UniqueLabelList("school"),
                    false,
                    Optional.empty()),
                new Task(new Title("Birthday Party for David"),
                    Optional.ofNullable(new Deadline("01-04-2017 1200")),
                    Optional.ofNullable(new Deadline("01-04-2017 2300")),
                    true,
                    new UniqueLabelList("family"),
                    false,
                    Optional.empty()),
                new Task(new Title("Catch up with Irfan Ibrahim"),
                    Optional.ofNullable(new Deadline("01-03-2017 1900")),
                    Optional.ofNullable(new Deadline("07-03-2017 1900")),
                    false,
                    new UniqueLabelList("classmates"),
                    false,
                    Optional.empty()),
                new Task(new Title("Meet Roy Balakrishnan for coffee"),
                    Optional.ofNullable(new Deadline("next Monday 1900")),
                    Optional.ofNullable(new Deadline("next Saturday 1900")),
                    false,
                    new UniqueLabelList("colleagues"),
                    false,
                    Optional.empty())
            };
            Arrays.sort(tasks);
            return tasks;
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        } catch (IllegalDateTimeValueException e) {
            throw new AssertionError("sample deadline cannot be parse", e);
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
