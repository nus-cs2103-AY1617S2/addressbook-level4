package seedu.ezdo.model.util;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Email;
import seedu.ezdo.model.todo.Name;
<<<<<<< HEAD
import seedu.ezdo.model.todo.Person;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.UniquePersonList.DuplicatePersonException;
=======
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.Phone;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
<<<<<<< HEAD
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Priority(1), new Email("alexyeoh@gmail.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Person(new Name("Bernice Yu"), new Priority(2), new Email("berniceyu@gmail.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Priority(3), new Email("charlotte@yahoo.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Person(new Name("David Li"), new Priority(1), new Email("lidavid@google.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Person(new Name("Irfan Ibrahim"), new Priority(1), new Email("irfan@outlook.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Priority(2), new Email("royb@gmail.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
=======
            return new Task[] {
                new Task(new Name("Buy some milk"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                    new StartDate("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("groceries")),
                new Task(new Name("Study for CS2103"), new Phone("99272758"), new Email("berniceyu@gmail.com"),
                    new StartDate("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("school", "exams")),
                new Task(new Name("Buy new phone"), new Phone("93210283"), new Email("charlotte@yahoo.com"),
                    new StartDate("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("personal")),
                new Task(new Name("Visit neighbours"), new Phone("91031282"), new Email("lidavid@google.com"),
                    new StartDate("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("personal")),
                new Task(new Name("Prepare for midterms"), new Phone("92492021"), new Email("irfan@outlook.com"),
                    new StartDate("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("school", "exams")),
                new Task(new Name("Prepare for OP2"), new Phone("92624417"), new Email("royb@gmail.com"),
                    new StartDate("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("school", "exams"))
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyEzDo getSampleEzDo() {
        try {
            EzDo sampleEzDo = new EzDo();
            for (Task sampleTask : getSampleTasks()) {
                sampleEzDo.addTask(sampleTask);
            }
            return sampleEzDo;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
