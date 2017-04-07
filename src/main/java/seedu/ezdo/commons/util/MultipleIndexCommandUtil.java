//@@author A0139248X
package seedu.ezdo.commons.util;

import java.util.ArrayList;

import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;

/**
 * Utility methods for commands supporting multiple index
 */
public class MultipleIndexCommandUtil {

    /** checks if the indexes specified are all smaller than the size of the list and not 0 i.e. valid */
    public static boolean isIndexValid(UnmodifiableObservableList<ReadOnlyTask> lastShownList,
            ArrayList<Integer> targetIndexes) {
        return targetIndexes.stream().allMatch(index -> index <= lastShownList.size() && index != 0);
    }
    
    //@@author A0139177W
    /** checks if the tasks with the indexes specified are marked as done */
    public static boolean isDone(UnmodifiableObservableList<ReadOnlyTask> lastShownList,
            ArrayList<Integer> targetIndexes) {
        for (int i = 0; i < targetIndexes.size(); i++) {
            Task task = (Task) lastShownList.get(targetIndexes.get(i) - 1);
            if (task.getDone()) {
                return false;
            }
        }
        return true;
    }
    //@@author A0139177W

    /** adds read only tasks from the unmodifiable observable list to the ArrayList list */
    public static void addReadOnlyTasksToList(ArrayList<ReadOnlyTask> list,
            UnmodifiableObservableList<ReadOnlyTask> lastShownList, ArrayList<Integer> targetIndexes) {
        for (int i = 0; i < targetIndexes.size(); i++) {
            ReadOnlyTask readOnlyTask = lastShownList.get(targetIndexes.get(i) - 1);
            list.add(readOnlyTask);
        }
    }

    /** adds tasks from the unmodifiable observable list to the ArrayList list */
    public static void addTasksToList(ArrayList<Task> list,
            UnmodifiableObservableList<ReadOnlyTask> lastShownList, ArrayList<Integer> targetIndexes) {
        for (int i = 0; i < targetIndexes.size(); i++) {
            Task task = (Task) lastShownList.get(targetIndexes.get(i) - 1);
            list.add(task);
        }
    }
}
