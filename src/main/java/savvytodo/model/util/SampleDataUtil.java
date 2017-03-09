package savvytodo.model.util;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.ReadOnlyTaskManager;
import savvytodo.model.TaskManager;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.Address;
import savvytodo.model.task.Description;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                    new Task(new Name("Alex Yeoh"), new Priority("87438807"), new Description("alexyeoh@gmail.com"),
                            new Address("Blk 30 Geylang Street 29, #06-40"), new UniqueCategoryList("friends")),
                    new Task(new Name("Bernice Yu"), new Priority("99272758"), new Description("berniceyu@gmail.com"),
                            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                            new UniqueCategoryList("colleagues", "friends")),
                    new Task(new Name("Charlotte Oliveiro"), new Priority("93210283"),
                            new Description("charlotte@yahoo.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                            new UniqueCategoryList("neighbours")),
                    new Task(new Name("David Li"), new Priority("91031282"), new Description("lidavid@google.com"),
                            new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                            new UniqueCategoryList("family")),
                    new Task(new Name("Irfan Ibrahim"), new Priority("92492021"), new Description("irfan@outlook.com"),
                            new Address("Blk 47 Tampines Street 20, #17-35"), new UniqueCategoryList("classmates")),
                    new Task(new Name("Roy Balakrishnan"), new Priority("92624417"), new Description("royb@gmail.com"),
                            new Address("Blk 45 Aljunied Street 85, #11-31"), new UniqueCategoryList("colleagues")) };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addCategory(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
