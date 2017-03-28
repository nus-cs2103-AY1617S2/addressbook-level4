package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.tasklist.commons.util.StringUtil;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.testutil.TestUtil;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String COMMENT_FIELD_ID = "#comment";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String STATUS_FIELD_ID = "#tickLogo";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String ENDDATE_FIELD_ID = "#endDate";

    private Node node;

    protected TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    protected boolean getNodeVisibility(String fieldId) {
        return getNodeVisibility(fieldId, node);
    }

    private String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    private String getComment() {
        return getTextFromLabel(COMMENT_FIELD_ID);
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
                .map(tag -> StringUtil.removeSquareBrackets(tag.tagName))
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
                && TestUtil.isSameStringList(getTags(), getTags(task.getTags()))
                && getStatus() == task.getStatus().value
                && getPriority().equals(task.getPriority().value)
                && isDeadlineSame && isStartDateSame && isEndDateSame;
    }

    private String getEndDate() {
        try {
            return getTextFromLabel(ENDDATE_FIELD_ID).split(" ", 3)[2];
        } catch (ArrayIndexOutOfBoundsException aob) {
            return "";
        }
    }

    private String getStartDate() {
        try {
            return getTextFromLabel(STARTDATE_FIELD_ID).split(" ", 3)[2];
        } catch (ArrayIndexOutOfBoundsException aob) {
            return "";
        }
    }

    private String getDeadline() {
        try {
            return getTextFromLabel(DEADLINE_FIELD_ID).split(" ", 2)[1];
        } catch (ArrayIndexOutOfBoundsException aob) {
            return "";
        }
    }

    //@@author A0143355J
    private boolean getStatus() {
        return getNodeVisibility(STATUS_FIELD_ID);
    }

    //@@author
    private String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID).toLowerCase();
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
        return getFullName() + " " + getDeadline() + getStartDate() + " " + getEndDate()
                + " " + getComment() + " " + getPriority() + " " + getTags();
    }
}
