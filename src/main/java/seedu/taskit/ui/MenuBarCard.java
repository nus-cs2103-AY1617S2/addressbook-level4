package seedu.taskit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

//@@author A0141872E
/**
 * MenuBarCard defines format for individual menu bar item.
 */
public class MenuBarCard extends UiPart<Region> {

    private static final String FXML = "MenuBarCard.fxml";

    @FXML
    private ImageView imageView;
    @FXML
    private HBox menuBarCardPane;
    @FXML
    private Label title;

    public MenuBarCard(String label, String path) {
        super(FXML);
        title.setText(label);
        imageView.setImage(new Image(path));
    }

}
