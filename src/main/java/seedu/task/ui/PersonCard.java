package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

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
    private FlowPane tags;

    public PersonCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        description.setText(person.getDescription().description);
        id.setText(displayedIndex + ". ");
        priority.setText("Priority: " + person.getPriority().value);
        priority.setStyle("-fx-background-color: " + person.getPriority().getPriorityColor() + ";");
        startTiming.setText("Start Timing: " + person.getStartTiming().value);
        endTiming.setText("End Timing: " + person.getEndTiming().value);
        initTags(person);
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
