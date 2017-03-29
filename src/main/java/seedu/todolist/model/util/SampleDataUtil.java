package seedu.todolist.model.util;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.ReadOnlyTodoList;
import seedu.todolist.model.TodoList;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.Name;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.model.todo.UniqueTodoList.DuplicateTodoException;

public class SampleDataUtil {
    public static Todo[] getSampleTodos() {
        try {
            return new Todo[] {
                new Todo(new Name("Walk the dog"),
                    new UniqueTagList("pets")),
                new Todo(new Name("Do math homework"),
                    new UniqueTagList("school")),
                new Todo(new Name("Do english homework"),
                    new UniqueTagList("schoool")),
                new Todo(new Name("Do science homework"),
                    new UniqueTagList("school")),
                new Todo(new Name("Get groceries"),
                    new UniqueTagList("chores")),
                new Todo(new Name("Feed cat"),
                    new UniqueTagList("pets"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTodoList getSampleTodoList() {
        try {
            TodoList sampleAB = new TodoList();
            for (Todo sampleTodo : getSampleTodos()) {
                sampleAB.addTodo(sampleTodo);
            }
            return sampleAB;
        } catch (DuplicateTodoException e) {
            throw new AssertionError("sample data cannot contain duplicate todos", e);
        }
    }
}
