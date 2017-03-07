package seedu.task.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.model.TaskBook;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.EndDateTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartDateTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
                new Task(new Name("Alex Yeoh"), new Description("87438807"), new StartDateTime("01/01/1980 0000"),
                    new EndDateTime("01/01/1980 0000"),
                    new UniqueTagList("friends")),
                new Task(new Name("Bernice Yu"), new Description("99272758"), new StartDateTime("01/01/1980 0000"),
                    new EndDateTime("01/01/1980 0000"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Name("Charlotte Oliveiro"), new Description("93210283"),
                    new StartDateTime("01/01/1980 0000"),
                    new EndDateTime("01/01/1980 0000"),
                    new UniqueTagList("neighbours")),
                new Task(new Name("David Li"), new Description("91031282"), new StartDateTime("01/01/1980 0000"),
                    new EndDateTime("01/01/1980 0000"),
                    new UniqueTagList("family")),
                new Task(new Name("Irfan Ibrahim"), new Description("92492021"), new StartDateTime("01/01/1980 0000"),
                    new EndDateTime("01/01/1980 0000"),
                    new UniqueTagList("classmates")),
                new Task(new Name("Roy Balakrishnan"), new Description("92624417"),
                    new StartDateTime("01/01/1980 0000"),
                    new EndDateTime("01/01/1980 0000"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskBook getSampleAddressBook() {
        try {
            TaskBook sampleAB = new TaskBook();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addTask(samplePerson);
            }
            return sampleAB;
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
