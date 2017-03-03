package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Status;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Priority("87438807"), new Status("alexyeoh@gmail.com"),
                    new Note("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Person(new Name("Bernice Yu"), new Priority("99272758"), new Status("berniceyu@gmail.com"),
                    new Note("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Priority("93210283"), new Status("charlotte@yahoo.com"),
                    new Note("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Person(new Name("David Li"), new Priority("91031282"), new Status("lidavid@google.com"),
                    new Note("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Person(new Name("Irfan Ibrahim"), new Priority("92492021"), new Status("irfan@outlook.com"),
                    new Note("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Priority("92624417"), new Status("royb@gmail.com"),
                    new Note("Blk 45 Aljunied Street 85, #11-31"),
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
