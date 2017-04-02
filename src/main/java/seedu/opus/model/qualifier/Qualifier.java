package seedu.opus.model.qualifier;

import seedu.opus.model.task.ReadOnlyTask;

/**
 * The API of the Qualifier component.
 */
public interface Qualifier {
    boolean run(ReadOnlyTask task);
}
