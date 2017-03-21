package seedu.address.ui;

import java.util.Arrays;
import java.util.TreeSet;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;

/**
 * Replaces TextField to provide auto-complete functionality
 */
public class AutoCompleteTextField extends TextField {

    /** Initialize set of prefix strings to auto complete. */
    public static final char PREFIXSYMBOL = '/';
    public static final String[] PREFIXSTRINGS = new String[] { PREFIXSYMBOL + "venue ",
            PREFIXSYMBOL + "from ",
            PREFIXSYMBOL + "to ",
            PREFIXSYMBOL + "level",
            PREFIXSYMBOL + "description" };
    public static final TreeSet<String> PREFIXCOMMANDS = new TreeSet<String>(Arrays.asList(PREFIXSTRINGS));

    /** List of suggestions for auto complete. */
    private ContextMenu suggestionsList;

    public AutoCompleteTextField() {
        super();
        suggestionsList = new ContextMenu();

        textProperty().addListener(new TextFieldListener(this, suggestionsList));
        focusedProperty().addListener(new FocusListener(this, suggestionsList));
    }

}