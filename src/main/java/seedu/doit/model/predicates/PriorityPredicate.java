// @@author A0139399J
package seedu.doit.model.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.doit.commons.util.StringUtil;
import seedu.doit.model.item.ReadOnlyTask;

/**
* A predicate that will return true if the ReadOnlyTask priority matches the
* priorityKeyWords
*/
public class PriorityPredicate implements Predicate<ReadOnlyTask> {
    private Set<String> priorityKeyWords;

    public PriorityPredicate(Set<String> priorityKeyWords) {
        this.priorityKeyWords = priorityKeyWords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return this.priorityKeyWords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getPriority().value, keyword));
    }
}
