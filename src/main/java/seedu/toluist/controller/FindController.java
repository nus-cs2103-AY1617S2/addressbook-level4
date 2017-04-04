//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.LogsCenter;

import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Searches the task list for matches in the parameters, and displays the results received
 */
public class FindController extends Controller {
    private static final String COMMAND_WORD_FIND = "find";
    private static final String COMMAND_WORD_FILTER = "filter";
    private static final String COMMAND_WORD_LIST = "list";

    private static final String PARAMETER_TAG = "/tag";
    private static final String PARAMETER_NAME = "/name";
    private static final String PARAMETER_NULL = StringUtil.EMPTY_STRING;
    public static final String PARAMETER_KEYWORDS = "keywords";

    private static final int NUMBER_OF_SPLITS_FOR_COMMAND_PARSE = 2;
    private static final String COMMAND_SPLITTER_REGEX = StringUtil.SINGLE_SPACE;
    private static final int SECTION_PARAMETER = 1;

    private static final String MESSAGE_RESULT_TEMPLATE_FIND = "Searching for \"%s\" by %s.\n%s found";
    private static final String MESSAGE_RESULT_TEMPLATE_LIST = "Listing all %s";
    private static final String MESSAGE_RESULT_TEMPLATE_TAB = ", %s of which are shown in the current tab.";
    private static final String MESSAGE_NAME = "name";
    private static final String MESSAGE_TAG = "tag";
    private static final String MESSAGE_NAME_AND_TAG = "name and tag";
    private static final String MESSAGE_STRING_JOINING = "\", \"";

    private static final String HELP_DETAILS = "Finds tasks whose names contain any of the given keywords.";
    private static final String HELP_FORMAT = "filter/list/find [KEYWORDS] [tag/] [name/]";
    private static final String[] HELP_COMMENTS = { "By default the name and tag is searched.",
                                                    "Using `tag/` will only search by tag",
                                                    "Using `name/` will only search by name.",
                                                    "Case insensitive. `find a` and `find A` are the same.",
                                                    "If no keyword is entered, the list of all tasks is displayed." };
    private static final String[] HELP_EXAMPLES = { "`find a`\nFinds tasks with `a` in the name or tag.",
                                                    "`filter b tag/ name/`\nFinds tasks with `b` in the name or tag.",
                                                    "`list c name/`\nFinds tasks with `c` in the name.",
                                                    "`find d tag/`\nFinds tasks with `d` in the tag.",
                                                    "`filter\nLists all tasks." };

    private static final Logger logger = LogsCenter.getLogger(FindController.class);

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass() + "will handle command");

        // initialize keywords and variables for searching
        boolean isSearchByTag = tokens.get(PARAMETER_NAME) == null;
        boolean isSearchByName = tokens.get(PARAMETER_TAG) == null;
        String[] keywordList = StringUtil.convertToArray(tokens.get(PARAMETER_KEYWORDS));

        Predicate<Task> taskPredicate = task ->
                (isSearchByTag && task.isAnyKeywordsContainedInAnyTagIgnoreCase(keywordList)
                || (isSearchByName && task.isAnyKeywordsContainedInDescriptionIgnoreCase(keywordList)));


        ArrayList<Task> foundTasksList = TodoList.getInstance().getFilterTasks(taskPredicate);
        uiStore.setTasks(foundTasksList);

        // display formatting
        uiStore.setCommandResult(
                formatDisplay(isSearchByTag, isSearchByName, keywordList, foundTasksList.size()));
    }

    private CommandResult formatDisplay(boolean isSearchByTag, boolean isSearchByName,
                                        String[] keywordList, int foundCount) {
        if (keywordList[0].equals(PARAMETER_NULL)) {
            return new CommandResult(String.format(MESSAGE_RESULT_TEMPLATE_LIST,
                StringUtil.nounWithCount("task", foundCount))
                + String.format(MESSAGE_RESULT_TEMPLATE_TAB, uiStore.getShownTasks().size()));
        }

        String searchParameters = nameSearchParameters(isSearchByTag, isSearchByName);

        String keywords = String.join(MESSAGE_STRING_JOINING, keywordList);
        return new CommandResult(String.format(MESSAGE_RESULT_TEMPLATE_FIND,
                keywords, searchParameters, StringUtil.nounWithCount("result", foundCount))
                + String.format(MESSAGE_RESULT_TEMPLATE_TAB, uiStore.getShownTasks().size()));
    }
    
    private String nameSearchParameters(boolean isSearchByTag, boolean isSearchByName) {
        if (isSearchByName && isSearchByTag) {
            return MESSAGE_NAME_AND_TAG;
        } else if (isSearchByName) {
            return MESSAGE_NAME;
        } else { //isSearchByTag
            return MESSAGE_TAG;
        }
    }

    public Map<String, String> tokenize(String command) {
        Map<String, String> tokens = super.tokenize(command);

        // keyword for matching
        String keywords = Pattern.compile(PARAMETER_TAG, Pattern.CASE_INSENSITIVE).matcher(command)
                .replaceAll(PARAMETER_NULL);
        keywords = Pattern.compile(PARAMETER_NAME, Pattern.CASE_INSENSITIVE).matcher(keywords)
                .replaceAll(PARAMETER_NULL);
        String[] listOfParameters = keywords.split(COMMAND_SPLITTER_REGEX, NUMBER_OF_SPLITS_FOR_COMMAND_PARSE);
        if (listOfParameters.length > 1) {
            tokens.put(PARAMETER_KEYWORDS, listOfParameters[SECTION_PARAMETER].trim());
        }

        return tokens;
    }

    public boolean matchesCommand(String command) {
        String trimmedAndLowerCasedCommand = command.trim().toLowerCase();
        return (trimmedAndLowerCasedCommand.startsWith(COMMAND_WORD_FILTER.toLowerCase())
                || trimmedAndLowerCasedCommand.startsWith(COMMAND_WORD_FIND.toLowerCase())
                || trimmedAndLowerCasedCommand.startsWith(COMMAND_WORD_LIST.toLowerCase()));
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD_FILTER, COMMAND_WORD_FIND, COMMAND_WORD_LIST };
    }

    public Map<String, String[]> getCommandKeywordMap() {
        String[] keywords = new String[] { PARAMETER_NAME, PARAMETER_TAG };
        HashMap<String, String[]> keywordMap = new HashMap<>();
        for (String keyword : keywords) {
            keywordMap.put(keyword, new String[0]);
        }
        return keywordMap;
    }

    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
