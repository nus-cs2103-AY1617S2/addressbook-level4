package guitests.guihandles;

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
 * Provides a handle to a person card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String DESC_FIELD_ID = "#description";
    private static final String STARTING_TIMING_FIELD_ID = "#startTiming";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String END_TIMING_FIELD_ID = "#endTiming";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String RECURRING_FIELD_ID = "#recurring";
    private static final String EMAIL_FIELD_ID = "#email";
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
        return getTextFromLabel(DESC_FIELD_ID);
    }
    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }
    public String getStartingTiming() {
        return getTextFromLabel(STARTING_TIMING_FIELD_ID);
    }
    public String getEndTiming() {
        return getTextFromLabel(END_TIMING_FIELD_ID);
    }
    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getAddress() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getRecurring() {
        return getTextFromLabel(RECURRING_FIELD_ID);
    }

    public String getEmail() {
        return getTextFromLabel(EMAIL_FIELD_ID);
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer.getChildrenUnmodifiable().stream().map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags.asObservableList().stream().map(tag -> tag.tagName).collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    public boolean isSameTask(ReadOnlyTask task) {
        boolean recurSame = true;
        if (task.isRecurring()) {
            recurSame = getRecurring().equals("Recurring Task: " + task.getFrequency().frequency);
        }
        return getDescription().equals(task.getDescription().description)
                && getPriority().equals("Priority: " + task.getPriority().value)
                && getStartingTiming().equals("Start Timing: " + task.getStartTiming().value)
                && getEndTiming().equals("End Timing: " + task.getEndTiming().value)
                && getTags().equals(getTags(task.getTags()))
                && recurSame;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName()) && getRecurring().equals(handle.getRecurring())
                    && getEmail().equals(handle.getEmail()) && getAddress().equals(handle.getAddress())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDescription() + " " + getPriority();
    }
}
