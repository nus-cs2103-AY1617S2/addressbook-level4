package seedu.doist.ui.util;

import static seedu.doist.logic.parser.CliSyntax.PREFIX_AS;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_UNDER;

import org.fxmisc.richtext.InlineCssTextArea;

//@@author A0147980U
public class CommandHighlightController {
    public static final String COMMAND_WORD_STYLE = "-fx-fill: #1a75ff;";  // blue"
    public static final String PARAMETER_KEY_STYLE = "-fx-fill: #55ae47;";  // green
    public static final String TIME_VALUE_STYLE = "-fx-fill: #e68a00;";  // orange
    public static final String PRIORITY_VALUE_STYLE = "-fx-fill: #cd5c5c;";  // red
    public static final String TAGS_STYLE = "-fx-fill: #7300e6;";  // purple
    public static final String NORMAL_STYLE = "-fx-fill: black;";

    private static CommandHighlightController instance;

    // for singleton pattern
    public static CommandHighlightController getInstance() {
        if (instance == null) {
            instance = new CommandHighlightController();
        }
        return instance;
    }

    public void highlight(InlineCssTextArea commandTextField) {
        String content = commandTextField.getText();
        int position = 0;

        // highlight command word, which is the start of a command
        while (position < content.length() && content.charAt(position) != ' ') {
            commandTextField.setStyle(position, position + 1, COMMAND_WORD_STYLE);
            position++;
        }

        // highlight the following parameters, which are key-value pairs
        String key = "";
        while (position < content.length()) {
            if (content.charAt(position) == '\\') {
                StringBuilder keyBuilder = new StringBuilder();
                while (position < content.length() && content.charAt(position) != ' ') {
                    commandTextField.setStyle(position, position + 1, PARAMETER_KEY_STYLE);
                    keyBuilder.append(content.charAt(position));
                    position++;
                }
                key = keyBuilder.toString();
            }
            if (position >= content.length()) {
                break;
            }

            highlightCharacterOfParameterValue(position, key, commandTextField);
            position++;
        }
    }

    /**
     * highlight a single character inside a "value" string paired up with the input "key"
     * @param position  the position at which the character to be highlighted
     * @param key       the closest parameter key before this character
     * @param commandTextField
     */
    private void highlightCharacterOfParameterValue(int position, String key, InlineCssTextArea commandTextField) {
        if (isTimeRelatedKey(key)) {
            commandTextField.setStyle(position, position + 1, TIME_VALUE_STYLE);
        } else if (key.equals(PREFIX_AS.toString())) {
            commandTextField.setStyle(position, position + 1, PRIORITY_VALUE_STYLE);
        } else if (key.equals(PREFIX_UNDER.toString())) {
            commandTextField.setStyle(position, position + 1, TAGS_STYLE);
        } else {
            commandTextField.setStyle(position, position + 1, NORMAL_STYLE);
        }
    }

    private boolean isTimeRelatedKey(String key) {
        return key.equals(PREFIX_BY.toString()) ||
               key.equals(PREFIX_FROM.toString()) ||
               key.equals(PREFIX_TO.toString());
    }
}





