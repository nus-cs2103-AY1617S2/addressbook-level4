// @@author A0139399J
package seedu.doit.model.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.doit.commons.util.StringUtil;
import seedu.doit.model.item.ReadOnlyTask;

/**
 * A predicate that will return true if the ReadOnlyTask description matches the
 * descriptionKeyWords
 */
public class DescriptionPredicate implements Predicate<ReadOnlyTask> {
    private Set<String> descriptionKeyWords;

    public DescriptionPredicate(Set<String> descriptionKeyWords) {
        this.descriptionKeyWords = descriptionKeyWords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return this.descriptionKeyWords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().value, keyword));
    }
}
