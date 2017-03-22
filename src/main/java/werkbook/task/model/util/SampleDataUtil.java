package werkbook.task.model.util;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.model.ReadOnlyTaskList;
import werkbook.task.model.TaskList;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.StartDateTime;
import werkbook.task.model.task.Task;
import werkbook.task.model.task.UniqueTaskList;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Walk the dog"),
                        new Description("Take Zelda on a walk around the park"),
                        new StartDateTime("03/01/2016 0900"), new EndDateTime("03/01/2016 1100"),
                        new UniqueTagList("Incomplete")),
                new Task(new Name("Get groceries"), new Description("Egg, cheese, and milk"),
                        new StartDateTime("10/01/2016 1900"), new EndDateTime("10/01/2016 2100"),
                        new UniqueTagList("Incomplete")),
                new Task(new Name("Finish report"), new Description("Do up citations and references"),
                        new StartDateTime("15/03/2016 1000"), new EndDateTime("23/03/2016 2359"),
                        new UniqueTagList("Incomplete")),
                new Task(new Name("Take out the trash"), new Description(""),
                        new StartDateTime("25/12/2015 1400"), new EndDateTime("25/12/2015 1415"),
                        new UniqueTagList("Completed")),
                new Task(new Name("Do Christmas shopping"), new Description("Compare prices with Amazon"),
                        new StartDateTime("20/12/2015 1200"), new EndDateTime("24/12/2015 2200"),
                        new UniqueTagList("Complete")),
                new Task(new Name("Learn how to cook"), new Description("Google instant noodle recipes"),
                        new StartDateTime("11/11/2015 0900"), new EndDateTime("11/11/2015 1900"),
                        new UniqueTagList("Complete")) };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskList getSampleTaskList() {
        try {
            TaskList sampleTaskList = new TaskList();
            for (Task sampleTask : getSampleTasks()) {
                sampleTaskList.addTask(sampleTask);
            }
            return sampleTaskList;
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
