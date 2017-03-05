package seedu.address.model;

import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestPerson;

/**
 * Test classes for Person
 */
public class PersonTest {
    @Test
    public void person_hashCodeShouldMatch_returnTrue() {
        TestPerson pb;
        try {
            pb = (new PersonBuilder())
                    .withAddress("Singapore")
                    .withLabels("testLabel")
                    .withName("Name")
                    .withPhone("12345678")
                    .withEmail("email@email.com")
                    .build();
            Person p = new Person(pb);
            int hashcode = 1027801006;
            assert(p.hashCode() == hashcode);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            fail("Person fail Illegal value");
        }
    }
}
