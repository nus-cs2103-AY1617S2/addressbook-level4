package seedu.onetwodo.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String DONE_PSUEDO_CLASS = "done";
    public static final String DATE_SPACING = "  -  ";
    public static final String DEADLINE_PREFIX = "by: ";
    public static final DateTimeFormatter INFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter OUTFORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy hh:mm a");

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label priority;
    @FXML
    private Label id;
    @FXML
    private HBox dateBox;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex, char indexPrefix) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(Character.toString(indexPrefix) + displayedIndex);
        setPriority(task);
        setDate(task);
        setDescription(task);
        initTags(task);
        if (task.getDoneStatus()) {
            PseudoClass donePseudoClass = PseudoClass.getPseudoClass(DONE_PSUEDO_CLASS);
            name.pseudoClassStateChanged(donePseudoClass, true);
            cardPane.pseudoClassStateChanged(donePseudoClass, true);
        }
    }

    private void setPriority(ReadOnlyTask task) {
        String priorityText = task.getPriority().value;
        priority.setText(priorityText);
        switch (priorityText) {
        case Priority.HIGH_LABEL:
            PseudoClass donePseudoClass = PseudoClass.getPseudoClass(Priority.HIGH_LABEL);
            priority.pseudoClassStateChanged(donePseudoClass, true);
            break;
        case Priority.MEDIUM_LABEL:
        	PseudoClass mediumPseudoClass = PseudoClass.getPseudoClass(Priority.MEDIUM_LABEL);
            priority.pseudoClassStateChanged(mediumPseudoClass, true);
            break;
        case Priority.LOW_LABEL:
        	PseudoClass lowPseudoClass = PseudoClass.getPseudoClass(Priority.LOW_LABEL);
            priority.pseudoClassStateChanged(lowPseudoClass, true);
            break;
        }
    }

    private void setDate(ReadOnlyTask task) {
        switch (task.getTaskType()) {
        case DEADLINE:
            LocalDateTime deadlineDateTime = task.getEndDate().getLocalDateTime();
            endDate.setText(DEADLINE_PREFIX + deadlineDateTime.format(OUTFORMATTER));
            break;
        case EVENT:
            LocalDateTime eventStartDateTime = task.getStartDate().getLocalDateTime();
            LocalDateTime eventEndDateTime = task.getEndDate().getLocalDateTime();
            startDate.setText(eventStartDateTime.format(OUTFORMATTER) + DATE_SPACING);
            endDate.setText(eventEndDateTime.format(OUTFORMATTER));
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
            description.setText("");
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
