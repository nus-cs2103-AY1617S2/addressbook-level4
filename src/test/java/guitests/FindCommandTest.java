package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.commands.DeleteCommand;
import seedu.todolist.logic.commands.FindCommand;
import seedu.todolist.logic.parser.CliSyntax;
import seedu.todolist.testutil.TestTodo;
import seedu.todolist.testutil.TodoBuilder;

public class FindCommandTest extends TodoListGuiTest {
    // This tag only tagged one item (td.cat) in the list of test todos
    private static final String SINGLE_TAG = "cat";

    // This tag tagged multiple items (td.dog, td.cat) in the list of test todos
    private static final String MULTI_TAG = "petcare";

    private static final String START_TIME_AFTER = "1:00PM 11/11/17";
    private static final String START_TIME_BEFORE = "11:00AM 11/11/17";
    private static final String END_TIME_AFTER = "7:00PM 11/11/17";
    private static final String END_TIME_BEFORE = "5:00PM 11/11/17";
    private static final String COMPLETE_TIME_AFTER = "7:00PM 11/11/17";
    private static final String COMPLETE_TIME_BEFORE = "5:00PM 11/11/17";

    @Test
    public void find_nonEmptyList() {
        assertFindResult(FindCommand.COMMAND_WORD + " supper"); // no results
        assertFindResult(FindCommand.COMMAND_WORD + " homework", td.math, td.english); // multiple results

        //find after deleting one result

        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFindResult(FindCommand.COMMAND_WORD + " homework", td.english);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult(FindCommand.COMMAND_WORD + " homework"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand(FindCommand.COMMAND_WORD + "homework");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    //@@author A0163720M
    /**
     * Test to find todo with just one tag e.g. tags = some_Tag
     */
    public void find_todo_with_one_tag() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_TAG.getPrefix() + SINGLE_TAG, td.cat);
    }
    //@@author

    @Test
    //@@author A0163720M
    /**
     * Test to find todo with multiple tags e.g. tags = some_tag, some_tag2
     */
    public void find_todo_with_multiple_tags() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_TAG.getPrefix() + MULTI_TAG, td.dog, td.cat);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_startTimeAfter() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_START_TIME.getPrefix() + START_TIME_AFTER,
                td.toilet, td.tennis);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_startTimeBefore() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_START_TIME.getPrefix() + START_TIME_BEFORE);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_endTimeAfter() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_END_TIME.getPrefix() + END_TIME_AFTER,
                td.essay, td.toilet, td.library, td.tennis);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_endTimeBefore() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_END_TIME.getPrefix() + END_TIME_BEFORE,
                td.toilet, td.tennis);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_completeTimeAfter() {
        assertFindResult(
                FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + COMPLETE_TIME_AFTER,
                td.car, td.library, td.tennis);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_completeTimeBefore() {
        assertFindResult(
                FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + COMPLETE_TIME_BEFORE,
                td.tennis);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_startTimeCompleteTime() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_START_TIME.getPrefix() + START_TIME_AFTER
                + " " + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + COMPLETE_TIME_AFTER, td.tennis);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_startTimeEndTime() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_START_TIME.getPrefix() + START_TIME_AFTER
                + " " + CliSyntax.PREFIX_END_TIME.getPrefix() + END_TIME_AFTER, td.toilet, td.tennis);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_endTimeCompleteTime() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_END_TIME.getPrefix() + END_TIME_AFTER + " "
                + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + COMPLETE_TIME_AFTER, td.library, td.tennis);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_nameStartTime() {
        assertFindResult(FindCommand.COMMAND_WORD + " Go to the bathroom " + CliSyntax.PREFIX_START_TIME.getPrefix()
                + START_TIME_AFTER, td.toilet);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_startTimeTag() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_START_TIME.getPrefix() + START_TIME_AFTER
                + " " + CliSyntax.PREFIX_TAG.getPrefix() + "personal", td.toilet);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_allParams() {
        assertFindResult(FindCommand.COMMAND_WORD + " tennis " + CliSyntax.PREFIX_END_TIME.getPrefix() + END_TIME_AFTER
                + " " + CliSyntax.PREFIX_START_TIME.getPrefix() + START_TIME_AFTER + " "
                + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + COMPLETE_TIME_AFTER + " "
                + CliSyntax.PREFIX_TAG.getPrefix() + "sports", td.tennis);
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_startBeforeToday() {
        try {
            TestTodo newTodo = new TodoBuilder().withName("test")
                    .withEndTime(new SimpleDateFormat(DATE_FORMAT).format(new Date())).build();
            commandBox.runCommand(newTodo.getAddCommand());
            assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_END_TIME.getPrefix()
                + "today", newTodo);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_startBeforeTomorrow() {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, 1);
            TestTodo newTodo = new TodoBuilder().withName("test")
                    .withEndTime(new SimpleDateFormat(DATE_FORMAT).format(c.getTime())).build();
            commandBox.runCommand(newTodo.getAddCommand());
            assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_END_TIME.getPrefix()
                + "tomorrow", newTodo);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    //@@author
    //@@author A0163786N
    @Test
    public void find_allCompleted() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix(),
                td.car, td.library, td.tennis);
    }
    //@@author
  //@@author A0163786N
    @Test
    public void find_allUncompleted() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + "not",
                td.dog, td.cat, td.math, td.english, td.dishes, td.lawn, td.dinner, td.essay, td.toilet);
    }
    //@@author
    private void assertFindResult(String command, TestTodo... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " todos listed!");
        assertTrue(todoListPanel.isListMatching(true, expectedHits));
    }
}
