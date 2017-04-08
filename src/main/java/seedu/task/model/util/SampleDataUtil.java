package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskStatus;
import seedu.task.model.task.TaskTime;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0146757R
public class SampleDataUtil {

    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                    new Task(new TaskName("Buy apple juice"), new TaskDate("010117"),
                            new TaskTime("0800"), new TaskTime("1000"),
                            new String("Look out for lucky draws."), new TaskStatus("Ongoing"),
                            new UniqueTagList()),
                    new Task(new TaskName("Buy blueberry juice"), new TaskDate("020117"),
                            new TaskTime("0900"), new TaskTime("1001"),
                            new String("Look out for lucky dips."), new TaskStatus("Ongoing"),
                            new UniqueTagList()),
                    new Task(new TaskName("Buy coke"), new TaskDate("030117"), new TaskTime("0930"),
                            new TaskTime("1002"), new String("Look out for traffic."),
                            new TaskStatus("Ongoing"), new UniqueTagList()),
                    new Task(new TaskName("Buy isotonic drinks"), new TaskDate("040117"),
                            new TaskTime("1000"), new TaskTime("1100"),
                            new String("Look out for promotions."), new TaskStatus("Ongoing"),
                            new UniqueTagList()),
                    new Task(new TaskName("Buy orange juice"), new TaskDate("050117"),
                            new TaskTime("1010"), new TaskTime("1100"),
                            new String("Look out for sweets along the way."),
                            new TaskStatus("Ongoing"), new UniqueTagList()),
                    new Task(new TaskName("Pick Jasmine from work"), new TaskDate("050117"),
                            new TaskTime("1800"), new TaskTime("1830"),
                            new String("Take note of traffic jams."), new TaskStatus("Ongoing"),
                            new UniqueTagList()),
                    new Task(new TaskName("Pick James from school"), new TaskDate("050117"),
                            new TaskTime("1830"), new TaskTime("1900"),
                            new String("Take note of traffic jams and kids on the road."),
                            new TaskStatus("Ongoing"), new UniqueTagList()),
                    new Task(new TaskName("Pick up takeaways from hawker centre"),
                            new TaskDate("050117"), new TaskTime("2000"), new TaskTime("2020"),
                            new String("Call in advance for food order."),
                            new TaskStatus("Ongoing"), new UniqueTagList()),
                    new Task(new TaskName("Buy toiletries."), new TaskDate("090117"),
                            new TaskTime("1830"), new TaskTime("1900"),
                            new String("Check for ongoing promotions."), new TaskStatus("Ongoing"),
                            new UniqueTagList()),
                    new Task(new TaskName("Send dad home from airport"), new TaskDate("100117"),
                            new TaskTime("2230"), new TaskTime("2350"),
                            new String("Take note of traffic jams and kids on the road."),
                            new TaskStatus("Ongoing"), new UniqueTagList())

            };

        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleTM = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleTM.addJobTask(sampleTask);
            }
            return sampleTM;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
// @@author
