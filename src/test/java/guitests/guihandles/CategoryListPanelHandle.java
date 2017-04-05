package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.taskboss.TestApp;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.testutil.TestCategory;
import seedu.taskboss.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the category list.
 */
public class CategoryListPanelHandle extends GuiHandle{

    private static final String CATEGORY_LIST_VIEW_ID = "#categoryListView";
    private static final String CATEGORY_CARD_PANE_ID = "#categoryCardPane";

    public CategoryListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Category> getViewingCategories() {
        ListView<Category> categoryList = getListView();
        return categoryList.getSelectionModel().getSelectedItems();
    }

    public ListView<Category> getListView() {
        return getNode(CATEGORY_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the category details correctly and in correct order.
     * @param expectedCategoryList A list of categories in the correct order.
     */
    public boolean isListMatching(List<TestCategory> expectedCategoryList) {
        return this.isListMatching(0, expectedCategoryList);
    }

    /**
     * Returns true if the list is showing the category details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param expectedCategoryList A list of categories in the correct order.
     */
    public boolean isListMatching(int startPosition, List<TestCategory> expectedCategoryList) throws IllegalArgumentException {
        if (expectedCategoryList.size() + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, expectedCategoryList));
        for (int i = 0; i < expectedCategoryList.size(); i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndCategory(getCategoryCardHandle(expectedCategoryList.get(i)), expectedCategoryList.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the {@code categories} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, List<TestCategory> expectedCategoryList) {
        List<Category> categoriesInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + expectedCategoryList.size() > categoriesInList.size()) {
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < expectedCategoryList.size(); i++) {
            if (!categoriesInList.get(startPosition + i).categoryName.equals(expectedCategoryList.get(i).categoryName)) {
                return false;
            }
        }

        return true;
    }

    public CategoryCardHandle getCategoryCardHandle(String name) throws IllegalValueException {
        return getCategoryCardHandle(new TestCategory(name));
    }

    public CategoryCardHandle getCategoryCardHandle(TestCategory category) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> categoryCardNode = nodes.stream()
                .filter(n -> new CategoryCardHandle(guiRobot, primaryStage, n).isSameCategory(category))
                .findFirst();
        if (categoryCardNode.isPresent()) {
            return new CategoryCardHandle(guiRobot, primaryStage, categoryCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CATEGORY_CARD_PANE_ID).queryAll();
    }
}
