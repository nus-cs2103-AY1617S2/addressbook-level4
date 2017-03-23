package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyToDoApp;
import seedu.address.model.ToDoApp;
import seedu.address.model.person.Completion;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Start;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Buy printer"), new Start("today"), new Deadline("tomorrow"), new Priority(1),
                        new UniqueTagList("shopping"), new Notes("this is a note"), new Completion("true")),
                new Task(new Name("Go to the gym"), new Start(""), new Deadline(""), new Priority(0),
                        new UniqueTagList("exercise"), new Notes(""), new Completion("")), };
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
