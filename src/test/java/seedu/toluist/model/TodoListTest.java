package seedu.toluist.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for TodoList model
 */
public class TodoListTest {
    private TodoList todoList1;
    private final Task task1 = new Task("Task 1");
    private final Task task2 = new Task("Task 2");
    private final Task task3 = new Task("Task 3");
    private final Task task4 = new Task("Task 4");
    private final Task task5 = new Task("Task 5");

    @Before
    public void setUp() {
        todoList1 = new TodoList();
        todoList1.add(task1);
        todoList1.add(task2);
        todoList1.add(task3);
        todoList1.add(task4);
    }

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
    public void testGetTask_getValidTask() {
        Task task = todoList1.getTask("3");
        assertTrue(task.equals(task3));
    }

    @Test
    public void testGetTask_getInvalidTask() {
        try {
            todoList1.getTask("-1");
            fail("Should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        }
        try {
            todoList1.getTask("5");
            fail("Should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        }
        try {
            todoList1.getTask("Banana");
            fail("Should throw NumberFormatException");
        } catch (NumberFormatException numberFormatException) {
        }
    }

    @Test
    public void testGetTasks_allTasks() {
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testGetTasks_someTasks() {
        List<Integer> indexes = Arrays.asList(1, 3);
        List<Task> actual = todoList1.getTasks(indexes);
        List<Task> expected = Arrays.asList(task1, task3);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testAddTask_addDuplicatedTask() {
        todoList1.add(task1);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testAddTask_addNewTask() {
        todoList1.add(task5);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4, task5);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testRemoveTask_addExistingTask() {
        todoList1.remove(task2);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task3, task4);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testRemoveTask_addNonExistingTask() {
        todoList1.remove(task5);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4);
        assertTrue(actual.equals(expected));
    }
}
