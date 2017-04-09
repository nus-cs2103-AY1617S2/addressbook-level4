//@@author A0131125Y
package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.ui.UiStore;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultView extends UiView {

    private static final String FXML = "ResultView.fxml";

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextArea resultDisplay;

    public ResultView() {
        super(FXML);
        makeFullWidth();
        configureBindings();
    }

    private void configureBindings() {
        UiStore store = UiStore.getInstance();
        store.bind(this, store.getObservableCommandResult());
    }

    @Override
    protected void viewDidMount() {
        registerAsAnEventHandler(this);
        String result = UiStore.getInstance().getObservableCommandResult().getValue().getFeedbackToUser();
        resultDisplay.setText(result);
    }

    private void makeFullWidth() {
        FxViewUtil.makeFullWidth(getRoot());
        FxViewUtil.makeFullWidth(mainPane);
        FxViewUtil.makeFullWidth(resultDisplay);
    }
}
