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

    public TaskCard(ReadOnlyEvent person, int displayedIndex, String FXML) {
	super(FXML);
	name.setText(person.getTitle().fullName);
	id.setText(displayedIndex + ". ");
	loc.setText(person.getLocation().value);
	description.setText(person.getDescription().value);
	if (person.hasStartEndTime()) {
	    startTime.setText(person.getStartTime().toString() + " - ");
	    endTime.setText(person.getEndTime().toString());
	} else {
	    startTime.setText("");
	    endTime.setText("");
	}
	if (person.hasDeadline()) {
	    deadline.setText(" by " + person.getDeadline().toString());
	} else {
	    deadline.setText("");
	}
	initTags(person);
    }

    private void initTags(ReadOnlyEvent person) {
	person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
