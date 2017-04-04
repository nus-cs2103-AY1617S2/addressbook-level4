//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.LogsCenter;

import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Searches the task list for matches in the parameters, and displays the results received
 */
public class UntagController extends Controller {
    private static final String COMMAND_UNTAG_WORD = "untag";

    private static final String PARAMETER_INDEX = "index";
    private static final String PARAMETER_KEYWORDS = "keywords";

    private static final int NUMBER_OF_SPLITS_FOR_COMMAND_PARSE = 2;
    private static final String COMMAND_SPLITTER_REGEX = StringUtil.SINGLE_SPACE;
    private static final int SECTION_INDEX = 0;
    private static final int SECTION_KEYWORDS = 1;

    private static final String MESSAGE_TEMPLATE_SUCCESS = "Sucessfully removed \"%s\".\n";
    private static final String MESSAGE_TEMPLATE_FAIL = "Failed to remove \"%s\".\n";
    private static final String MESSAGE_TEMPLATE_RESULT = "%s%s successfully removed.";

    private static final String HELP_DETAILS = "Removes a tag or multiple tags from an existing task.";
    private static final String HELP_FORMAT = "untag INDEX TAG(S)";
    private static final String[] HELP_COMMENTS = { "Related commands: `tag`",
                                                    "All tags are one word long.",
                                                    "Each word entered after the index will be untagged separately.", };
    private static final String[] HELP_EXAMPLES = { "`untag 1 schoolwork`\n"
                                                        + "Removes the tag `schoolwork` from the task at index 1.",
                                                    "`untag 1 housework groceries`\nRemoves the tags "
                                                        + "`housework` and `groceries` from the task at index 1." };

    private static final Logger logger = LogsCenter.getLogger(UntagController.class);

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass() + "will handle command");

        // initialize keywords and variables for searching
        String[] keywordList = convertToArray(tokens.get(PARAMETER_KEYWORDS));
        int index = Integer.parseInt(tokens.get(PARAMETER_INDEX)) - 1;
        TodoList todoList = TodoList.getInstance();
        Task task = UiStore.getInstance().getShownTasks().get(index);
        ArrayList<String> successfulList = new ArrayList();
        ArrayList<String> failedList = new ArrayList();

        for (String keyword : keywordList) {
            if (task.removeTag(new Tag(keyword))) {
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

    //!!!!!same as FindController method
    private String[] convertToArray(String keywords) {
        if (keywords == null || keywords.trim().isEmpty()) {
            return new String[] { StringUtil.EMPTY_STRING };
        }

        String trimmedKeywords = keywords.trim();
        String[] keywordList = trimmedKeywords.split(StringUtil.WHITE_SPACE);
        ArrayList<String> replacementList = new ArrayList<>();
        for (String keyword : keywordList) {
            if (!keyword.equals(StringUtil.EMPTY_STRING)) {
                replacementList.add(keyword);
            }
        }
        return replacementList.toArray(new String[0]);
    }

    private CommandResult formatDisplay(String[] successfulList, String[] failedList, int successCount) {
        String successWords = String.join(StringUtil.QUOTE_DELIMITER, successfulList);
        String failWords = String.join(StringUtil.QUOTE_DELIMITER, failedList);
        String resultMessage = StringUtil.EMPTY_STRING;

        if (successfulList.length > 0) {
            resultMessage += String.format(MESSAGE_TEMPLATE_SUCCESS, successWords);
        }
        if (failedList.length > 0) {
            resultMessage += String.format(MESSAGE_TEMPLATE_FAIL, failWords);
        }

        return new CommandResult(String.format(MESSAGE_TEMPLATE_RESULT, resultMessage,
                StringUtil.nounWithCount(StringUtil.WORD_TAG, successCount)));
    }

    public Map<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap();

        command = Pattern.compile(COMMAND_UNTAG_WORD, Pattern.CASE_INSENSITIVE).matcher(command)
                .replaceFirst(StringUtil.EMPTY_STRING).trim();
        String[] listOfParameters = command.split(COMMAND_SPLITTER_REGEX, NUMBER_OF_SPLITS_FOR_COMMAND_PARSE);
        tokens.put(PARAMETER_INDEX, listOfParameters[SECTION_INDEX]);
        tokens.put(PARAMETER_KEYWORDS, listOfParameters[SECTION_KEYWORDS]);

        return tokens;
    }

    public boolean matchesCommand(String command) {
        String trimmedAndLowercasedCommand = command.trim().toLowerCase();
        return trimmedAndLowercasedCommand.startsWith(COMMAND_UNTAG_WORD);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_UNTAG_WORD };
    }

    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
