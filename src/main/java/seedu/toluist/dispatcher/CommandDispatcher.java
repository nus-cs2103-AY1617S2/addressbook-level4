package seedu.toluist.dispatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.EventsCenter;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.events.ui.NewResultAvailableEvent;
import seedu.toluist.controller.AddTaskController;
import seedu.toluist.controller.AliasController;
import seedu.toluist.controller.ClearController;
import seedu.toluist.controller.Controller;
import seedu.toluist.controller.DeleteTaskController;
import seedu.toluist.controller.ExitController;
import seedu.toluist.controller.FindController;
import seedu.toluist.controller.ListController;
import seedu.toluist.controller.LoadController;
import seedu.toluist.controller.MarkController;
import seedu.toluist.controller.RedoController;
import seedu.toluist.controller.StoreController;
import seedu.toluist.controller.SwitchController;
import seedu.toluist.controller.TagController;
import seedu.toluist.controller.UnaliasController;
import seedu.toluist.controller.UndoController;
import seedu.toluist.controller.UntagController;
import seedu.toluist.controller.UpdateTaskController;
import seedu.toluist.controller.ViewAliasController;
import seedu.toluist.model.AliasTable;
import seedu.toluist.ui.Ui;

public class CommandDispatcher extends Dispatcher {
    private static final Logger logger = LogsCenter.getLogger(CommandDispatcher.class);
    private final EventsCenter eventsCenter = EventsCenter.getInstance();
    private final AliasTable aliasConfig = Config.getInstance().getAliasTable();

    public CommandDispatcher() {
        super();
        aliasConfig.setReservedKeywords(getControllerKeywords());
    }

    public void dispatch(Ui renderer, String command) {
        String deAliasedCommand = aliasConfig.dealias(command);
        logger.info("De-aliased command to be dispatched: " + deAliasedCommand + " original command " + command);

        Controller controller = getBestFitController(renderer, deAliasedCommand);
        logger.info("Controller class to be executed: " + controller.getClass());
        CommandResult feedbackToUser = controller.execute(deAliasedCommand);
        eventsCenter.post(new NewResultAvailableEvent(feedbackToUser.getFeedbackToUser()));
    }

    private Controller getBestFitController(Ui renderer, String command) {
        Collection<Controller> controllerCollection = getAllControllers(renderer);

        return controllerCollection
                .stream()
                .filter(controller -> controller.matchesCommand(command))
                .findFirst()
                .orElse(new ListController(renderer)); // fail-safe
    }

    private Collection<Class <? extends Controller>> getAllControllerClasses() {
        return new ArrayList<>(Arrays.asList(
                AddTaskController.class,
                ClearController.class,
                UpdateTaskController.class,
                DeleteTaskController.class,
                StoreController.class,
                LoadController.class,
                UndoController.class,
                RedoController.class,
                ExitController.class,
                AliasController.class,
                UnaliasController.class,
                ViewAliasController.class,
                UntagController.class,
                FindController.class,
                TagController.class,
                MarkController.class,
                SwitchController.class,
                ListController.class
        ));
    }

    private Collection<Controller> getAllControllers(Ui renderer) {
        return getAllControllerClasses()
                .stream()
                .map((Class<? extends Controller> klass) -> {
                    try {
                        Constructor constructor = klass.getConstructor(Ui.class);
                        return (Controller) constructor.newInstance(renderer);
                    } catch (NoSuchMethodException | InstantiationException
                            | IllegalAccessException | InvocationTargetException e) {
                        // fail-safe. But should not actually reach here
                        return new ListController(renderer);
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
