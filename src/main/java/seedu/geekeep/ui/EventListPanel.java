package seedu.geekeep.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.model.task.ReadOnlyTask;

public class EventListPanel extends TaskListPanel {
    private static final String EVENT_FXML = "EventListPanel.fxml";

    public EventListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> filteredList) {
        super(EVENT_FXML, taskListPlaceholder, filteredList);
        this.type = "event";
        this.logger = LogsCenter.getLogger(EventListPanel.class);
    }
}
