package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private static final String SEARCH_BY_TAG = "tag/";
    private static final String SEARCH_BY_NAME = "name/";
    private static final String COMMAND_RESULT_TEMPLATE = "Searching for \"%s\" by %s.\n%d results found";
    private static final String TRUE_COMMAND = "true";
    private static final String FALSE_COMMAND = "false";
    private static final String NAME_PARAMETER = "name";
    private static final String TAG_PARAMETER = "tag";
    private static final String NAME_AND_TAG_PARAMETER = "name and tag";
    private static final String KEYWORDS = "keywords";
    private static final String SPLITTER_COMMAND = " ";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public FindController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass() + "will handle command");

        //initialize keywords and variables for searching
        HashMap<String, String> tokens = tokenize(command);
        boolean isSearchByTag = tokens.get(SEARCH_BY_TAG).equals(TRUE_COMMAND);
        boolean isSearchByName = tokens.get(SEARCH_BY_NAME).equals(TRUE_COMMAND);
        String[] keywordList = convertToArray(tokens.get(KEYWORDS));

        //initialize search parameters and variables
        ArrayList<Task> foundTasks = new ArrayList<Task>();
        boolean isFound = false;
        TodoList todoList = TodoList.load();
        ArrayList<Task> taskList = todoList.getTasks();
        Task currentTask;
        int foundValue = 0;

        for (int i = 0; i < taskList.size(); i++) {
            currentTask = taskList.get(i);
            for (int j = 0; j < keywordList.length; j++) {
                if (isSearchByTag && !isFound) {
                    for (int k = 0; k < currentTask.allTags.size(); k++) {
                        if (currentTask.allTags.get(k).tagName.toLowerCase()
                                .contains(keywordList[j].toLowerCase())) {
                            foundTasks.add(currentTask);
                            isFound = true;
                            foundValue++;
                        }
                    }
                }
                if (isSearchByName && !isFound) {
                        if (currentTask.description.toLowerCase()
                            .contains(keywordList[j].toLowerCase())) {
                        foundTasks.add(currentTask);
                        isFound = true;
                        foundValue++;
                    }
                }
            }
            isFound = false;
        }

        uiStore.setTask(foundTasks);
        renderer.render();

        //display formatting
        return formatDisplay(isSearchByTag, isSearchByName, keywordList, foundValue);
    }

    private String[] convertToArray(String keywords) {
        String[] keywordList = keywords.split(" ");
        ArrayList<String> replacementList = new ArrayList<String>();
        for (int i = 0; i < keywordList.length; i++) {
            if (!keywordList[i].equals("")) {
                replacementList.add(keywordList[i]);
            }
        }
        return replacementList.toArray(new String[0]);
    }

    private CommandResult formatDisplay(boolean isSearchByTag, boolean isSearchByName,
                                        String[] keywordList, int foundValue) {
        String searchParameters;
        if (isSearchByName) {
            if (isSearchByTag) {
                searchParameters = NAME_AND_TAG_PARAMETER;
            }
            else {
                searchParameters = NAME_PARAMETER;
            }
        }
        else {
            searchParameters = TAG_PARAMETER;
        }

        String keywords;
        if (keywordList.length > 0) {
            keywords = keywordList[0];
            for (int k = 1; k < keywordList.length; k++) {
                keywords += " " + keywordList[k];
            }
        }
        else {
            keywords = "";
        }
        return new CommandResult(String.format(COMMAND_RESULT_TEMPLATE, keywords, searchParameters,foundValue));
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();

        //search by tag
        if (command.contains(SEARCH_BY_TAG)){
            tokens.put(SEARCH_BY_TAG, TRUE_COMMAND);
        }
        else {
            tokens.put(SEARCH_BY_TAG, FALSE_COMMAND);
        }

        //search by name
        if (command.contains(SEARCH_BY_NAME) || !command.contains(SEARCH_BY_TAG)){
            tokens.put(SEARCH_BY_NAME, TRUE_COMMAND);
        }
        else {
            tokens.put(SEARCH_BY_NAME, FALSE_COMMAND);
        }

        //keyword for matching
        String keywords = command.replace(SEARCH_BY_TAG, "");
        keywords = keywords.replace(SEARCH_BY_NAME, "");
        String[] listOfParameters = keywords.split(SPLITTER_COMMAND, 2);
        if (listOfParameters.length > 1) {
            tokens.put(KEYWORDS, listOfParameters[1].trim());
        }
        else {
            tokens.put(KEYWORDS, "");
        }

        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }
}
