package seedu.doit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.doit.model.item.ReadOnlyFloatingTask;

public class FloatingTaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label description;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;

    public FloatingTaskCard(ReadOnlyFloatingTask floatingTask, int displayedIndex) {
        super(FXML);
        this.name.setText(floatingTask.getName().fullName);
        this.id.setText(displayedIndex + ". ");
        this.priority.setText(floatingTask.getPriority().value);
        this.description.setText(floatingTask.getDescription().value);
        initTags(floatingTask);
    }

    private void initTags(ReadOnlyFloatingTask floatingTask) {
        floatingTask.getTags().forEach(tag -> this.tags.getChildren().add(new Label(tag.tagName)));
    }
}
