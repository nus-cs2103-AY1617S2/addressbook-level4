# A0139845R-reused
###### \java\seedu\watodo\logic\commands\DeleteCommand.java
``` java
    @Override
    public void unexecute() {
        assert model != null;

        try {
            model.addTask(new Task(taskToDelete));
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

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        @Override
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            assert nameKeyWords != null;
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(
                            task.getDescription().fullDescription, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
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
        if (task.getStatus().toString().equals("Done")) {
            cardPane.setStyle("-fx-background-color: #2bba36;");
        }

        initTags(task);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
```
