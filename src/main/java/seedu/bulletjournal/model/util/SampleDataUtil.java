package seedu.bullletjournal.model.util;

import seedu.bullletjournal.commons.exceptions.IllegalValueException;
import seedu.bullletjournal.model.ReadOnlyTodoList;
import seedu.bullletjournal.model.TodoList;
import seedu.bullletjournal.model.tag.UniqueTagList;
import seedu.bullletjournal.model.task.Deadline;
import seedu.bullletjournal.model.task.Description;
import seedu.bullletjournal.model.task.Detail;
import seedu.bullletjournal.model.task.Status;
import seedu.bullletjournal.model.task.Task;
import seedu.bullletjournal.model.task.UniqueTaskList.DuplicatePersonException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
                new Task(new Description("Alex Yeoh"), new Deadline("87438807"), new Status("alexyeoh@gmail.com"),
                    new Detail("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new Description("Bernice Yu"), new Deadline("99272758"), new Status("berniceyu@gmail.com"),
                    new Detail("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Description("Charlotte Oliveiro"), new Deadline("93210283"),
                    new Status("charlotte@yahoo.com"),
                    new Detail("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new Description("David Li"), new Deadline("91031282"), new Status("lidavid@google.com"),
                    new Detail("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new Description("Irfan Ibrahim"), new Deadline("92492021"), new Status("irfan@outlook.com"),
                    new Detail("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new Description("Roy Balakrishnan"), new Deadline("92624417"), new Status("royb@gmail.com"),
                    new Detail("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTodoList getSampleAddressBook() {
        try {
            TodoList sampleAB = new TodoList();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addPerson(samplePerson);
            }
            return sampleAB;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
