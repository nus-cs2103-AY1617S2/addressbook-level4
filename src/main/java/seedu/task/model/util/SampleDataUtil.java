package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskList;
import seedu.task.model.TaskList;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Name;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Buy milk and egss"), new UniqueTagList("home")),
                new Task(new Name("Hand in CS assignment"), new UniqueTagList("school", "hi-pri")),
                new Task(new Name("Send resume to recruiter"), new UniqueTagList("hi-pri")),
                new Task(new Name("Plan vacation after exams"), new UniqueTagList("friends")),
                new Task(new Name("Call contractor for new flooring"), new UniqueTagList("home")),
                new Task(new Name("File taxes"), new UniqueTagList("hi-pri"))
            };
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
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
