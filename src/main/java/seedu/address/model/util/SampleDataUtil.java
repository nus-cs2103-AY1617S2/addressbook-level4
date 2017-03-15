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
                    new Task(new Title("CS2103 Tutorial"), new Venue("COM1-B103"), new StartTime("Tuesday 10:00"),
                            new EndTime("Tuesday 11:00"), new UrgencyLevel("3"), new Description("Deadline of V0.2"),
                            new UniqueTagList("lesson", "tutorial")),
                    new Task(new Title("DBS Internship interview"), new Venue("Raffles Place"),
                            new StartTime("March 31, 9:30"), new EndTime("Wednesday 12:00"),
                            new UrgencyLevel("3"), new Description("I love Intership"),
                            new UniqueTagList("interview", "internship", "important")),
                    new Task(new Title("Hang out with Joe"), new Venue("313 Somerset"), new StartTime("Saturday 11:00"),
                            new EndTime("Saturday 17:00"), new UrgencyLevel("3"), new Description("I love Joe"),
                            new UniqueTagList("friend", "shopping", "weekend")),
                    new Task(new Title("Stats society meeting"), new Venue("S16-04-30"), new StartTime("Wednesday 19:00"),
                            new EndTime("Wednesday 21:00"), new UrgencyLevel("3"), new Description("I love stats society"),
                            new UniqueTagList("family")),
                    new Task(new Title("String ensemble rehearsal"), new Venue("UCC Hall"), new StartTime("Friday 9:00"),
                            new EndTime("Friday 17:00"), new UrgencyLevel("3"), new Description("I love rehearsals"),
                            new UniqueTagList("CCA", "performance")),
                    new Task(new Title("MA3269 Quiz"), new Venue("LT26"), new StartTime("Thursday 12:00"),
                            new EndTime("Thursday 14:00"), new UrgencyLevel("3"), new Description("I hate hate quiz"),
                            new UniqueTagList("important", "test"))
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
