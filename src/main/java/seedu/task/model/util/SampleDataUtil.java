//@@author A0139539R
package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Instruction;
import seedu.task.model.task.Priority;
import seedu.task.model.task.Task;
import seedu.task.model.task.Title;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {

    public static final int SAMPLE_TASKS_LENGTH = 50;

    private static String[] sampleItems = new String[] {
        "apples", "bananas", "coconuts", "durians", "elderberries", "figs", "grapes",
        "honeydews", "imbes", "jackfruits", "kiwis", "lemons", "mangoes", "nectarines",
        "oranges", "pineapples", "quinces", "rambutans", "strawberries", "tangerines",
        "ugnis", "vanilla beans", "watermelons", "xango mangosteens", "yangmeis",
        "zuchinnis", "almonds", "bonbons", "cupcakes", "donuts", "eclairs", "froyos",
        "gingerbreads", "honeycombs", "ice cream sandwiches", "jellybeans", "kitkats",
        "lollipops", "marshmellows", "nougats", "orange tarts", "petit fours",
        "red velvet cakes", "swiss rolls", "tiramisus", "unsalted popcorns",
        "waffles", "xtremes", "yule logs", "zeppoles"};
    private static String[] sampleVerbs = new String[] {"Buy ", "Eat ", "Taste ", "Get "};
    private static String[] sampleDays = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday", "Sunday", "floating"};
    private static String[] samplePriorities = new String[] {"1", "2", "3", "4", "5", "-1"};

    /**
     * Generates a sample list of tasks
     * @return a sample list of tasks
     */
    public static Task[] getSampleTasks() {
        Task[] sampleTasks = new Task[SAMPLE_TASKS_LENGTH];
        for (int i = 0; i < SAMPLE_TASKS_LENGTH; i++) {
            try {
                int itemIndex = i % sampleItems.length;
                int verbIndex = i % sampleVerbs.length;
                int dayIndex = i % sampleDays.length;
                int priorityIndex = i % samplePriorities.length;

                sampleTasks[i] = new Task(new Title(sampleVerbs[verbIndex] + sampleItems[itemIndex]),
                        new Deadline(sampleDays[dayIndex]),
                        new Priority(samplePriorities[priorityIndex]),
                        new Instruction("Cheap offers"),
                        new UniqueTagList("groceries"));
            } catch (IllegalValueException e) {
                throw new AssertionError("sample data cannot be invalid", e);
            }
        }
        return sampleTasks;
    }
    /**
     * Generates a task manager with sample tasks
     * @return a sample task manager
     */
    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleTaskManager = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleTaskManager.addTask(sampleTask);
            }
            return sampleTaskManager;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
