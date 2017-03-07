package seedu.geekeep.model.util;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.ReadOnlyTaskManager;
import seedu.geekeep.model.TaskManager;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.Location;
import seedu.geekeep.model.task.StartDateTime;
import seedu.geekeep.model.task.EndDateTime;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.model.task.UniqueTaskList.DuplicatePersonException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("Alex Yeoh"), new EndDateTime("87438807"), new StartDateTime("alexyeoh@gmail.com"),
                    new Location("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new Title("Bernice Yu"), new EndDateTime("99272758"), new StartDateTime("berniceyu@gmail.com"),
                    new Location("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Title("Charlotte Oliveiro"), new EndDateTime("93210283"), new StartDateTime("charlotte@yahoo.com"),
                    new Location("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new Title("David Li"), new EndDateTime("91031282"), new StartDateTime("lidavid@google.com"),
                    new Location("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new Title("Irfan Ibrahim"), new EndDateTime("92492021"), new StartDateTime("irfan@outlook.com"),
                    new Location("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new Title("Roy Balakrishnan"), new EndDateTime("92624417"), new StartDateTime("royb@gmail.com"),
                    new Location("Blk 45 Aljunied Street 85, #11-31"),
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
