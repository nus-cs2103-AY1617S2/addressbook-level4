import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import seedu.taskboss.model.category.Category;

public class CategoryListPanel {

    @FXML
    private ListView<Category> categoryListView;

    //@@author A0143157J-unused
    /**
     * Sets event handler to notify subscribers of the CategoryListPanelViewingChangedEvent.
     *
     * Unused reason:
     * Refer to reason under CategoryListPanelViewingChangedEvent.
     */
    private void setEventHandlerForViewingChangeEvent() {
        categoryListView.getSelectionModel().selectedItemProperty()
          .addListener((observable, oldValue, newValue) -> {
              if (newValue != null) {
                  logger.fine("Viewing in category list panel changed to : '" + newValue + "'");
                  raise(new CategoryListPanelViewingChangedEvent(newValue));
              }
          });
    }
}
