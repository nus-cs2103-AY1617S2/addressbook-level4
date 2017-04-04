package seedu.task.ui;


import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seedu.task.MainApp;
import seedu.task.model.tag.TagColorMap;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCardDefault.fxml";
    protected static final String FXML_LIGHT = "TaskListCardLight.fxml";
    protected static final String FXML_DARK = "TaskListCardDark.fxml";

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
    @FXML
    private TitledPane plane;

    private boolean status;
    //@@author A0139975J
    public TaskCard(ReadOnlyTask task, int displayedIndex, String...fxml) {
        super(fxml.length==0?FXML:fxml[0]);
//        plane.setText(displayedIndex + ". " + task.getName().fullName);
//        plane.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        //plane.setCollapsible(true);
      //prohibit animating
     // plane.setAnimated(false);
        plane.setExpanded(false);
        this.status = false;
        name.setText(displayedIndex + ". " + task.getName().fullName);
        name.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        //id.setText(displayedIndex + ". ");
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
    //@@author A0142939W
    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(
                createLabel(tag.tagName, TagColorMap.getColor(tag.tagName))));
    }

    //@@author A0142939W
    private Label createLabel(String tagName, String color) {
        Label tag = new Label(tagName);
        tag.setStyle("-fx-background-color: " + color);
        return tag;
    }
    //@@author A0139975J
    public  void setExpend(boolean value) {
        this.status = value;
        plane.setExpanded(value);
    }
    //@@author A0139975J
    public boolean expendStatus() {
        return this.status;
    }
}
