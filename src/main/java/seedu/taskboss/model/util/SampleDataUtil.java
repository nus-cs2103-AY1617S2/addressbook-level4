package seedu.taskboss.model.util;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Alex Yeoh"), new PriorityLevel("3"),
                        new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueCategoryList("friends")),
                new Task(new Name("Bernice Yu"), new PriorityLevel("2"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueCategoryList("colleagues", "friends")),
                new Task(new Name("Charlotte Oliveiro"), new PriorityLevel("3"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueCategoryList("neighbours")),
                new Task(new Name("David Li"), new PriorityLevel("1"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueCategoryList("family")),
                new Task(new Name("Irfan Ibrahim"), new PriorityLevel("2"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueCategoryList("classmates")),
                new Task(new Name("Roy Balakrishnan"), new PriorityLevel("2"),
                    new DateTime("Feb 19, 2017"), new DateTime("Feb 20, 2017"),
                    new Information("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueCategoryList("colleagues"))
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
