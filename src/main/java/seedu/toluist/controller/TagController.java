//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.LogsCenter;

import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Searches the task list for matches in the parameters, and displays the results received
 */
public class TagController extends Controller {
    private static final String COMMAND_TAG_WORD = "tag";

    private static final String PARAMETER_INDEX = "index";
    private static final String PARAMETER_KEYWORDS = "keywords";

    private static final int NUMBER_OF_SPLITS_FOR_COMMAND_PARSE = 2;
    private static final String COMMAND_SPLITTER_REGEX = " ";
    private static final int SECTION_INDEX = 0;
    private static final int SECTION_KEYWORDS = 1;

    private static final String MESSAGE_TEMPLATE_SUCCESS = "Successfully added \"%s\".\n";
    private static final String MESSAGE_TEMPLATE_FAIL = "Failed to add \"%s\".\n";
    private static final String MESSAGE_TEMPLATE_RESULT = "%s%s successfully added.";

    private static final String HELP_DETAILS = "Adds a tag(s) to an existing task.";
    private static final String HELP_FORMAT = "tag INDEX TAG(S)";
    private static final String[] HELP_COMMENTS = { "Related commands: `untag`",
                                                    "All tags are one word long.",
                                                    "Each word entered after the index will be its own tag.", };
    private static final String[] HELP_EXAMPLES = { "`tag 1 schoolwork`\n"
                                                        + "Adds the tag `schoolwork` to the task at index 1.",
                                                    "`tag 1 housework groceries`\nAdds the tags "
                                                        + "`housework` and `groceries` to the task at index 1." };

    private static final Logger logger = LogsCenter.getLogger(TagController.class);

    public void execute(String command) {
        logger.info(getClass() + "will handle command");

        // initialize keywords and variables for searching
        HashMap<String, String> tokens = tokenize(command);
        String[] keywordList = StringUtil.convertToArray(tokens.get(PARAMETER_KEYWORDS));
        int index = Integer.parseInt(tokens.get(PARAMETER_INDEX)) - 1;
        TodoList todoList = TodoList.getInstance();
        Task task = UiStore.getInstance().getShownTasks().get(index);
        ArrayList<String> successfulList = new ArrayList<>();
        ArrayList<String> failedList = new ArrayList<>();

        for (String keyword : keywordList) {
            if (task.addTag(new Tag(keyword))) {
                successfulList.add(keyword);
            } else {
                failedList.add(keyword);
            }
        }

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        // display formatting
        uiStore.setCommandResult(formatDisplay(successfulList.toArray(new String[successfulList.size()]),
                                failedList.toArray(new String[failedList.size()]),
                                successfulList.size()));
    }

    private CommandResult formatDisplay(String[] successfulList, String[] failedList, int successCount) {
        String successWords = String.join("\", \"", successfulList);
        String failWords = String.join("\", \"", failedList);
        String resultMessage = "";

        if (successfulList.length > 0) {
            resultMessage += String.format(MESSAGE_TEMPLATE_SUCCESS, successWords);
        }
        if (failedList.length > 0) {
            resultMessage += String.format(MESSAGE_TEMPLATE_FAIL, failWords);
        }

        return new CommandResult(String.format(MESSAGE_TEMPLATE_RESULT, resultMessage,
                StringUtil.nounWithCount("tag", successCount)));
    }

    public HashMap<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();

        String replacedCommand = Pattern.compile(COMMAND_TAG_WORD, Pattern.CASE_INSENSITIVE).matcher(command)
                .replaceFirst("").trim();
        String[] listOfParameters = replacedCommand
                .split(COMMAND_SPLITTER_REGEX, NUMBER_OF_SPLITS_FOR_COMMAND_PARSE);
        tokens.put(PARAMETER_INDEX, listOfParameters[SECTION_INDEX]);
        tokens.put(PARAMETER_KEYWORDS, listOfParameters[SECTION_KEYWORDS]);

        return tokens;
    }

    public boolean matchesCommand(String command) {
        String trimmedAndLowercasedCommand = command.trim().toLowerCase();
        return trimmedAndLowercasedCommand.startsWith(COMMAND_TAG_WORD.toLowerCase());
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_TAG_WORD };
    }

    public static String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_DETAILS, HELP_FORMAT };
    }

    public static String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
