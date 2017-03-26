package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.NattyDateUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartTime;

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

    public PersonBuilder withAddress(String address) throws IllegalValueException {
        this.person.setAddress(new CompletionStatus(Boolean.valueOf(address)));
        return this;
    }

    public PersonBuilder withDateString(String dateString) throws IllegalValueException {
        this.person.setPhone(new StartTime(NattyDateUtil.parseSingleDate(dateString)));
        return this;
    }

    public PersonBuilder withEmail(String email) throws IllegalValueException {
        // TODO: Fix this nonsense.
        this.person.setEmail(new EndTime(NattyDateUtil.parseSingleDate(email)));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
