package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.ui.UiStore;

/**
 * Status bar to display storage location status
 */
public class StatusBar extends UiView {
    private static final String FXML = "StatusBar.fxml";
    private static final String DATA_LOCATION_TEMPLATE = "Storage Path: %s";

    @FXML
    private Label storageLabel;

    public StatusBar() {
        super(FXML);
        FxViewUtil.makeFullWidth(getRoot());
        configureBindings();
    }

    @Override
    protected void viewDidMount() {
        storageLabel.setText(String.format(DATA_LOCATION_TEMPLATE,
                UiStore.getInstance().getObservableStoragePath().getValue()));
    }

    private void configureBindings() {
        UiStore store = UiStore.getInstance();
        store.bind(this, store.getObservableStoragePath());
    }
}
