package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TodoList;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;

/**
 *
 */
public class TypicalTestTodos {

    public TestTodo alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTodos() {
        try {
            alice = new TodoBuilder().withName("Alice Pauline")
                    .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@gmail.com")
                    .withPhone("85355255")
                    .withTags("friends").build();
            benson = new TodoBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TodoBuilder().withName("Carl Kurz").withPhone("95352563")
                    .withEmail("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new TodoBuilder().withName("Daniel Meier").withPhone("87652533")
                    .withEmail("cornelia@google.com").withAddress("10th street").build();
            elle = new TodoBuilder().withName("Elle Meyer").withPhone("9482224")
                    .withEmail("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new TodoBuilder().withName("Fiona Kunz").withPhone("9482427")
                    .withEmail("lydia@gmail.com").withAddress("little tokyo").build();
            george = new TodoBuilder().withName("George Best").withPhone("9482442")
                    .withEmail("anna@google.com").withAddress("4th street").build();

            // Manually added
            hoon = new TodoBuilder().withName("Hoon Meier").withPhone("8482424")
                    .withEmail("stefan@mail.com").withAddress("little india").build();
            ida = new TodoBuilder().withName("Ida Mueller").withPhone("8482131")
                    .withEmail("hans@google.com").withAddress("chicago ave").build();
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
