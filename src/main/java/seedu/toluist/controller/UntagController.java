package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

/**
 * Searches the task list for matches in the parameters, and displays the results received
 */
public class UntagController extends Controller {
    private static final String COMMAND_UNTAG_WORD = "untag";

    private static final String INDEX_PARAMETER = "index";
    private static final String KEYWORDS_PARAMETER = "keywords";

    private static final int NUMBER_OF_SPLITS_FOR_COMMAND_PARSE = 2;
    private static final String COMMAND_SPLITTER_REGEX = " ";
    private static final int INDEX_SECTION = 0;
    private static final int KEYWORDS_SECTION = 1;

    private static final String SUCCESS_MESSAGE_TEMPLATE = "Sucessfully removed \"%s\".\n";
    private static final String FAIL_MESSAGE_TEMPLATE = "Failed to remove \"%s\".\n";
    private static final String RESULT_MESSAGE_TEMPLATE = "%s%d tag(s) successfully removed.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public UntagController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass() + "will handle command");

        // initialize keywords and variables for searching
        HashMap<String, String> tokens = tokenize(command);
        String[] keywordList = convertToArray(tokens.get(KEYWORDS_PARAMETER));
        int index = Integer.parseInt(tokens.get(INDEX_PARAMETER)) - 1;
        TodoList todoList = TodoList.load();
        Task task = UiStore.getInstance().getTasks().get(index);
        ArrayList<String> successfulList = new ArrayList<String>();
        ArrayList<String> failedList = new ArrayList<String>();

        for (String keyword : keywordList) {
            if (task.removeTag(new Tag(keyword))) {
                successfulList.add(keyword);
            } else {
                failedList.add(keyword);
            }
        }

        if (todoList.save()) {
            uiStore.setTask(todoList.getTasks());
            renderer.render();
        }

        // display formatting
        return formatDisplay(successfulList.toArray(new String[successfulList.size()]),
                                failedList.toArray(new String[failedList.size()]),
                                successfulList.size());
    }

    //!!!!!same as FindController method
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

    private CommandResult formatDisplay(String[] successfulList, String[] failedList, int successCount) {
        String successWords = String.join("\", \"", successfulList);
        String failWords = String.join("\", \"", failedList);
        String resultMessage = "";

        if (successfulList.length > 0) {
            resultMessage += String.format(SUCCESS_MESSAGE_TEMPLATE, successWords);
        }
        if (failedList.length > 0) {
            resultMessage += String.format(FAIL_MESSAGE_TEMPLATE, failWords);
        }

        return new CommandResult(String.format(RESULT_MESSAGE_TEMPLATE, resultMessage, successCount));
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();

        command = command.replace(COMMAND_UNTAG_WORD, "").trim();
        String[] listOfParameters = command.split(COMMAND_SPLITTER_REGEX, NUMBER_OF_SPLITS_FOR_COMMAND_PARSE);
        tokens.put(INDEX_PARAMETER, listOfParameters[INDEX_SECTION]);
        tokens.put(KEYWORDS_PARAMETER, listOfParameters[KEYWORDS_SECTION]);

        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.startsWith(COMMAND_UNTAG_WORD);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_UNTAG_WORD };
    }
}
