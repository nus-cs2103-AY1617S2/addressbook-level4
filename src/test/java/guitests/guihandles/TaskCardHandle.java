package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.opus.model.tag.UniqueTagList;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Priority;
import seedu.opus.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String NOTE_FIELD_ID = "#note";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String STARTTIME_FIELD_ID = "#startTime";
    private static final String ENDTIME_FIELD_ID = "#endTime";
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
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getNote() {
        return getTextFromLabel(NOTE_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
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

    //@@author A0124368A
    public boolean isSameTask(ReadOnlyTask task) {
        return getFullName().equals(task.getName().fullName)
                && getPriority().equals(task.getPriority().map(Priority::toString).map(String::toUpperCase).orElse(""))
                && getNote().equals(task.getNote().map(Note::toString).orElse(""))
                && getStartTime().equals(task.getStartTime().map(DateTime::toString).map(s -> "Start: " + s).orElse(""))
                && getEndTime().equals(task.getEndTime().map(DateTime::toString).map(s -> "End: " + s).orElse(""))
                && getTags().equals(getTags(task.getTags()));
    }
    //@@author

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getPriority().equals(handle.getPriority())
                    && getNote().equals(handle.getNote())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getNote();
    }
}
