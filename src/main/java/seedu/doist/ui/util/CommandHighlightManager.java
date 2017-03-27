package seedu.doist.ui.util;

import static seedu.doist.logic.parser.CliSyntax.PREFIX_AS;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_UNDER;

import org.fxmisc.richtext.InlineCssTextArea;

//@@author A0147980U
public class CommandHighlightManager {
    public static final String COMMAND_WORD_STYLE = "-fx-fill: #1a75ff;";  // blue"
    public static final String PARAMETER_KEY_STYLE = "-fx-fill: #55ae47;";  // green
    public static final String TIME_VALUE_STYLE = "-fx-fill: #e68a00;";  // orange
    public static final String PRIORITY_VALUE_STYLE = "-fx-fill: #cd5c5c;";  // red
    public static final String TAGS_STYLE = "-fx-fill: #7300e6;";  // purple
    public static final String NORMAL_STYLE = "-fx-fill: black;";

    private static CommandHighlightManager instance;

    // for singleton pattern
    public static CommandHighlightManager getInstance() {
        if (instance == null) {
            instance = new CommandHighlightManager();
        }
        return instance;
    }

    public void highlight(InlineCssTextArea commandTextField) {
        String content = commandTextField.getText();
        int i = 0;
        while (i < content.length() && content.charAt(i) != ' ') {
            commandTextField.setStyle(i, i + 1, COMMAND_WORD_STYLE);
            i++;
        }
        String key = "";
        while (i < content.length()) {
            if (content.charAt(i) == '\\') {
                StringBuilder keyBuilder = new StringBuilder();
                while (i < content.length() && content.charAt(i) != ' ') {
                    commandTextField.setStyle(i, i + 1, PARAMETER_KEY_STYLE);
                    keyBuilder.append(content.charAt(i));
                    i++;
                }
                key = keyBuilder.toString();
            }
            if (i >= content.length()) {
                break;
            }
            if (key.equals(PREFIX_BY.toString()) ||
                key.equals(PREFIX_FROM.toString()) ||
                key.equals(PREFIX_TO.toString())) {
                commandTextField.setStyle(i, i + 1, TIME_VALUE_STYLE);
            } else if (key.equals(PREFIX_AS.toString())) {
                commandTextField.setStyle(i, i + 1, PRIORITY_VALUE_STYLE);
            } else if (key.equals(PREFIX_UNDER.toString())) {
                commandTextField.setStyle(i, i + 1, TAGS_STYLE);
            } else {
                commandTextField.setStyle(i, i + 1, NORMAL_STYLE);
            }
            i++;
        }
    }
}
