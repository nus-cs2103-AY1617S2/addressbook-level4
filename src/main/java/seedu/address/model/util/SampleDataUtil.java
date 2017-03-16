package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyToDoApp;
import seedu.address.model.ToDoApp;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Name;
import seedu.address.model.person.Start;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Buy printer"), new Start("today"), new Deadline("tomorrow"),
                    new UniqueTagList("shopping")),
                new Task(new Name("Go to the gym"), new Start(""), new Deadline(""),
                    new UniqueTagList("exercise")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyToDoApp getSampleToDoApp() {
        try {
            ToDoApp sampleTDA = new ToDoApp();
            for (Task sampleTask : getSampleTasks()) {
                sampleTDA.addTask(sampleTask);
            }
            return sampleTDA;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
