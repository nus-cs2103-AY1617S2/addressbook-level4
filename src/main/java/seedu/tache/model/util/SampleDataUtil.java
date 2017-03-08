package seedu.tache.model.util;

import seedu.tache.commons.exceptions.IllegalValueException;
<<<<<<< HEAD
import seedu.tache.model.AddressBook;
import seedu.tache.model.ReadOnlyAddressBook;
import seedu.tache.model.person.Address;
import seedu.tache.model.person.Email;
import seedu.tache.model.person.Name;
import seedu.tache.model.person.Task;
import seedu.tache.model.person.Phone;
import seedu.tache.model.person.UniqueTaskList.DuplicateTaskException;
=======
import seedu.tache.model.TaskManager;
import seedu.tache.model.ReadOnlyTaskManager;
>>>>>>> ImplementTaskModels
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
<<<<<<< HEAD
                new Task(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@gmail.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@yahoo.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new Name("David Li"), new Phone("91031282"), new Email("lidavid@google.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@outlook.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@gmail.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
=======
                new Task(new Name("Buy Eggs and Bread"),new UniqueTagList("HighPriority")),
                new Task(new Name("Read Book about Software Engineering"), new UniqueTagList("LowPriority")),
                new Task(new Name("Visit Grandma"), new UniqueTagList("MediumPriority")),
                new Task(new Name("Pay David 20 for cab"), new UniqueTagList("LowPriority")),
                new Task(new Name("Get Fit"), new UniqueTagList("LowPriority")),
                new Task(new Name("Find a girlfriend"), new UniqueTagList("LowPriority"))
>>>>>>> ImplementTaskModels
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleAddressBook() {
        try {
<<<<<<< HEAD
            AddressBook sampleAB = new AddressBook();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addPerson(samplePerson);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
=======
            TaskManager sampleAB = new TaskManager();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addTask(samplePerson);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
>>>>>>> ImplementTaskModels
        }
    }
}
