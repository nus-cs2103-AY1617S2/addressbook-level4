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
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.jobs.TestApp;
import seedu.jobs.commons.core.EventsCenter;
import seedu.jobs.commons.events.BaseEvent;
import seedu.jobs.model.TaskBook;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.testutil.TestUtil;
import seedu.jobs.testutil.TypicalTestTasks;

/**
 * A GUI Test class for JOBS.
 */
public abstract class TaskBookGuiTest {

    public static final int SLEEP = 0;

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTasks td = new TypicalTestTasks();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TaskListPanelHandle taskListPanel;
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
            taskListPanel = mainGui.getTaskListPanel();
            resultDisplay = mainGui.getResultDisplay();
            try {
                commandBox = mainGui.getCommandBox();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData,
getDataFileLocation()));
        FxToolkit.showStage();
        try {
            Thread.sleep(20 * SLEEP); //wait for the browser to finish loading
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     * @throws IllegalTimeException
     */
    protected TaskBook getInitialData() {
        try {
            TaskBook ab = new TaskBook();
            TypicalTestTasks.loadTaskBookWithSampleData(ab);
            return ab;
        } catch (IllegalTimeException e) {
            assert false;
            //not possible
            return null;
        }
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
    protected void assertListSize(int size) {
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
