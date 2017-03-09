package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
                new Task(new Name("EE2021"), new UniqueTagList("homework")),
                new Task(new Name("MA1506"), new UniqueTagList("killer", "revision")),
                new Task(new Name("CS2103 Project"), new UniqueTagList("Help")),
                new Task(new Name("PC1222 Lecture"), new UniqueTagList("lecture")),
                new Task(new Name("GEH1027 Lecture"), new UniqueTagList("lecture")),
                new Task(new Name("HOHO Time"), new UniqueTagList("LMAO"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAB = new AddressBook();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addPerson(samplePerson);
            }
            return sampleAB;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
