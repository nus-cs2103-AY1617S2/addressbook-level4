package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class EventTaskCardHandle extends GuiHandle {
    private static final String TASKNAME_FIELD_ID = "#taskname";
    private static final String STARTDATETIME_FIELD_ID = "#startDateTime";
    private static final String ENDDATETIME_FIELD_ID = "#endDateTime";
    private static final String COMPLETED_FIELD_ID = "#markedCompleted";
    private static final String CATEGORIES_FIELD_ID = "#categories";

    private Node node;

    public EventTaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTaskName() {
        return getTextFromLabel(TASKNAME_FIELD_ID);
    }

    public String getStartDate() {
        String startDate = getTextFromLabel(STARTDATETIME_FIELD_ID);
        startDate = startDate.substring(12 , startDate.length() - 19);
        return startDate;
    }

    public String getStartTime() {
        String startTime = getTextFromLabel(STARTDATETIME_FIELD_ID);
        startTime = startTime.substring(startTime.length() - 4);
        return startTime;
    }

    public String getEndDate() {
        String endDate = getTextFromLabel(ENDDATETIME_FIELD_ID);
        endDate = endDate.substring(12 , endDate.length() - 19);
        return endDate;
    }

    public String getEndTime() {
        String endTime = getTextFromLabel(ENDDATETIME_FIELD_ID);
        endTime = endTime.substring(endTime.length() - 4);
        return endTime;
    }

    public String getIsMarkedAsCompleted() {
        return getTextFromLabel(COMPLETED_FIELD_ID);
    }

    public List<String> getCategories() {
        return getCategories(getCategoriesContainer());
    }

    private List<String> getCategories(Region categoriesContainer) {
        return categoriesContainer.getChildrenUnmodifiable().stream().map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getCategories(UniqueCategoryList categories) {
        return categories.asObservableList().stream().map(category -> category.categoryName)
                .collect(Collectors.toList());
    }

    private Region getCategoriesContainer() {
        return guiRobot.from(node).lookup(CATEGORIES_FIELD_ID).query();
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return task != null || this == task && getTaskName().equals(task.getTaskName())
                && getStartDate().equals(task.getStartDate()) && getStartTime().equals(task.getStartTime())
                && getEndDate().equals(task.getEndDate()) && getEndTime().equals(task.getEndTime())
                && getIsMarkedAsCompleted().equals(task.getIsMarkedAsComplete())
                && getCategories().equals(getCategories(task.getCategories()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventTaskCardHandle) {
            EventTaskCardHandle handle = (EventTaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName()) && getStartDate().equals(handle.getStartDate())
                    && getStartTime().equals(handle.getStartTime()) && getEndDate().equals(handle.getEndDate())
                    && getEndTime().equals(handle.getEndTime())
                    && getIsMarkedAsCompleted().equals(handle.getIsMarkedAsCompleted())
                    && getCategories().equals(handle.getCategories());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getStartDate() + " " + getStartTime() + " " + getEndDate() + " " + getEndTime();
    }
}
