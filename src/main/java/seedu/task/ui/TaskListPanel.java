package seedu.task.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    protected Theme theme = Theme.Default;
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanelDefault.fxml";
    protected static final String FXML_LIGHT = "TaskListPanelLight.fxml";
    protected static final String FXML_DARK = "TaskListPanelDark.fxml";

    private TaskCard[] cardlist = new TaskCard[1000];

    @FXML
    private ListView<ReadOnlyTask> taskListView;


    // @@author A0142487Y-reused
    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList, Theme theme,
            String... fxml) {
        super(fxml.length == 0 ? FXML : fxml[0]);
        this.theme = theme;
        setConnections(taskList);
        addToPlaceholder(taskListPlaceholder);
    }

    // @@author
    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());

        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    // @@author A0139975J
    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index - 3);
            taskListView.getSelectionModel().clearAndSelect(index);
            if (cardlist[index + 1].expendStatus()) {
                cardlist[index + 1].setExpend(false);

            } else {
                cardlist[index + 1].setExpend(true);
            }
        });
    }

    // @@author A0142939W
    public void scrollDown(Scroll scroll) {
//        scroll = new Scroll();
//        scroll.scrollDown(taskListView);
        new Scroll().scrollDown(taskListView);
    }

    // @@author A0142939W
    public void scrollUp(Scroll scroll) {
        new Scroll().scrollUp(taskListView);
//        scroll = new Scroll();
//        scroll.scrollUp(taskListView);
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {
        // @@author A0139975J-reused
        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                TaskCard taskcard = null;
                switch (TaskListPanel.this.theme) {
                case Dark:
                    taskcard = new TaskCard(task, getIndex() + 1, TaskCard.FXML_DARK);
                    break;
                case Light:
                    taskcard = new TaskCard(task, getIndex() + 1, TaskCard.FXML_LIGHT);
                    break;
                default:
                    taskcard = new TaskCard(task, getIndex() + 1);
                }
                setGraphic(taskcard.getRoot());
                cardlist[getIndex() + 1] = (taskcard);

            }
        }
    }

    public ListView<ReadOnlyTask> getTaskListView() {
        return taskListView;
    }

}
