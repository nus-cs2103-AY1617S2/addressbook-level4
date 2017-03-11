package seedu.doit.model.util;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.ReadOnlyTaskManager;
import seedu.doit.model.TaskManager;
import seedu.doit.model.tag.UniqueTagList;
import seedu.doit.model.task.Deadline;
import seedu.doit.model.task.Description;
import seedu.doit.model.task.Name;
import seedu.doit.model.task.Priority;
import seedu.doit.model.task.Task;
import seedu.doit.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Refactor"), new Priority("1"), new Deadline("today"),
                    new Description("Refactor all mentions of task / taskManager"),
                    new UniqueTagList("Huanhui")),
                new Task(new Name("pass Travis"), new Priority("1"), new Deadline("today"),
                    new Description("Remove trailing whitespaces in md files for Travis to pass"),
                    new UniqueTagList("keanwai", "z")),
                new Task(new Name("help"), new Priority("1"), new Deadline("today"),
                    new Description("Update help command"),
                    new UniqueTagList("ChiaSin")),
                new Task(new Name("merge"), new Priority("1"), new Deadline("today"),
                    new Description("Fix the master branch merge conflicts"),
                    new UniqueTagList("JinShun")),
                new Task(new Name("help botton"), new Priority("2"), new Deadline("next week"),
                    new Description("Update Help button to our user guide"),
                    new UniqueTagList("ChiaSin")),
                new Task(new Name("UI interface"), new Priority("3"), new Deadline("next week"),
                    new Description("have 3 lists"),
                    new UniqueTagList("Huanhui"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
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
