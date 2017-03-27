package project.taskcrusher.model.shared;

import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Priority;

/**Parent interface of ReadOnlyEvent and ReadOnlyTask. This interface is used avoid duplicate codes
 * for run() method of Qualifier class inside ModelManager.
 */

public interface ReadOnlyUserToDo {
    Name getName();
    Priority getPriority();
    Description getDescription();
    UniqueTagList getTags();
    void markComplete();
    void markIncomplete();
    boolean isComplete();
}
