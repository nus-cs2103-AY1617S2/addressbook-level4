package seedu.bulletjournal.testutil;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.model.TodoList;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask assignment, buymilk, creatework, dumpmilk, eatleftovers, findsocks, getclothes, hangclothes,
            interviewprep, submitreport, floatone, floattwo, deadone, deadtwo;

    public TypicalTestTasks() {
        try {
            assignment = new TaskBuilder().withTaskName("Assignment for CS2103")
                    .withBeginDate("14th April 12pm").withStatus("undone")
                    .withDueDate("14th April 4pm").withTags("friends").build();
            buymilk = new TaskBuilder().withTaskName("Buy milk").withBeginDate("14th April 12pm")
                    .withStatus("done").withDueDate("14th April 4pm").withTags("owesMoney", "friends").build();
            creatework = new TaskBuilder().withTaskName("Create more work").withDueDate("14th April 4pm")
                    .withStatus("done").withBeginDate("14th April 12pm").build();
            dumpmilk = new TaskBuilder().withTaskName("Dump milk").withDueDate("14th April 4pm")
                    .withStatus("undone").withBeginDate("14th April 12pm").build();
            eatleftovers = new TaskBuilder().withTaskName("Eat leftovers").withDueDate("14th April 4pm")
                    .withStatus("done").withBeginDate("14th April 12pm").build();
            findsocks = new TaskBuilder().withTaskName("Find socks").withDueDate("14th April 4pm")
                    .withStatus("undone").withBeginDate("little tokyo").build();
            getclothes = new TaskBuilder().withTaskName("Get clothes").withDueDate("14th April 4pm")
                    .withStatus("undone").withBeginDate("14th April 12pm").build();

            // Manually added
            hangclothes = new TaskBuilder().withTaskName("Hang up clothes").withDueDate("14th April 4pm")
                    .withStatus("done").withBeginDate("14th April 12pm").build();
            interviewprep = new TaskBuilder().withTaskName("Interview preparation").withDueDate("14th April 4pm")
                    .withStatus("undone").withBeginDate("14th April 12pm").build();
            submitreport = new TaskBuilder().withTaskName("Submit FYP report").withDueDate("14th April 4pm")
                    .withStatus("undone").withBeginDate("14th April 12pm").build();
            floatone = new TaskBuilder().withTaskName("Float task one").withDueDate("-")
                    .withStatus("undone").withBeginDate("-").build();
            floattwo = new TaskBuilder().withTaskName("Float task two").withDueDate("-")
                    .withStatus("undone").withBeginDate("-").build();
            deadone = new TaskBuilder().withTaskName("Deadline task one").withDueDate("210317")
                    .withStatus("undone").withBeginDate("-").build();
            deadtwo = new TaskBuilder().withTaskName("Deadline task two").withDueDate("210417")
                    .withStatus("undone").withBeginDate("-").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTodoListWithSampleData(TodoList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { assignment, buymilk, creatework, dumpmilk, eatleftovers, findsocks, getclothes };
    }

    //@@author A0105748B
    public TestTask[] getUndoneTasks() {
        return new TestTask[] { assignment, dumpmilk, findsocks, getclothes };
    }

    public TodoList getTypicalTodoList() {
        TodoList ab = new TodoList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
