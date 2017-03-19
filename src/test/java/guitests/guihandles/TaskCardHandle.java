package guitests.guihandles;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String ID_FIELD_ID = "#id";
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

    public String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getID() {
        return getTextFromLabel(ID_FIELD_ID);
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
        return getName().equals(task.getName().toString())
                && getDeadline().equals(task.getDeadline().toString())
                && getDescription().equals(task.getDescription().toString())
                && compareStringListOrderInsensitive(getTags(), getTags(task.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getDeadline().equals(handle.getDeadline())
                    && getDescription().equals(handle.getDescription())
                    && compareStringListOrderInsensitive(getTags(), handle.getTags());
        }
        return super.equals(obj);
    }

    public boolean compareStringListOrderInsensitive(List<String> list1, List<String> list2) {
        return list1 == list2 || new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    @Override
    public String toString() {
        return getName() + " " + getID();
    }
}
