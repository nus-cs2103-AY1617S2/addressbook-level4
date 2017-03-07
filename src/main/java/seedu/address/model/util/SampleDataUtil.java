package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Content;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Content("CS2101"),
                    new UniqueTagList("modules")),
                new Task(new Content("CS2103"),
                    new UniqueTagList("SoftwareEngineering")),
                new Task(new Content("CS2105"),
                    new UniqueTagList("networking")),
                new Task(new Content("CS2106"),
                    new UniqueTagList("OperatingSystem")),
                new Task(new Content("CS2107"),
                    new UniqueTagList("security")),
                new Task(new Content("CS2100"),
                    new UniqueTagList("computerOrganisation"))
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
