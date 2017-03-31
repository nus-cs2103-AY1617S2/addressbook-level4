package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.commands.ViewCommand;

/**
 * The Browser Panel of the App.
 */
public class TaskTabPanel extends UiPart<Region> {

    private static final String FXML = "TaskTabPanel.fxml";
    public static final String ALL_TASKS_TAB = "all";
    public static final String DONE_TASKS_TAB = "done";
    public static final String FLOATING_TASKS_TAB = "floating";
    public static final String OVERDUE_TASKS_TAB = "overdue";
    public static final String PENDING_TASKS_TAB = "pending";
    public static final String TODAY_TASKS_TAB = "today";

    @FXML
    private Tab allTasksTab;

    @FXML
    private Tab doneTasksTab;

    @FXML
    private Tab floatingTasksTab;

    @FXML
    private Tab overdueTasksTab;

    @FXML
    private Tab pendingTasksTab;

    // TODO rename this
    @FXML
    private AnchorPane personListPanelPlaceholder;

    @FXML
    private static Tab todayTasksTab;

    @FXML
    private TabPane taskTabPanel;

    /**
     * @param placeholder The AnchorPane where the taskTabPanel must be inserted
     */
    public TaskTabPanel(AnchorPane placeholder) {
        super(FXML);
        addToPlaceholder(placeholder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(taskTabPanel, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(taskTabPanel);
    }

    // TODO rename typeOfList
    public void switchTabPanel(String typeOfList) {
        switch(typeOfList) {
        case ViewCommand.TYPE_DONE :
            taskTabPanel.getSelectionModel().select(doneTasksTab);
            break;
        case ViewCommand.TYPE_FLOATING :
            taskTabPanel.getSelectionModel().select(floatingTasksTab);
            break;
        case ViewCommand.TYPE_OVERDUE :
            taskTabPanel.getSelectionModel().select(overdueTasksTab);
            break;
        case ViewCommand.TYPE_PENDING :
            taskTabPanel.getSelectionModel().select(pendingTasksTab);
            break;
        case ViewCommand.TYPE_TODAY :
            taskTabPanel.getSelectionModel().select(todayTasksTab);
            break;
        default :
            taskTabPanel.getSelectionModel().select(allTasksTab);
            break;
        }
    }

    // TODO rename this
    public AnchorPane getPersonListPlaceholder() {
        return personListPanelPlaceholder;
    }

}
