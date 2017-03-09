package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Address;
import seedu.address.model.task.Email;
import seedu.address.model.task.Description;
import seedu.address.model.task.Phone;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static FloatingTask[] getSampleTasks() {
        try {
            return new FloatingTask[] {
                new FloatingTask(new Description("Read Lord of The Rings"),
                    new UniqueTagList("reading")),
                new FloatingTask(new Description("Do CS2103 V0.1"),
                    new UniqueTagList("school", "homework")),
                new FloatingTask(new Description("Learn airflares"),
                    new UniqueTagList("dance")),
                new FloatingTask(new Description("Design RPG using RPG Maker VX Ace"),
                    new UniqueTagList("gamedesign"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskList getSampleAddressBook() {
        try {
            TaskList sampleAB = new TaskList();
            for (FloatingTask samplePerson : getSampleTasks()) {
                sampleAB.addTask(samplePerson);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
