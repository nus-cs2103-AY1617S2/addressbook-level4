package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TodoList;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;

/**
 *
 */
public class TypicalTestTodos {
    public TestTodo dog, cat, math, english, dishes, lawn, dinner, essay, toilet,
                    car, library, tennis, shopping, laundry, job, lunch;

    //@@author A0163786N
    public TypicalTestTodos() {
        try {
            dog = new TodoBuilder().withName("Walk the dog")
                    .withTags("petcare").build();
            cat = new TodoBuilder().withName("Walk the cat")
                    .withTags("petcare", "cat").build();
            math = new TodoBuilder().withName("Do math homework").withTags("homework").build();
            english = new TodoBuilder().withName("Do english homework").build();
            dishes = new TodoBuilder().withName("Wash dishes").build();
            lawn = new TodoBuilder().withName("Mow the lawn").build();
            dinner = new TodoBuilder().withName("Cook dinner").build();
            essay = new TodoBuilder().withName("Write essay").withEndTime("6:00PM 11/11/2017").build();
            toilet = new TodoBuilder().withName("Go to the bathroom").withStartTime("12:00PM 11/11/2017")
                    .withEndTime("1:00PM 11/11/2017").build();
            car = new TodoBuilder().withName("Wash car").withCompleteTime("6:00PM 11/11/2017").build();
            library = new TodoBuilder().withName("Return library book").withEndTime("6:00PM 11/11/2017")
                    .withCompleteTime("6:00PM 11/11/2017").build();
            tennis = new TodoBuilder().withName("Play tennis").withStartTime("12:00PM 11/11/2017")
                    .withEndTime("1:00PM 11/11/2017").withCompleteTime("6:00PM 11/11/2017").build();
            // Manually added
            shopping = new TodoBuilder().withName("Go shopping").build();
            laundry = new TodoBuilder().withName("Do laundry").build();
            job = new TodoBuilder().withName("Apply to job").withEndTime("6:00PM 11/11/2017").build();
            lunch = new TodoBuilder().withName("lunch").withStartTime("12:00PM 11/11/2017")
                    .withEndTime("1:00PM 11/11/2017").build();
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
        return new TestTodo[] { dog, cat, math, english, dishes, lawn, dinner, essay, toilet, car, library, tennis};
    }

    public TodoList getTypicalTodoList() {
        TodoList ab = new TodoList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
