package seedu.geekeep.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.model.task.ReadOnlyTask;

public class FloatingTaskListPanel extends TaskListPanel {
    private static final String FLOATING_TASK_FXML = "FloatingTaskListPanel.fxml";

    public FloatingTaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> filteredList) {
        super(FLOATING_TASK_FXML, taskListPlaceholder, filteredList);
        this.type = "floatingTask";
        this.logger = LogsCenter.getLogger(FloatingTaskListPanel.class);
    }
}
