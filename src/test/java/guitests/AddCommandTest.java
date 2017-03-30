package guitests;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import guitests.guihandles.TodoCardHandle;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.testutil.TestTodo;
import seedu.todolist.testutil.TestUtil;
import seedu.todolist.testutil.TodoBuilder;

public class AddCommandTest extends TodoListGuiTest {

    @Test
    public void add() {
        //add one todo
        TestTodo[] currentList = td.getTypicalTodos();
        TestTodo todoToAdd = td.laundry;

        assertAddSuccess(todoToAdd, currentList);
        currentList = TestUtil.addTodosToList(currentList, todoToAdd);

        //add another todo
        todoToAdd = td.shopping;
        assertAddSuccess(todoToAdd, currentList);
        currentList = TestUtil.addTodosToList(currentList, todoToAdd);

        //add duplicate todo
        commandBox.runCommand(td.laundry.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TODO);
        assertTrue(todoListPanel.isListMatching(true, currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.dog);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

    }

    @Test
    public void addEventTest() {
        TestTodo[] currentList = td.getTypicalTodos();
        TestTodo todoToAdd = td.lunch;
        assertAddSuccess(todoToAdd, currentList);
    }

    @Test
    public void addDeadLineTest() {
        TestTodo[] currentList = td.getTypicalTodos();
        TestTodo todoToAdd = td.job;
        assertAddSuccess(todoToAdd, currentList);
    }

    @Test
    public void getTimeByDefaultTest() {
        try {
            Set<String> str = new HashSet<String>();
            AddCommand addComand = new AddCommand("testGetTime", str);

            TestTodo[] currentList = td.getTypicalTodos();

            TestTodo eventToAdd = new TodoBuilder().withName("DefaultEvent").
                    withStartTime(getTodaytoString(addComand.getToday())).
                    withEndTime(getTomorrowtoString(addComand.getTomorrow())).build();

            assertAddSuccess(eventToAdd, currentList);

            commandBox.runCommand("undo");

            TestTodo deadLineToAdd = new TodoBuilder().withName("DefaultDeadLine").
                    withEndTime(getTomorrowtoString(addComand.getTomorrow())).build();

            assertAddSuccess(deadLineToAdd, currentList);

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : " get time fail";
        }
    }

    @Test
    public void invalidDateTimeInputTest() {
        commandBox.runCommand("add invalidDateTimeInput s/11");
        assertResultMessage(AddCommand.MESSAGE_INVALID_TIME);

        commandBox.runCommand("adds Johonny s/");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTodo todoToAdd, TestTodo... currentList) {

        commandBox.runCommand(todoToAdd.getAddCommand());

        //confirm the new card contains the right data
        TodoCardHandle addedCard = todoListPanel.navigateToTodo(todoToAdd.getName().fullName);
        assertMatching(todoToAdd, addedCard);

        //confirm the list now contains all previous todos plus the new todo
        TestTodo[] expectedList = TestUtil.addTodosToList(currentList, todoToAdd);
        assertTrue(todoListPanel.isListMatching(true, expectedList));
    }

    private String getTomorrowtoString(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("h:mma dd/MM/yyyy");
        return dateFormat.format(dt);
    }

    private String getTodaytoString(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("h:mma dd/MM/yyyy");
        return dateFormat.format(dt);
    }
}
