package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.taskboss.testutil.TestCategory;

public class CategoryCardHandle extends GuiHandle {
    private static final String CATEGORY_FIELD_ID = "#categoryText";
    private static final String CATEGORY_TASK_COUNT_FIELD_ID = "#categoryTaskCount";

    private Node node;

    public CategoryCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getCategoryName() {
        return getTextFromLabel(CATEGORY_FIELD_ID);
    }

    public String getCategoryTaskCount() {
        return getTextFromLabel(CATEGORY_TASK_COUNT_FIELD_ID);
    }

    public boolean isSameCategory(TestCategory category) {
        return getCategoryName().equals(category.categoryName)
                && getCategoryTaskCount().equals(category.getTaskCount());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CategoryCardHandle) {
            CategoryCardHandle handle = (CategoryCardHandle) obj;
            return getCategoryName().equals(handle.getCategoryName())
                    && getCategoryTaskCount().equals(handle.getCategoryTaskCount());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getCategoryName() + " " + getCategoryTaskCount();
    }
}
