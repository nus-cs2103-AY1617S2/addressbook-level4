package seedu.taskboss.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seedu.taskboss.model.category.Category;

//@@author A0143157J
public class CategoryCard extends UiPart<Region> {

    private static final String FXML = "CategoryCard.fxml";

    @FXML
    private Label categoryText;

    @FXML
    private Label categoryTaskCount;

    @FXML
    private Rectangle taskCountRectangle;

    public CategoryCard(Category category, int taskCount) {
        super(FXML);
        initCategories(category, taskCount);
    }

    private void initCategories(Category category, int taskCount) {
        categoryText.setText(category.categoryName);
        categoryTaskCount.setText(Integer.toString(taskCount));
        if (category.categoryName.equals("Alltasks") || category.categoryName.equals("Done")) {
            taskCountRectangle.setFill(Color.web("F26177"));
        }
    }
}
