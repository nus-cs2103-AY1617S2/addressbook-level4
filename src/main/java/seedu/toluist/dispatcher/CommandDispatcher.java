//@@author A0131125Y
package seedu.toluist.dispatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.util.Pair;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.Controller;
import seedu.toluist.controller.ControllerLibrary;
import seedu.toluist.controller.FindController;
import seedu.toluist.controller.HistoryController;
import seedu.toluist.controller.NavigateHistoryController;
import seedu.toluist.controller.UnknownCommandController;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

public class CommandDispatcher extends Dispatcher {
    private static final Logger logger = LogsCenter.getLogger(CommandDispatcher.class);
    private static final int SUGGESTION_LIMIT = 7;
    private static final int KEYWORD_CHECK_SIZE = 2;

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
        aliasConfig.setReservedKeywords(controllerLibrary.getCommandControllerCommandWords());
    }

    public void dispatchRecordingHistory(String command) {
        dispatch(command);
        commandHistory.recordCommand(command);
    }

    public void dispatch(String command) {
        String processedCommand = getDealiasedCommand(command).trim();
        logger.info("De-aliased command to be dispatched: " + processedCommand + " original command " + command);
        Controller controller = getBestFitController(processedCommand);
        logger.info("Controller class to be executed: " + controller.getClass());
        handleHistoryController(controller);
        handleNavigateHistoryController(controller);
        execute(processedCommand, controller);
    }

    /**
     * Execute command using controller
     * @param command a command that is already de-aliased
     * @param controller controller to handle the command
     */
    private void execute(String command, Controller controller) {
        Map<String, String> tokens = controller.tokenize(command);
        tokenHistoryList.recordTokens(controller.getClass(), tokens);
        try {
            controller.execute(tokens);
        } catch (InvalidCommandException e) {
            UiStore.getInstance().setCommandResult(
                    new CommandResult(e.getMessage(), CommandResult.CommandResultType.FAILURE));
        }
    }

    //@@author A0162011A
    /**
     * Special handling for navigate history controller (if applicable)
     * @param controller a Controller instance
     */
    private void handleNavigateHistoryController(Controller controller) {
        if (controller instanceof NavigateHistoryController) {
            ((NavigateHistoryController) controller).setCommandHistory(commandHistory);
        }
    }

    /**
     * Special handling for history controller (if applicable)
     * @param controller a Controller instance
     */
    private void handleHistoryController(Controller controller) {
        if (controller instanceof HistoryController) {
            ((HistoryController) controller).setCommandHistory(commandHistory);
        }
    }

    //@@author A0131125Y
    /**
     * Get set of suggestions for a command based on, in order:
     * - Command word / Replacement for alias suggestion
     * - Search keywords
     * - Arguments for general keywords
     * - General keywords
     */
    public SortedSet<String> getSuggestions(String command) {
        if (!StringUtil.isPresent(command)) {
            return new TreeSet<>();
        }

        List<Function<String, SortedSet<String>>> getSuggestionMethods = Arrays.asList(
                this::getCommandWordAndAliasSuggestions,
                this::getSearchSuggestions,
                this::getKeywordArgumentSuggestions,
                this::getKeywordSuggestions
        );

        SortedSet<String> suggestions = getSuggestionMethods.stream()
                .reduce(new TreeSet<String>(),
                    (accumulator, next) -> {
                        if (!accumulator.isEmpty()) {
                            return accumulator;
                        }
                        return next.apply(command).stream()
                                .limit(SUGGESTION_LIMIT)
                                .collect(Collectors.toCollection(TreeSet::new));
                    },
                    (set1, set2) -> set1); // This line will not be actually be run
        logger.info("Suggestions for command " + command +  " " + suggestions.toString());
        return suggestions;
    }

    /**
     * Return suggestions for command words, or possible alias expansion
     * @param command command string
     * @return sorted set of suggestions
     */
    private SortedSet<String> getCommandWordAndAliasSuggestions(String command) {
        SortedSet<String> suggestions = new TreeSet<>();
        String[] words = command.trim().split(StringUtil.WHITE_SPACE);

        if (command.substring(command.length() - 1).equals(StringUtil.SINGLE_SPACE) || words.length > 1) {
            return suggestions;
        }
        String firstWordOfCommand = words[0];

        Map<String, String> aliasMapping = aliasConfig.getAliasMapping();
        for (String alias : aliasMapping.keySet()) {
            if (StringUtil.startsWithIgnoreCase(alias, firstWordOfCommand)) {
                suggestions.add(aliasMapping.get(alias));
            }
        }

        for (String commandWord : controllerLibrary.getCommandControllerCommandWords()) {
            if (StringUtil.startsWithIgnoreCase(commandWord, firstWordOfCommand)) {
                suggestions.add(commandWord);
            }
        }

        suggestions.remove(firstWordOfCommand);

        return suggestions;
    }

    /**
     * Return suggestions for command keywords, not including those already used in the command
     * Works for aliased command too
     * @param command command string
     * @return sorted set of suggestions
     */
    private SortedSet<String> getKeywordSuggestions(String command) {
        String deAliasedCommand = getDealiasedCommand(command);
        String lastComponentOfCommand = StringUtil.getLastComponent(deAliasedCommand);
        Controller bestFitController = getBestFitController(deAliasedCommand);
        Map<String, String[]> keywordMap = bestFitController.getCommandKeywordMap();
        Set<String> existingKeywords = bestFitController.keywordize(deAliasedCommand).stream()
                .map(keywordValuePair -> keywordValuePair.getKey())
                .collect(Collectors.toSet());
        return keywordMap.keySet().stream()
                // do not repeat keywords
                .filter(keyword -> !existingKeywords.contains(keyword))
                // do not suggest keywords that conflict with existing keywords
                .filter(keyword -> Collections.disjoint(bestFitController.getConflictingKeywords(keyword),
                        existingKeywords))
                .filter(keyword -> StringUtil.startsWithIgnoreCase(keyword, lastComponentOfCommand))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Return suggestions for argument for command keywords
     * Works for aliased command too
     * @param command command string
     * @return sorted set of suggestions
     */
    private SortedSet<String> getKeywordArgumentSuggestions(String command) {
        String deAliasedCommand = getDealiasedCommand(command);
        String lastComponentOfCommand = StringUtil.getLastComponent(deAliasedCommand);
        Controller bestFitController = getBestFitController(deAliasedCommand);
        Map<String, String[]> keywordMap = bestFitController.getCommandKeywordMap();
        List<Pair<String, String>> keywordValuePairs = bestFitController.keywordize(deAliasedCommand);
        if (keywordValuePairs.size() < KEYWORD_CHECK_SIZE) {
            return new TreeSet<>();
        }

        keywordValuePairs.remove(0);

        Pair<String, String> lastKeywordTokenPair = keywordValuePairs.get(keywordValuePairs.size() - 1);

        // Do not suggests arguments if something is already there
        if (!StringUtil.isPresent(lastComponentOfCommand)
            && StringUtil.isPresent(lastKeywordTokenPair.getValue())) {
            return new TreeSet<>();
        }

        return Arrays.stream(keywordMap.get(lastKeywordTokenPair.getKey()))
                .filter(value -> StringUtil.startsWithIgnoreCase(value, lastComponentOfCommand))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Return suggestions for find/search command
     * Works for aliased command too
     * @param command command string
     * @return sorted set of suggestions
     */
    private SortedSet<String> getSearchSuggestions(String command) {
        String deAliasedCommand = getDealiasedCommand(command);
        Controller bestFitController = getBestFitController(deAliasedCommand);
        if (!(bestFitController instanceof FindController)) {
            return new TreeSet<>();
        }

        Set<String> existingKeywords = new HashSet(Arrays.asList(bestFitController.tokenize(deAliasedCommand)
                .get(FindController.PARAMETER_KEYWORDS).split(StringUtil.WHITE_SPACE)));

        String lastComponentOfCommand = StringUtil.getLastComponent(deAliasedCommand);
        return tokenHistoryList.retrieveTokens(bestFitController.getClass(), FindController.PARAMETER_KEYWORDS)
                .stream()
                .map(keywords -> keywords.split(StringUtil.WHITE_SPACE))
                .flatMap(Arrays::stream)
                .filter(value -> !existingKeywords.contains(value))
                .filter(value -> StringUtil.startsWithIgnoreCase(value, lastComponentOfCommand))
                .collect(Collectors.toCollection(TreeSet::new));
    }


    /**
     * Returns de-aliased version of a command
     * @param command a command string
     * @return the converted command
     */
    private String getDealiasedCommand(String command) {
        return aliasConfig.dealias(command);
    }

    /**
     * Retrieve the controller that can best handle a command
     * @param command command string, should have already been de-aliased but not necessarily trimmed
     * @return Controller object
     */
    private Controller getBestFitController(String command) {
        Collection<Controller> controllerCollection = controllerLibrary.getAllControllers();

        return controllerCollection
                .stream()
                .filter(controller -> controller.matchesCommand(command.trim()))
                .findFirst()
                .orElse(new UnknownCommandController()); // fail-safe
    }
}
