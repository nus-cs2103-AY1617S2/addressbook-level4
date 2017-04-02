//@@author A0131125Y
package seedu.toluist.dispatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.util.Pair;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.Controller;
import seedu.toluist.controller.ControllerLibrary;
import seedu.toluist.controller.FindController;
import seedu.toluist.controller.HistoryController;
import seedu.toluist.controller.NavigateHistoryController;
import seedu.toluist.controller.UnknownCommandController;

import seedu.toluist.controller.commons.KeywordTokenizer;

public class CommandDispatcher extends Dispatcher {
    private static final Logger logger = LogsCenter.getLogger(CommandDispatcher.class);

    //@@author A0162011A
    /**
     * ArrayList to store previous commands entered since starting the application
     */
    private CommandHistoryList commandHistory = new CommandHistoryList();
    private ControllerLibrary controllerLibrary = new ControllerLibrary();
    private TokenHistoryList tokenHistoryList = new TokenHistoryList();

    //@@author A0131125Y
    public CommandDispatcher() {
        super();
        aliasConfig.setReservedKeywords(controllerLibrary.getCommandControllerKeywords());
    }

    public void dispatchRecordingHistory(String command) {
        dispatch(command);
        commandHistory.recordCommand(command);
    }

    public void dispatch(String command) {
        String deAliasedCommand = getDealiasedCommand(command);
        logger.info("De-aliased command to be dispatched: " + deAliasedCommand + " original command " + command);

        Controller controller = getBestFitController(deAliasedCommand);
        logger.info("Controller class to be executed: " + controller.getClass());

        if (controller instanceof HistoryController) {
            ((HistoryController) controller).setCommandHistory(commandHistory);
        }
        if (controller instanceof NavigateHistoryController) {
            ((NavigateHistoryController) controller).setCommandHistory(commandHistory);
        }
        HashMap<String, String> tokens = controller.tokenize(deAliasedCommand);
        tokenHistoryList.recordTokens(controller, tokens);
        controller.execute(tokens);
    }

    public SortedSet<String> getSuggestions(String command) {
        if (!StringUtil.isPresent(command)
            || !StringUtil.isPresent(command.substring(command.length() - 1))) {
            return new TreeSet<>();
        }

        SortedSet<String> commandWordAndAliasSuggestions = getCommandWordAndAliasSuggestions(command);
        if (!commandWordAndAliasSuggestions.isEmpty()) {
            return commandWordAndAliasSuggestions;
        }

        SortedSet<String> keywordSuggestions = getKeywordSuggestions(command);
        if (!keywordSuggestions.isEmpty()) {
            return keywordSuggestions;
        }

        SortedSet<String> keywordArgumentSuggestions = getKeywordArgumentSuggestions(command);
        if (!keywordArgumentSuggestions.isEmpty()) {
            return keywordArgumentSuggestions;
        }

        return getSearchSuggestions(command);
    }

    /**
     * Return suggestions for command words, or possible alias expansion
     * @param command command string
     * @return sorted set of suggestions
     */
    private SortedSet<String> getCommandWordAndAliasSuggestions(String command) {
        SortedSet<String> suggestions = new TreeSet<>();
        String[] words = command.trim().split("\\s+");

        if (words.length > 1) {
            return suggestions;
        }
        String firstWordOfCommand = words[0];

        Map<String, String> aliasMapping = aliasConfig.getAliasMapping();
        for (String alias : aliasMapping.keySet()) {
            if (StringUtil.startsWithIgnoreCase(alias, firstWordOfCommand)) {
                suggestions.add(aliasMapping.get(alias));
            }
        }

        for (String commandWord : controllerLibrary.getCommandControllerKeywords()) {
            if (StringUtil.startsWithIgnoreCase(commandWord, firstWordOfCommand)) {
                suggestions.add(commandWord);
            }
        }

        return suggestions;
    }

    /**
     * Return suggestions for command keywords, not including those already used in the command
     * @param command command string
     * @return sorted set of suggestions
     */
    private SortedSet<String> getKeywordSuggestions(String command) {
        String lastWordOfCommand = StringUtil.getLastWord(command);

        HashMap<String, String[]> keywordMap = getBestFitController(command).getCommandKeywordMap();
        HashMap<String, String> tokens = KeywordTokenizer.tokenize(command, null,
                keywordMap.keySet().toArray(new String[0]));
        return keywordMap.keySet().stream()
                .filter(keyword -> StringUtil.startsWithIgnoreCase(keyword, lastWordOfCommand)
                        && !tokens.keySet().contains(keyword))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Return suggestions for argument for command keywords
     * @param command command string
     * @return sorted set of suggestions
     */
    private SortedSet<String> getKeywordArgumentSuggestions(String command) {
        String lastWordOfCommand = StringUtil.getLastWord(command);
        HashMap<String, String[]> keywordMap = getBestFitController(command).getCommandKeywordMap();
        List<Pair<String, String>> tokens = KeywordTokenizer.tokenizeInOrder(command, null,
                keywordMap.keySet().toArray(new String[0]));
        if (tokens.isEmpty()) {
            return new TreeSet<>();
        }

        Pair<String, String> lastKeywordTokenPair = tokens.get(tokens.size() - 1);

        return Arrays.stream(keywordMap.get(lastKeywordTokenPair.getKey()))
                .filter(value -> StringUtil.startsWithIgnoreCase(value, lastWordOfCommand))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Return suggestions for find/search command
     * @param command command string
     * @return sorted set of suggestions
     */
    private SortedSet<String> getSearchSuggestions(String command) {
        Controller bestFitController = getBestFitController(command);
        if (!(bestFitController instanceof FindController)) {
            return new TreeSet<>();
        }

        String lastWordOfCommand = StringUtil.getLastWord(command);
        return tokenHistoryList.retrieveTokens(bestFitController, FindController.PARAMETER_KEYWORDS).stream()
                .map(keywords -> keywords.split("\\s+"))
                .flatMap(Arrays::stream)
                .filter(value -> StringUtil.startsWithIgnoreCase(value, lastWordOfCommand))
                .collect(Collectors.toCollection(TreeSet::new));
    }


    /**
     * Returns de-aliased version of a command
     * @param command a command string
     * @return the converted command
     */
    private String getDealiasedCommand(String command) {
        String trimmedCommand = command.trim();
        return aliasConfig.dealias(trimmedCommand);
    }

    //@@author A0131125Y
    private Controller getBestFitController(String command) {
        Collection<Controller> controllerCollection = controllerLibrary.getAllControllers();

        return controllerCollection
                .stream()
                .filter(controller -> controller.matchesCommand(command))
                .findFirst()
                .orElse(new UnknownCommandController()); // fail-safe
    }
}
