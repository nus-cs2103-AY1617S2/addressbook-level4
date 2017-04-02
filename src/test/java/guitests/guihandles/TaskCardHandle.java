package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.tag.UniqueTagList;

//@@author A0148038A
/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DEADLINE_FIELD_ID = "#byTimeDate";
    private static final String LOCATION_FIELD_ID = "#locations";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getByTimeDate() {
        return getTextFromLabel(DEADLINE_FIELD_ID);
    }

    public String getLocation() {
        return getTextFromLabel(LOCATION_FIELD_ID);
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
        return getDescription().equals(task.getDescription().description)
                && getPriority().equals(task.getPriority().toString())
                && getByTimeDate().equals(task.getByTime().toString()
                        + " " + task.getByDate().toString())
                && getLocation().equals(task.getLocation().value)
                && getTags().equals(getTags(task.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getDescription().equals(handle.getDescription())
                    && getPriority().equals(handle.getPriority())
                    && getByTimeDate().equals(handle.getByTimeDate())
                    && getLocation().equals(handle.getLocation())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDescription() + " " + getPriority() + " " + getByTimeDate() + " " + getLocation();
    }
}
