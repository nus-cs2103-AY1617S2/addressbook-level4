package seedu.todolist;

import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.todolist.commons.core.Config;
import seedu.todolist.commons.core.GuiSettings;
import seedu.todolist.model.ReadOnlyTodoList;
import seedu.todolist.model.UserPrefs;
import seedu.todolist.storage.XmlSerializableTodoList;
import seedu.todolist.testutil.TestUtil;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    public static final String APP_TITLE = "Test app";
    protected static final String TODO_LIST_NAME = "Test";
    protected Supplier<ReadOnlyTodoList> initialDataSupplier = () -> null;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyTodoList> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            TestUtil.createDataFileWithData(
                    new XmlSerializableTodoList(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
    }

    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setTodoListFilePath(saveFileLocation);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        config.setTodoListName(TODO_LIST_NAME);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(Config config) {
        UserPrefs userPrefs = super.initPrefs(config);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        return userPrefs;
    }


    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
