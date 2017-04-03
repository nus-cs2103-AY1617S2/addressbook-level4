package project.taskcrusher.model.shared;

import project.taskcrusher.model.tag.UniqueTagList;

//@@author A0127737X
/**Parent interface of ReadOnlyEvent and ReadOnlyTask. This interface is used avoid duplicate codes
 * for run() method of Qualifier class inside ModelManager.
 */

public interface ReadOnlyUserToDo {
    Name getName();
    Priority getPriority();
    Description getDescription();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();
    void markComplete();
    void markIncomplete();
    boolean isComplete();
    String toString();
}
