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
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import onlythree.imanager.TestApp;
import onlythree.imanager.commons.core.EventsCenter;
import onlythree.imanager.commons.events.BaseEvent;
import onlythree.imanager.model.TaskList;
import onlythree.imanager.model.task.ReadOnlyTask;
import onlythree.imanager.testutil.TestUtil;
import onlythree.imanager.testutil.TypicalTestTasks;

/**
 * A GUI Test class for TaskList.
 */
public abstract class TaskListGuiTest {

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
    protected PersonListPanelHandle personListPanel;
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
            personListPanel = mainGui.getPersonListPanel();
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
    protected TaskList getInitialData() {
        TaskList taskList = new TaskList();
        TypicalTestTasks.loadTaskListWithSampleData(taskList);
        return taskList;
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
    public void assertMatching(ReadOnlyTask task, PersonCardHandle card) {
        assertTrue(TestUtil.compareCardAndPerson(card, task)); // TODO UI renaming
    }

    /**
     * Asserts the size of the person list is equal to the given number. // TODO UI renaming
     */
    protected void assertListSize(int size) {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        // TODO Strange regression where expected contains "\r\n" as line separator while
        // resultDisplay.getText() only uses "\n" as line separator. Could it be that JavaFX
        // TextArea automatically convert "\r\n" to "\n"?
        assertEquals(expected.replaceAll("\\r\\n", "\n"), resultDisplay.getText().replaceAll("\\r\\n", "\n"));
    }

    public void raise(BaseEvent e) {
        //JUnit doesn't run its test cases on the UI thread. Platform.runLater is used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }
}
