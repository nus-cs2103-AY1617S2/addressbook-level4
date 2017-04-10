package seedu.onetwodo.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.events.ui.DeselectCardsEvent;
import seedu.onetwodo.commons.events.ui.SelectCardEvent;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String DONE_PSEUDO_CLASS = "done";
    private static final String SELECTED_PSEUDO_CLASS = "selected";
    private static final String OVERDUE_PSEUDO_CLASS = "overdue";
    public static final String DATE_SPACING = "  -  ";
    public static final String DEADLINE_PREFIX = "by: ";
    public static final DateTimeFormatter INFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter OUTFORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy hh:mm a");
    private static final Logger logger = LogsCenter.getLogger(TaskCard.class);

    private String prefixId;

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
    private Label recur;
    @FXML
    private Label description;
    @FXML
    private HBox descriptionBox;
    @FXML
    private FlowPane tags;

    //@@author A0143029M
    public TaskCard(ReadOnlyTask task, int displayedIndex, char indexPrefix) {
        super(FXML);
        prefixId = Character.toString(indexPrefix) + displayedIndex;
        registerCard();
        name.setText(task.getName().fullName);
        id.setText(prefixId);
        setPriority(task);
        setDate(task);
        setRecur(task);
        setDescription(task);
        initTags(task);
        if (task.getDoneStatus()) {
            PseudoClass donePseudoClass = PseudoClass.getPseudoClass(DONE_PSEUDO_CLASS);
            name.pseudoClassStateChanged(donePseudoClass, true);
            cardPane.pseudoClassStateChanged(donePseudoClass, true);
        }
    }

    private void registerCard() {
        EventsCenter.getInstance().registerHandler(this);
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
            highlightIfOverdue(task, endDate);
            break;
        case EVENT:
            LocalDateTime eventStartDateTime = task.getStartDate().getLocalDateTime();
            LocalDateTime eventEndDateTime = task.getEndDate().getLocalDateTime();
            startDate.setText(eventStartDateTime.format(OUTFORMATTER) + DATE_SPACING);
            endDate.setText(eventEndDateTime.format(OUTFORMATTER));
            highlightIfOverdue(task, startDate, endDate);
            break;
        case TODO:
            dateBox.getChildren().clear();
            break;
        }
    }

    private void highlightIfOverdue(ReadOnlyTask task, Label... labels) {
        if (!task.isOverdue()) {
            return;
        }
        for (Label label : labels) {
            PseudoClass overduePseudoClass = PseudoClass.getPseudoClass(OVERDUE_PSEUDO_CLASS);
            label.pseudoClassStateChanged(overduePseudoClass, true);
        }
    }

    private void setDescription(ReadOnlyTask task) {
        String descriptionText = task.getDescription().value;
        if (descriptionText.length() > 0) {
            description.setText(descriptionText);
        } else {
            description.setText("");
            descriptionBox.setMaxHeight(0);
            description.setMaxHeight(0);
        }
    }

    private void setRecur(ReadOnlyTask task) {
        String recurText = task.getRecur().value;
        recur.setText(recurText);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Subscribe
    private void handleSelectTaskEvent(SelectCardEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String prefix = event.taskType.toString();
        String cardPrefixId = prefix + (event.targetIndex + 1);
        if (prefixId.equals(cardPrefixId)) {
            PseudoClass selectedPseudoClass = PseudoClass.getPseudoClass(SELECTED_PSEUDO_CLASS);
            cardPane.pseudoClassStateChanged(selectedPseudoClass, true);
        }
    }

    @Subscribe
    private void handleDeselectCard(DeselectCardsEvent event) {
        PseudoClass selectedPseudoClass = PseudoClass.getPseudoClass(SELECTED_PSEUDO_CLASS);
        cardPane.pseudoClassStateChanged(selectedPseudoClass, false);
    }
}
