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
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    //@FXML
    //private Label name;
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

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        plane.setText(displayedIndex + ". " + task.getName().fullName);
        plane.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        //plane.setCollapsible(true);
      //prohibit animating
     // plane.setAnimated(false);
        plane.setExpanded(false);
        this.status = false;
        //name.setText(task.getName().fullName);
        //id.setText(displayedIndex + ". ");
        startDate.setText(task.getStartDate().toString());
        endDate.setText(task.getEndDate().toString());
        loc.setText(task.getLocation().value);
        if (task.isDone()) {
            //done.setText("Done");
            plane.setTextFill(Color.GREEN);
            Image image = new Image(MainApp.class.getResourceAsStream("/images/tick.png"));
            plane.setGraphic(new ImageView(image));
            plane.setContentDisplay(ContentDisplay.RIGHT);
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
    
    public  void setExpend(boolean value ){
        this.status=value;
        plane.setExpanded(value);
    }
    public boolean expendStatus(){
        return this.status;
    }
}
