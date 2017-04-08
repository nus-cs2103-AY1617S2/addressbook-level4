package onlythree.imanager.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import onlythree.imanager.logic.parser.DateTimeUtil;
import onlythree.imanager.model.task.ReadOnlyTask;
import onlythree.imanager.model.task.StartEndDateTime;

//@@author A0135998H
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private HBox showStartEndDate;
    @FXML
    private HBox showDeadline;
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

        // TODO change date format
        DateTimeFormatter dateFormat = DateTimeUtil.DISPLAY_FORMAT_TODO;
        if (task.getStartEndDateTime().isPresent()) {
            showStartEndDate.setVisible(true);
            showDeadline.setVisible(false);
            StartEndDateTime startEndDateTime = task.getStartEndDateTime().get();
            startDate.setText(startEndDateTime.getStartDateTime().format(dateFormat));
            endDate.setText(startEndDateTime.getEndDateTime().format(dateFormat));
        } else if (task.getDeadline().isPresent()) {
            showStartEndDate.setVisible(false);
            showDeadline.setVisible(true);
            deadline.setText(task.getDeadline().get().getDateTime().format(dateFormat));
        } else {
            showStartEndDate.setVisible(false);
            showDeadline.setVisible(false);
        }

        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
