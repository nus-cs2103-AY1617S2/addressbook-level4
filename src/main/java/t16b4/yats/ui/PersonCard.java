package t16b4.yats.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import t16b4.yats.model.item.ReadOnlyItem;

public class PersonCard extends UiPart<Region> {

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
    private FlowPane tags;

    public PersonCard(ReadOnlyItem person, int displayedIndex) {
        super(FXML);
        name.setText(person.getTitle().fullName);
        id.setText(displayedIndex + ". ");
        phone.setText(person.getPhone().value);
        address.setText(person.getDescription().value);
        email.setText(person.getEmail().value);
        initTags(person);
    }

    private void initTags(ReadOnlyItem person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
