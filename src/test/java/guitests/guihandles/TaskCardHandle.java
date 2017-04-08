package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import project.taskcrusher.commons.util.UiDisplayUtil;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DESCRIPTION_FIELD_ID = "#description";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTaskName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID);
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    //@@author A0127737X
    public boolean isSameTask(ReadOnlyTask task) {
        String uiAdjustedPriority = UiDisplayUtil.priorityForUi(task.getPriority());
        String uiAdjustedDeadline = UiDisplayUtil.deadlineForUi(task.getDeadline());

        return getTaskName().equals(task.getName().name)
                && getPriority().equals(uiAdjustedPriority)
                && getDeadline().equals(uiAdjustedDeadline)
                && getDescription().equals(task.getDescription().description)
                && getTags().equals(getTags(task.getTags()));
    }

    //@@author
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName())
                    && getPriority().equals(handle.getPriority())
                    && getDeadline().equals(handle.getDeadline())
                    && getDescription().equals(handle.getDescription())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getDescription();
    }
}
