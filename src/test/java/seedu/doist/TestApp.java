package seedu.doist;

import java.util.function.Supplier;

import javafx.stage.Screen;
import seedu.doist.commons.core.Config;
import seedu.doist.commons.core.GuiSettings;
import seedu.doist.logic.LogicManager;
import seedu.doist.model.Model;
import seedu.doist.model.ModelManager;
import seedu.doist.model.ReadOnlyAliasListMap;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.UserPrefs;
import seedu.doist.storage.Storage;
import seedu.doist.storage.StorageManager;
import seedu.doist.storage.XmlSerializableTodoList;
import seedu.doist.testutil.TestUtil;
import seedu.doist.ui.UiManager;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    public static final String APP_TITLE = "Test App";
    protected static final String ADDRESS_BOOK_NAME = "Test";
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
    public void init() throws Exception {
        LOGGER.info("=========================[ Initializing " + APP_TITLE + " ]=======================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getTodoListFilePath(), config.getAliasListMapFilePath(),
                                        config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs, config);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs, Config config) {
        ReadOnlyTodoList initialData = initTodoListData(storage);
        ReadOnlyAliasListMap initialAliasData = initAliasListMapData(storage);

        // isTest is set to true so that tests don't have to consider auto sorting of tasks to pass
        return new ModelManager(initialData, initialAliasData, userPrefs, config, true);
    }

    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setTodoListFilePath(saveFileLocation);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        config.setTodoListName(ADDRESS_BOOK_NAME);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(Config config) {
        UserPrefs userPrefs = super.initPrefs(config);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(800.0, 1000.0, (int) x, (int) y));
        return userPrefs;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
