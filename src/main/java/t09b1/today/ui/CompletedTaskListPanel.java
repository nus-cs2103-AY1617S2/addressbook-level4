package t09b1.today.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import t09b1.today.commons.core.LogsCenter;
import t09b1.today.commons.events.ui.ShowCompletedTaskEvent;
import t09b1.today.commons.util.FxViewUtil;
import t09b1.today.model.task.ReadOnlyTask;

/**
 * Controller for the completed task list
 */
public class CompletedTaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "CompletedTaskListPanel.fxml";

    // Init parameters for completed panel animation
    private static double completedPanelHeight = 400.0d;
    private boolean flag = false;

    @FXML
    private ListView<ReadOnlyTask> completedTaskListView;

    @FXML
    private TitledPane completedTaskListTitle;

    // @@author A0144315N
    public CompletedTaskListPanel(AnchorPane completedTaskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        addToPlaceholder(completedTaskListPlaceholder);
        registerAsAnEventHandler(this);
        completedTaskListView.toFront();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        completedTaskListView.setItems(taskList);
        completedTaskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    // @@author
    private void addToPlaceholder(AnchorPane placeHolderPane) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        completedTaskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in completed task list panel changed to : '" + newValue + "'");
                        // TODO: create a new event
                        // raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            completedTaskListView.scrollTo(index);
            completedTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, task.getID()).getRoot());
            }
        }
    }

    // @@author A0144315N
    public void display(boolean show) {
        if (show) {
            // show completed task list panel
            completedTaskListView.setPrefHeight(0.0d);
            flag = true;

            Timeline timeline = new Timeline();
            timeline.getKeyFrames()
                    .addAll(new KeyFrame(Duration.ZERO, new KeyValue(completedTaskListView.prefHeightProperty(), 0),
                            new KeyValue(completedTaskListTitle.visibleProperty(), false),
                            new KeyValue(completedTaskListView.opacityProperty(), 0.8d)),
                            new KeyFrame(Duration.millis(300.0d),
                                    new KeyValue(completedTaskListView.prefHeightProperty(), completedPanelHeight),
                                    new KeyValue(completedTaskListTitle.visibleProperty(), true),
                                    new KeyValue(completedTaskListView.opacityProperty(), 1)));
            timeline.play();
        } else {
            // collapse
            Timeline timeline = new Timeline();
            timeline.getKeyFrames()
                    .addAll(new KeyFrame(Duration.ZERO,
                            new KeyValue(completedTaskListView.prefHeightProperty(), completedPanelHeight),
                            new KeyValue(completedTaskListTitle.visibleProperty(), true),
                            new KeyValue(completedTaskListView.opacityProperty(), 1)),
                            new KeyFrame(Duration.millis(200.0d),
                                    new KeyValue(completedTaskListView.opacityProperty(), 0.8d)),
                            new KeyFrame(Duration.millis(300.0d),
                                    new KeyValue(completedTaskListView.prefHeightProperty(), 0),
                                    new KeyValue(completedTaskListTitle.visibleProperty(), false),
                                    new KeyValue(completedTaskListView.opacityProperty(), 0)));
            timeline.play();
            flag = false;
        }
    }

    @Subscribe
    private void handleShowCompletedTasksEvent(ShowCompletedTaskEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.action == ShowCompletedTaskEvent.Action.SHOW && !flag) {
            display(true);
        } else if (event.action == ShowCompletedTaskEvent.Action.HIDE && flag) {
            display(false);
        } else if (event.action == ShowCompletedTaskEvent.Action.TOGGLE) {
            display(!flag);
        }
    }
}
