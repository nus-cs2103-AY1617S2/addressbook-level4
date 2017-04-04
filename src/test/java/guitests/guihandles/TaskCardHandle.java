package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final int INDEX_END_DATE_POSITION = 4;
    private static final int INDEX_START_DATE_POSITION = 6;
    private static final String NAME_FIELD_ID = "#name";
    private static final String INFORMATION_FIELD_ID = "#information";
    private static final String PRIORITY_FIELD_ID = "#priorityLevel";
    private static final String START_DATE_FIELD_ID = "#startDateTime";
    private static final String END_DATE_FIELD_ID = "#endDateTime";
    private static final String RECURRENCE_FIELD_ID = "#recurrence";
    private static final String CATEGORIES_FIELD_ID = "#categories";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    protected ImageView getImageFromImageView(String fieldId) {
        return getImageFromImageView(fieldId, node);
    }
    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getInformation() {
        return getTextFromLabel(INFORMATION_FIELD_ID);
    }

    public String getPriority() {
        ImageView imageView = getImageFromImageView(PRIORITY_FIELD_ID);

        if (imageView.isVisible()) {
            return "High priority";
        } else {
            return "No priority";
        }
    }

    public String getStartDateTime() {
        String value = getTextFromLabel(START_DATE_FIELD_ID);
        String result = value.substring(INDEX_START_DATE_POSITION).trim();
        return result;
    }

    public String getEndDateTime() {
        String value = getTextFromLabel(END_DATE_FIELD_ID);
        String result = value.substring(INDEX_END_DATE_POSITION).trim();
        return result;
    }

    public String getRecurrence() {
        return getTextFromLabel(RECURRENCE_FIELD_ID);
    }

    public List<String> getCategories() {
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
                .map(category -> category.categoryName)
                .collect(Collectors.toList());
    }

    private Region getCategoriesContainer() {
        return guiRobot.from(node).lookup(CATEGORIES_FIELD_ID).query();
    }

    public boolean isSameTask(ReadOnlyTask task) {
       return getFullName().equals(task.getName().fullName)
                && getPriority().equals(task.getPriorityLevel().value)
                && getStartDateTime().equals(task.getStartDateTime().value)
                && getEndDateTime().equals(task.getEndDateTime().value)
                && getInformation().equals(task.getInformation().value)
                && getRecurrence().equals(task.getRecurrence().toString())
                && getCategories().equals(getCategories(task.getCategories()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getPriority().equals(handle.getPriority())
                    && getStartDateTime().equals(handle.getStartDateTime())
                    && getEndDateTime().equals(handle.getEndDateTime())
                    && getInformation().equals(handle.getInformation())
                    && getRecurrence().equals(handle.getRecurrence())
                    && getCategories().equals(handle.getCategories());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getInformation();
    }
}
