//@@author A0131125Y
package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import seedu.toluist.commons.core.SwitchConfig;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.ui.UiStore;

/**
 * View to display the tab bar
 */
public class TabBarView extends UiView {

    private static final String FXML = "TabBarView.fxml";

    @FXML
    private Pane tabContainer;
    private SwitchConfig switchConfig = SwitchConfig.getDefaultSwitchConfig();

    public TabBarView() {
        super(FXML);
        configureBindings();
    }

    private void configureBindings() {
        UiStore store = UiStore.getInstance();
        store.bind(this, store.getObservableSwitchPredicate());
        store.bind(this, store.getObservableTasks());
    }

    @Override
    protected void viewDidMount() {
        FxViewUtil.makeFullWidth(getRoot());
        tabContainer.getChildren().clear();
        for (TaskSwitchPredicate switchPredicate : switchConfig.getAllPredicates()) {
            TabView tabView = new TabView(switchPredicate,
                    switchPredicate.equals(UiStore.getInstance().getObservableSwitchPredicate().getValue()));
            tabView.setParent(tabContainer);
            tabView.render();
        }
    }
}
