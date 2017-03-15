package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TodoList;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;

/**
 *
 */
public class TypicalTestTodos {

    public TestTodo alice, benson, carl, daniel, elle, fiona, george, eventTest, deadLineTest, hoon, ida;

    public TypicalTestTodos() {
        try {
            alice = new TodoBuilder().withName("Alice Pauline")
                    .withTags("friends").build();
            benson = new TodoBuilder().withName("Benson Meier")
                    .withTags("owesMoney", "friends").build();
            carl = new TodoBuilder().withName("Carl Kurz").build();
            daniel = new TodoBuilder().withName("Daniel Meier").build();
            elle = new TodoBuilder().withName("Elle Meyer").build();
            fiona = new TodoBuilder().withName("Fiona Kunz").build();
            george = new TodoBuilder().withName("George Best").build();
            // Manually added
            hoon = new TodoBuilder().withName("Hoon Meier").build();
            ida = new TodoBuilder().withName("Ida Mueller").build();
            eventTest = new TodoBuilder().withName("eventTest").withStartTime("11-11-17T5:00")
                    .withEndTime("11-11-17T6:00").build();
            deadLineTest = new TodoBuilder().withName("deadLinetTest").withEndTime("11-11-17T6:00").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTodoListWithSampleData(TodoList ab) {
        for (TestTodo todo : new TypicalTestTodos().getTypicalTodos()) {
            try {
                ab.addTodo(new Todo(todo));
            } catch (UniqueTodoList.DuplicateTodoException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTodo[] getTypicalTodos() {
        return new TestTodo[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TodoList getTypicalTodoList() {
        TodoList ab = new TodoList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
