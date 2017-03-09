package seedu.toluist.testutil;

import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

/**
 * Typical todo list test data
 */
public class TypicalTestTodoLists {
    public Task cleaning, studying;

    public TypicalTestTodoLists() {
        cleaning = new Task("clean the house while Lewis is gone");
        studying = new Task("do assignments for Louis");
    }

    public static void loadTodoListWithSampleData(TodoList todoList) {
        for (Task task : new TypicalTestTodoLists().getTypicalTasks()) {
            todoList.add(task);
        }
    }

    public Task[] getTypicalTasks() {
        return new Task[] { cleaning, studying };
    }

    public TodoList getTypicalTodoList() {
        TodoList todoList = new TodoList();
        loadTodoListWithSampleData(todoList);
        return todoList;
    }
}
