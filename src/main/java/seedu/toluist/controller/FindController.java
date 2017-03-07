package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

/**
 * Searches the task list for matches in the parameters, and displays the results received
 */
public class FindController extends Controller {
    private static final String COMMAND_TEMPLATE = "^(?<command>(find|filter|list))"
            + "(\\s+(?<keywords>.+))?\\s*";
    private static final String SEARCH_BY_TAG = "tag/";
    private static final String SEARCH_BY_NAME = "name/";
    private static final String COMMAND_RESULT_TEMPLATE = "%d results found";
    private static final String TRUE_COMMAND = "true";
    private static final String FALSE_COMMAND = "false";
    private static final String KEYWORDS = "keywords";
//    private static final String SPLITTER_COMMAND = "(?i:)( |tag\\|name\\)(?-i)";
    private static final String SPLITTER_COMMAND = " ";
      
    private final Logger logger = LogsCenter.getLogger(getClass());

    public FindController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass() + "will handle command");
        
        //initialize variables for searching
        ArrayList<Task> foundTasks = new ArrayList<Task>();
        HashMap<String, String> tokens = tokenize(command);
        boolean isSearchByTag = tokens.get(SEARCH_BY_TAG).equals(TRUE_COMMAND);
        boolean isSearchByName = tokens.get(SEARCH_BY_NAME).equals(TRUE_COMMAND);
        String keywords = tokens.get(KEYWORDS);
        String[] keywordList = keywords.split(" ");
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

        return new CommandResult(String.format(COMMAND_RESULT_TEMPLATE, foundValue));
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
        keywords.replace(SEARCH_BY_NAME, "");
        String[] listOfParameters = command.split(SPLITTER_COMMAND, 2);
        if (listOfParameters.length > 1) {
            tokens.put(KEYWORDS, listOfParameters[1]);
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
