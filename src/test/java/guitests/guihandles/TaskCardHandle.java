package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DESCRIPTION_FIELD_ID = "#descriptionOfTask";
    private static final String DATE_FIELD_ID = "#date";
    private static final String TAGS_FIELD_ID = "#tags";

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

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
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

    //TODO only works for v0.2
    public boolean isSameTask(ReadOnlyTask task) {
        return getTitle().equals(task.getTitle().title)
                && getDate().equals(getDisplayedDate(task))
                && getDescription().equals(task.getDescriptoin().value)
                && getTags().equals(getTags(task.getTags()));
    }

    public String getDisplayedDate(ReadOnlyTask task) {
        String displayedDate = "";
        if (task.getEndDateTime() != null && task.getStartDateTime() != null) {
            displayedDate = task.getStartDateTime() + " until " + task.getEndDateTime();
        } else if (task.getEndDateTime() != null && task.getStartDateTime() == null) {
            displayedDate = task.getEndDateTime().value;
        } else {
            displayedDate = "";
        }
        return displayedDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTitle().equals(handle.getTitle())
                    && getDate().equals(handle.getDate())
                    && getDescription().equals(handle.getDescription())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTitle() + " " + getDescription();
    }
}
