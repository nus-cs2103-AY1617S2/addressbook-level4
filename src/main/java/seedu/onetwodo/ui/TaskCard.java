package seedu.onetwodo.ui;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.onetwodo.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label description;
    @FXML
    private Label endDate;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex, char indexPrefix) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(Character.toString(indexPrefix) + displayedIndex);
        startDate.setText(task.getStartDate().value);
        description.setText(task.getDescription().value);
        endDate.setText(task.getEndDate().value);
        initTags(task);
        if (task.getDoneStatus()) {
            PseudoClass donePseudoClass = PseudoClass.getPseudoClass("done");
            name.pseudoClassStateChanged(donePseudoClass, true);
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
