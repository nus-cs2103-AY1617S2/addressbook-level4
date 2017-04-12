package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.todolist.TestApp;
import seedu.todolist.model.todo.ReadOnlyTodo;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the todo list.
 */
public class TodoListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TODO_LIST_VIEW_ID = "#todoListView";

    public TodoListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTodo> getSelectedTodos() {
        ListView<ReadOnlyTodo> todoList = getListView();
        return todoList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTodo> getListView() {
        return getNode(TODO_LIST_VIEW_ID);
    }

    /**
     * @param compareCompleteTime true to use complete time in match comparison
     * @param todos A list of todo in the correct order.
     */
    public boolean isListMatching(boolean compareCompleteTime, ReadOnlyTodo... todos) {
        return this.isListMatching(0, compareCompleteTime, todos);
    }

    /**
     * Returns true if the list is showing the todo details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param compareCompleteTime True to use complete time in match comparison
     * @param todos A list of todo in the correct order.
     */
    public boolean isListMatching(int startPosition, boolean compareCompleteTime,
            ReadOnlyTodo... todos) throws IllegalArgumentException {
        if (todos.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " todos");
        }
        assertTrue(this.containsInOrder(startPosition, todos));
        for (int i = 0; i < todos.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTodo(getTodoCardHandle(startPosition + i), todos[i], compareCompleteTime)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code todos} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTodo... todos) {
        List<ReadOnlyTodo> todosInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + todos.length > todosInList.size()) {
            return false;
        }

        // Return false if any of the todos doesn't match
        for (int i = 0; i < todos.length; i++) {
            if (!todosInList.get(startPosition + i).getName().fullName.equals(todos[i].getName().fullName)) {
                return false;
            }
        }

        return true;
    }

    public TodoCardHandle navigateToTodo(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTodo> todo = getListView().getItems().stream()
                                                    .filter(p -> p.getName().fullName.equals(name))
                                                    .findAny();
        if (!todo.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToTodo(todo.get());
    }

    /**
     * Navigates the listview to display and select the todo.
     */
    public TodoCardHandle navigateToTodo(ReadOnlyTodo todo) {
        int index = getTodoIndex(todo);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTodoCardHandle(todo);
    }


    /**
     * Returns the position of the todo given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTodoIndex(ReadOnlyTodo targetTodo) {
        List<ReadOnlyTodo> todosInList = getListView().getItems();
        for (int i = 0; i < todosInList.size(); i++) {
            if (todosInList.get(i).getName().equals(targetTodo.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a todo from the list by index
     */
    public ReadOnlyTodo getTodo(int index) {
        return getListView().getItems().get(index);
    }

    public TodoCardHandle getTodoCardHandle(int index) {
        return getTodoCardHandle(new Todo(getListView().getItems().get(index)));
    }

    public TodoCardHandle getTodoCardHandle(ReadOnlyTodo todo) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> todoCardNode = nodes.stream()
                .filter(n -> new TodoCardHandle(guiRobot, primaryStage, n).isSameTodo(todo))
                .findFirst();
        if (todoCardNode.isPresent()) {
            return new TodoCardHandle(guiRobot, primaryStage, todoCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTodos() {
        return getListView().getItems().size();
    }
}
