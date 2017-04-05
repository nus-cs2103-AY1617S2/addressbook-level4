package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.YTomorrow;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.Email;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList.DuplicatePersonException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {

            return new Task[] {
                new Task(new Name("study SE"), new Date("May 6"), new StartDate("09.01"),
                            new Email("alexyeoh@gmail.com"), new Group("learning"), new UniqueTagList(Tag.TAG_INCOMPLETE)),
                new Task(new Name("watch Beauty and Beast"), new Date("May 4"), new StartDate("Jan 1"),
                            new Email("berniceyu@gmail.com"), new Group("relax"),
                            new UniqueTagList(Tag.TAG_COMPLETE)),
                new Task(new Name("do tutorial"), new Date("May 1"), new StartDate("Jan 2"),
                            new Email("charlotte@yahoo.com"), new Group("learning"), new UniqueTagList(Tag.TAG_INCOMPLETE)),
                new Task(new Name("review the lesson"), new Date("May 4"), new StartDate("Jan 3"),
                            new Email("lidavid@google.com"), new Group("learning"), new UniqueTagList(Tag.TAG_INCOMPLETE)),
                new Task(new Name("read books"), new Date("May 5"), new StartDate("Jan 4"),
                            new Email("irfan@outlook.com"), new Group("leisure time"), new UniqueTagList(Tag.TAG_INCOMPLETE)),
                new Task(new Name("painting"), new Date("May 6"), new StartDate("Jan 1"),
                            new Email("royb@gmail.com"), new Group("leisure time"), new UniqueTagList(Tag.TAG_INCOMPLETE))
                
            };

        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

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
