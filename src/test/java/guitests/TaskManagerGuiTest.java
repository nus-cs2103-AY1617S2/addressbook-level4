package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.CompletedTaskListHandle;
import guitests.guihandles.FutureTaskListPanelHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TodayTaskListPanelHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import t09b1.today.TestApp;
import t09b1.today.commons.core.EventsCenter;
import t09b1.today.commons.core.LogsCenter;
import t09b1.today.commons.events.BaseEvent;
import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.logic.Logic;
import t09b1.today.model.TaskManager;
import t09b1.today.model.task.ReadOnlyTask;
import t09b1.today.model.task.Task;
import t09b1.today.testutil.TestUtil;
import t09b1.today.testutil.TypicalTasks;

/**
 * A GUI Test class for TaskManager.
 */
public abstract class TaskManagerGuiTest {
    private static final Logger logger = LogsCenter.getLogger(TaskManagerGuiTest.class);

    /*
     * The TestName Rule makes the current test name available inside test
     * methods
     */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    // @author A0093999Y
    protected TypicalTasks td = new TypicalTasks();
    protected Task[] emptyTaskList = new Task[] {};
    protected Task[] todayList = td.getTodayListTasks();
    protected Task[] futureList = td.getFutureListTasks();
    protected Task[] completedList = td.getCompletedListTasks();

    // @author
    /*
     * Handles to GUI elements present at the start up are created in advance
     * for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TodayTaskListPanelHandle todayTaskListPanel;
    protected FutureTaskListPanelHandle futureTaskListPanel;
    protected CompletedTaskListHandle completedTaskListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    protected BrowserPanelHandle browserPanel;
    private Stage stage;
    protected Logic logic;

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
            futureTaskListPanel = mainGui.getFutureTaskListPanel();
            todayTaskListPanel = mainGui.getTodayTaskListPanel();
            completedTaskListPanel = mainGui.getCompletedTaskList();
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

    /**
     * Override this in child classes to set the initial local data. Return null
     * to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected TaskManager getInitialData() {
        TaskManager ab = new TaskManager();
        TypicalTasks.loadTaskManagerWithSampleData(ab);
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
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyTask task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertFutureListSize(int size) {
        int numberOfTasks = futureTaskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }

    // @author A0093999Y
    /**
     * Asserts the UI Today and Future Task Lists match the given expected lists
     */
    protected void assertTodayFutureListsMatching(Task[] todayList, Task[] futureList) throws IllegalValueException {
        assertTrue(todayTaskListPanel.isListMatching(todayList));
        assertTrue(futureTaskListPanel.isListMatching(futureList));
    }

    /**
     * Asserts the All UI Task Lists match the given expected lists
     */
    protected void assertAllListsMatching(Task[] todayList, Task[] futureList, Task[] completedList)
            throws IllegalValueException {
        assertTodayFutureListsMatching(todayList, futureList);
        commandBox.runCommand("listcompleted");
        assertTrue(completedTaskListPanel.isListMatching(completedList));
        commandBox.runCommand("list");
    }

    // @author
    /**
     * Asserts the message shown in the Result Display area is same as the given
     * string.
     */
    protected void assertResultMessage(String expected) {
        logger.info("Expected:" + expected);
        logger.info("Actual:" + resultDisplay.getText());
        assertEquals(expected, resultDisplay.getText());
    }

    public void raise(BaseEvent e) {
        // JUnit doesn't run its test cases on the UI thread. Platform.runLater
        // is used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }
}
