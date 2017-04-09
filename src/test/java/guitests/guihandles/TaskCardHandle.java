package guitests.guihandles;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String DESC_FIELD_ID = "#desc";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String START_TIME_FIELD_ID = "#startTime";
    private static final String END_TIME_FIELD_ID = "#endTime";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String FINISH_STATUS_FIELD_ID = "#checkbox";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    private String getDesc() {
        return getTextFromLabel(DESC_FIELD_ID);
    }

    private String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    private boolean getFinishStatus() {
        return getIsSelectedFromCheckbox(FINISH_STATUS_FIELD_ID, node);
    }

    private String getStartTime() {
        return getTextFromLabel(START_TIME_FIELD_ID);
    }

    private String getEndTime() {
        return getTextFromLabel(END_TIME_FIELD_ID);
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
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    public boolean isStyleInStyleClass(String style) {
        return node.getStyleClass().contains(style);
    }

    //@@author A0140887W
    public boolean isSameTask(ReadOnlyTask task) {

        // Sort by alphabetical order
        List<String> cardTags = getTags().stream().sorted().collect(Collectors.toList());
        List<String> taskTags = getTags(task.getTags()).stream().sorted().collect(Collectors.toList());
        return getDesc().equals(task.getDescription().desc)
                && getPriority().equals(task.getPriority().toString())
                && getFinishStatus() == task.getFinishedStatus().getIsFinished()
                && hasEqualTime(task)
                && cardTags.equals(taskTags);
    }

    /**
     * Returns true if the task has the same times as this taskcard
     * @param task the task to compare this task card to
     */
    private boolean hasEqualTime(ReadOnlyTask task) {
        final String startTimeText = "Start: ";
        final String endTimeText = "End: ";
        final String byTimeText = "By: ";

        if (task.getDates().isDeadline()) {
            return (getStartTime().equals(byTimeText + prettyDate(task.getDates().getStartDate()))
                    && getEndTime().equals(""));
        } else if (task.getDates().isEvent()) {
            return (getStartTime().equals(startTimeText + prettyDate(task.getDates().getStartDate()))
                    && getEndTime().equals(endTimeText + prettyDate(task.getDates().getEndDate())));
        } else {
            // floating task
            return (getStartTime().equals("") && getEndTime().equals(""));
        }
    }

    //@@author A0140887W-reused
    private String prettyDate (Date date) {
        StringBuilder prettydate = new StringBuilder();
        prettydate.append(prettyMonth (date.getMonth() + 1));
        prettydate.append(" " + date.getDate() + ", ");
        prettydate.append((date.getYear() + 1900) + " at ");
        prettydate.append(prettyTime(date.getHours(), date.getMinutes()));
        return prettydate.toString();
    }

    private String prettyMonth (int month) {
        switch (month) {
        case 1 : return "January";
        case 2 : return "February";
        case 3 : return "March";
        case 4 : return "April";
        case 5 : return "May";
        case 6 : return "June";
        case 7 : return "July";
        case 8 : return "August";
        case 9 : return "September";
        case 10 : return "October";
        case 11 : return "November";
        case 12 : return "December";
        default : return null;
        }
    }

    private String prettyTime (int hours, int minutes) {
        String suffix = (hours <= 12) ? "am" : "pm";
        String hour = (hours <= 12) ? Integer.toString(hours) : Integer.toString(hours - 12);
        String minute = (minutes < 10) ? "0" + Integer.toString(minutes) : Integer.toString(minutes);
        return hour + ":" + minute + suffix;
    }

    //@@author
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getDesc().equals(handle.getDesc())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndTime().equals(handle.getEndTime())
                    && getPriority().equals(handle.getPriority())
                    && getFinishStatus() == handle.getFinishStatus()
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDesc() + " " + getPriority();
    }
}
