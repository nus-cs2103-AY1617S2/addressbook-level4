package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
  /** Clears existing backing model and replaces with the provided new data. */
  void resetData(ReadOnlyTaskManager newData);

  /** Returns the TaskManager */
  ReadOnlyTaskManager getTaskManager();

  /** Deletes the given task. */
  void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

  /** Adds the given task */
  void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

  /**
   * Updates the person located at {@code filteredPersonListIndex} with
   * {@code editedPerson}.
   *
   * @throws DuplicateTaskException
   *           if updating the person's details causes the person to be
   *           equivalent to another existing person in the list.
   * @throws IndexOutOfBoundsException
   *           if {@code filteredPersonListIndex} < 0 or >= the size of the
   *           filtered list.
   */
  void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws UniqueTaskList.DuplicateTaskException;

  /**
   * Returns the filtered person list as an
   * {@code UnmodifiableObservableList<ReadOnlyPerson>}
   */
  UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

  /** Updates the filter of the filtered person list to show all persons */
  void updateFilteredListToShowAll();

  /**
   * Updates the filter of the filtered person list to filter by the given
   * keywords
   */
  void updateFilteredTaskList(Set<String> keywords);

}
