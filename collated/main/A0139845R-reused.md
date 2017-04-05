# A0139845R-reused
###### \java\seedu\watodo\ui\TaskCard.java
``` java
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label status;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private FlowPane tags;


    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        description.setText(task.getDescription().fullDescription);
        id.setText(displayedIndex + ". ");

        if (task.getStartDate() != null) {
            startDate.setText("Start: " + task.getStartDate());
        } else {
            startDate.setText("");
        }

        if (task.getEndDate() != null) {
            endDate.setText("By: " + task.getEndDate());
        } else {
            endDate.setText("");
        }

        status.setText(task.getStatus().toString());

        initTags(task);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
```
