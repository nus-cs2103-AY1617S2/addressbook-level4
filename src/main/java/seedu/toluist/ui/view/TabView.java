package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.toluist.model.TaskSwitchPredicate;

/**
 * View to display the tab bar
 */
public class TabView extends UiView {
    private static final String SELECTED_TAB_CLASS = "selected";
    private static final String FXML = "TabView.fxml";

    @FXML
    private Label tabLabel;
    private final TaskSwitchPredicate switchPredicate;
    private final boolean isSelected;

    public TabView(TaskSwitchPredicate switchPredicate, boolean isSelected) {
        super(FXML);
        this.switchPredicate = switchPredicate;
        this.isSelected = isSelected;
    }

    @Override
    protected void viewDidMount() {
        tabLabel.setText(switchPredicate.getDisplayName());
        if (isSelected) {
            tabLabel.getStyleClass().add(SELECTED_TAB_CLASS);
        }
    }
}
