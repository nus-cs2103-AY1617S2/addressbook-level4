//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Sort Controller is responsible for changing the order of the displayed tasks
 */
public class SortController extends Controller {
    private static final String MESSAGE_RESULT = "List is now sorted by: %s.";
    private static final String MESSAGE_ERROR = "Unable to sort by: %s";
    private static final String MESSAGE_NO_VALID_KEYWORD = "No valid keyword entered."
            + " Please type 'help sort' for details";
    private static final String MESSAGE_MULTIPLE_KEYWORDS_AND_DEFAULT = "'Default' keyword may not"
            + " be used with other parameters.";
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*sort.*";
    private static final String COMMAND_WORD = "sort";
    private static final String WORD_BY = "by";


    private static final String[] KEYWORD_CATEGORIES = {
        Task.CATEGORY_DEFAULT, Task.CATEGORY_DESCRIPTION, Task.CATEGORY_ENDDATE,
        Task.CATEGORY_OVERDUE, Task.CATEGORY_PRIORITY, Task.CATEGORY_STARTDATE
    };
    private static final String PARAMETER_CATEGORY = "category";

    private static final Logger logger = LogsCenter.getLogger(SortController.class);

    private static final String HELP_DETAILS = "Changes the sorting order of the tasks.";
    private static final String HELP_FORMAT = "sort CATEGORY(priority/overdue/enddate/startdate/description/default)";
    private static final String[] HELP_COMMENTS = { "The default sorting order is: "
                                                    + "overdue, priority, end date, start date, description",
                                                    "Using this command will push the chosen category"
                                                    + " to the top of the sorting order." };
    private static final String[] HELP_EXAMPLES = { "`sort priority`\nReorders the task list by description.",
                                                    "`sort overdue`\nReorders the task list by description.",
                                                    "`sort enddate`\nReorders the task list by description.",
                                                    "`sort startdate`\nReorders the task list by description.",
                                                    "`sort description`\nReorders the task list by description.",
                                                    "`sort default`\nResets the task list to the default ordering." };

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass().toString() + " will handle command");

        String keywords = tokens.get(PARAMETER_CATEGORY);
        if (keywords.equals(StringUtil.EMPTY_STRING)) {
            throw new InvalidCommandException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, COMMAND_WORD));
        }
        ArrayList<String> keywordList = new ArrayList<String>(Arrays.asList(StringUtil.convertToArray(keywords)));
        if (keywordList.contains(Task.CATEGORY_DEFAULT) && keywordList.size() > 1) {
            throw new InvalidCommandException(MESSAGE_MULTIPLE_KEYWORDS_AND_DEFAULT);
        }
        ArrayList<String> invalidKeywords = new ArrayList<String>();
        removeInvalidKeywords(keywordList, invalidKeywords);

        sortByKeywords(keywordList);

        displayResult(invalidKeywords, keywordList);
    }

    private void displayResult(ArrayList<String> invalidKeywords, ArrayList<String> keywordList)
            throws InvalidCommandException {
        String[] resultantOrder = Task.getCurrentSort();
        String resultMessage = StringUtil.EMPTY_STRING;
        UiStore uiStore = UiStore.getInstance();
        if (keywordList.isEmpty()) {
            throw new InvalidCommandException(MESSAGE_NO_VALID_KEYWORD);
        }
        if (!invalidKeywords.isEmpty()) {
            resultMessage += String.format(MESSAGE_ERROR, String.join(StringUtil.COMMA_DELIMITER, invalidKeywords));
            resultMessage += StringUtil.NEW_LINE;
        }
        resultMessage += String.format(MESSAGE_RESULT, String.join(StringUtil.COMMA_DELIMITER, resultantOrder));
        uiStore.setTasks(TodoList.getInstance().getTasks());
        uiStore.setCommandResult(new CommandResult(resultMessage));
    }

    private void sortByKeywords(ArrayList<String> keywordList) {
        for (int i = keywordList.size() - 1; i >= 0; i--) {
            Task.sortBy(keywordList.get(i).toLowerCase());
        }
    }

    private void removeInvalidKeywords(ArrayList<String> keywordList, ArrayList<String> invalidKeywords) {
        for (String keyword : keywordList) {
            if (!isKeywordInCategoryList(keyword)) {
                invalidKeywords.add(keyword);
            }
        }
        for (String keyword : invalidKeywords) {
            keywordList.remove(keyword);
        }
    }

    private boolean isKeywordInCategoryList(String keyword) {
        for (String category : KEYWORD_CATEGORIES) {
            if (keyword.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();
        String replacedCommand = Pattern.compile(WORD_BY, Pattern.CASE_INSENSITIVE).matcher(command)
                .replaceFirst(StringUtil.EMPTY_STRING);
        replacedCommand = Pattern.compile(COMMAND_WORD, Pattern.CASE_INSENSITIVE).matcher(replacedCommand)
                .replaceFirst(StringUtil.EMPTY_STRING).trim();

        tokens.put(PARAMETER_CATEGORY, replacedCommand);
        return tokens;
    }

    public Map<String, String[]> getCommandKeywordMap() {
        Map<String, String[]> keywords = new HashMap<>();
        for (String category : KEYWORD_CATEGORIES) {
            keywords.put(category, new String[0]);
        }
        return keywords;
    }

    public String[][][] getConflictingKeywordsList() {
        return new String[][][] { new String[][] {
            new String[] { Task.CATEGORY_DEFAULT },
            new String[] { Task.CATEGORY_DESCRIPTION, Task.CATEGORY_ENDDATE,
                Task.CATEGORY_OVERDUE, Task.CATEGORY_PRIORITY, Task.CATEGORY_STARTDATE
            }
        }};
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
