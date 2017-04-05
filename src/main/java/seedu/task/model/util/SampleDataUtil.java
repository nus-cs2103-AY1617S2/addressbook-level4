package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskList;
import seedu.task.model.TaskList;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.Task;
import seedu.task.model.task.Timing;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
                new Task(new Description("Visit Alex"), new Priority("3"), new Timing("26/03/2017"),
                        new Timing("27/03/2017"), new UniqueTagList("visit"), false, null),
                new Task(new Description("CS Midterm"), new Priority("1"), new Timing("15/03/2017"),
                            new Timing("19/03/2017"), new UniqueTagList("study", "exams"), false, null),
                new Task(new Description("Buy grocery"), new Priority("2"), new Timing("10/03/2017"),
                            new Timing("10/03/2017"), new UniqueTagList("misc"), false, null),
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
