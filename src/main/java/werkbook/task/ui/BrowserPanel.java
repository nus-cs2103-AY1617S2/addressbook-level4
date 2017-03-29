package werkbook.task.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import werkbook.task.commons.util.FxViewUtil;
import werkbook.task.model.task.ReadOnlyTask;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private static final String DOG =
            "\n─────────▄──────────────▄────" +
            "\n────────▌▒█───────────▄▀▒▌───" +
            "\n────────▌▒▒▀▄───────▄▀▒▒▒▐───" +
            "\n───────▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐───" +
            "\n─────▄▄▀▒▒▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐───" +
            "\n───▄▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀██▀▒▌───" +
            "\n──▐▒▒▒▄▄▄▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄▒▒▌──" +
            "\n──▌▒▒▐▄█▀▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐──" +
            "\n─▐▒▒▒▒▒▒▒▒▒▒▒▌██▀▒▒▒▒▒▒▒▒▀▄▌─" +
            "\n─▌▒▀▄██▄▒▒▒▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌─" +
            "\n─▌▀▐▄█▄█▌▄▒▀▒▒▒▒▒▒░░░░░░▒▒▒▐─" +
            "\n▐▒▀▐▀▐▀▒▒▄▄▒▄▒▒▒▒▒░░░░░░▒▒▒▒▌" +
            "\n▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒░░░░░░▒▒▒▐─" +
            "\n─▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌─" +
            "\n─▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐──" +
            "\n──▀▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▌──" +
            "\n────▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀───" +
            "\n───▐▀▒▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀─────" +
            "\n──▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▀────────";

    private static final String MOTD = "Welcome back, Jim!" + DOG;

    @FXML
    private AnchorPane browser;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private Label description;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;

    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public BrowserPanel(AnchorPane placeholder) {
        super(FXML);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering
                                                     // events for typing inside
                                                     // the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browser);
    }

    //@@author A0139930B
    /**
     * Initializes the task panel with welcome message
     */
    void initTaskPanel() {
        name.setText(MOTD);
        description.setText("");
        startDateTime.setText("");
        endDateTime.setText("");
        clearTags();
    }

    /**
     * Loads the task panel with {@code task}
     * @param task
     */
    void loadTaskPanel(ReadOnlyTask task) {
        name.setText(task.getName().taskName);
        description.setText(task.getDescription().toString());
        startDateTime.setText(task.getStartDateTime().toString());
        endDateTime.setText(task.getEndDateTime().toString());
        initTags(task);
    }

    /**
     * Clears the task panel
     */
    void clearTaskPanel() {
        name.setText("Great job for getting things done! A doge is pleased." + DOG);
        description.setText("");
        startDateTime.setText("");
        endDateTime.setText("");
        clearTags();
    }

    /**
     * Clears the tags in the task panel
     */
    private void clearTags() {
        tags.getChildren().clear();
    }
    //@@author

    private void initTags(ReadOnlyTask task) {
        tags.getChildren().clear();
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
