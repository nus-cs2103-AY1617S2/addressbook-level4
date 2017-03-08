package seedu.tache.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tache.model.task.FloatingTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    
    @FXML
    private Label name;
    /*
    @FXML
    private Label date;
    */
    @FXML
    private FlowPane tags;

    public TaskCard(FloatingTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().toString());
        initTags(task);
    }

    private void initTags(FloatingTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
