package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.Activity;
import seedu.address.model.person.UniqueActivityList;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Whatsleft objects.
 * Example usage: <br>
 *     {@code WhatsLeft ab = new WhatsLeftBuilder().withActivity("John", "Doe").withTag("Friend").build();}
 */
public class WhatsLeftBuilder {

    private WhatsLeft whatsLeft;

    public WhatsLeftBuilder(WhatsLeft whatsLeft) {
        this.whatsLeft = whatsLeft;
    }

    public WhatsLeftBuilder withActivity(Activity activity) throws UniqueActivityList.DuplicateActivityException {
        whatsLeft.addActivity(activity);
        return this;
    }

    public WhatsLeftBuilder withTag(String tagName) throws IllegalValueException {
        whatsLeft.addTag(new Tag(tagName));
        return this;
    }

    public WhatsLeft build() {
        return whatsLeft;
    }
}
