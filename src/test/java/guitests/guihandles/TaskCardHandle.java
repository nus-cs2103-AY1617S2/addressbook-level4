package guitests.guihandles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    //@@author A0142255M
    private static final String FIELD_ID_NAME = "#name";
    private static final String FIELD_ID_START_DATE = "#startdate";
    private static final String FIELD_ID_START_TIME = "#starttime";
    private static final String FIELD_ID_END_DATE = "#enddate";
    private static final String FIELD_ID_END_TIME = "#endtime";
    private static final String FIELD_ID_SYMBOL = "#symbol";
    private static final String FIELD_ID_ID = "#id";
    private static final String FIELD_ID_TAGS = "#tags";
    //@@author

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(FIELD_ID_NAME);
    }

    //@@author A0142255M
    public String getId() {
        return getTextFromLabel(FIELD_ID_ID);
    }

    public String getStartDate() {
        String displayed = getTextFromLabel(FIELD_ID_START_DATE);
        return displayed.substring(12);
    }

    public String getStartTime() {
        String displayed = getTextFromLabel(FIELD_ID_START_TIME);
        return displayed.substring(12);
    }

    public String getEndDate() {
        String displayed = getTextFromLabel(FIELD_ID_END_DATE);
        return displayed.substring(10);
    }

    public String getEndTime() {
        String displayed = getTextFromLabel(FIELD_ID_END_TIME);
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

    protected Image getImageFromImageView(String fieldId) {
        return getImageFromImageView(fieldId, node);
    }

    public Image getSymbol() {
        return getImageFromImageView(FIELD_ID_SYMBOL);
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
        return guiRobot.from(node).lookup(FIELD_ID_TAGS).query();
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
