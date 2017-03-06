package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building To-do list objects.
 * Example usage: <br>
 *     {@code ToDoList ab = new ToDoListBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class ToDoListBuilder {

    private ToDoList todoList;

    public ToDoListBuilder(ToDoList todoList) {
        this.todoList = todoList;
    }

    public ToDoListBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        todoList.addTask(task);
        return this;
    }

    public ToDoListBuilder withTag(String tagName) throws IllegalValueException {
        todoList.addTag(new Tag(tagName));
        return this;
    }

    public ToDoList build() {
        return todoList;
    }
}
