package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class LabelCard extends UiPart<Region> {

    private static final String FXML = "LabelListCard.fxml";

    @FXML
    private Label labelText;

    public LabelCard(seedu.address.model.label.Label labels) {
        super(FXML);
        initLabel(labels);
    }

    private void initLabel(seedu.address.model.label.Label labelToSet) {
        labelText.setText(labelToSet.getLabelName());
    }
}
