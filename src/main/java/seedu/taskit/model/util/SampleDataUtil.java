package seedu.taskit.model.util;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.model.AddressBook;
import seedu.taskit.model.ReadOnlyAddressBook;
import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.Title;
import seedu.taskit.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("HW"), new Date(), new Date(),new UniqueTagList("school")),
                new Task(new Title("Meet with friend"), new Date(), new Date(), new UniqueTagList("leisure", "friends")),
                new Task(new Title("clean room"), new Date(), new Date(), new UniqueTagList("household"))
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
            throw new AssertionError("sample data cannot contain duplicate Tasks", e);
        }
    }
}
