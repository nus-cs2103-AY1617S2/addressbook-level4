package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.TaskList;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.Email;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
                new Task(new Description("Alex Yeoh"), new Priority("1"), new Email("alexyeoh@gmail.com"),
                    new UniqueTagList("friends")),
                new Task(new Description("Bernice Yu"), new Priority("1"), new Email("berniceyu@gmail.com"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Description("Charlotte Oliveiro"), new Priority("1"), new Email("charlotte@yahoo.com"),
                    new UniqueTagList("neighbours")),
                new Task(new Description("David Li"), new Priority("1"), new Email("lidavid@google.com"),
                    new UniqueTagList("family")),
                new Task(new Description("Irfan Ibrahim"), new Priority("1"), new Email("irfan@outlook.com"),
                    new UniqueTagList("classmates")),
                new Task(new Description("Roy Balakrishnan"), new Priority("1"), new Email("royb@gmail.com"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskList getSampleAddressBook() {
        try {
            TaskList sampleAB = new TaskList();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addTask(samplePerson);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
