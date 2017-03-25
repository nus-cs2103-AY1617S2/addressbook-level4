package seedu.tasklist.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tasklist.model.task.ReadOnlyTask;

public class UpcomingTaskPanel extends UiPart<Region> {

    private static final String FXML = "UpcomingTaskPanel.fxml";

    public UpcomingTaskPanel(AnchorPane upcomingTaskPlaceholder, ObservableList<ReadOnlyTask> todayTask,
            ObservableList<ReadOnlyTask> tomorrowTask) {
        super(FXML);
        setTodayConnections(todayTask);
        setTomorrowConnections(tomorrowTask);
    }
}
