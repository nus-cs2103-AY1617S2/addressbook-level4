package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
                new Task(new Title("Alex Yeoh"),
                    new Deadline("01-01-2018"),
                    new UniqueLabelList("friends")),
                new Task(new Title("Bernice Yu"),
                    new Deadline("01-06-2018"),
                    new UniqueLabelList("colleagues", "friends")),
                new Task(new Title("Charlotte Oliveiro"),
                    new Deadline("01-05-2018"),
                    new UniqueLabelList("neighbours")),
                new Task(new Title("David Li"),
                    new Deadline("01-04-2018"),
                    new UniqueLabelList("family")),
                new Task(new Title("Irfan Ibrahim"),
                    new Deadline("01-03-2018"),
                    new UniqueLabelList("classmates")),
                new Task(new Title("Roy Balakrishnan"),
                    new Deadline("01-02-2018"),
                    new UniqueLabelList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSamplePersons()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
