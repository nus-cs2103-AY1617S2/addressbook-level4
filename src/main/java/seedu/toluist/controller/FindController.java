package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

/**
 * Find Controller is responsible for handling find requests
 */
public class FindController extends Controller {
    private static final String RESULT_MESSAGE_TEMPLATE = "%d tasks are found for search term %s";
    private static final String COMMAND_TEMPLATE = "find(\\s+(?<term>\\S+))?\\s*";
    private static final String COMMAND_WORD = "find";

    private static final String SEARCH_TERM = "term";

    public FindController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        HashMap<String, String> tokens = tokenize(command);
        String searchTermToken = tokens.get(SEARCH_TERM);
        final String searchTerm = searchTermToken != null ? searchTermToken.toLowerCase() : "";

        Predicate<Task> taskPredicate = task -> task.description.toLowerCase().contains(searchTerm);
        ArrayList<Task> filteredTasks = TodoList.load().filter(taskPredicate);

        UiStore.getInstance().setTask(filteredTasks);
        renderer.render();
        return new CommandResult(String.format(RESULT_MESSAGE_TEMPLATE, filteredTasks.size(), searchTerm));
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(SEARCH_TERM, matcher.group(SEARCH_TERM));
        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
