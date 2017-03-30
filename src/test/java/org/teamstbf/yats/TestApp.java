package org.teamstbf.yats;

import java.util.function.Supplier;

import org.teamstbf.yats.commons.core.Config;
import org.teamstbf.yats.commons.core.Config.DuplicateFileException;
import org.teamstbf.yats.commons.core.GuiSettings;
import org.teamstbf.yats.model.ReadOnlyTaskManager;
import org.teamstbf.yats.model.UserPrefs;
import org.teamstbf.yats.storage.XmlSerializableTaskManager;
import org.teamstbf.yats.testutil.TestUtil;

import javafx.stage.Screen;
import javafx.stage.Stage;

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
	protected Supplier<ReadOnlyTaskManager> initialDataSupplier = () -> null;
	protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

	public TestApp() {
	}

	public TestApp(Supplier<ReadOnlyTaskManager> initialDataSupplier, String saveFileLocation) {
		super();
		this.initialDataSupplier = initialDataSupplier;
		this.saveFileLocation = saveFileLocation;

		// If some initial local data has been provided, write those to the file
		if (initialDataSupplier.get() != null) {
			TestUtil.createDataFileWithData(
					new XmlSerializableTaskManager(this.initialDataSupplier.get()),
					this.saveFileLocation);
		}
	}

	@Override
	protected Config initConfig(String configFilePath) {
		Config config = super.initConfig(configFilePath);
		config.setAppTitle(APP_TITLE);
		try {
			config.setTaskManagerFilePath(saveFileLocation);
		} catch (DuplicateFileException e) {
			e.printStackTrace();
		}
		config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
		config.setTaskManagerName(ADDRESS_BOOK_NAME);
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
