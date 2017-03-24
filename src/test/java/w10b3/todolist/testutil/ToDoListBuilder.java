package w10b3.todolist.testutil;

import w10b3.todolist.commons.exceptions.IllegalValueException;
import w10b3.todolist.model.ToDoList;
import w10b3.todolist.model.tag.Tag;
import w10b3.todolist.model.task.Task;
import w10b3.todolist.model.task.UniqueTaskList;

/**
 * A utility class to help with building To-do list objects.
 * Example usage: <br>
 * {@code ToDoList ab = new ToDoListBuilder().withTask("John", "Doe").withTag("Friend").build();}
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
