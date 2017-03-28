package seedu.geekeep.testutil;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.GeeKeep;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;

/**
 * A utility class to help with building GeeKeep objects.
 * Example usage: <br>
 *     {@code GeeKeep ab = new GeeKeepBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class GeeKeepBuilder {

    private GeeKeep geeKeep;

    public GeeKeepBuilder(GeeKeep geeKeep) {
        this.geeKeep = geeKeep;
    }

    public GeeKeepBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        geeKeep.addTask(task);
        return this;
    }

    public GeeKeepBuilder withTag(String tagName) throws IllegalValueException {
        geeKeep.addTag(new Tag(tagName));
        return this;
    }

    public GeeKeep build() {
        return geeKeep;
    }
}
