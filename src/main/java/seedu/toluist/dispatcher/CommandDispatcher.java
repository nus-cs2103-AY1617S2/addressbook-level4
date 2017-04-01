//@@author A0131125Y
package seedu.toluist.dispatcher;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.Controller;
import seedu.toluist.controller.ControllerLibrary;
import seedu.toluist.controller.HistoryController;
import seedu.toluist.controller.NavigateHistoryController;
import seedu.toluist.controller.UnknownCommandController;

import seedu.toluist.model.CommandHistoryList;

public class CommandDispatcher extends Dispatcher {
    private static final Logger logger = LogsCenter.getLogger(CommandDispatcher.class);

    //@@author A0162011A
    /**
     * ArrayList to store previous commands entered since starting the application
     */
    private CommandHistoryList commandHistory;
    private ControllerLibrary controllerLibrary = new ControllerLibrary();

    //@@author A0131125Y
    public CommandDispatcher() {
        super();
        aliasConfig.setReservedKeywords(controllerLibrary.getCommandControllerKeywords());
        commandHistory = new CommandHistoryList();
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
        controller.execute(deAliasedCommand);
    }

    public SortedSet<String> getPredictedCommands(String command) {
        SortedSet<String> predictedCommands = new TreeSet<>();

        if (!StringUtil.isPresent(command)) {
            return predictedCommands;
        }

        String firstWordOfCommand = command.trim().split("\\s+")[0];

        Map<String, String> aliasMapping = aliasConfig.getAliasMapping();
        for (String alias : aliasMapping.keySet()) {
            if (StringUtil.startsWithIgnoreCase(alias, firstWordOfCommand)) {
                String replacedCommand = command.replaceFirst(Pattern.quote(firstWordOfCommand), alias);
                predictedCommands.add(getDealiasedCommand(replacedCommand).trim());
            }
        }

        for (String commandWord : controllerLibrary.getCommandControllerKeywords()) {
            if (StringUtil.startsWithIgnoreCase(commandWord, firstWordOfCommand)) {
                predictedCommands.add(
                        command.replaceFirst(Pattern.quote(firstWordOfCommand), commandWord).trim());
            }
        }

        logger.info("Predicted commands: " + predictedCommands.toString());
        return predictedCommands;
    }

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
