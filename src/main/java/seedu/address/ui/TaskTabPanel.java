package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0135998H
/**
 * The Browser Panel of the App.
 */
public class TaskTabPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskTabPanel.class);
    private static final String FXML = "TaskTabPanel.fxml";

    private final Logic logic;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel taskListPanel;

    @FXML
    private AnchorPane currTaskListPlaceHolder;

    @FXML
    private Tab allTasksTab;

    @FXML
    private AnchorPane allTasksListPanelPlaceholder;

    @FXML
    private Tab doneTasksTab;

    @FXML
    private AnchorPane doneTasksListPanelPlaceholder;

    @FXML
    private Tab floatingTasksTab;

    @FXML
    private AnchorPane floatingTasksListPanelPlaceholder;

    @FXML
    private Tab overdueTasksTab;

    @FXML
    private AnchorPane overdueTasksListPanelPlaceholder;

    @FXML
    private Tab pendingTasksTab;

    @FXML
    private AnchorPane pendingTasksListPanelPlaceholder;

    @FXML
    private Tab todayTasksTab;

    @FXML
    private AnchorPane todayTasksListPanelPlaceholder;

    @FXML
    private TabPane taskTabPane;

    /**
     * @param placeholder The AnchorPane where the taskTabPanel must be inserted
     */
    public TaskTabPanel(AnchorPane placeholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(placeholder);
        fillTaskListPanel(allTasksListPanelPlaceholder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getTaskTabPane(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(taskTabPane);
    }

    private void fillTaskListPanel(AnchorPane placeholder) {
        if (currTaskListPlaceHolder != null) {
            currTaskListPlaceHolder.getChildren().clear();
        }
        taskListPanel = new TaskListPanel(placeholder, logic.getFilteredPersonList());
        currTaskListPlaceHolder = placeholder;
    }

    public TabPane getTaskTabPane() {
        return taskTabPane;
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    public void switchTabOnCommand(String typeOfList) {
        switch(typeOfList) {
        case ViewCommand.TYPE_ALL :
            getTaskTabPane().getSelectionModel().select(allTasksTab);
            fillTaskListPanel(allTasksListPanelPlaceholder);
            break;
        case ViewCommand.TYPE_DONE :
            getTaskTabPane().getSelectionModel().select(doneTasksTab);
            fillTaskListPanel(doneTasksListPanelPlaceholder);
            break;
        case ViewCommand.TYPE_FLOATING :
            getTaskTabPane().getSelectionModel().select(floatingTasksTab);
            fillTaskListPanel(floatingTasksListPanelPlaceholder);
            break;
        case ViewCommand.TYPE_OVERDUE :
            getTaskTabPane().getSelectionModel().select(overdueTasksTab);
            fillTaskListPanel(overdueTasksListPanelPlaceholder);
            break;
        case ViewCommand.TYPE_PENDING :
            getTaskTabPane().getSelectionModel().select(pendingTasksTab);
            break;
        case ViewCommand.TYPE_TODAY :
            getTaskTabPane().getSelectionModel().select(todayTasksTab);
            fillTaskListPanel(todayTasksListPanelPlaceholder);
            break;
        default :
            getTaskTabPane().getSelectionModel().select(allTasksTab);
            fillTaskListPanel(allTasksListPanelPlaceholder);
            break;
        }
    }

    public void switchTabOnClick() {
        try {
            CommandResult commandResult;

            Tab currTaskTab = getTaskTabPane().getSelectionModel().getSelectedItem();
            if (currTaskTab.getId().equals(allTasksTab.getId())) {
                commandResult = logic.execute(ViewCommand.COMMAND_WORD + " " + ViewCommand.TYPE_ALL);
            } else if (currTaskTab.getId().equals(floatingTasksTab.getId())) {
                commandResult = logic.execute(ViewCommand.COMMAND_WORD + " " + ViewCommand.TYPE_FLOATING);
            } else if (currTaskTab.getId().equals(overdueTasksTab.getId())) {
                commandResult = logic.execute(ViewCommand.COMMAND_WORD + " " + ViewCommand.TYPE_OVERDUE);
            } else if (currTaskTab.getId().equals(todayTasksTab.getId())) {
                commandResult = logic.execute(ViewCommand.COMMAND_WORD + " " + ViewCommand.TYPE_TODAY);
            } else {
                commandResult = logic.execute(ViewCommand.COMMAND_WORD + " " + ViewCommand.TYPE_ALL);
            }
            logger.info(commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            logger.info("Unable to switch tab.");
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

}
