package seedu.toluist;

import java.util.function.Supplier;

import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TestUtil;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {
    public static final String APP_TITLE = "Test App";
    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("todolist.json");
    public static final String CONFIG_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("preferences.json");
    protected Supplier<TodoList> initialDataSupplier = () -> null;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp(Supplier<TodoList> initialDataSupplier, String configLocation, String saveFileLocation) {
        super();

        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;
        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            TodoList todoList = initialDataSupplier.get();
            TestUtil.setTodoListTestData(todoList, configLocation, saveFileLocation);
        }
    }
}
