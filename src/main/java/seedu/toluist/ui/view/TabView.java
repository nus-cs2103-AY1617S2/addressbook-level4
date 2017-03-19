package seedu.toluist.ui.view;

import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.ui.UiStore;

/**
 * View to display the individual tab
 */
public class TabView extends UiView {
    private static final String SELECTED_TAB_CLASS = "selected";
    private static final String FXML = "TabView.fxml";

    @FXML
    private HBox tabPane;
    @FXML
    private Label tabLabel;
    @FXML
    private Label tabShortcutLabel;
    private final TaskSwitchPredicate switchPredicate;
    private final boolean isSelected;

    public TabView(TaskSwitchPredicate switchPredicate, boolean isSelected) {
        super(FXML);
        this.switchPredicate = switchPredicate;
        this.isSelected = isSelected;
    }

    @Override
    protected void viewDidMount() {
        UiStore uiStore = UiStore.getInstance();
        String taskInfo = " ("
                + uiStore.getTasks().stream()
                         .filter(switchPredicate.getPredicate()).collect(Collectors.toList()).size()
                + "/" + uiStore.getTasks().size() + ")";
        tabShortcutLabel.setText(switchPredicate.getDisplayName().substring(0, 1));
        tabLabel.setText(switchPredicate.getDisplayName().substring(1) + taskInfo);
        if (isSelected) {
            tabPane.getStyleClass().add(SELECTED_TAB_CLASS);
        }
    }
}
