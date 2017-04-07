package seedu.taskmanager.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.taskmanager.commons.util.FxViewUtil;
import seedu.taskmanager.model.task.ReadOnlyTask;

    public class OverdueTaskListPanel extends UiPart<Region> {
	private final Logger logger = LogsCenter.getLogger(OverdueTaskListPanel.class);
    private static final String FXML = "OverdueTaskListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> OverdueTaskListView;

    public OverdueTaskListPanel(AnchorPane overdueTaskListPlaceholder, ObservableList<ReadOnlyTask> overdueTaskList) {
        super(FXML);
        setConnections(overdueTaskList);
        addToPlaceholder(overdueTaskListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> overdueTaskList) {
        OverdueTaskListView.setItems(overdueTaskList);
        OverdueTaskListView.setCellFactory(listView -> new OverdueTaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        OverdueTaskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            OverdueTaskListView.scrollTo(index);
            OverdueTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class OverdueTaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }
}
