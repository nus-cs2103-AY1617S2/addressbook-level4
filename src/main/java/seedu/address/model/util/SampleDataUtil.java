package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Alex Yeoh"), new UniqueTagList("friends")),
                new Task(new Name("Bernice Yu"), new UniqueTagList("colleagues", "friends")),
                new Task(new Name("Charlotte Oliveiro"), new UniqueTagList("neighbours")),
                new Task(new Name("David Li"), new UniqueTagList("family")),
                new Task(new Name("Irfan Ibrahim"), new UniqueTagList("classmates")),
                new Task(new Name("Roy Balakrishnan"), new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskList getSampleAddressBook() {
        try {
            TaskList sampleAB = new TaskList();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
