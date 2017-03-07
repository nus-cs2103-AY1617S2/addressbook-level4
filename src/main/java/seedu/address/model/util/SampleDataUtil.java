package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("CS2101"),
                    new UniqueTagList("modules")),
                new Task(new Name("CS2103"),
                    new UniqueTagList("Software Engineering")),
                new Task(new Name("CS2105"),
                    new UniqueTagList("networking")),
                new Task(new Name("CS2106"),
                    new UniqueTagList("Operating System")),
                new Task(new Name("CS2107"),
                    new UniqueTagList("security")),
                new Task(new Name("CS2100"),
                    new UniqueTagList("computer organisation"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAB = new AddressBook();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
