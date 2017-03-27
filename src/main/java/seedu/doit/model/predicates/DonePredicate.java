// @@author A0139399J
package seedu.doit.model.predicates;

import java.util.function.Predicate;

import seedu.doit.model.item.ReadOnlyTask;

/**
 * A predicate that will return true if the ReadOnlyTask isDone matches the
 * showDone boolean
 */
public class DonePredicate implements Predicate<ReadOnlyTask> {
    private boolean showDone;

    public DonePredicate(boolean showDone) {
        this.showDone = showDone;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return (task.getIsDone() == showDone);
    }
}
