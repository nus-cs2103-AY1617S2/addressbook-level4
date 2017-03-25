//@@author A0131125Y
package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * View to display the indiviual tag
 */
public class TagView extends UiView {
    private static final String FXML = "TagView.fxml";

    @FXML
    protected Label tagLabel;
    private final String tagName;

    public TagView(String tagName) {
        super(FXML);
        this.tagName = tagName;
    }

    @Override
    protected void viewDidMount() {
        tagLabel.setText(tagName);
    }
}
