package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label info;
    @FXML
    private FlowPane tags;

    //@@author A0141928B
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getTaskName().taskName);
        id.setText(displayedIndex + ". ");
        date.setText(task.getDate().value);
        info.setText(task.getInfo().value);
        switch(task.getPriority().value) {
        case "1":
            cardPane.setId("cardPaneP1");
            break;
        case "2":
            cardPane.setId("cardPaneP2");
            break;
        case "3":
            cardPane.setId("cardPaneP3");
            break;
        case "4":
            cardPane.setId("cardPaneP4");
            break;
        default: //No priority given
            break;
        }
        initTags(task);
    }
    //@@author

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
