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
                new Person(new Description("CS2010 Written Quiz 1"), new Phone("87438807"),
                    new Email("alexyeoh@gmail.com"), new Location("SR1"),
                    new UniqueTagList("friends")),
                new Person(new Description("CS2103 Tutorial 6"), new Phone("99272758"),
                    new Email("berniceyu@gmail.com"), new Location("COM-1-B1"),
                    new UniqueTagList("colleagues", "friends")),
                new Person(new Description("Buy fruits"), new Phone("93210283"),
                    new Email("charlotte@yahoo.com"),
                    new Location("FairPrice"),
                    new UniqueTagList("neighbours")),
                new Person(new Description("Home Assignment 2"), new Phone("91031282"), new Email("lidavid@google.com"),
                    new Location("CLB"),
                    new UniqueTagList("family")),
                new Person(new Description("CS2102 Consultation"), new Phone("92492021"),
                    new Email("irfan@outlook.com"), new Location("I-Cube"),
                    new UniqueTagList("classmates")),
                new Person(new Description("IVLE Survey"), new Phone("92624417"), new Email("royb@gmail.com"),
                    new Location("anywhere"),
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
