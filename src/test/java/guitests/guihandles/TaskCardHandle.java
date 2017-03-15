package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String COMMENT_FIELD_ID = "#comment";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String STATUS_FIELD_ID = "#status";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String ENDDATE_FIELD_ID = "#endDate";

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

    public String getComment() {
        return getTextFromLabel(COMMENT_FIELD_ID);
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
        boolean isDeadlineSame;
        boolean isStartDateSame;
        boolean isEndDateSame;
        String type = task.getType();
        switch (type) {
        case FloatingTask.TYPE:
            isDeadlineSame = true;
            isStartDateSame = true;
            isEndDateSame = true;
            break;
        case DeadlineTask.TYPE:
            isDeadlineSame = getDeadline().equals(((ReadOnlyDeadlineTask) task).getDeadlineString());
            isStartDateSame = true;
            isEndDateSame = true;
            break;
        case EventTask.TYPE:
            isDeadlineSame = true;
            isStartDateSame = getStartDate().equals(((ReadOnlyEventTask) task).getStartDateString());
            isEndDateSame = getEndDate().equals(((ReadOnlyEventTask) task).getEndDateString());
            break;
        default:
            isDeadlineSame = true;
            isStartDateSame = true;
            isEndDateSame = true;
        }
        return getFullName().equals(task.getName().fullName)
                && getComment().equals(task.getComment().value)
                && getTags().equals(getTags(task.getTags()))
                //TODO
                //&& getStatus() == task.getStatus().value
                && getPriority().equals(task.getPriority().value)
                && isDeadlineSame && isStartDateSame && isEndDateSame;
    }

    private String getEndDate() {
        return getTextFromLabel(ENDDATE_FIELD_ID).split(" ", 3)[2];
    }

    private String getStartDate() {
        return getTextFromLabel(STARTDATE_FIELD_ID).split(" ", 3)[2];
    }

    private String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID).split(" ", 2)[1];
    }

    private boolean getStatus() {
        return Boolean.parseBoolean(getTextFromLabel(STATUS_FIELD_ID));
    }

    private String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID).split(" ")[1];
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getComment().equals(handle.getComment())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getComment();
    }
}
