package seedu.todolist.model.util;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.ReadOnlyToDoList;
import seedu.todolist.model.ToDoList;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
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

    public static ReadOnlyToDoList getSampleAddressBook() {
        try {
            ToDoList sampleAB = new ToDoList();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addTask(samplePerson);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
