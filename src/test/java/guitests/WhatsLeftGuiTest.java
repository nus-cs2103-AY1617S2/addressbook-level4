package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.ActivityListPanelHandle;

//import guitests.guihandles.ActivityCardHandle;
//import guitests.guihandles.ActivityListPanelHandle;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestActivities;
import seedu.address.testutil.TypicalTestEvents;
import seedu.address.testutil.TypicalTestTasks;

/**
 * A GUI Test class for WhatsLeft.
 */
public abstract class WhatsLeftGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestActivities td = new TypicalTestActivities();
    protected TypicalTestEvents te = new TypicalTestEvents();
    protected TypicalTestTasks tt = new TypicalTestTasks();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected ActivityListPanelHandle activityListPanel;
    protected EventListPanelHandle eventListPanel;
    protected TaskListPanelHandle taskListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    protected BrowserPanelHandle browserPanel;
    private Stage stage;

    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            mainMenu = mainGui.getMainMenu();
            activityListPanel = mainGui.getActivityListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected WhatsLeft getInitialData() {
        WhatsLeft ab = new WhatsLeft();
        TypicalTestActivities.loadWhatsLeftWithSampleData(ab);
        return ab;
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the event shown in the card is same as the given activity
     */
    public void assertMatchingEvent(ReadOnlyEvent event, EventCardHandle card) {
        assertTrue(TestUtil.compareCardAndEvent(card, event));
    }

    /**
     * Asserts the task shown in the card is same as the given activity
     */
    public void assertMatchingTask(ReadOnlyTask task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
     * Asserts the size of the event list is equal to the given number.
     */
    protected void assertEventListSize(int size) {
        int numberOfEvent = eventListPanel.getNumberOfEvent();
        assertEquals(size, numberOfEvent);
    }

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertTaskListSize(int size) {
        int numberOfTask = taskListPanel.getNumberOfTask();
        assertEquals(size, numberOfTask);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }

    public void raise(BaseEvent e) {
        //JUnit doesn't run its test cases on the UI thread. Platform.runLater is used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }
}
