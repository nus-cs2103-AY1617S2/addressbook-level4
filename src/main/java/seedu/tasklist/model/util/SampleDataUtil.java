package seedu.tasklist.model.util;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.ReadOnlyTaskList;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                    new Task(new Name("Alex Yeoh"), new Comment("Blk 30 Geylang Street 29, #06-40"),
                            new UniqueTagList("friends")),
                    new Task(new Name("Bernice Yu"), new Comment("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                            new UniqueTagList("colleagues", "friends")),
                    new Task(new Name("Charlotte Oliveiro"), new Comment("Blk 11 Ang Mo Kio Street 74, #11-04"),
                            new UniqueTagList("neighbours")),
                    new Task(new Name("David Li"), new Comment("Blk 436 Serangoon Gardens Street 26, #16-43"),
                            new UniqueTagList("family")),
                    new Task(new Name("Irfan Ibrahim"), new Comment("Blk 47 Tampines Street 20, #17-35"),
                            new UniqueTagList("classmates")),
                    new Task(new Name("Roy Balakrishnan"), new Comment("Blk 45 Aljunied Street 85, #11-31"),
                            new UniqueTagList("colleagues")) };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskList getSampleTaskList() {
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
