package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String DESC_FIELD_ID = "#desc";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String START_TIME_FIELD_ID = "#startTime";
    private static final String END_TIME_FIELD_ID = "#endTime";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String FINISH_STATUS_FIELD_ID = "#checkbox";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    private String getDesc() {
        return getTextFromLabel(DESC_FIELD_ID);
    }

    private String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    private boolean getFinishStatus() {
        return getIsSelectedFromCheckbox(FINISH_STATUS_FIELD_ID, node);
    }

    private String getStartTime() {
        return getTextFromLabel(START_TIME_FIELD_ID);
    }

    private String getEndTime() {
        return getTextFromLabel(END_TIME_FIELD_ID);
    }

    private List<String> getTags() {
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

    public boolean isStyleInStyleClass(String style) {
        return node.getStyleClass().contains(style);
    }

    //@@author A0140887W
    public boolean isSameTask(ReadOnlyTask task) {

        // Sort by alphabetical order
        List<String> cardTags = getTags().stream().sorted().collect(Collectors.toList());
        List<String> taskTags = getTags(task.getTags()).stream().sorted().collect(Collectors.toList());
        return getDesc().equals(task.getDescription().desc)
                && getPriority().equals(task.getPriority().toString())
                && getFinishStatus() == task.getFinishedStatus().getIsFinished()
                //&& getStartTime().equals(task.getStartDate().toString())
                //&& getEndTime().equals(task.getEndDate().toString())
                && cardTags.equals(taskTags);
    }

    //@@author
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getDesc().equals(handle.getDesc())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndTime().equals(handle.getEndTime())
                    && getPriority().equals(handle.getPriority())
                    && getFinishStatus() == handle.getFinishStatus()
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDesc() + " " + getPriority();
    }
}
