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

    private TestTask person;

    public PersonBuilder() {
        this.person = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(TestTask personToCopy) {
        this.person = new TestTask(personToCopy);
    }

    public TestTask build() {
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
