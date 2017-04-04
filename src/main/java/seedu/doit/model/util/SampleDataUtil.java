// @@author A0139399J
package seedu.doit.model.util;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.ReadOnlyItemManager;
import seedu.doit.model.TaskManager;
import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Represents sample data of tasks that will be loaded into TaskManager if the
 * data file is not found on startup
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] { new Task(new Name("Refactor"), new Priority("low"), new EndTime("today"),
                    new Description("Refactor all mentions of task / taskManager"),
                    new UniqueTagList("Huanhui")),
                new Task(new Name("pass Travis"), new Priority("med"), new EndTime("today"),
                    new Description("Remove trailing whitespaces in md files for Travis to pass"),
                    new UniqueTagList("keanwai", "z")),
                new Task(new Name("help"), new Priority("high"), new EndTime("today"),
                    new Description("Update help command"),
                    new UniqueTagList("ChiaSin")),
                new Task(new Name("merge"), new Priority("med"), new EndTime("today"),
                    new Description("Fix the master branch merge conflicts"),
                    new UniqueTagList("JinShun")),
                new Task(new Name("help botton"), new Priority("high"), new EndTime("next week"),
                    new Description("Update Help button to our user guide"),
                    new UniqueTagList("ChiaSin")),
                new Task(new Name("UI interface"), new Priority("low"), new EndTime("next week"),
                    new Description("have 3 lists"),
                    new UniqueTagList("Huanhui")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyItemManager getSampleTaskManager() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
