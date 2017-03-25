package seedu.doit.model.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.doit.commons.util.StringUtil;
import seedu.doit.model.item.ReadOnlyTask;

//@@author A0139399J
/**
* A predicate that will return true if the ReadOnlyTask name matches the
* nameKeyWords
*/
public class NamePredicate implements Predicate<ReadOnlyTask> {
    private Set<String> nameKeyWords;

    public NamePredicate(Set<String> nameKeyWords) {
        this.nameKeyWords = nameKeyWords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return this.nameKeyWords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword));
    }
}
