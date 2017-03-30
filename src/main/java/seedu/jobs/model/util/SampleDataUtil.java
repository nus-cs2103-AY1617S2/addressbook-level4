package seedu.jobs.model.util;

import java.util.Optional;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.ReadOnlyTaskBook;
import seedu.jobs.model.TaskBook;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Description;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Period;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.Time;
import seedu.jobs.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name(Optional.of("CS2101")), new Time(Optional.of("16/03/2017 15:00")),
                            new Time(Optional.of("16/03/2017 16:00")),
                            new Description(Optional.of("Effetive Communication For Computing Professionlas")),
                            new UniqueTagList("lectures"), new Period(Optional.of("0"))),
                new Task(new Name(Optional.of("CS2102")), new Time(Optional.of("17/03/2017 08:00")),
                            new Time(Optional.of("17/03/2017 09:00")), new Description(Optional.of("Database")),
                            new UniqueTagList("tutorials"), new Period(Optional.of("0"))),
                new Task(new Name(Optional.of("CS2103")), new Time(Optional.of("01/02/2017 11:00")),
                            new Time(Optional.of("01/02/2017 12:00")),
                            new Description(Optional.of("Software Engineering")), new UniqueTagList("tutorials"),
                            new Period(Optional.of("0"))),
                new Task(new Name(Optional.of("Meeting")), new Time(Optional.of("18/03/2017 12:00")),
                            new Time(Optional.of("16/03/2017 17:00")),
                            new Description(Optional.of("Meet with group members at UTown")),
                            new UniqueTagList("meetings"), new Period(Optional.of("0")))};
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskBook getSampleAddressBook() {
        try {
            TaskBook sampleAB = new TaskBook();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
