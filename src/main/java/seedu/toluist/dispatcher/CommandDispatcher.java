package seedu.toluist.dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.toluist.commons.core.EventsCenter;
import seedu.toluist.commons.events.ui.NewResultAvailableEvent;
import seedu.toluist.controller.AliasController;
import seedu.toluist.controller.Controller;
import seedu.toluist.controller.ExitController;
import seedu.toluist.controller.ListController;
import seedu.toluist.controller.RedoController;
import seedu.toluist.controller.StoreController;
import seedu.toluist.controller.TaskController;
import seedu.toluist.controller.UndoController;
import seedu.toluist.model.CommandAliasConfig;
import seedu.toluist.ui.Ui;

public class CommandDispatcher extends Dispatcher {

    private final EventsCenter eventsCenter = EventsCenter.getInstance();
    private final CommandAliasConfig aliasConfig = new CommandAliasConfig();

    public void dispatch(Ui renderer, String command) {
        final Controller controller = getBestFitController(renderer, command);
        final CommandResult feedbackToUser = controller.execute(command);
        eventsCenter.post(new NewResultAvailableEvent(feedbackToUser.getFeedbackToUser()));
    }

    private Controller getBestFitController(Ui renderer, String command) {
        Collection<Controller> controllerCollection = getAllControllers(renderer);
        Set<String> reservedKeywords = getControllerKeywords(controllerCollection);
        // It is okay to do this because the commands are the same throughout
        aliasConfig.setReservedKeywords(reservedKeywords);

        return controllerCollection
                .stream()
                .filter(controller -> controller.matchesCommand(command))
                .findFirst()
                .orElse(new ListController(renderer)); // fail-safe
    }

    private Collection<Controller> getAllControllers(Ui renderer) {
        return new ArrayList<>(Arrays.asList(new Controller[] {
            new TaskController(renderer),
            new StoreController(renderer),
            new UndoController(renderer),
            new RedoController(renderer),
            new ExitController(renderer),
            new AliasController(renderer, aliasConfig),
            new ListController(renderer)
        }));
    }

    private Set<String> getControllerKeywords(Collection<Controller> controllerCollection) {
        List<String> keywordList = controllerCollection
                .stream()
                .map(controller -> Arrays.asList(controller.getCommandWords()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return new HashSet<>(keywordList);
    }
}
