package seedu.doist.ui;

import org.fxmisc.richtext.InlineCssTextArea;

public class CommandHighlightManager {
    private static final String COMMAND_WORD_STYLE = "-fx-fill: orange;";
    private static final String PARAMETER_KEY_STYLE = "-fx-fill: green;";
    private static final String NORMAL_STYLE = "-fx-fill: black;";

    private static CommandHighlightManager instance;

    static CommandHighlightManager getInstance() {
        if (instance == null) {
            instance = new CommandHighlightManager();
        }
        return instance;
    }

    public void highlight(InlineCssTextArea commandTextField, String content) {
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
            if (key.equals("\\by") || key.equals("\\from") || key.equals("\\to")) {
                commandTextField.setStyle(i, i + 1, "-fx-fill: blue;");
            } else if (key.equals("\\as")) {
                commandTextField.setStyle(i, i + 1, "-fx-fill: #cd5c5c;");
            } else {
                commandTextField.setStyle(i, i + 1, NORMAL_STYLE);
            }
            i++;
        }
    }
}
