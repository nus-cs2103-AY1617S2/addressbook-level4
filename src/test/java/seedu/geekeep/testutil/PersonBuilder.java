package seedu.geekeep.testutil;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Location;
import seedu.geekeep.model.task.Title;

/**
 *
 */
public class PersonBuilder {

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(TestPerson personToCopy) {
        this.person = new TestPerson(personToCopy);
    }

    public TestPerson build() {
        return this.person;
    }

    public PersonBuilder withEndDateTime(String phone) throws IllegalValueException {
        this.person.setEndDateTime(new DateTime(phone));
        return this;
    }

    public PersonBuilder withLocation(String address) throws IllegalValueException {
        this.person.setLocation(new Location(address));
        return this;
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setTitle(new Title(name));
        return this;
    }

    public PersonBuilder withStartDateTime(String email) throws IllegalValueException {
        this.person.setStartDateTime(new DateTime(email));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        person.setTags(new UniqueTagList());
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

}
