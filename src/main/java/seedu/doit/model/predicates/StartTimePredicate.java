// @@author A0139399J
package seedu.doit.model.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.doit.commons.util.StringUtil;
import seedu.doit.model.item.ReadOnlyTask;

public class StartTimePredicate implements Predicate<ReadOnlyTask> {
    private Set<String> startTimeKeyWords;

    public StartTimePredicate(Set<String> endTimeKeyWords) {
        this.startTimeKeyWords = endTimeKeyWords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        if (task.hasStartTime()) {
            return this.startTimeKeyWords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getStartTime().value, keyword));
        } else {
            return false;
        }
    }
}
