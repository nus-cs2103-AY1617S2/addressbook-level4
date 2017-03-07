package seedu.toluist;

import java.util.function.Supplier;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.controller.Controller;
import seedu.toluist.model.TodoList;
import seedu.toluist.storage.JsonStorage;
import seedu.toluist.storage.Storage;
import seedu.toluist.testutil.TestUtil;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {
    public static final String APP_TITLE = "Test App";
    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("todolist.json");
    protected Supplier<TodoList> initialDataSupplier = () -> null;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp(Supplier<TodoList> initialDataSupplier, String saveFileLocation) {
        super();

        LogsCenter.getLogger(getClass()).info("EST APP EST APP EST APPP TEST APP ...... ");
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;
        LogsCenter.getLogger(getClass()).info("initial data supplier ...... " + initialDataSupplier.get().toString());
        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            LogsCenter.getLogger(getClass()).info("seting save for app ...... ");
            Storage storage = new JsonStorage(saveFileLocation);
            TodoList.setStorage(storage);
            TodoList todoList = initialDataSupplier.get();
            todoList.save();
            Controller.setStorage(storage);
        }
    }
}
