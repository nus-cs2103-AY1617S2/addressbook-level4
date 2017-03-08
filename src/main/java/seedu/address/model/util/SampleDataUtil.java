package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.IdentificationNumber;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Go fly kite"), new Deadline("27-02-2017"),
                    new Description("Flying kite at park"),
                    new IdentificationNumber("1"),
                    new UniqueTagList("friends")),
                new Task(new Name("Buy christmas presents"), new Deadline("23-12-2017"),
                    new Description("Buy presents for Familiy and Friends"),
                    new IdentificationNumber("2"),
                    new UniqueTagList("presents", "christmas")),
                new Task(new Name("Meeting with Charlotte Oliveiro"), new Deadline("02-02-2017"),
                    new Description("Prepare for Meeting with Charlotte"),
                    new IdentificationNumber("3"),
                    new UniqueTagList("important", "meeting")),
                new Task(new Name("Dinner outing with family"), new Deadline("17-12-2017"),
                    new Description("Going to the zoo with family"),
                    new IdentificationNumber("4"),
                    new UniqueTagList("family", "zoo")),
                new Task(new Name("Class reunion"), new Deadline("15-01-2018"),
                    new Description("Secondary school class reunion"),
                    new IdentificationNumber("5"),
                    new UniqueTagList("classmates")),
                new Task(new Name("Company meeting"), new Deadline("04-05-2017"),
                    new Description("Prepare for company meeting"),
                    new IdentificationNumber("6"),
                    new UniqueTagList("important", "meeting"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        TaskManager sampleAB = new TaskManager();
        for (Task samplePerson : getSampleTasks()) {
            sampleAB.addTask(samplePerson);
        }
        return sampleAB;
    }
}
