package seedu.jobs.model.util;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.AddressBook;
import seedu.jobs.model.ReadOnlyAddressBook;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Description;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.Time;
import seedu.jobs.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                    new Task(new Name("CS2101"), new Time("16/03/2017 15:00"), new Time("16/03/2017 16:00"),
                            new Description("Effetive Communication For Computing Professionlas"),
                            new UniqueTagList("lectures")),
                    new Task(new Name("CS2102"), new Time("17/03/2017 08:00"), new Time("17/03/2017 09:00"),
                            new Description("Database"),
                            new UniqueTagList("tutorials")),
                    new Task(new Name("CS2103"), new Time("01/02/2017 11:00"), new Time("01/02/2017 12:00"),
                            new Description("Software Engineering"),
                            new UniqueTagList("tutorials")),
                    new Task(new Name("Meeting"),new Time("18/03/2017 12:00"),new Time("16/03/2017 17:00"),
                            new Description("Meet with group members at UTown"),
                            new UniqueTagList("meetings"))};
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAB = new AddressBook();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
