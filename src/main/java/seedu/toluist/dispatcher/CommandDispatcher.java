//@@author A0131125Y
package seedu.toluist.dispatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.AddTaskController;
import seedu.toluist.controller.AliasController;
import seedu.toluist.controller.ClearController;
import seedu.toluist.controller.Controller;
import seedu.toluist.controller.DeleteTaskController;
import seedu.toluist.controller.ExitController;
import seedu.toluist.controller.FindController;
import seedu.toluist.controller.HelpController;
import seedu.toluist.controller.HistoryController;
import seedu.toluist.controller.LoadController;
import seedu.toluist.controller.MarkController;
import seedu.toluist.controller.NavigateHistoryController;
import seedu.toluist.controller.RedoController;
import seedu.toluist.controller.StoreController;
import seedu.toluist.controller.SwitchController;
import seedu.toluist.controller.TagController;
import seedu.toluist.controller.UnaliasController;
import seedu.toluist.controller.UndoController;
import seedu.toluist.controller.UnknownCommandController;
import seedu.toluist.controller.UntagController;
import seedu.toluist.controller.UpdateTaskController;
import seedu.toluist.controller.ViewAliasController;

import seedu.toluist.model.CommandHistoryList;

public class CommandDispatcher extends Dispatcher {
    private static final Logger logger = LogsCenter.getLogger(CommandDispatcher.class);

    //@@author A0162011A
    /**
     * ArrayList to store previous commands entered since starting the application
     */
    private CommandHistoryList commandHistory;

    //@@author A0131125Y
    public CommandDispatcher() {
        super();
        aliasConfig.setReservedKeywords(getControllerKeywords());
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

        for (String commandWord : getControllerKeywords()) {
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
        Collection<Controller> controllerCollection = getAllControllers();

        return controllerCollection
                .stream()
                .filter(controller -> controller.matchesCommand(command))
                .findFirst()
                .orElse(new UnknownCommandController()); // fail-safe
    }

    private Collection<Class <? extends Controller>> getAllControllerClasses() {
        return new ArrayList<>(Arrays.asList(
                AddTaskController.class,
                ClearController.class,
                UpdateTaskController.class,
                DeleteTaskController.class,
                StoreController.class,
                HistoryController.class,
                LoadController.class,
                UndoController.class,
                HelpController.class,
                RedoController.class,
                ExitController.class,
                AliasController.class,
                UnaliasController.class,
                NavigateHistoryController.class,
                ViewAliasController.class,
                UntagController.class,
                FindController.class,
                TagController.class,
                MarkController.class,
                SwitchController.class,
                UnknownCommandController.class
        ));
    }

    private Collection<Controller> getAllControllers() {
        return getAllControllerClasses()
                .stream()
                .map((Class<? extends Controller> klass) -> {
                    try {
                        Constructor constructor = klass.getConstructor();
                        return (Controller) constructor.newInstance();
                    } catch (NoSuchMethodException | InstantiationException
                            | IllegalAccessException | InvocationTargetException e) {
                        // fail-safe. But should not actually reach here
                        return new UnknownCommandController();
                    }
                })
                .collect(Collectors.toList());
    }

    private Set<String> getControllerKeywords() {
        List<String> keywordList = getAllControllerClasses()
                .stream()
                .map((Class<? extends Controller> klass) -> {
                    try {
                        final String methodName = "getCommandWords";
                        Method method = klass.getMethod(methodName);
                        return Arrays.asList((String[]) method.invoke(null));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        return new ArrayList<String>();
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return new HashSet<>(keywordList);
    }
}
