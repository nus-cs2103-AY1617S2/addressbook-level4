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

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.DeadlineTaskListPanelHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.EventTaskListPanelHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.FloatingTaskListPanelHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.taskmanager.TestApp;
import seedu.taskmanager.commons.core.EventsCenter;
import seedu.taskmanager.commons.events.BaseEvent;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.testutil.TestUtil;
import seedu.taskmanager.testutil.TypicalTestTasks;

/**
 * A GUI Test class for AddressBook.
 */
public abstract class TaskManagerGuiTest {

    // @@author A0141102H
    /*
     * The TestName Rule makes the current test name available inside test
     * methods
     */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTasks td = new TypicalTestTasks();

    /*
     * Handles to GUI elements present at the start up are created in advance
     * for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected EventTaskListPanelHandle eventTaskListPanel;
    protected DeadlineTaskListPanelHandle deadlineTaskListPanel;
    protected FloatingTaskListPanelHandle floatingTaskListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
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
            eventTaskListPanel = mainGui.getEventTaskListPanel();
            deadlineTaskListPanel = mainGui.getDeadlineTaskListPanel();
            floatingTaskListPanel = mainGui.getFloatingTaskListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
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
        TaskManager tm = new TaskManager();
        TypicalTestTasks.loadTaskManagerWithSampleData(tm);
        return tm;
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
    public void assertMatching(ReadOnlyTask task, EventTaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyTask task, DeadlineTaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyTask task, FloatingTaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfTask = eventTaskListPanel.getNumberOfTask() + deadlineTaskListPanel.getNumberOfTask()
        + floatingTaskListPanel.getNumberOfTask();
        assertEquals(size, numberOfTask);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given
     * string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }

    public void raise(BaseEvent e) {
        // JUnit doesn't run its test cases on the UI thread. Platform.runLater
        // is used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }
}
