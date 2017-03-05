package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Title;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
                new Task(new Title("Alex Yeoh"),
                    new Deadline("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueLabelList("friends")),
                new Task(new Title("Bernice Yu"),
                    new Deadline("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueLabelList("colleagues", "friends")),
                new Task(new Title("Charlotte Oliveiro"),
                    new Deadline("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueLabelList("neighbours")),
                new Task(new Title("David Li"),
                    new Deadline("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueLabelList("family")),
                new Task(new Title("Irfan Ibrahim"),
                    new Deadline("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueLabelList("classmates")),
                new Task(new Title("Roy Balakrishnan"),
                    new Deadline("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueLabelList("colleagues"))
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
