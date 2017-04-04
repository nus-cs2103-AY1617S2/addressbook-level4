package seedu.taskboss.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.taskboss.model.category.Category;

//@@author A0143157J
public class CategoryCard extends UiPart<Region> {

    private static final String FXML = "CategoryCard.fxml";

    @FXML
    private Label categoryText;

    @FXML
    private Label categoryTaskCount;

    public CategoryCard(Category category, int taskCount) {
        super(FXML);
        initCategories(category, taskCount);
    }

    private void initCategories(Category category, int taskCount) {
        categoryText.setText(category.categoryName);
        categoryTaskCount.setText(Integer.toString(taskCount));
    }
}
