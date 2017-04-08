package org.teamstbf.yats.ui;

import org.teamstbf.yats.model.item.ReadOnlyEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

//@@author A0102778B

public class TaskCard extends UiPart<Region> {

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label loc;
    @FXML
    private Label description;
    @FXML
    private Label startTime;
    @FXML
    private Label hypen;
    @FXML
    private Label endTime;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyEvent task, int displayedIndex, String FXML) {
        super(FXML);
        name.setText(task.getTitle().fullName);
        id.setText(displayedIndex + ". ");
        loc.setText(task.getLocation().value);
        description.setText(task.getDescription().value);
        if (task.isRecurring()) {
            startTime.setText(task.getRecurrence().getLatestUndoneString());
            endTime.setText(task.getRecurrence().getPeriodicity());
            deadline.setText("");
            // TODO show next undone occurence? show start date together?
        } else {
            if (task.hasStartEndTime()) {
                startTime.setText(task.getStartTime().toString() + " - ");
                endTime.setText(task.getEndTime().toString());
            } else {
                startTime.setText("");
                endTime.setText("");
            }
            if (task.hasDeadline()) {
                deadline.setText(" by " + task.getDeadline().toString());
            } else {
                deadline.setText("");
            }
        }
        initTags(task);
    }

    private void initTags(ReadOnlyEvent person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
