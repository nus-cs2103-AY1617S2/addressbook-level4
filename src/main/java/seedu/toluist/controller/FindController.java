package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;

/**
 * Searches the task list for matches in the parameters, and displays the results received
 */
public class FindController extends Controller {
    private static final String COMMAND_FIND_WORD = "find";
    private static final String COMMAND_FILTER_WORD = "filter";
    private static final String COMMAND_LIST_WORD = "list";

    private static final String TAG_PARAMETER = "tag/";
    private static final String NAME_PARAMETER = "name/";
    private static final String TRUE_PARAMETER = "true";
    private static final String FALSE_PARAMETER = "false";
    private static final String KEYWORDS_PARAMETER = "keywords";

    private static final int NUMBER_OF_SPLITS_FOR_COMMAND_PARSE = 2;
    private static final String COMMAND_SPLITTER_REGEX = " ";
    private static final int PARAMETER_SECTION = 1;

    private static final String RESULT_MESSAGE_TEMPLATE = "Searching for \"%s\" by %s.\n%d results found";
    private static final String NAME_MESSAGE = "name";
    private static final String TAG_MESSAGE = "tag";
    private static final String NAME_AND_TAG_MESSAGE = "name and tag";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public FindController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass() + "will handle command");

        // initialize keywords and variables for searching
        HashMap<String, String> tokens = tokenize(command);
        boolean isSearchByTag = tokens.get(TAG_PARAMETER).equals(TRUE_PARAMETER);
        boolean isSearchByName = tokens.get(NAME_PARAMETER).equals(TRUE_PARAMETER);
        String[] keywordList = convertToArray(tokens.get(KEYWORDS_PARAMETER));

        Predicate<Task> taskPredicate = task ->
                (isSearchByTag && task.isAnyKeywordsContainedInAnyTagIgnoreCase(keywordList)
                || (isSearchByName && task.isAnyKeywordsContainedInDescriptionIgnoreCase(keywordList)));


        ArrayList<Task> foundTasksList = TodoList.load().getFilterTasks(taskPredicate);
        uiStore.setTask(foundTasksList);
        renderer.render();

        // display formatting
        return formatDisplay(isSearchByTag, isSearchByName, keywordList, foundTasksList.size());
    }

    private String[] convertToArray(String keywords) {
        if (keywords == null || keywords.trim().isEmpty()) {
            return new String[] { "" };
        }

        String trimmedKeywords = keywords.trim();
        String[] keywordList = trimmedKeywords.split(" ");
        ArrayList<String> replacementList = new ArrayList<>();
        for (String keyword : keywordList) {
            if (!keyword.equals("")) {
                replacementList.add(keyword);
            }
        }
        return replacementList.toArray(new String[0]);
    }

    private CommandResult formatDisplay(boolean isSearchByTag, boolean isSearchByName,
                                        String[] keywordList, int foundCount) {
        String searchParameters;
        if (isSearchByName && isSearchByTag) {
            searchParameters = NAME_AND_TAG_MESSAGE;
        } else if (isSearchByName) {
            searchParameters = NAME_MESSAGE;
        } else { //isSearchByTag
            searchParameters = TAG_MESSAGE;
        }

        String keywords = String.join(" ", keywordList);
        return new CommandResult(String.format(RESULT_MESSAGE_TEMPLATE, keywords, searchParameters, foundCount));
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();

        // search by tag
        if (command.contains(TAG_PARAMETER)) {
            tokens.put(TAG_PARAMETER, TRUE_PARAMETER);
        } else {
            tokens.put(TAG_PARAMETER, FALSE_PARAMETER);
        }

        // search by name
        if (command.contains(NAME_PARAMETER) || !command.contains(TAG_PARAMETER)) {
            tokens.put(NAME_PARAMETER, TRUE_PARAMETER);
        } else {
            tokens.put(NAME_PARAMETER, FALSE_PARAMETER);
        }

        // keyword for matching
        String keywords = command.replace(TAG_PARAMETER, "");
        keywords = keywords.replace(NAME_PARAMETER, "");
        String[] listOfParameters = keywords.split(COMMAND_SPLITTER_REGEX, NUMBER_OF_SPLITS_FOR_COMMAND_PARSE);
        if (listOfParameters.length > 1) {
            tokens.put(KEYWORDS_PARAMETER, listOfParameters[PARAMETER_SECTION].trim());
        }

        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return (command.startsWith(COMMAND_FILTER_WORD)
                || command.startsWith(COMMAND_FIND_WORD)
                || command.startsWith(COMMAND_LIST_WORD));
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_FILTER_WORD, COMMAND_FIND_WORD, COMMAND_LIST_WORD };
    }
}
