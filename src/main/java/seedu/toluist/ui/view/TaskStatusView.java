//@@author A0131125Y
package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * View to display the task's status, e.g. high priority / overdue
 */
public class TaskStatusView extends UiView {
    private static final String FXML = "TaskStatusView.fxml";

    @FXML
    private ImageView imageView;
    private final Image image;

    public TaskStatusView(Image image) {
        super(FXML);
        this.image = image;
    }

    @Override
    protected void viewDidMount() {
        imageView.setImage(image);
    }
}
