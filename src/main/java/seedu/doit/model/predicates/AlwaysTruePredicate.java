package seedu.doit.model.predicates;

import java.util.function.Predicate;

import seedu.doit.model.item.ReadOnlyTask;

//@@author A0139399J
/**
 * A utility Predicate that will always return true
 */
public class AlwaysTruePredicate implements Predicate<ReadOnlyTask> {
    @Override
    public boolean test(ReadOnlyTask task) {
        return true;
    }
}
