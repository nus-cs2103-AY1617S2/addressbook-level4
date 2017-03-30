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
            interviewprep;

    public TypicalTestTasks() {
        try {
            assignment = new TaskBuilder().withTaskName("Assignment for CS2103")
                    .withBeginDate("123, Jurong West Ave 6, #08-111").withStatus("undone")
                    .withDueDate("85355255").withTags("friends").build();
            buymilk = new TaskBuilder().withTaskName("Buy milk").withBeginDate("311, Clementi Ave 2, #02-25")
                    .withStatus("done").withDueDate("98765432").withTags("owesMoney", "friends").build();
            creatework = new TaskBuilder().withTaskName("Create more work").withDueDate("95352563")
                    .withStatus("done").withBeginDate("wall street").build();
            dumpmilk = new TaskBuilder().withTaskName("Dump milk").withDueDate("87652533")
                    .withStatus("undone").withBeginDate("10th street").build();
            eatleftovers = new TaskBuilder().withTaskName("Eat leftovers").withDueDate("9482224")
                    .withStatus("done").withBeginDate("michegan ave").build();
            findsocks = new TaskBuilder().withTaskName("Find socks").withDueDate("9482427")
                    .withStatus("undone").withBeginDate("little tokyo").build();
            getclothes = new TaskBuilder().withTaskName("Get clothes").withDueDate("9482442")
                    .withStatus("undone").withBeginDate("4th street").build();

            // Manually added
            hangclothes = new TaskBuilder().withTaskName("Hang up clothes").withDueDate("8482424")
                    .withStatus("done").withBeginDate("little india").build();
            interviewprep = new TaskBuilder().withTaskName("Interview preparation").withDueDate("8482131")
                    .withStatus("undone").withBeginDate("chicago ave").build();
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

    public TodoList getTypicalTodoList() {
        TodoList ab = new TodoList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
