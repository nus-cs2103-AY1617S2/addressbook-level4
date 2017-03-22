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
    private HBox dateBox;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private HBox descriptionBox;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex, char indexPrefix) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(Character.toString(indexPrefix) + displayedIndex);
        setDate(task);
        setDescription(task);
        initTags(task);
        if (task.getDoneStatus()) {
            PseudoClass donePseudoClass = PseudoClass.getPseudoClass("done");
            name.pseudoClassStateChanged(donePseudoClass, true);
        }
    }

    private void setDate(ReadOnlyTask task) {
        String startDateText = task.getStartDate().value;
        String endDateText = task.getEndDate().value;
        if (startDateText.length() > 0 || endDateText.length() > 0) {
            startDate.setText(startDateText);
            endDate.setText(endDateText);
        } else {
            dateBox.getChildren().clear();
        }
    }

    private void setDescription(ReadOnlyTask task) {
        String descriptionText = task.getDescription().value;
        if (descriptionText.length() > 0) {
            description.setText(descriptionText);
        } else {
            descriptionBox.getChildren().clear();
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
