package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
<<<<<<< HEAD:src/main/java/seedu/address/ui/PersonCard.java
import seedu.address.model.person.ReadOnlyTask;
=======
import seedu.task.model.task.ReadOnlyTask;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/ui/TaskCard.java

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
    private FlowPane tags;

<<<<<<< HEAD:src/main/java/seedu/address/ui/PersonCard.java
    public PersonCard(ReadOnlyTask person, int displayedIndex) {
=======
    public TaskCard(ReadOnlyTask person, int displayedIndex) {
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/ui/TaskCard.java
        super(FXML);
        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");
        phone.setText(person.getDate().value);
        address.setText(person.getLocation().value);
        email.setText(person.getRemark().value);
        initTags(person);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
