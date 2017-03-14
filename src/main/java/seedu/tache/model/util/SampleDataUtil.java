package seedu.tache.model.util;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.ReadOnlyTaskManager;
import seedu.tache.model.TaskManager;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Date;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.Time;
import seedu.tache.model.task.UniqueDetailedTaskList.DuplicateDetailedTaskException;
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
                new Task(new Name("Find a girlfriend"), new UniqueTagList("LowPriority"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static DetailedTask[] getSampleDetailedTasks() {
        try {
            return new DetailedTask[] {
                new DetailedTask(new Name("Walk the Dog"), new Date("14 April 2017"), new Date("14 April 2017"),
                                 new Time("17:00"), new Time("17:00"), new UniqueTagList("MediumPriority")),
                new DetailedTask(new Name("Buy Medicine"), new Date("15 April 2017"), new Date("-"),
                                 new Time("12:00"), new Time("12:00"), new UniqueTagList("LowPriority")),
                new DetailedTask(new Name("Submit Project Proposal"), new Date("-"), new Date("17 April 2017"),
                                 new Time("15:00"), new Time("15:00"), new UniqueTagList("HighPriority")),
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
            for (DetailedTask sampleDetailedTask : getSampleDetailedTasks()) {
                sampleAB.addDetailedTask(sampleDetailedTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        } catch (DuplicateDetailedTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate detailed tasks", e);
        }
    }
}
