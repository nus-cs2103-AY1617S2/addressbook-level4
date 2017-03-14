package seedu.onetwodo.model.util;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Alex Yeoh"), new StartDate("tomorrow"), new EndDate("tomorrow"),
                    new Description("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
//                new Task(new Name("Bernice Yu"), new StartDate("99272758"), new EndDate("berniceyu@gmail.com"),
//                    new Description("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
//                    new UniqueTagList("colleagues", "friends")),
//                new Task(new Name("Charlotte Oliveiro"), new StartDate("93210283"), new EndDate("charlotte@yahoo.com"),
//                    new Description("Blk 11 Ang Mo Kio Street 74, #11-04"),
//                    new UniqueTagList("neighbours")),
//                new Task(new Name("David Li"), new StartDate("91031282"), new EndDate("lidavid@google.com"),
//                    new Description("Blk 436 Serangoon Gardens Street 26, #16-43"),
//                    new UniqueTagList("family")),
//                new Task(new Name("Irfan Ibrahim"), new StartDate("92492021"), new EndDate("irfan@outlook.com"),
//                    new Description("Blk 47 Tampines Street 20, #17-35"),
//                    new UniqueTagList("classmates")),
//                new Task(new Name("Roy Balakrishnan"), new StartDate("92624417"), new EndDate("royb@gmail.com"),
//                    new Description("Blk 45 Aljunied Street 85, #11-31"),
//                    new UniqueTagList("colleagues"))
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
