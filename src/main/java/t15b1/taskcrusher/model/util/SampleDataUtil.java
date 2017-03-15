package t15b1.taskcrusher.model.util;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.model.ReadOnlyUserInbox;
import t15b1.taskcrusher.model.UserInbox;
import t15b1.taskcrusher.model.shared.Description;
import t15b1.taskcrusher.model.shared.Name;
import t15b1.taskcrusher.model.tag.UniqueTagList;
import t15b1.taskcrusher.model.task.Deadline;
import t15b1.taskcrusher.model.task.Priority;
import t15b1.taskcrusher.model.task.Task;
import t15b1.taskcrusher.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
               new Task(new Name("CS2103 tutorial"), new Deadline("tomorrow"), new Priority("3"), 
                       new Description("presentation"), new UniqueTagList("school")),
               new Task(new Name("CS2106 assignment"), new Deadline("next Monday"), new Priority("2"), 
                       new Description("submit assignment"), new UniqueTagList("school"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyUserInbox getSampleUserInbox() {
        try {
            UserInbox sampleuserInbox = new UserInbox();
            for (Task sampleTask : getSampleTasks()) {
                sampleuserInbox.addTask(sampleTask);
            }
            return sampleuserInbox;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
