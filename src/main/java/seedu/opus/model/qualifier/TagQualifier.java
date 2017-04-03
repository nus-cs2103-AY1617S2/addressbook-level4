package seedu.opus.model.qualifier;

import java.util.Set;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.tag.Tag;
import seedu.opus.model.task.ReadOnlyTask;

//@@author A0126345J
/**
 * Compares and filters the tag attribute of a task in the task manager
 */
public class TagQualifier implements Qualifier {
    private Set<String> tagKeyWords;

    public TagQualifier(Set<String> tagKeyWords) {
        this.tagKeyWords = tagKeyWords;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        return tagKeyWords.stream()
                .filter(keyword -> {
                    try {
                        return task.getTags().contains(new Tag(keyword));
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .findAny()
                .isPresent();
    }

}
//@@author
