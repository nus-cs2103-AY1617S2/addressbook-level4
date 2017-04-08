package project.taskcrusher.model.shared;

import project.taskcrusher.model.tag.UniqueTagList;

//@@author A0127737X
/**Parent interface of ReadOnlyEvent and ReadOnlyTask.
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

    boolean isComplete();

    String toString();
}
