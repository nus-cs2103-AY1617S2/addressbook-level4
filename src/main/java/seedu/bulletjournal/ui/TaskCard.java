package seedu.bulletjournal.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.bulletjournal.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

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

    //@@author A0105748B

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getTaskName().fullName);
        id.setText(displayedIndex + ". ");
        phone.setText(task.getPhone() == null ? "" : task.getPhone().toString());
        address.setText(task.getAddress() == null ? "" : task.getAddress().toString());
        email.setText(task.getStatus() == null ? "" : task.getStatus().value);
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
