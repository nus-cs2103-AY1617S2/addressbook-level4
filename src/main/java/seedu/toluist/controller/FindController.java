package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static final String COMMAND_TEMPLATE = "^(?<command>(find|filter|list))"
            + "(\\s+(?<keywords>.+))?\\s*";

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

        //initialize keywords and variables for searching
        HashMap<String, String> tokens = tokenize(command);
        boolean isSearchByTag = tokens.get(TAG_PARAMETER).equals(TRUE_PARAMETER);
        boolean isSearchByName = tokens.get(NAME_PARAMETER).equals(TRUE_PARAMETER);
        String[] keywordList = convertToArray(tokens.get(KEYWORDS_PARAMETER));

        //initialize search parameters and variables
        ArrayList<Task> foundTasks = new ArrayList<Task>();
        boolean isFound = false;
        TodoList todoList = TodoList.load();
        ArrayList<Task> taskList = todoList.getTasks();
        Task currentTask;

        for (int i = 0; i < taskList.size(); i++) {
            currentTask = taskList.get(i);
            for (int j = 0; j < keywordList.length; j++) {
                if (isSearchByTag && !isFound) {
                    if (currentTask.isStringContainedInAnyTagIgnoreCase(keywordList[j])) {
                            foundTasks.add(currentTask);
                            isFound = true;
                    }
                }

                if (isSearchByName && !isFound) {
                    if (currentTask.isStringContainedInDescriptionIgnoreCase(keywordList[j])) {
                        foundTasks.add(currentTask);
                        isFound = true;
                    }
                }
            }
            isFound = false;
        }

        uiStore.setTask(foundTasks);
        renderer.render();

        //display formatting
        return formatDisplay(isSearchByTag, isSearchByName, keywordList, foundTasks.size());
    }

    private String[] convertToArray(String keywords) {
        if (keywords == null) {
            return new String[]{""};
        } else {
            String[] keywordList = keywords.split(" ");
            ArrayList<String> replacementList = new ArrayList<String>();
            for (int i = 0; i < keywordList.length; i++) {
                if (!keywordList[i].equals("")) {
                    replacementList.add(keywordList[i]);
                }
            }
            return replacementList.toArray(new String[0]);
        }
    }

    private CommandResult formatDisplay(boolean isSearchByTag, boolean isSearchByName,
                                        String[] keywordList, int foundCount) {
        String searchParameters;
        if (isSearchByName && isSearchByTag) {
            searchParameters = NAME_AND_TAG_MESSAGE;
        } else if (isSearchByName){
            searchParameters = NAME_MESSAGE;
        }
        else { //isSearchByTag
            searchParameters = TAG_MESSAGE;
        }

        String keywords="";
        if (keywordList.length > 0) {
            keywords = keywordList[0];
            for (int k = 1; k < keywordList.length; k++) {
                keywords += " " + keywordList[k];
            }
        }
        return new CommandResult(String.format(RESULT_MESSAGE_TEMPLATE, keywords, searchParameters,foundCount));
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();

        //search by tag
        if (command.contains(TAG_PARAMETER)){
            tokens.put(TAG_PARAMETER, TRUE_PARAMETER);
        }
        else {
            tokens.put(TAG_PARAMETER, FALSE_PARAMETER);
        }

        //search by name
        if (command.contains(NAME_PARAMETER) || !command.contains(TAG_PARAMETER)){
            tokens.put(NAME_PARAMETER, TRUE_PARAMETER);
        }
        else {
            tokens.put(NAME_PARAMETER, FALSE_PARAMETER);
        }

        //keyword for matching
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
        return command.matches(COMMAND_TEMPLATE);
    }
}
