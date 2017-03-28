// @@author A0139399J
package seedu.doit.model.predicates;

import java.util.function.Predicate;

import seedu.doit.model.item.ReadOnlyTask;

/**
 * A utility Predicate that will always return true
 */
public class AlwaysTruePredicate implements Predicate<ReadOnlyTask> {
    @Override
    public boolean test(ReadOnlyTask task) {
        return true;
    }
}
