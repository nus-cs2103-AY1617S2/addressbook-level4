package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.teamstbf.yats.TestApp;
import org.teamstbf.yats.commons.core.EventsCenter;
import org.teamstbf.yats.commons.events.BaseEvent;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList.DuplicateEventException;
import org.teamstbf.yats.testutil.TestUtil;
import org.teamstbf.yats.testutil.TypicalTestEvents;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * A GUI Test class for AddressBook.
 */
public abstract class TaskManagerGuiTest {

	@BeforeClass
	public static void setupSpec() {
		try {
			FxToolkit.registerPrimaryStage();
			FxToolkit.hideStage();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	/*
	 * The TestName Rule makes the current test name available inside test
	 * methods
	 */
	@Rule
	public TestName name = new TestName();

	TestApp testApp;

	protected TypicalTestEvents td = new TypicalTestEvents();
	/*
	 * Handles to GUI elements present at the start up are created in advance
	 * for easy access from child classes.
	 */
	protected MainGuiHandle mainGui;
	protected MainMenuHandle mainMenu;
	protected TaskListPanelHandle taskListPanel;
	protected ResultDisplayHandle resultDisplay;
	protected CommandBoxHandle commandBox;
	protected BrowserPanelHandle browserPanel;

	private Stage stage;

	/**
	 * Asserts the size of the person list is equal to the given number.
	 */
	protected void assertListSize(int size) {
		int numberOfPeople = taskListPanel.getNumberOfEvent();
		assertEquals(size, numberOfPeople);
	}

	/**
	 * Asserts the person shown in the card is same as the given person
	 */
	public void assertMatching(ReadOnlyEvent person, EventCardHandle card) {
		assertTrue(TestUtil.compareCardAndEvent(card, person));
	}

	/**
	 * Asserts the message shown in the Result Display area is same as the given
	 * string.
	 */
	protected void assertResultMessage(String expected) {
		assertEquals(expected, resultDisplay.getText());
	}

	@After
	public void cleanup() throws TimeoutException {
		FxToolkit.cleanupStages();
	}

	/**
	 * Override this in child classes to set the data file location.
	 */
	protected String getDataFileLocation() {
		return TestApp.SAVE_LOCATION_FOR_TESTING;
	}

	/**
	 * Override this in child classes to set the initial local data. Return null
	 * to use the data in the file specified in {@link #getDataFileLocation()}
	 *
	 * @throws DuplicateEventException
	 */
	protected TaskManager getInitialData() {
		TaskManager ab = new TaskManager();
		TypicalTestEvents.loadAddressBookWithSampleData(ab);
		return ab;
	}

	public void raise(BaseEvent e) {
		// JUnit doesn't run its test cases on the UI thread. Platform.runLater
		// is used to post event on the UI thread.
		Platform.runLater(() -> EventsCenter.getInstance().post(e));
	}

	@Before
	public void setup() throws Exception {
		FxToolkit.setupStage((stage) -> {
			mainGui = new MainGuiHandle(new GuiRobot(), stage);
			mainMenu = mainGui.getMainMenu();
			taskListPanel = mainGui.getPersonListPanel();
			resultDisplay = mainGui.getResultDisplay();
			commandBox = mainGui.getCommandBox();
			browserPanel = mainGui.getBrowserPanel();
			this.stage = stage;
		});
		EventsCenter.clearSubscribers();
		testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
		FxToolkit.showStage();
		while (!stage.isShowing())
			;
		mainGui.focusOnMainApp();
	}
}
