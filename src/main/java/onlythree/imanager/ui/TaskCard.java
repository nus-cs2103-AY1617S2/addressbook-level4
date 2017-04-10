package onlythree.imanager.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import onlythree.imanager.commons.core.DateTimeFormats;
import onlythree.imanager.model.task.Deadline;
import onlythree.imanager.model.task.ReadOnlyTask;
import onlythree.imanager.model.task.StartEndDateTime;

//@@author A0135998H
/**
 * A ui container that stores a task's information displayed in the task list panel.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    /* a UI container storing the startDate and endDate labels */
    @FXML
    private HBox startEndDateContainer;
    /* a UI container storing the deadline label */
    @FXML
    private HBox deadlineContainer;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().value);
        id.setText(displayedIndex + ". ");

        DateTimeFormatter dateFormat = DateTimeFormats.LOCALIZED_FORMAT;
        StartEndDateTime taskStartEndDateTime = getStartEndDateTime(task);
        Deadline taskDeadline = getDeadline(task);
        if (taskStartEndDateTime != null) {
            showStartEndDateContainer();
            hideDeadlineContainer();
            startDate.setText(getStartDateTime(taskStartEndDateTime, dateFormat));
            endDate.setText(getEndDateTime(taskStartEndDateTime, dateFormat));
        } else if (taskDeadline != null) {
            showDeadlineContainer();
            hideStartEndDateContainer();
            deadline.setText(getDeadlineDateTime(taskDeadline, dateFormat));
        } else {
            hideStartEndDateContainer();
            hideDeadlineContainer();
        }
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void showStartEndDateContainer() {
        startEndDateContainer.setVisible(true);
    }

    private void showDeadlineContainer() {
        deadlineContainer.setVisible(true);
    }

    private void hideStartEndDateContainer() {
        startEndDateContainer.setVisible(false);
    }

    private void hideDeadlineContainer() {
        deadlineContainer.setVisible(false);
    }

    private StartEndDateTime getStartEndDateTime(ReadOnlyTask task) {
        if (task.getStartEndDateTime().isPresent()) {
            return task.getStartEndDateTime().get();
        } else {
            return null;
        }
    }

    private Deadline getDeadline(ReadOnlyTask task) {
        if (task.getDeadline().isPresent()) {
            return task.getDeadline().get();
        } else {
            return null;
        }
    }

    private String getStartDateTime(StartEndDateTime taskStartEndDateTime,
            DateTimeFormatter dateFormat) {
        return taskStartEndDateTime.getStartDateTime().format(dateFormat);
    }

    private String getEndDateTime(StartEndDateTime taskStartEndDateTime,
            DateTimeFormatter dateFormat) {
        return taskStartEndDateTime.getEndDateTime().format(dateFormat);
    }

    private String getDeadlineDateTime(Deadline taskDeadline,
            DateTimeFormatter dateFormat) {
        return taskDeadline.getDateTime().format(dateFormat);
    }

}
