package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.ToDoList;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UrgencyLevel;
import seedu.address.model.task.Venue;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                //event task with full task details
                new Task(new Title("CS2103 Tutorial"), new Venue("COM1-B103"),
                        new StartTime("Wednesday 9:00"), new EndTime("Wednesday 10:00"),
                        new UrgencyLevel("5"), new Description("Deadline of V0.3"), new UniqueTagList()),
                //event task without urgency level
                new Task(new Title("CS3230 Lab Quiz"), new Venue("COM1-SR1"),
                        new StartTime("Thursday 19:00"), new EndTime("Thursday 20:00"),
                        null, new Description("20% of final grade"), new UniqueTagList()),
                //deadline task without venue and urgency level
                new Task(new Title("CS2100 Report"), null,
                        null, new EndTime("Wednesday 2359"),
                        null, new Description("I love CS2100"), new UniqueTagList()),
                //deadline task without venue, urgency level and description
                new Task(new Title("LAJ1201 Essay"), null,
                        null, new EndTime("Wednesday 10:00"),
                        null, null, new UniqueTagList()),
                //floating task with only title
                new Task(new Title("Do laundry"), null, null, null,
                        null, null, new UniqueTagList()),
                //floating task with urgency level
                new Task(new Title("Do Tutorials"), null, null, null,
                        new UrgencyLevel("3"), null, new UniqueTagList()),
                //floating task with description
                new Task(new Title("Call grandma"), null, null, null, null,
                        new Description("Interview her for project about aging population"), new UniqueTagList()),
                new Task(new Title("buy new book"), new Venue("popular bookstore at Clementi mall"), null, null,
                        null, null, new UniqueTagList())
                };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyToDoList getSampleToDoList() {
        try {
            ToDoList sampleAB = new ToDoList();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
