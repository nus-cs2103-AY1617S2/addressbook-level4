package seedu.geekeep.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.model.task.ReadOnlyTask;

public class DeadlineListPanel extends TaskListPanel {
    private static final String DEADLINE_FXML = "DeadlineListPanel.fxml";

    public DeadlineListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> filteredList) {
        super(DEADLINE_FXML, taskListPlaceholder, filteredList);
        this.type = "deadline";
        this.logger = LogsCenter.getLogger(DeadlineListPanel.class);
    }
}
