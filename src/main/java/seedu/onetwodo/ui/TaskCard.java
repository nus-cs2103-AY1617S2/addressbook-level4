package seedu.onetwodo.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.onetwodo.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String DONE_PSUEDO_CLASS = "done";
    private static final String DATE_SPACING = "  -  ";
    private static final DateTimeFormatter INFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTFORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy hh:mm a");

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
            PseudoClass donePseudoClass = PseudoClass.getPseudoClass(DONE_PSUEDO_CLASS);
            name.pseudoClassStateChanged(donePseudoClass, true);
            cardPane.pseudoClassStateChanged(donePseudoClass, true);
        }
    }

    private void setDate(ReadOnlyTask task) {
        String startDateText = task.getStartDate().value;
        String endDateText = task.getEndDate().value;
        switch (task.getTaskType()) {
        case DEADLINE:
            LocalDateTime endDateTime = LocalDateTime.parse(endDateText.substring(5), INFORMATTER);
            endDate.setText("by: " + endDateTime.format(OUTFORMATTER));
            break;
        case EVENT:
            LocalDateTime startDateTime = LocalDateTime.parse(startDateText.substring(7), INFORMATTER);
            startDate.setText(startDateTime.format(OUTFORMATTER) + DATE_SPACING);
            LocalDateTime endDateTime1 = LocalDateTime.parse(endDateText.substring(5), INFORMATTER);
            endDate.setText(endDateTime1.format(OUTFORMATTER));
            break;
        case TODO:
            dateBox.getChildren().clear();
            break;
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
