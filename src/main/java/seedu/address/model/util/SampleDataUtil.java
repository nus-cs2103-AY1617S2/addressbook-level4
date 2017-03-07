package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Task;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.TaskName;
import seedu.address.model.person.PriorityLevel;
import seedu.address.model.person.Information;
import seedu.address.model.person.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new TaskName("Sleep"), new Deadline("070317"), new PriorityLevel("4"),
                    new Information("I don't want to sleep"),
                    new UniqueTagList("Home")),
                new Task(new TaskName("Buy car"), new Deadline("270518"), new PriorityLevel("1"),
                    new Information("Audi"),
                    new UniqueTagList("Transport")),
                new Task(new TaskName("Buy bus"), new Deadline("280518"), new PriorityLevel("1"),
                    new Information("VOLVO"),
                    new UniqueTagList("Transport")),
                new Task(new TaskName("Buy MRT"), new Deadline("290518"), new PriorityLevel("1"),
                    new Information("Become the government"),
                    new UniqueTagList("Transport")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAB = new AddressBook();
            for (Task samplePerson : getSampleTasks()) {
                sampleAB.addTask(samplePerson);
            }
            return (ReadOnlyAddressBook) sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
