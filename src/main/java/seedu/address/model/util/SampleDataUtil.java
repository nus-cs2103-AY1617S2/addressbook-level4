package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.ToDoList;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.Venue;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("Alex Yeoh"), new Venue("87438807"), new StartTime("alexyeoh@gmail.com"),
                    new EndTime("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new Title("Bernice Yu"), new Venue("99272758"), new StartTime("berniceyu@gmail.com"),
                    new EndTime("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Title("Charlotte Oliveiro"), new Venue("93210283"), new StartTime("charlotte@yahoo.com"),
                    new EndTime("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new Title("David Li"), new Venue("91031282"), new StartTime("lidavid@google.com"),
                    new EndTime("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new Title("Irfan Ibrahim"), new Venue("92492021"), new StartTime("irfan@outlook.com"),
                    new EndTime("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new Title("Roy Balakrishnan"), new Venue("92624417"), new StartTime("royb@gmail.com"),
                    new EndTime("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyToDoList getSampleToDoList() {
        try {
            ToDoList sampleAB = new ToDoList();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
