package guitests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskListHandle;

import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.toluist.TestApp;
import seedu.toluist.commons.core.EventsCenter;
import seedu.toluist.commons.events.BaseEvent;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui Test class
 */
public abstract class ToLuistGuiTest {

    // The TestName Rule makes the current test name available inside test methods.
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    // Handles to GUI elements present at the start up are created in advance for easy access from child classes.
    protected MainGuiHandle mainGui;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    protected TaskListHandle taskList;

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
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            taskList = mainGui.getTaskList();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        // Sleep for 1 second to allow initial data to be loaded
        TimeUnit.SECONDS.sleep(1);
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected TodoList getInitialData() {
        TodoList todoList = new TypicalTestTodoLists().getTypicalTodoList();
        return todoList;
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

    public void raise(BaseEvent e) {
        //JUnit doesn't run its test cases on the UI thread. Platform.runLater is used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(resultDisplay.getText(), expected);
    }

    /**
     * Check if a task is shown in the TaskList
     */
    protected boolean isTaskShown(Task task) {
        boolean taskIsPresent = taskList.getTaskList().getItems().stream()
                .filter(t -> t.equals(task))
                .findFirst()
                .isPresent();
        return taskIsPresent;
    }

    /**
     * Check if tasks are shown in the TaskList. Returns true only if all tasks are shown.
     */
    protected boolean areTasksShown(Task... tasks) {
        for (Task task : tasks) {
            if (!isTaskShown(task)) {
                return false;
            }
        }
        return true;
    }
}
