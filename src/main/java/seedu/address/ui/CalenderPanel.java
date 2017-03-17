package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FxViewUtil;

/**
 * The Calender Panel of the App.
 */
//tutorial
//https://www.youtube.com/watch?v=HiZ-glk9_LE&t=568s
public class CalenderPanel extends UiPart<Region> {
    private static final String FXML = "CalenderPanel.fxml";
    @FXML
    private ListView<String> listview1;

    @FXML
    private Label label1;

    public CalenderPanel(AnchorPane calendertPlaceholder) {
        super(FXML);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        ObservableList<String> data = FXCollections.observableArrayList("hey", "you");
        label1.setText("still working on it v0.0");
        listview1.getItems().addAll("eat pizza", "go to gym");
        calendertPlaceholder.getChildren().add(getRoot());
    }
}
