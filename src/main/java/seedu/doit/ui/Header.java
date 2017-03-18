package seedu.doit.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.doit.commons.util.FxViewUtil;

/**
 * A ui for the header that is displayed at the top of the lists.
 */
public class Header extends UiPart<Region> {
    private static final String FXML = "StatusBarFooter.fxml";
    @FXML
    private Header header;

    public Header(AnchorPane placeHolder) {
        super(FXML);
        addToPlaceholder(placeHolder);
    }

    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }
}
