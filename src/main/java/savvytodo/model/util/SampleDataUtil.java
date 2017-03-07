package savvytodo.model.util;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.AddressBook;
import savvytodo.model.ReadOnlyAddressBook;
import savvytodo.model.tag.UniqueTagList;
import savvytodo.model.task.Address;
import savvytodo.model.task.Email;
import savvytodo.model.task.Name;
import savvytodo.model.task.Person;
import savvytodo.model.task.Phone;
import savvytodo.model.task.UniquePersonList.DuplicatePersonException;

public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@gmail.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@yahoo.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@google.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@outlook.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@gmail.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
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
