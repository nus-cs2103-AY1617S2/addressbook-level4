package project.taskcrusher.model.shared;

/**Parent interface of ReadOnlyEvent and ReadOnlyTask. This interface is used avoid duplicate codes
 * for run() method of Qualifier class inside ModelManager.
 */

public interface UserToDo {
    Name getName();
}
