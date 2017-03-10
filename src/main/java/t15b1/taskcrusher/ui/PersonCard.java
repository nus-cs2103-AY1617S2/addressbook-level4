package t15b1.taskcrusher.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import t15b1.taskcrusher.model.task.ReadOnlyTask;

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

    public PersonCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getTaskName().taskName);
        id.setText(displayedIndex + ". ");
        //phone.setText(task.getPhone().value);
        address.setText(task.getDescription().value);
        //email.setText(task.getEmail().value);
        initTags(task);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
