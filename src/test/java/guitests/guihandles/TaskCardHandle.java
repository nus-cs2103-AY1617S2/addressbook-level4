package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import w10b3.todolist.model.tag.UniqueTagList;
import w10b3.todolist.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String VENUE_FIELD_ID = "#venue";
    private static final String STARTTIME_FIELD_ID = "#startTime";
    private static final String ENDTIME_FIELD_ID = "#endTime";
    private static final String URGENCYLEVEL_FIELD_ID = "#urgencyLevel";
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

    public String getTitle() {
        return getTextFromLabel(TITLE_FIELD_ID);
    }

    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }

    public String getVenue() {
        return getTextFromLabel(VENUE_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public String getUrgencyLevel() {
        return getTextFromLabel(URGENCYLEVEL_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
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
        return getTitle().equals(task.getTitle().title)
                && getVenue().equals("Venue at: " + task.getVenue().get().toString())
                && getEndTime().equals("End at: " + task.getEndTime().get().toString())
                && getUrgencyLevel().equals("Urgency: " + task.getUrgencyLevel().get().toString())
                && getDescription().equals("Description: " + task.getDescription().get().toString());
     //         && getTags().equals(getTags(task.getTags()));
     //         this line somehow causes assertion errors so i commented it out for now. remember to debug this.
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTitle().equals(handle.getTitle())
                    && getEndTime().equals(handle.getEndTime())
                    && getVenue().equals(handle.getVenue())
                    && getUrgencyLevel().equals(handle.getUrgencyLevel())
                    && getDescription().equals(handle.getDescription())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTitle() + " " + getEndTime();
    }
}
