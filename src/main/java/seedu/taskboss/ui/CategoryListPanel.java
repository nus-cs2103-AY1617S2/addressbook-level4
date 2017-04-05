package seedu.taskboss.ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.taskboss.commons.core.LogsCenter;
import seedu.taskboss.commons.events.model.TaskBossChangedEvent;
import seedu.taskboss.commons.events.ui.CategoryListPanelViewingChangedEvent;
import seedu.taskboss.commons.util.FxViewUtil;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;

//@@author A0143157J
public class CategoryListPanel extends UiPart<Region> {

    private final Logger logger = LogsCenter.getLogger(CategoryListPanel.class);

    private static final int AMOUNT_ONE = 1;
    private static final String FXML = "CategoryListPanel.fxml";
    private ObservableList<ReadOnlyTask> tasks;
    private ObservableList<Category> categories;
    private HashMap<Category, Integer> categoryHm;

    @FXML
    private ListView<Category> categoryListView;

    public CategoryListPanel(AnchorPane categoryListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        tasks = taskList;
        updateCategoryCountToHashMap();
        initCategories();
        setConnections();
        addToPlaceholder(categoryListPlaceholder);
        registerAsAnEventHandler(this);
        setEventHandlerForViewingChangeEvent();
    }

    /**
     * Compute task counts in each category,
     * and save them into {@code HashMap<Category, Integer>} categoryHm
     */
    public void updateCategoryCountToHashMap() {
        categoryHm = new HashMap<Category, Integer>();

        for (ReadOnlyTask task : tasks) {
            for (Category category : task.getCategories()) {
                if (!categoryHm.containsKey(category)) {
                    categoryHm.put(category, AMOUNT_ONE);
                } else {
                    categoryHm.put(category, categoryHm.get(category) + AMOUNT_ONE);
                }
            }
        }
    }

    /**
     * Initializes {@code ObservableList<Category>} categories
     */
    private void initCategories() {
        categories = FXCollections.observableArrayList();
        for (Entry<Category, Integer> entry : categoryHm.entrySet()) {
            if (entry.getValue() > 0) {
                categories.add(entry.getKey());
            }
        }
        sortCategoryList();
    }

    /**
     * Sort category list according to alphabetical order,
     * but with Alltasks always on top, and Done always at the bottom.
     */
    private void sortCategoryList() {
        Comparator<Category> categoryCmp = new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                if (o1.categoryName.equals("Alltasks"))
                    return -1;
                else if (o2.categoryName.equals("Alltasks"))
                    return 1;
                else if (o1.categoryName.equals("Done"))
                    return 1;
                else if (o2.categoryName.equals("Done"))
                    return -1;
                else
                    return o1.categoryName.compareTo(o2.categoryName);
            }
        };

        FXCollections.sort(categories, categoryCmp);
    }

    /**
     * Subscribe to changes in TaskBoss and
     * updates categories in the CategoryListPanel accordingly
     */
    @Subscribe
    public void handleTaskBossChangedEvent(TaskBossChangedEvent tmce) {
        updateCategoryCountToHashMap();
        initCategories();
        setConnections();
    }

    private void setConnections() {
        categoryListView.setItems(categories);
        categoryListView.setCellFactory(listView -> new CategoryListViewCell());
        setEventHandlerForViewingChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForViewingChangeEvent() {
        categoryListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Viewing in category list panel changed to : '" + newValue + "'");
                        raise(new CategoryListPanelViewingChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            categoryListView.scrollTo(index);
            categoryListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class CategoryListViewCell extends ListCell<Category> {

        @Override
        protected void updateItem(Category category, boolean empty) {
            super.updateItem(category, empty);

            if (empty || category == null) {
                setGraphic(null);
                setText(null);
            } else {
                Integer taskCount = categoryHm.get(category);
                setGraphic(new CategoryCard(category, taskCount).getRoot());
            }
        }
    }

}
