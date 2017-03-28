package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Date;
import seedu.address.model.person.Email;
import seedu.address.model.person.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("study SE"), new Date("12.12"), new Email("alexyeoh@gmail.com"),
                    new Group("learning"),
                    new UniqueTagList("undone")),
                new Person(new Name("watch Beauty and Beast"), new Date("01.01"), new Email("berniceyu@gmail.com"),
                    new Group("relax"),
                    new UniqueTagList("undone")),
                new Person(new Name("do tutorial"), new Date("03.11"), new Email("charlotte@yahoo.com"),
                    new Group("learning"),
                    new UniqueTagList("undone")),
                new Person(new Name("review the lesson"), new Date("03.21"), new Email("lidavid@google.com"),
                    new Group("learning"),
                    new UniqueTagList("undone")),
                new Person(new Name("read books"), new Date("03.31"), new Email("irfan@outlook.com"),
                    new Group("leisure time"),
                    new UniqueTagList("undone")),
                new Person(new Name("painting"), new Date("03.06"), new Email("royb@gmail.com"),
                    new Group("leisure time"),
                    new UniqueTagList("done"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAB = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAB.addPerson(samplePerson);
            }
            return sampleAB;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
