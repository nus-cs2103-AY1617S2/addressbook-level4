package seedu.ezdo.commons.util;

import java.util.ArrayList;

import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.model.todo.ReadOnlyTask;
//@@author A0139248X
/**
 * Utility methods for commands supporting multiple index
 */
public class MultipleIndexCommandUtil {
    /** checks if the indexes specified are all smaller than the size of the list and not 0 i.e. valid */
    public static boolean isIndexValid(UnmodifiableObservableList<ReadOnlyTask> lastShownList, ArrayList<Integer> targetIndexes) {
        return targetIndexes.stream().allMatch(index -> index <= lastShownList.size() && index != 0);
    }
}
