package seedu.tasklist.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tasklist.model.task.ReadOnlyTask;

public class UpcomingTaskPanel extends UiPart<Region> {

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
    }

    public void setTodayListView(ObservableList<ReadOnlyTask> todayList) {
        todayTaskListView.setItems(todayList);
    }

    public void setTomorrowListView(ObservableList<ReadOnlyTask> tomorrowList) {
        tomorrowTaskListView.setItems(tomorrowList);
    }
}
