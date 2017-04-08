package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TASK_NAME_FIELD_ID = "#name";
    private static final String TASK_DATE_FIELD_ID = "#date";
    private static final String TASK_START_TIME_FIELD_ID = "#startTime";
    private static final String TASK_END_TIME_FIELD_ID = "#endTime";
    private static final String TASK_DESCRIPTION_FIELD_ID = "#description";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(TASK_NAME_FIELD_ID);
    }

    public String getTaskDate() {
        return getTextFromLabel(TASK_DATE_FIELD_ID);
    }

    public String getTaskStartTime() {
        return getTextFromLabel(TASK_START_TIME_FIELD_ID);
    }

    public String getTaskEndTime() {
        return getTextFromLabel(TASK_END_TIME_FIELD_ID);
    }

    public String getTaskDescription() {
        return getTextFromLabel(TASK_DESCRIPTION_FIELD_ID);
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer.getChildrenUnmodifiable().stream()
                .map(node -> ((Labeled) node).getText()).collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags.asObservableList().stream().map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return getFullName().equals(task.getTaskName().fullTaskName)
                && getTaskDate().equals(task.getTaskDate().value)
                && getTaskStartTime().equals(task.getTaskStartTime().value)
                && getTaskEndTime().equals(task.getTaskEndTime().value)
                && getTaskDescription().equals(task.getTaskDescription())
                && getTags().equals(getTags(task.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getTaskDate().equals(handle.getTaskDate())
                    && getTaskStartTime().equals(handle.getTaskStartTime())
                    && getTaskEndTime().equals(handle.getTaskEndTime())
                    && getTaskDescription().equals(handle.getTaskDescription())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getTaskDate();
    }
}
