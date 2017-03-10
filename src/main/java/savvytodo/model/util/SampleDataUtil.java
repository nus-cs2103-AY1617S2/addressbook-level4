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
                    new Task(new Name("Project Security"), new Priority("low"), new Description("milestone 3"),
                            new Address("Blk 30 NUS Street 29, #06-40"), new UniqueCategoryList("project")),
                    new Task(new Name("Meet up with aunty"), new Priority("low"), new Description("2pm, Rmb to bring gifts"),
                            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                            new UniqueCategoryList("family", "meetup")),
                    new Task(new Name("Skype Meeting 3"), new Priority("high"),
                            new Description("Skype meeting with project team 2"), new Address("None"),
                            new UniqueCategoryList("project")),
                    new Task(new Name("David Li BDay"), new Priority("low"), new Description("6pm, rmb to buy present"),
                            new Address("Blk 436 Botanic Gardens Street 26, #16-43"),
                            new UniqueCategoryList("family")),
                    new Task(new Name("Driving lesson"), new Priority("medium"), new Description("Bring PDL and wear shoes"),
                            new Address("Ubi Crescent"), new UniqueCategoryList("Drving")),
                    new Task(new Name("CS8888 Test"), new Priority("high"), new Description("3pm, Open Book"),
                            new Address("NUS, MPSH 4"), new UniqueCategoryList("test")) };
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
