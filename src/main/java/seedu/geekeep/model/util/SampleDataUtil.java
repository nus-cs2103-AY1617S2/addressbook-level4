package seedu.geekeep.model.util;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.ReadOnlyTaskManager;
import seedu.geekeep.model.TaskManager;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.Address;
import seedu.geekeep.model.task.Email;
import seedu.geekeep.model.task.Phone;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.model.task.UniqueTaskList.DuplicatePersonException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new Title("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@gmail.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Title("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@yahoo.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new Title("David Li"), new Phone("91031282"), new Email("lidavid@google.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new Title("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@outlook.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new Title("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@gmail.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleAddressBook() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
