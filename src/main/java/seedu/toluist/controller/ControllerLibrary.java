//@@author A0131125Y
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller Library is responsible for querying the list of controllers
 */
public class ControllerLibrary {
    /**
     * Returns a collection of Controllers associated with specific commands
     */
    public Collection<Controller> getCommandControllers() {
        return new ArrayList(Arrays.asList(
                new AddTaskController(),
                new ClearController(),
                new UpdateTaskController(),
                new DeleteTaskController(),
                new StoreController(),
                new HistoryController(),
                new LoadController(),
                new UndoController(),
                new HelpController(),
                new RedoController(),
                new ExitController(),
                new AliasController(),
                new UnaliasController(),
                new ViewAliasController(),
                new UntagController(),
                new FindController(),
                new TagController(),
                new MarkController(),
                new SwitchController()
        ));
    }

    /**
     * Returns a collection of Controllers not associated with specific commands
     */
    public Collection<Controller> getNonCommandControllers() {
        return new ArrayList(Arrays.asList(
                new NavigateHistoryController(),
                new UnknownCommandController()
        ));
    }

    /**
     * Returns a collection of all Controllers
     */
    public Collection<Controller> getAllControllers() {
        Collection<Controller> controllers = new ArrayList<>();
        controllers.addAll(getCommandControllers());
        controllers.addAll(getNonCommandControllers());
        return controllers;
    }

    /**
     * Returns set of command words for all command controllers
     * @return set of controller command words
     */
    public Set<String> getCommandControllerCommandWords() {
        List<String> keywordList = getCommandControllers().stream()
                .map(controller -> Arrays.asList(controller.getCommandWords()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return new HashSet<>(keywordList);
    }

    //@@author A0162011A
    /**
     * Returns list of list basic help strings for all command controllers
     * @return list of list of help strings
     */
    public List<List<String>> getCommandControllerBasicHelps() {
        return getCommandControllers().stream()
                .map(controller -> Arrays.asList(controller.getBasicHelp()))
                .collect(Collectors.toList());
    }
}
