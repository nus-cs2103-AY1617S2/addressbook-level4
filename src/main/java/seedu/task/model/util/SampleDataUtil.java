package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndDate;
import seedu.task.model.task.Name;
import seedu.task.model.task.Person;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.UniquePersonList.DuplicatePersonException;

public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new StartDate("87438807"), new EndDate("alexyeoh@gmail.com"),
                    new CompletionStatus("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Person(new Name("Bernice Yu"), new StartDate("99272758"), new EndDate("berniceyu@gmail.com"),
                    new CompletionStatus("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new StartDate("93210283"), new EndDate("charlotte@yahoo.com"),
                    new CompletionStatus("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Person(new Name("David Li"), new StartDate("91031282"), new EndDate("lidavid@google.com"),
                    new CompletionStatus("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Person(new Name("Irfan Ibrahim"), new StartDate("92492021"), new EndDate("irfan@outlook.com"),
                    new CompletionStatus("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Person(new Name("Roy Balakrishnan"), new StartDate("92624417"), new EndDate("royb@gmail.com"),
                    new CompletionStatus("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleAddressBook() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Person samplePerson : getSamplePersons()) {
                sampleAB.addPerson(samplePerson);
            }
            return sampleAB;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
