package guitests.guihandles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestUtil;
import seedu.onetwodo.ui.TaskCard;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String PREFIX_INDEX_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String START_DATE_FIELD_ID = "#startDate";
    private static final String END_DATE_FIELD_ID = "#endDate";
    private static final String DESCRIPTION_FIELD_ID = "#description";
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

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getStartDateTime() {
        return getTextFromLabel(START_DATE_FIELD_ID);
    }

    public String getEndDateTime() {
        return getTextFromLabel(END_DATE_FIELD_ID);
    }

    public TaskType getTaskType() {
        return TestUtil.getTaskTypeFromIndex(getTextFromLabel(PREFIX_INDEX_ID));
    }

    public List<String> getTags() {
        List<String> result = getTags(getTagsContainer());
        Collections.sort(result);
        return result;
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer.getChildrenUnmodifiable().stream().map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        List<String> result = tags.asObservableList().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
        Collections.sort(result);
        return result;
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    public boolean isSameTask(ReadOnlyTask task) {
        // Compare taskCardHandle with task
        TaskType typeToCompare = task.getTaskType();
        if (!getTaskType().equals(typeToCompare)) {
            return false;
        }
        boolean dateEquals = true;
        switch (typeToCompare) {
        case DEADLINE:
            LocalDateTime endDateTime = task.getEndDate().getLocalDateTime();
            dateEquals = getEndDateTime().equals(TaskCard.DEADLINE_PREFIX + endDateTime.format(TaskCard.OUTFORMATTER));
            break;
        case EVENT:
            LocalDateTime startDateTime = task.getStartDate().getLocalDateTime();
            LocalDateTime endDateTime1 = task.getEndDate().getLocalDateTime();
            String endDateTaskCard = getEndDateTime();
            String startDateTaskCard = getStartDateTime();
            dateEquals = startDateTaskCard.equals(startDateTime.format(TaskCard.OUTFORMATTER) + TaskCard.DATE_SPACING)
                    && endDateTaskCard.equals(endDateTime1.format(TaskCard.OUTFORMATTER));
            break;
        case TODO:
            break;
        }
        return getFullName().equals(task.getName().fullName)
                && dateEquals
                && getDescription().equals(task.getDescription().value)
                && getTags().equals(getTags(task.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getStartDateTime().equals(handle.getStartDateTime())
                    && getEndDateTime().equals(handle.getEndDateTime())
                    && getDescription().equals(handle.getDescription())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getDescription();
    }
}
