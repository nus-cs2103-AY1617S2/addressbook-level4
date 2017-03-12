package seedu.address.model.util;

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
        try {
            return new Task[] {
                new Task(new Title("Meet Prof Alex Yeoh"),
                    new Deadline("Tomorrow 1600"),
                    new Deadline("Tomorrow 2300"),
                    new UniqueLabelList("school")),
                new Task(new Title("Dinner with family"),
                    new Deadline("Sunday 1800"),
                    new Deadline("Sunday 2300"),
                    new UniqueLabelList("family")),
                new Task(new Title("CS2103 Project Meeting"),
                    new Deadline("Saturday 1600"),
                    new Deadline("Saturday 1900"),
                    new UniqueLabelList("school")),
                new Task(new Title("Birthday Party for David"),
                    new Deadline("01-04-2017 1200"),
                    new Deadline("01-04-2017 2300"),
                    new UniqueLabelList("family")),
                new Task(new Title("Catch up with Irfan Ibrahim"),
                    new Deadline("01-03-2017 1900"),
                    new Deadline("07-03-2017 1900"),
                    new UniqueLabelList("classmates")),
                new Task(new Title("Meet Roy Balakrishnan for coffee"),
                    new Deadline("next Monday"),
                    new Deadline("next Saturday"),
                    new UniqueLabelList("colleagues"))
            };
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
