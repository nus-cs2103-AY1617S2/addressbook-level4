package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.stage.Stage;

//@@author A0140887W
public class ChangeThemeTest extends DoistGUITest {

    private static final String LIGHT_THEME = "LightTheme";
    private static final String DARK_THEME = "DarkTheme";
    private final String lightThemeUrl = getClass().getResource("/view/LightTheme.css").toExternalForm();
    private final String darkThemeUrl = getClass().getResource("/view/DarkTheme.css").toExternalForm();

    @Test
    public void changeTheme() {
        //using menu button
        assertThemeIsChanged(mainMenu.changeThemeUsingMenu(DARK_THEME), DARK_THEME, darkThemeUrl);
        assertThemeIsChanged(mainMenu.changeThemeUsingMenu(LIGHT_THEME), LIGHT_THEME, lightThemeUrl);
        // TODO: use command
    }

    private void assertThemeIsChanged(Stage primaryStage, String themeName, String themeUrl) {
        assertTrue(primaryStage.getScene().getStylesheets().contains(themeUrl));
    }
}
