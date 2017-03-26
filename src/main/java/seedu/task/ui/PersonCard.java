package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Timing;

public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    //@@author A0164212U
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label startTiming;
    @FXML
    private Label endTiming;
    @FXML
    private Label recurring;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        description.setText(task.getDescription().description);
        id.setText(displayedIndex + ". ");
        priority.setText("Priority: " + task.getPriority().value);
        priority.setStyle("-fx-background-color: " + task.getPriority().getPriorityColor() + ";");

        if (task.getStartTiming().value.equals(Timing.NULL_TIMING)) {
            startTiming.setText("");
        } else {
            startTiming.setText("Start Timing: " + task.getStartTiming().value);
        }

        if (task.getEndTiming().value.equals(Timing.NULL_TIMING)) {
            endTiming.setText("");
        } else {
            endTiming.setText("End Timing: " + task.getEndTiming().value);

        }
        if (task.isRecurring()) {
            recurring.setText("Recurring Task");
            recurring.setStyle("-fx-background-color: pink;");
        } else {
            recurring.setText("");
        }
        initTags(task);
    }
    //@@author

    private void initTags(ReadOnlyTask person) {
        //        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        person.getTags().forEach(tag -> {
            String complete = "complete";
            System.out.println(tag.tagName);
            Label label1 = new Label(tag.tagName);
            //cannot use == to do string comparison
            if (tag.tagName.equals(complete)) {
                label1.setStyle("-fx-border-color:yellow; -fx-background-color:green;");
            }
            tags.getChildren().add(label1);
        });
    }
}
