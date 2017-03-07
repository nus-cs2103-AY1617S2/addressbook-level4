package seedu.taskboss.model.util;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.category.UniqueTagList;
import seedu.taskboss.model.task.Address;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.Phone;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Alex Yeoh"), new Phone("87438807"),
                	new Address("Blk 30 Geylang Street 29, #06-40"),
                	new UniqueTagList("friends")),
                new Task(new Name("Bernice Yu"), new Phone("99272758"),
                	new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                	new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new Name("David Li"), new Phone("91031282"),
                	new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new Name("Irfan Ibrahim"), new Phone("92492021"),
                	new Address("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new Name("Roy Balakrishnan"), new Phone("92624417"),
                	new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
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
