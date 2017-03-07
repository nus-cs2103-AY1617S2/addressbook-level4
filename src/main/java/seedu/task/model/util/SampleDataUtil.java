package seedu.task.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.model.TaskBook;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndDateTime;
import seedu.task.model.task.StartDateTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.Task;
import seedu.task.model.task.Description;
import seedu.task.model.task.UniqueTaskList.DuplicatePersonException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
                new Task(new Name("Alex Yeoh"), new Description("87438807"), new StartDateTime("alexyeoh@gmail.com"),
                    new EndDateTime("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new Name("Bernice Yu"), new Description("99272758"), new StartDateTime("berniceyu@gmail.com"),
                    new EndDateTime("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Name("Charlotte Oliveiro"), new Description("93210283"), new StartDateTime("charlotte@yahoo.com"),
                    new EndDateTime("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new Name("David Li"), new Description("91031282"), new StartDateTime("lidavid@google.com"),
                    new EndDateTime("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new Name("Irfan Ibrahim"), new Description("92492021"), new StartDateTime("irfan@outlook.com"),
                    new EndDateTime("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new Name("Roy Balakrishnan"), new Description("92624417"), new StartDateTime("royb@gmail.com"),
                    new EndDateTime("Blk 45 Aljunied Street 85, #11-31"),
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
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
