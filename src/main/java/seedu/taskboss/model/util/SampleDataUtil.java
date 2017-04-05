package seedu.taskboss.model.util;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.Recurrence;
import seedu.taskboss.model.task.Recurrence.Frequency;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Attend wedding"), new PriorityLevel("Yes"),
                        new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("@Blk 30 Geylang Street 29, #06-40"),
                    new Recurrence(Frequency.WEEKLY),
                    new UniqueCategoryList("Alltasks")),
                new Task(new Name("Birthday party"), new PriorityLevel("No"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information(""),
                    new Recurrence(Frequency.WEEKLY),
                    new UniqueCategoryList("Alltasks")),
                new Task(new Name("Clean house"), new PriorityLevel("No"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("Clean kitchen, living room, main bedroom"),
                    new Recurrence(Frequency.DAILY),
                    new UniqueCategoryList("Alltasks")),
                new Task(new Name("Debug code"), new PriorityLevel("Yes"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("line 45 47 135 in test.java"),
                    new Recurrence(Frequency.WEEKLY),
                    new UniqueCategoryList("Alltasks")),
                new Task(new Name("Fix errors in report"), new PriorityLevel("Yes"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("paragraph 3 top discuusion"),
                    new Recurrence(Frequency.WEEKLY),
                    new UniqueCategoryList("Alltasks")),
                new Task(new Name("Having dinner with Hoon Meier"), new PriorityLevel("No"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information(""),
                    new Recurrence(Frequency.WEEKLY),
                    new UniqueCategoryList("Alltasks"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskBoss getSampleTaskBoss() {
        try {
            TaskBoss sampleTB = new TaskBoss();
            for (Task sampleTask : getSampleTasks()) {
                sampleTB.addTask(sampleTask);
            }
            return sampleTB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
