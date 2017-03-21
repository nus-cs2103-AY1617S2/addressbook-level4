package seedu.toluist.controller;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import seedu.toluist.commons.exceptions.DataStorageException;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.storage.TodoListStorage;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Base class for Controller tests. All Controller tests should extend this class
 */
public abstract class ControllerTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    protected TodoListStorage storage;
    protected Controller controller;
    protected TodoList todoList;

    /**
     * Returns an instance of the controller to be teste
     * @return the controller instance to be tested
     */
    protected abstract Controller controllerUnderTest();

    @Before
    public void setUp() throws DataStorageException {
        when(storage.load()).thenReturn(new TypicalTestTodoLists().getTypicalTodoList());
        todoList = new TodoList(storage);
        controller = controllerUnderTest();
    }

    /**
     * Asserts that each task exists in memory
     * @param tasks varargs of tasks
     */
    protected void assertTasksExist(Task... tasks) {
        for (Task task : tasks) {
            if (!todoList.getTasks().contains(task)) {
                fail("Task should exist");
            }
        }
    }

    /**
     * Asserts that each task does not exist in memory
     * @param tasks varargs of tasks
     */
    protected void assertTasksNotExist(Task... tasks) {
        for (Task task : tasks) {
            if (TodoList.load().getTasks().contains(task)) {
                fail("Task should not exist");
            }
        }
    }
}
