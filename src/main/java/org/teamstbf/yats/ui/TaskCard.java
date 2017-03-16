package org.teamstbf.yats.ui;

import org.teamstbf.yats.model.item.ReadOnlyEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label hypen;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyEvent person, int displayedIndex) {
        super(FXML);
        name.setText(person.getTitle().fullName);
        id.setText(displayedIndex + ". ");
        phone.setText(person.getLocation().value);
        address.setText(person.getDescription().value);
        email.setText(person.getStartTime().value);
        endTime.setText(person.getEndTime().value);
        initTags(person);
    }

    private void initTags(ReadOnlyEvent person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
