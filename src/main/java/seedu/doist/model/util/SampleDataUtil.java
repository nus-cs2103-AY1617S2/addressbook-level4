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
                new Task(new Description("Buy milk"), new Priority("VERY IMPORTANT"), new UniqueTagList("health")),
                new Task(new Description("Do CS2103 tutorial"), new Priority("IMPORTANT"),
                        new UniqueTagList("NUS", "tutorial")),
                new Task(new Description("Exercise at home"), new Priority("NORMAL"),
                        new UniqueTagList("health")),
                new Task(new Description("Have some beauty sleep"), new Priority("NORMAL"),
                        new UniqueTagList("health")),
                new Task(new Description("Do more tutorials"), new Priority("IMPORTANT"),
                        new UniqueTagList("tutorial")),
                new Task(new Description("Talk to people"), new Priority("NORMAL"), new UniqueTagList("interests"))
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
