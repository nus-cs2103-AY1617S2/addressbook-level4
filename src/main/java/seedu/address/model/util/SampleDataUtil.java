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
                new Task(new Description("Visit Alex"), new Priority("3"), new Email("2"),
                    new UniqueTagList("visit")),
                new Task(new Description("CS Midterm"), new Priority("1"), new Email("12"),
                    new UniqueTagList("study", "exams")),
                new Task(new Description("Buy grocery"), new Priority("2"), new Email("21"),
                    new UniqueTagList("misc")),
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
