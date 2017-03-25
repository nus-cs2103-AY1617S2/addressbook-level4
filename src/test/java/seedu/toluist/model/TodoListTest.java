//@@author Melvin
package seedu.toluist.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import seedu.toluist.commons.exceptions.DataStorageException;
import seedu.toluist.storage.TodoListStorage;
import seedu.toluist.testutil.TestUtil;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Tests for TodoList model
 */
public class TodoListTest {
    //@@author A0131125Y
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private TodoListStorage storage;
    private TodoList todoList1;
    private TodoList todoListWithStorage;
    private TodoList sampleTodoList = new TypicalTestTodoLists().getTypicalTodoList();
    private final Task task1 = new Task("Task 1");
    private final Task task2 = new Task("Task 2");
    private final Task task3 = new Task("Task 3");
    private final Task task4 = new Task("Task 4");
    private final Task task5 = new Task("Task 5");

    @Before
    public void setUp() throws DataStorageException, NoSuchFieldException, IllegalAccessException {
        TestUtil.resetSingleton(TodoList.class);
        when(storage.load()).thenReturn(sampleTodoList);
        todoList1 = new TodoList();
        todoList1.add(task1);
        todoList1.add(task2);
        todoList1.add(task3);
        todoList1.add(task4);

        todoListWithStorage = new TodoList();
        todoListWithStorage.setStorage(storage);
        todoListWithStorage.load();
    }


    @Test
    public void todoList_withStorage_loadTasks() {
        assertEquals(todoListWithStorage.getTasks(), sampleTodoList.getTasks());
    }

    @Test
    public void multipleLoad_returnsSameTodoList() {
        assertEquals(TodoList.getInstance(), TodoList.getInstance());
    }

    //@@author Melvin
    @Test
    public void testEquals() {
        TodoList todoList2 = new TodoList();
        todoList2.add(task1);
        todoList2.add(task2);
        todoList2.add(task3);
        todoList2.add(task4);
        assertTrue(todoList1.equals(todoList1));
        assertTrue(todoList1.equals(todoList2));
    }

    @Test
    public void testNotEquals() {
        TodoList todoList2 = new TodoList();
        todoList2.add(task1);
        todoList2.add(task2);
        todoList2.add(task3);
        TodoList todoList3 = new TodoList();
        todoList2.add(task1);
        todoList2.add(task3);
        todoList2.add(task2);
        assertFalse(todoList1.equals(todoList2));
        assertFalse(todoList2.equals(todoList3));
    }

    @Test
    public void testGetAllTasks() {
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testAddDuplicatedTask() {
        todoList1.add(task1);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testAddNewTask() {
        todoList1.add(task5);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4, task5);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testRemoveExistingTask() {
        todoList1.remove(task2);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task3, task4);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testRemoveNonExistingTask() {
        todoList1.remove(task5);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4);
        assertTrue(actual.equals(expected));
    }
}
