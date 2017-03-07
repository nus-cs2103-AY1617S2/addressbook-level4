package seedu.address.todo;


import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.ReadOnlyTodo;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTodoList {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyTodo> getTodoList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
