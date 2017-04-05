package seedu.whatsleft.testutil;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.activity.UniqueEventList;
import seedu.whatsleft.model.activity.UniqueTaskList;
import seedu.whatsleft.model.tag.Tag;

/**
 * A utility class to help with building Whatsleft objects.
 * Example usage: <br>
 *     {@code WhatsLeft ab = new WhatsLeftBuilder().withEvent("Meeting", "Consultation").withTag("formal").build();}
 */
public class WhatsLeftBuilder {

    private WhatsLeft whatsLeft;

    public WhatsLeftBuilder(WhatsLeft whatsLeft) {
        this.whatsLeft = whatsLeft;
    }

    public WhatsLeftBuilder withEvent(Event event) throws UniqueEventList.DuplicateEventException {
        whatsLeft.addEvent(event);
        return this;
    }

    public WhatsLeftBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        whatsLeft.addTask(task);
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
