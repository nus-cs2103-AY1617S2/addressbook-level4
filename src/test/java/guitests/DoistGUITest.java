package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.doist.TestApp;
import seedu.doist.commons.core.EventsCenter;
import seedu.doist.commons.events.BaseEvent;
import seedu.doist.logic.commands.SortCommand.SortType;
import seedu.doist.model.TodoList;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskAlphabetComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskCombinedComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskFinishedStatusComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskPriorityComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskTimingComparator;
import seedu.doist.testutil.TestUtil;
import seedu.doist.testutil.TypicalTestTasks;
import seedu.doist.ui.util.CommandHighlightManager;

/**
 * A GUI Test class for Doist
 */
public abstract class DoistGUITest {

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
            taskListPanel = mainGui.getPersonListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            browserPanel = mainGui.getBrowserPanel();
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
    protected TodoList getInitialData() {
        TodoList ab = new TodoList();
        TypicalTestTasks.loadDoistWithSampleData(ab);
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
     * Asserts the person shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyTask task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndPerson(card, task));
    }

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfPeople = taskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfPeople);
    }

    //@@author A0147980U
    protected void assertCorrectSuggestions(String lastWord) {
        List<String> contentAssistItemTexts = commandBox.getContentAssistItemTexts();
        if (contentAssistItemTexts.isEmpty()) {
            assertTrue(!commandBox.getContentAssistWindow().isShowing());
        } else {
            for (String text : contentAssistItemTexts) {
                assertTrue(text.contains((CharSequence) lastWord.subSequence(0, lastWord.length())));
            }
        }
    }

    protected void assertCorrectHighlight() {
        List<String> wordsInKeyStyle =
                commandBox.getWordListWithStyle(CommandHighlightManager.PARAMETER_KEY_STYLE);
        for (String word : wordsInKeyStyle) {
            assertTrue(word.startsWith("\\"));
        }

        List<String> wordsInCommandWordStyle =
                commandBox.getWordListWithStyle(CommandHighlightManager.COMMAND_WORD_STYLE);
        assertTrue(wordsInCommandWordStyle.size() <= 1);
        if (wordsInCommandWordStyle.size() == 1) {
            String firstWord = commandBox.getCommandInput().split(" +")[0];
            assertTrue(wordsInCommandWordStyle.get(0).equals(firstWord));
        }
    }
    //@@author

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

    //@@author A0140887W
    public static void sortTasks(List<SortType> sortTypes, ReadOnlyTask[] tasks) {
        List<Comparator<ReadOnlyTask>> comparatorList = new ArrayList<Comparator<ReadOnlyTask>>();
        // Finished tasks are always put at the bottom
        comparatorList.add(new ReadOnlyTaskFinishedStatusComparator());
        for (SortType type : sortTypes) {
            if (type.equals(SortType.PRIORITY)) {
                comparatorList.add(new ReadOnlyTaskPriorityComparator());
            } else if (type.equals(SortType.TIME)) {
                comparatorList.add(new ReadOnlyTaskTimingComparator());
            } else if (type.equals(SortType.ALPHA)) {
                comparatorList.add(new ReadOnlyTaskAlphabetComparator());
            }
        }
        Arrays.sort(tasks, new ReadOnlyTaskCombinedComparator(comparatorList));
    }

    /**
     * Sorts the given tasks by priority, time then alpha. Finished tasks are always put at the bottom
     */
    public static void sortTasksByDefault(ReadOnlyTask[] tasks) {
        List<SortType> sortTypes = new ArrayList<SortType>();
        sortTypes.add(SortType.TIME);
        sortTypes.add(SortType.PRIORITY);
        sortTypes.add(SortType.ALPHA);
        sortTasks(sortTypes, tasks);
    }
}
