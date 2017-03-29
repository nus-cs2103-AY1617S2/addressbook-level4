// @@author A0139399J
package seedu.doit.model.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.tag.Tag;

/**
* A predicate that will return true if any of the ReadOnlyTask tags matches the
* tagKeywords
*/
public class    TagPredicate implements Predicate<ReadOnlyTask> {
    private Set<String> tagKeywords;

    public TagPredicate(Set<String> tagKeywords) {
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return this.tagKeywords.stream().allMatch(keyword -> {
            try {
                return (task.getTags().contains(new Tag(keyword)));
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
