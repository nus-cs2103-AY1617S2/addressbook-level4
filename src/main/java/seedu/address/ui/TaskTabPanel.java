package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0135998H
public class TaskTabPanel extends UiPart<Region> {

    private static final String FXML = "TaskTabPanel.fxml";

    private ObservableList<ReadOnlyTask> taskList;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel taskListPanel;

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
    private TabPane taskTabPanel;

    /**
     * @param placeholder The AnchorPane where the taskTabPanel must be inserted
     */
    public TaskTabPanel(AnchorPane placeholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        this.taskList = taskList;
        addToPlaceholder(placeholder);
        fillTaskListPanel(allTasksListPanelPlaceholder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(taskTabPanel, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(taskTabPanel);
    }

    public void switchTabPanel(String typeOfList) {
        switch(typeOfList) {
        case ViewCommand.TYPE_DONE :
            taskTabPanel.getSelectionModel().select(doneTasksTab);
            fillTaskListPanel(doneTasksListPanelPlaceholder);
            break;
        case ViewCommand.TYPE_FLOATING :
            taskTabPanel.getSelectionModel().select(floatingTasksTab);
            fillTaskListPanel(floatingTasksListPanelPlaceholder);
            break;
        case ViewCommand.TYPE_OVERDUE :
            taskTabPanel.getSelectionModel().select(overdueTasksTab);
            fillTaskListPanel(overdueTasksListPanelPlaceholder);
            break;
        case ViewCommand.TYPE_PENDING :
            taskTabPanel.getSelectionModel().select(pendingTasksTab);
            fillTaskListPanel(pendingTasksListPanelPlaceholder);
            break;
        case ViewCommand.TYPE_TODAY :
            taskTabPanel.getSelectionModel().select(todayTasksTab);
            fillTaskListPanel(todayTasksListPanelPlaceholder);
            break;
        default :
            taskTabPanel.getSelectionModel().select(allTasksTab);
            fillTaskListPanel(allTasksListPanelPlaceholder);
            break;
        }
    }

    private void fillTaskListPanel(AnchorPane placeholder) {
        placeholder.getChildren().clear();
        taskListPanel = new TaskListPanel(placeholder, taskList);
    }

    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }

}
