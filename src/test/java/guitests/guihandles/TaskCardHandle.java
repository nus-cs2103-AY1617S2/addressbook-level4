package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.category.UniqueCategoryList;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TASKNAME_FIELD_ID = "#taskname";
    private static final String DATE_FIELD_ID = "#date";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String STARTTIME_FIELD_ID = "#starttime";
    private static final String ENDTIME_FIELD_ID = "#endtime";
    private static final String CATEGORIES_FIELD_ID = "#categories";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullTaskName() {
        return getTextFromLabel(TASKNAME_FIELD_ID);
    }

    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }

    public String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public String getEndTime() {
    	return getTextFromLabel(ENDTIME_FIELD_ID);
    }

/*    public List<String> getCategories() {
        return getCategories(getCategoriesContainer());
    }

    private List<String> getCategories(Region categoriesContainer) {
        return categoriesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getCategories(UniqueCategoryList categories) {
        return categories
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    private Region getCategoriesContainer() {
        return guiRobot.from(node).lookup(CATEGORIES_FIELD_ID).query();
    } */

    public boolean isSameTask(ReadOnlyTask task) {
        return getFullTaskName().equals(task.getTaskName().fullTaskName)
                && getDate().equals(task.getDate().value)
                && getDeadline().equals(task.getDeadline().value)
//                && getStartTime().equals(task.getStartTime().value)
                && getEndTime().equals(task.getEndTime().value);
//                && getCategories().equals(getCategories(person.getCategories()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullTaskName().equals(handle.getFullTaskName())
                    && getDate().equals(handle.getDate())
                    && getDeadline().equals(handle.getDeadline())
//                    && getStartTime().equals(handle.getStartTime())
                    && getEndTime().equals(handle.getEndTime());
//                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullTaskName() + " " + getDate();
    }
}
