package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartEndDateTime;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private HBox startEndOnly;
    @FXML
    private HBox deadlineOnly;
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

        if (task.getStartEndDateTime().isPresent()) {
            startEndOnly.setVisible(true);
            deadlineOnly.setVisible(false);
            StartEndDateTime startEndDateTime = task.getStartEndDateTime().get();
            startDate.setText(startEndDateTime.getStartDateTime().format(ParserUtil.DATE_TIME_FORMAT));
            endDate.setText(startEndDateTime.getEndDateTime().format(ParserUtil.DATE_TIME_FORMAT));
        } else if (task.getDeadline().isPresent()) {
            startEndOnly.setVisible(false);
            deadlineOnly.setVisible(true);
            deadline.setText(task.getDeadline().get().getValue().format(ParserUtil.DATE_TIME_FORMAT));
        } else {
            startEndOnly.setVisible(false);
            deadlineOnly.setVisible(false);
        }

        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
