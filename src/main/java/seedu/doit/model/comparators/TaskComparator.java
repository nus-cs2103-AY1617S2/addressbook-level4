// @@author A0139399J
package seedu.doit.model.comparators;

import java.util.Comparator;

import seedu.doit.model.item.ReadOnlyTask;

/**
 * Provides an interface for all TaskComparator classes to compare ReadOnlyTasks
 */
public interface TaskComparator extends Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask t1, ReadOnlyTask t2);

}
