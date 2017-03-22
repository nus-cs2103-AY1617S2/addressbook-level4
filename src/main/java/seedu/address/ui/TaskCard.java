package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label deadline;
    @FXML
    private Label status;
    @FXML
    private FlowPane labels;
    @FXML
    private FlowPane bookings;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().title);
        id.setText(displayedIndex + ". ");
        if (task.getStartTime().isPresent()) {
            startTime.setText(task.getStartTime().get().toString());
        } else {
            startTime.setVisible(false);
        }
        if (task.getDeadline().isPresent()) {
            deadline.setText(task.getDeadline().get().toString());
        } else {
            deadline.setVisible(false);
        }
        if (task.isCompleted()) {
            status.setText("Completed");
        } else {
            status.setText("Incomplete");
        }
        initLabels(task);
        initBookings(task);
    }

    private void initLabels(ReadOnlyTask task) {
        task.getLabels().forEach(label -> labels.getChildren().add(new Label(label.labelName)));
    }

    private void initBookings(ReadOnlyTask task) {
        task.getBookings().forEach(booking -> bookings.getChildren().add(new Label(booking.toString() + "\n")));
    }
}
