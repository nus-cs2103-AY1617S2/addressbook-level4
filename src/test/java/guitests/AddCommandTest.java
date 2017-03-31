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
    public void addEventByDateTest() {
        TestTodo[] currentList = td.getTypicalTodos();
        TestTodo todoToAdd;
        try {
            todoToAdd = new TodoBuilder().withName("DefaultEvent").
                    withStartTime("12:00AM 11/11/11").
                    withEndTime("12:00AM 11/11/11").build();

            commandBox.runCommand("add DefaultEvent s/11/11/11 e/11/11/11");

            TodoCardHandle addedCard = todoListPanel.navigateToTodo(todoToAdd.getName().fullName);
            assertMatching(todoToAdd, addedCard);

            TestTodo[] expectedList = TestUtil.addTodosToList(currentList, todoToAdd);
            assertTrue(todoListPanel.isListMatching(true, expectedList));
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void getTimeByDefaultTest() {
        try {
            Set<String> str = new HashSet<String>();
            AddCommand addComand = new AddCommand("testGetTime", str);

            TestTodo[] currentList = td.getTypicalTodos();

            TestTodo eventToAdd = new TodoBuilder().withName("DefaultEvent").
                    withStartTime(getTodayMidnighttoString(addComand.getTodayMidnight())).
                    withEndTime(getTomorrowMidnighttoString(addComand.getTomorrowMidnight())).build();

            assertAddSuccess(eventToAdd, currentList);

            commandBox.runCommand("undo");

            TestTodo deadLineToAdd = new TodoBuilder().withName("DefaultDeadLine").
                    withEndTime(getTomorrowMidnighttoString(addComand.getTomorrowMidnight())).build();

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

    private String getTomorrowMidnighttoString(Date dt) {
        Date dtAssign = dt;
        Calendar c = Calendar.getInstance();
        c.setTime(dtAssign);
        c.add(Calendar.DATE, 1);
        dtAssign = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "12:00AM" + " " + dateFormat.format(dtAssign);
    }

    private String getTodayMidnighttoString(Date dt) {
        Date dtAssign = dt;
        Calendar c = Calendar.getInstance();
        c.setTime(dtAssign);
        dtAssign = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "12:00AM" + " " + dateFormat.format(dtAssign);
    }
}
