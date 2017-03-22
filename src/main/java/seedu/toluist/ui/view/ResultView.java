//@@author A0131125Y
package seedu.toluist.ui.view;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.events.ui.NewResultAvailableEvent;
import seedu.toluist.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultView extends UiView {

    private static final Logger logger = LogsCenter.getLogger(ResultView.class);
    private static final String FXML = "ResultView.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextArea resultDisplay;

    public ResultView () {
        super(FXML);
    }

    @Override
    protected void viewDidMount() {
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
        makeFullWidth();
    }

    private void makeFullWidth() {
        FxViewUtil.makeFullWidth(getRoot());
        FxViewUtil.makeFullWidth(mainPane);
        FxViewUtil.makeFullWidth(resultDisplay);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayed.setValue(event.message);
    }
}
