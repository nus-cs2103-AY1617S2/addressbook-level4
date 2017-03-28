//@@author A0131125Y
package seedu.toluist.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

/**
 * Typical todo list test data
 */
public class TypicalTestTodoLists {
    public Task cleaning, studying;

    public TypicalTestTodoLists() {
        Tag lewisTag = new Tag("lewis");
        Tag workTag = new Tag("work");
        Tag louisTag = new Tag("louis");

        cleaning = new Task("clean the house while Lewis is gone");
        cleaning.replaceTags(new ArrayList<>(Arrays.asList(lewisTag, workTag)));
        studying = new Task("do assignments for Louis");
        studying.replaceTags(new ArrayList<>(Arrays.asList(louisTag, workTag)));
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
