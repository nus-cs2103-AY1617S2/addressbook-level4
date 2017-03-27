package guitests.guihandles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    //@@author A0142255M
    private static final String START_DATE_FIELD_ID = "#startdate";
    private static final String START_TIME_FIELD_ID = "#starttime";
    private static final String END_DATE_FIELD_ID = "#enddate";
    private static final String END_TIME_FIELD_ID = "#endtime";
    //@@author
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
    //@@author A0142255M
    public String getStartDate() {
        String displayed = getTextFromLabel(START_DATE_FIELD_ID);
        return displayed.substring(12);
    }

    public String getStartTime() {
        String displayed = getTextFromLabel(START_TIME_FIELD_ID);
        return displayed.substring(12);
    }

    public String getEndDate() {
        String displayed = getTextFromLabel(END_DATE_FIELD_ID);
        return displayed.substring(10);
    }

    public String getEndTime() {
        String displayed = getTextFromLabel(END_TIME_FIELD_ID);
        return displayed.substring(10);
    }

    public boolean hasStartDate() {
        try {
            getStartDate();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean hasStartTime() {
        try {
            getStartTime();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean hasEndDate() {
        try {
            getEndDate();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean hasEndTime() {
        try {
            getEndTime();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    //@@author

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

    //@@author A0142255M
    public boolean isSameTask(ReadOnlyTask task) {
        boolean start = true;
        boolean end = true;
        if ((hasStartDate() && hasStartTime()) && (task.getStartDateTime().isPresent())) {
            start = getStartDate().equals(task.getStartDateTime().get().getDateOnly()) &&
                    getStartTime().equals(task.getStartDateTime().get().getTimeOnly());
        }
        if ((hasEndDate() && hasEndTime()) && (task.getEndDateTime().isPresent())) {
            end = getEndDate().equals(task.getEndDateTime().get().getDateOnly()) &&
                  getEndTime().equals(task.getEndDateTime().get().getTimeOnly());
        }
        return getFullName().equals(task.getName().fullName)
                && start && end
                && getTags().equals(getTags(task.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getStartDate().equals(handle.getStartDate())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndDate().equals(handle.getEndDate())
                    && getEndTime().equals(handle.getEndTime())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }
    //@@author

    @Override
    public String toString() {
        return getFullName();
    }
}
