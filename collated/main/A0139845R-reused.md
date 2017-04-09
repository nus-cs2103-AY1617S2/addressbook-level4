# A0139845R-reused
###### \java\seedu\watodo\logic\commands\Command.java
``` java
    /**
     * Selects the last task of the task list
     */
    protected void selectLastTask() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        int targetIndex = lastShownList.size();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
    }

    /**
     * Selects the task at index (base 0)
     * @param taskIndex
     */
    protected void selectTaskAtIndex(int taskIndex) {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        assert lastShownList.size() > taskIndex;
        EventsCenter.getInstance().post(new JumpToListRequestEvent(taskIndex - 1));
    }
```
###### \java\seedu\watodo\logic\commands\DeleteCommand.java
``` java
    @Override
    public void unexecute() {
        assert model != null;

        try {
            while (!deletedTaskList.isEmpty()) {
                model.addTask(deletedTaskList.pop());
            }
            model.updateFilteredListToShowAll();

        } catch (DuplicateTaskException e) {

        }
    }

    @Override
    public void redo() {
        assert model != null;

        try {
            model.updateFilteredListToShowAll();
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException e) {

        }
    }

```
###### \java\seedu\watodo\model\ModelManager.java
``` java
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getImportantTaskList() {
        updateImportantTaskList();
        return new UnmodifiableObservableList<>(importantTasks);
    }

    /**
     * Searches for tasks with the tags that mark these tasks as important and
     * adds them to the ImportantTaskList
     */
    private void updateImportantTaskList() {
        Set<String> keywords = new HashSet<String>();
        keywords.add("important");
        keywords.add("impt");
        keywords.add("urgent");
        keywords.add("critical");
        keywords.add("crucial");
        keywords.add("vital");
        keywords.add("serious");
        importantTasks.setPredicate(new PredicateExpression(new TagQualifier(keywords))::satisfies);
    }
```
###### \java\seedu\watodo\ui\ImportantTaskCard.java
``` java
public class ImportantTaskCard extends UiPart<Region> {

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


    public ImportantTaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        description.setText(task.getDescription().fullDescription);
        id.setText("! ");
        setStartDate(task);
        setEndDate(task);
        setStatus(task);
        initTags(task);
    }

    /**
     * Changes status text and colour based on status of the task
     * @param task
     */
    private void setStatus(ReadOnlyTask task) {
        status.setText(task.getStatus().toString());
        if (task.getStatus().toString().equals("Done")) {
            cardPane.setStyle("-fx-background-color: #5c5c5c;");
            description.setStyle("-fx-text-fill: green !important;");
        }
    }

    /**
     * Gets the end date of the task and updates the card
     * If no date, blank the label
     * @param task
     */
    private void setEndDate(ReadOnlyTask task) {
        if (task.getEndDate() != null) {
            endDate.setText("By: " + task.getEndDate());
        } else {
            endDate.setText("");
        }
    }

    /**
     * Gets the start date of the task and updates the card
     * If no date, blank the label
     * @param task
     */
    private void setStartDate(ReadOnlyTask task) {
        if (task.getStartDate() != null) {
            startDate.setText("Start: " + task.getStartDate());
        } else {
            startDate.setText("");
        }
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
```
###### \java\seedu\watodo\ui\ImportantTaskListPanel.java
``` java
/**
 * Panel containing the list of persons.
 */
public class ImportantTaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(ImportantTaskListPanel.class);
    private static final String FXML = "ImportantTaskListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> importantTaskListView;

    public ImportantTaskListPanel(AnchorPane importantTaskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        addToPlaceholder(importantTaskListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        importantTaskListView.setItems(taskList);
        importantTaskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        importantTaskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in important task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            importantTaskListView.scrollTo(index);
            importantTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ImportantTaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}
```
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
        setStartDate(task);
        setEndDate(task);
        setStatus(task);
        initTags(task);
    }

    /**
     * Changes status text and colour based on status of the task
     * @param task
     */
    private void setStatus(ReadOnlyTask task) {
        status.setText(task.getStatus().toString());
        if (task.getStatus().toString().equals("Done")) {
            cardPane.setStyle("-fx-background-color: #5c5c5c;");
            description.setStyle("-fx-text-fill: #6dc006;");
            setLabelsColourGreen();

        }
    }

    private void setLabelsColourGreen() {
        status.setStyle("-fx-text-fill: #6dc006;");
        id.setStyle("-fx-text-fill: #6dc006;");
        startDate.setStyle("-fx-text-fill: #6dc006;");
        endDate.setStyle("-fx-text-fill: #6dc006;");
    }

    /**
     * Gets the end date of the task and updates the card
     * If no date, blank the label
     * @param task
     */
    private void setEndDate(ReadOnlyTask task) {
        if (task.getEndDate() != null) {
            endDate.setText(task.getEndDate().toString());
        } else {
            endDate.setText("");
        }
    }

    /**
     * Gets the start date of the task and updates the card
     * If no date, blank the label
     * @param task
     */
    private void setStartDate(ReadOnlyTask task) {
        if (task.getStartDate() != null) {
            startDate.setText(task.getStartDate().toString());
        } else {
            startDate.setText("");
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
```
