package seedu.tache.model.util;

import java.util.Optional;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.ReadOnlyTaskManager;
import seedu.tache.model.TaskManager;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.Task.RecurInterval;
import seedu.tache.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Buy Eggs and Bread"), new UniqueTagList("HighPriority")),
                new Task(new Name("Read Book about Software Engineering"), new UniqueTagList("LowPriority")),
                new Task(new Name("Visit Grandma"), new UniqueTagList("MediumPriority")),
                new Task(new Name("Pay David 20 for cab"), new UniqueTagList("LowPriority")),
                new Task(new Name("Get Fit"), new UniqueTagList("LowPriority")),
                new Task(new Name("Find a girlfriend"), new UniqueTagList("LowPriority")),
                new Task(new Name("Walk the Dog"), Optional.of(new DateTime("14 April 2017")),
                            Optional.of(new DateTime("14 April 2017")), new UniqueTagList("MediumPriority"),
                                true, false, RecurInterval.NONE),
                new Task(new Name("Buy Medicine"), Optional.of(new DateTime("15 April 2017")),
                            Optional.empty(), new UniqueTagList("LowPriority"), true, false, RecurInterval.NONE),
                new Task(new Name("Submit Project Proposal"), Optional.empty(),
                            Optional.of(new DateTime("17 April 2017")), new UniqueTagList("HighPriority"),
                                true, false, RecurInterval.NONE),
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
