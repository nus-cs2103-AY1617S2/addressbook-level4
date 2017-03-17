package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Panel containing a group of tasks.
 */
public class TaskGroupPanel extends UiPart<Region> {

    private final Logger logger = LogsCenter.getLogger(TaskGroupPanel.class);
    private static final String FXML = "TaskGroupPanel.fxml";

    @FXML
    private TitledPane titledPane;

    @FXML
    private VBox taskGroupView;

    private int indexOffset;

    public TaskGroupPanel(String title, ObservableList<ReadOnlyTask> taskList, int indexOffset) {
        super(FXML);
        setTitle(title);
        setIndexOffset(indexOffset);
        setConnections(taskList);
        closeTitlePane();
    }

    public void closeTitlePane() {
        titledPane.setExpanded(false);
    }

    public void openTitlePane() {
        titledPane.setExpanded(true);
    }

    public void setIndexOffset(int indexOffset) {
        this.indexOffset = indexOffset;
    }

    public void setTitle(String title) {
        titledPane.setText(title);
    }

    public String getTitle() {
        return titledPane.getText();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        // When taskList changes, update everything
        taskList.addListener(new ListChangeListener<ReadOnlyTask>() {
            public void onChanged(Change<? extends ReadOnlyTask> change) {
                createTaskGroupView(taskList);
            }
        });

        createTaskGroupView(taskList);
    }

    /**
     * Instantiate TaskCard objects and add them to taskListView.
     */
    private void createTaskGroupView(ObservableList<ReadOnlyTask> taskList) {
        taskGroupView.getChildren().clear();
        int index = 0;
        for (ReadOnlyTask task : taskList) {
            TaskCard taskCard = new TaskCard(task, index + 1 + indexOffset);
            taskGroupView.getChildren().add(taskCard.getRoot());
            index++;
        }
    }
}
