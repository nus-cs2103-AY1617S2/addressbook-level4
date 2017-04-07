//@@author A0131125Y
package seedu.toluist.ui.view;

import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.ui.UiStore;

/**
 * View to display the individual tab
 */
public class TabView extends UiView {
    private static final String STYLE_CLASS_SELECTED_TAB = "selected";
    private static final String FXML = "TabView.fxml";
    private static final String TAB_LABEL_TEMPLATE = "%s (%d/%d)";

    @FXML
    private HBox tabPane;
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
        UiStore uiStore = UiStore.getInstance();
        int numberOfShownTasks = uiStore.getTasks().stream()
                .filter(switchPredicate.getPredicate()).collect(Collectors.toList()).size();
        int numberOfTotalTasks = uiStore.getTasks().size();
        tabLabel.setText(String.format(TAB_LABEL_TEMPLATE, switchPredicate.getDisplayName(),
                numberOfShownTasks, numberOfTotalTasks));
        if (isSelected) {
            FxViewUtil.addStyleClass(tabPane, STYLE_CLASS_SELECTED_TAB);
        }
    }
}
