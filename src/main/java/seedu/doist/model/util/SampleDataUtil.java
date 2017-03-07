package seedu.doist.model.util;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.TodoList;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.Priority;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Description("Alex Yeoh"), new Priority("VERYIMPORTANT"), new UniqueTagList("friends")),
                new Task(new Description("Bernice Yu"), new Priority("IMPORTANT"),
                        new UniqueTagList("colleagues", "friends")),
                new Task(new Description("Charlotte Oliveiro"), new Priority("NORMAL"),
                        new UniqueTagList("neighbours")),
                new Task(new Description("David Li"), new Priority("NORMAL"), new UniqueTagList("family")),
                new Task(new Description("Irfan Ibrahim"), new Priority("IMPORTANT"), new UniqueTagList("classmates")),
                new Task(new Description("Roy Balakrishnan"), new Priority("NORMAL"), new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTodoList getSampleTodoList() {
        try {
            TodoList sampleAB = new TodoList();
            for (Task samplePerson : getSampleTasks()) {
                sampleAB.addTask(samplePerson);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
