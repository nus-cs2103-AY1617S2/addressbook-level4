package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.task.MainApp;
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
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label loc;
    @FXML
    private Label done;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        startDate.setText(task.getStartDate().toString());
        endDate.setText(task.getEndDate().toString());
        loc.setText(task.getLocation().value);
        if (task.isDone()) {
            //done.setText("Done");
            name.setTextFill(Color.GREEN);
            Image image = new Image(MainApp.class.getResourceAsStream("/images/tick.png"));
            name.setGraphic(new ImageView(image));
            name.setContentDisplay(ContentDisplay.RIGHT);
            //done.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        } else {
            //done.setText("Not Done");
            //done.setTextFill(Color.RED);
            //Image image = new Image(MainApp.class.getResourceAsStream("/images/cross.png"));
            //name.setGraphic(new ImageView(image));
            //name.setContentDisplay(ContentDisplay.RIGHT);
            //done.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        }
        remark.setText(task.getRemark().value);
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
