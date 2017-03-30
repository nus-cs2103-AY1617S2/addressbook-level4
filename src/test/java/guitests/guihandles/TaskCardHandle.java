package guitests.guihandles;

import java.util.Collections;
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
    private static final String NAME_FIELD_ID = "#name";
    private static final String LOCATION_FIELD_ID = "#loc";
    private static final String START_DATE_FIELD_ID = "#startDate";
    private static final String END_DATE_FIELD_ID = "#endDate";
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getLocation() {
        return getTextFromLabel(LOCATION_FIELD_ID);
    }

    public String getStartDate() {
        return getTextFromLabel(START_DATE_FIELD_ID);
    }

    public String getEndDate() {
        return getTextFromLabel(END_DATE_FIELD_ID);
    }

    public String getRemark() {
        return getTextFromLabel(REMARK_FIELD_ID);
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

    public boolean isSameTask(ReadOnlyTask task) {
        List<String> testTag = getTags();
        List<String> taskTag = getTags(task.getTags());
        Collections.sort(testTag);
        Collections.sort(taskTag);
        //Split the returned name into two parts, index and name, both as strings
        //only compare name with name, without index
        String[] indexAndName = getName().split(" ",2);
        
        return indexAndName[1].equals(task.getName().fullName)
                &&(task.getStartDate() == null || getStartDate().equals(task.getStartDate().toString()))
                && (task.getEndDate() == null || getEndDate().equals(task.getEndDate().toString()))
                && (task.getRemark() == null || getRemark().equals(task.getRemark().value))
                && (task.getLocation() == null || getLocation().equals(task.getLocation().value))
                && testTag.equals(taskTag);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && (handle.getStartDate() == null || getStartDate().equals(handle.getStartDate()))
                    && (handle.getEndDate() == null || getEndDate().equals(handle.getEndDate()))
                    && (handle.getRemark() == null || getRemark().equals(handle.getRemark()))
                    && (handle.getLocation() == null || getLocation().equals(handle.getLocation()))
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getLocation();
    }
}
