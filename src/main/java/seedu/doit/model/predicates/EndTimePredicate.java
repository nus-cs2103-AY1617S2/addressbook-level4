// @@author A0139399J
package seedu.doit.model.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.doit.commons.util.StringUtil;
import seedu.doit.model.item.ReadOnlyTask;

public class EndTimePredicate implements Predicate<ReadOnlyTask> {
    private Set<String> endTimeKeyWords;

    public EndTimePredicate(Set<String> endTimeKeyWords) {
        this.endTimeKeyWords = endTimeKeyWords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        if (task.hasEndTime()) {
            return this.endTimeKeyWords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDeadline().value, keyword));
        } else {
            return false;
        }
    }
}
