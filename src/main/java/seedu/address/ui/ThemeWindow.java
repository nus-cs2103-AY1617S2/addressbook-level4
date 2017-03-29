package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.UserPrefs;

/**
 * The Theme Window. Provides a list of css files to load in order to change the application theme.
 * Under the list, a file browsing entry allows usage of an external css file.
 * The chosen configuration option is saved.
 */
public class ThemeWindow extends Window {

    protected static final String ICON = "/images/theme_icon.png";
    protected static final String FXML = "ThemeWindow.fxml";
    private static final String TITLE = "Theme Manager";

    @FXML
    private AnchorPane themeManagerPlaceholder;

    @FXML
    Parent fxmlToApply;

    private UserPrefs prefs;

    public ThemeWindow(Parent fxmlToApply, UserPrefs prefs) {
        super(FXML);

        setTitle(TITLE);
        FxViewUtil.setStageIcon(stage, ICON);
        this.fxmlToApply = fxmlToApply;
        this.prefs = prefs;
    }

    private AnchorPane getThemeManagerPlaceholder() {
        return themeManagerPlaceholder;
    }

    public void fillInnerParts() {
        new ThemeManager(getThemeManagerPlaceholder(), fxmlToApply, prefs);
    }

}
