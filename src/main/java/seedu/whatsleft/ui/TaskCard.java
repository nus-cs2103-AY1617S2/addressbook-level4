package seedu.whatsleft.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.fxml.FXML;

//import javafx.beans.binding.Bindings;
//import javafx.geometry.Insets;
import javafx.scene.control.Label;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.whatsleft.model.activity.ReadOnlyTask;

//@@author A0148038A
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label byTimeDate;
    @FXML
    private Label locations;
    @FXML
    private FlowPane tags;
    // private final Background focusBackground =
    // new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY,
    // Insets.EMPTY));
    // private final Background unfocusBackground =
    // new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY,
    // Insets.EMPTY));
    // private VBox cardVBox = new VBox();

    // @@author A0124377A
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        description.setText(task.getDescription().description);
        id.setText(displayedIndex + ". ");

        priority.setText(task.getPriorityToShow());

        byTimeDate.setText(task.getByTimeDateToShow());

        locations.setText(task.getLocationToShow());
        initTags(task);
        setCardLook(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void setCardLook(ReadOnlyTask task) {
        if (task.getStatus()) {
            cardPane.getStyleClass().add("status-complete");
        } else if (isDueAndOverdue(task)) {
            cardPane.getStyleClass().add("status-dueOrOverdue");
        }
    }

    private boolean isDueAndOverdue(ReadOnlyTask task) {
        LocalDate taskDate = task.getByDate().getValue();
        LocalTime taskTime = task.getByTime().getValue();
        if (taskDate != null && taskTime != null) {
            return ((task.hasDeadline() && LocalDateTime.of(taskDate, taskTime).isBefore(LocalDateTime.now()))
                    || (LocalDateTime.of(taskDate, taskTime).getDayOfYear() == LocalDateTime.now().getDayOfYear()));
        } else {
            return false;
        }
    }
}
// private void feedback() {
// cardVBox.requestFocus();
// cardVBox.backgroundProperty().bind(Bindings
// .when(cardVBox.focusedProperty())
// .then(focusBackground)
// .otherwise(unfocusBackground));
// }
