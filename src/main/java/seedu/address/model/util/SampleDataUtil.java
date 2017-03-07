package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Location;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Description("CS2103 TUT1"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                    new Location("National University of Singapore"),
                    new UniqueTagList("friends")),
                new Person(new Description("CS2103 TUT2"), new Phone("99272758"), new Email("berniceyu@gmail.com"),
                    new Location("NUS"),
                    new UniqueTagList("colleagues", "friends")),
                new Person(new Description("CS2103 TUT3"), new Phone("93210283"), new Email("charlotte@yahoo.com"),
                    new Location("SOC"),
                    new UniqueTagList("neighbours")),
                new Person(new Description("CS2103 TUT4"), new Phone("91031282"), new Email("lidavid@google.com"),
                    new Location("NUS"),
                    new UniqueTagList("family")),
                new Person(new Description("CS2103 TUT5"), new Phone("92492021"), new Email("irfan@outlook.com"),
                    new Location("NUS"),
                    new UniqueTagList("classmates")),
                new Person(new Description("CS2103 TUT6"), new Phone("92624417"), new Email("royb@gmail.com"),
                    new Location("NUS"),
                    new UniqueTagList("colleagues"))
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
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
