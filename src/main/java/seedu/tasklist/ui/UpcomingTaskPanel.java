package seedu.tasklist.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.tasklist.model.task.ReadOnlyTask;

public class UpcomingTaskPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(UpcomingTaskPanel.class);
    private static final String FXML = "UpcomingTaskPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> todayTaskListView;
    @FXML
    private ListView<ReadOnlyTask> tomorrowTaskListView;

    public UpcomingTaskPanel(AnchorPane upcomingTaskPlaceholder, ObservableList<ReadOnlyTask> todayTask,
            ObservableList<ReadOnlyTask> tomorrowTask) {
        super(FXML);
        setTodayListView(todayTask);
        setTomorrowListView(tomorrowTask);
        setEventHandlerForSelectionChangeEvent();
    }

    private void setTodayListView(ObservableList<ReadOnlyTask> todayList) {
        todayTaskListView.setItems(todayList);
    }

    private void setTomorrowListView(ObservableList<ReadOnlyTask> tomorrowList) {
        tomorrowTaskListView.setItems(tomorrowList);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        setEventHandlerForTodaySelectionChangeEvent();
        setEventHandlerForTomorrowSelectionChangeEvent();
    }

    private void setEventHandlerForTodaySelectionChangeEvent() {
        todayTaskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in today list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    private void setEventHandlerForTomorrowSelectionChangeEvent() {
        tomorrowTaskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in tomorrow list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }
}
