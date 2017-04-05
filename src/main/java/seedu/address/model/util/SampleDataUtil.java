package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.YTomorrow;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.Email;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList.DuplicatePersonException;

public class SampleDataUtil {
    //@@author A0163848R
    public static Task[] getSamplePersons() {
        try {

            return new Task[] {
                    Task.factory(
                            new Name("study SE"),
                            new StartDate("09.01"),
                            new EndDate("12.12"),
                            new Group("learning")),
                    /*
                new Task(new Name("study SE"), new Date("12.12"), new StartDate("09.01"),
                            new Email("alexyeoh@gmail.com"), new Group("learning"), new UniqueTagList("undone")),
                new Task(new Name("watch Beauty and Beast"), new Date("01.01"), new StartDate("00.00"),
                            new Email("berniceyu@gmail.com"), new Group("relax"),
                            new UniqueTagList("undone", "important")),
                new Task(new Name("do tutorial"), new Date("03.11"), new StartDate("00.00"),
                            new Email("charlotte@yahoo.com"), new Group("learning"), new UniqueTagList("undone")),
                new Task(new Name("review the lesson"), new Date("03.21"), new StartDate("00.00"),
                            new Email("lidavid@google.com"), new Group("learning"), new UniqueTagList("undone")),
                new Task(new Name("read books"), new Date("03.31"), new StartDate("00.00"),
                            new Email("irfan@outlook.com"), new Group("leisure time"), new UniqueTagList("undone")),
                new Task(new Name("painting"), new Date("03.06"), new StartDate("00.00"),
                            new Email("royb@gmail.com"), new Group("leisure time"), new UniqueTagList("undone"))
                            */
            };

        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
    //@@author
    
    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            YTomorrow sampleAB = new YTomorrow();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addPerson(samplePerson);
            }
            return sampleAB;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
