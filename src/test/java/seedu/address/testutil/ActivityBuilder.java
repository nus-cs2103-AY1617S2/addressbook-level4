package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 *
 */
public class ActivityBuilder {

    private TestActivity activity;

    public ActivityBuilder() {
        this.activity = new TestActivity();
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code activityToCopy}.
     */
    public ActivityBuilder(TestActivity activityToCopy) {
        this.activity = new TestActivity(activityToCopy);
    }

    public ActivityBuilder withDescription(String description) throws IllegalValueException {
        this.activity.setDescription(new Description(description));
        return this;
    }

    public ActivityBuilder withTags(String ... tags) throws IllegalValueException {
        activity.setTags(new UniqueTagList());
        for (String tag: tags) {
            activity.getTags().add(new Tag(tag));
        }
        return this;
    }

    public ActivityBuilder withLocation(String location) throws IllegalValueException {
        this.activity.setLocation(new Location(location));
        return this;
    }

    public ActivityBuilder withPriority(String priority) throws IllegalValueException {
        this.activity.setPriority(new Priority(priority));
        return this;
    }

    public ActivityBuilder withEmail(String email) throws IllegalValueException {
        this.activity.setEmail(new Email(email));
        return this;
    }

    public TestActivity build() {
        return this.activity;
    }

}
