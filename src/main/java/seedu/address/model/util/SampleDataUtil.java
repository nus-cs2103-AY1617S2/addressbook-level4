package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("EE2021"), new UniqueTagList("homework")),
                new Person(new Name("MA1506"), new UniqueTagList("killer", "revision")),
                new Person(new Name("CS2103 Project"), new UniqueTagList("Help")),
                new Person(new Name("PC1222 Lecture"), new UniqueTagList("lecture")),
                new Person(new Name("GEH1027 Lecture"), new UniqueTagList("lecture")),
                new Person(new Name("HOHO Time"), new UniqueTagList("LMAO"))
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
