package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyTask;

public class CompletedTaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "CompletedTaskListPanel.fxml";

    // Init parameters for completed panel animation
    private static double COMPLETED_PANEL_HEIGHT = 400.0d;
    private boolean flag = false;


    @FXML
    private ListView<ReadOnlyTask> completedTaskListView;

    public CompletedTaskListPanel(AnchorPane completedTaskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        addToPlaceholder(completedTaskListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        completedTaskListView.setItems(taskList);
        completedTaskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        completedTaskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in completed task list panel changed to : '" + newValue + "'");
                        //TODO: create a new event
                        //raise(new TaskPanelSelectionChangedEvent(newValue));
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

    public void menuTest(){
        if(!flag){
            //show completed task list panel
            completedTaskListView.setPrefHeight(0.0d);
            flag = true;
   
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(completedTaskListView.prefHeightProperty(),0)
                    ),
                    new KeyFrame(Duration.millis(300.0d),
                            new KeyValue(completedTaskListView.prefHeightProperty(),COMPLETED_PANEL_HEIGHT)
                    )
            );
            timeline.play();
        }else{
            // collapse
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(completedTaskListView.prefHeightProperty(),COMPLETED_PANEL_HEIGHT)
                    ),
                    new KeyFrame(Duration.millis(300.0d),
                            new KeyValue(completedTaskListView.prefHeightProperty(),0)
                    )
            );
            timeline.play();
            flag = false;
        }
    }

}
