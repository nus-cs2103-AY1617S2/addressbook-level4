package seedu.tasklist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.tasklist.model.task.ReadOnlyTask;

//@@author A0143355J
public class UpcomingTaskCard extends UiPart<Region> {

    private static final String FXML = "UpcomingTaskListCard.fxml";

    @FXML
    private Label name;

    public UpcomingTaskCard(ReadOnlyTask task) {
        super(FXML);
        name.setText(task.getName().fullName);
    }
}
