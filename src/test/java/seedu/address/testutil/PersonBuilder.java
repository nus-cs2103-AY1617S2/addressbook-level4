package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.Email;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;

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

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        person.setTags(new UniqueTagList());
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    //@@author A0164889E
    public PersonBuilder withGroup(String group) throws IllegalValueException {
        this.person.setGroup(new Group(group));
        return this;
    }

    //@@author A0164889E
    public PersonBuilder withDate(String date) throws IllegalValueException {
        this.person.setDate(new Date(date));
        return this;
    }
    
    //@@author A0164032U
    public PersonBuilder withStartDate(String sdate) throws IllegalValueException {
        this.person.setStartDate(new StartDate(sdate));
        return this;
    }

    public PersonBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Email(email));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
