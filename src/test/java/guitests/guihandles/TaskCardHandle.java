package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String STARTTIME_FIELD_ID = "#startTime";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String LABELS_FIELD_ID = "#labels";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTitle() {
        return getTextFromLabel(TITLE_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }
    
    public String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID);
    }

    public List<String> getLabels() {
        return getLabels(getLabelsContainer());
    }

    private List<String> getLabels(Region labelsContainer) {
        return labelsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getLabels(UniqueLabelList labels) {
        return labels
                .asObservableList()
                .stream()
                .map(label -> label.labelName)
                .collect(Collectors.toList());
    }

    private Region getLabelsContainer() {
        return guiRobot.from(node).lookup(LABELS_FIELD_ID).query();
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return getTitle().equals(task.getTitle().title)
                && getDeadline().equals(task.getDeadline().toString())
                && getLabels().equals(getLabels(task.getLabels()));
                //&& getStartTime().equals(task.getStartTime()); 
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTitle().equals(handle.getTitle())
                    && getStartTime().equals(handle.getStartTime())
                    && getDeadline().equals(handle.getDeadline())
                    && getLabels().equals(handle.getLabels());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTitle() + " " + getDeadline();
    }
}
