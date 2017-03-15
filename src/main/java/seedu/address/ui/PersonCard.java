package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

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
    private Label email;
//    @FXML
//    private Label endDate;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        name.setText(person.getDescription().description);
        id.setText(displayedIndex + ". ");
        phone.setText("Priority:" + person.getPriority().value);
        email.setText(person.getStartDate().value);
//        endDate.setText(person.getEndDate().value);
        initTags(person);
    }

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
