//@@author A0162011A
package seedu.toluist.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.ui.commons.CommandResult;

/**
 * HelpController is responsible for rendering the initial UI
 */
public class HelpController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(HelpController.class);
    private static final String MESSAGE_ERROR = "Sorry, that command does not exist.\n"
                                                    + "Please type help for available commands.";
    private static final String MESSAGE_RESULT_GENERAL = "Displaying general help.";
    private static final String MESSAGE_RESULT_SPECIFIC = "Displaying detailed help for %s.";
    private static final String COMMAND_WORD = "help";
    private static final String COMMAND_REGEX = "(?iu)^\\s*help.*";

    private static final String PARAMETER_COMMAND = "command";
    private static final int SECTION_COMMAND = 1;

    private static final int NUMBER_OF_SPLITS_FOR_COMMAND_PARSE = 2;
    private static final String COMMAND_SPLITTER_REGEX = " ";

    private static final String METHOD_BASIC_HELP = "getBasicHelp";
    private static final String METHOD_DETAILED_HELP = "";
    private static final String METHOD_CONTROLLER_KEYWORDS = "getCommandWords";

    private static final String HELP_DETAILS = "Marks a task to be complete or incomplete.";
    private static final String HELP_FORMAT = "mark [complete/incomplete] INDEX(ES)";
    private static final String[] HELP_EXAMPLES = { "`help`\nShows general help for all commands.",
                                                    "`help add`\nShows detailed help for `add` command." };

    public void execute(String command) {
        logger.info(getClass().getName() + " will handle command");
        HashMap<String, String> tokens = tokenize(command);

        String commandWord = tokens.get(PARAMETER_COMMAND);
        if (commandWord.equals("")) {
            showGeneralHelp();
            uiStore.setCommandResult(new CommandResult(MESSAGE_RESULT_GENERAL));
        } else if (getControllerKeywords().contains(commandWord.toLowerCase())) {
            showSpecificHelp(commandWord);
            uiStore.setCommandResult(new CommandResult(String.format(MESSAGE_RESULT_SPECIFIC, commandWord)));
        } else {
            uiStore.setCommandResult(new CommandResult(MESSAGE_ERROR));
        }
    }

    private void showSpecificHelp(String commandWord) {
        Class<? extends Controller> controller = findControllerFromKeyword(commandWord);
      //uiStore.setTasks(controller.getDetailedHelp());
        getDetailedHelpFromController(controller);
    }

    private String[][] getDetailedHelpFromController(Class<? extends Controller> controller) {
        String methodName = METHOD_DETAILED_HELP;
        String[][] detailedHelp;
        try {
            Method method = controller.getMethod(methodName);
            detailedHelp = (String[][]) method.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
        return detailedHelp;
    }

    private Class<? extends Controller> findControllerFromKeyword(String commandWord) {
        Iterator iterator = getHelpControllerClasses().iterator();
        Class<? extends Controller> nextController;
        String[] keywordList;
        Method method;
        String methodName = METHOD_CONTROLLER_KEYWORDS;
        while (iterator.hasNext()) {
            nextController = (Class<? extends Controller>) iterator.next();
            try {
                method = nextController.getMethod(methodName);
                keywordList = (String[]) method.invoke(null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                keywordList = null;
            }
            for (int i = 0; i < keywordList.length; i++) {
                if (commandWord.equalsIgnoreCase(keywordList[i])) {
                    return nextController;
                }
            }
        }
        return null;
    }

    private void showGeneralHelp() {
        List<List<String>> commandsBasicHelp = getBasicHelpFromClasses();
        List<String> resultText = new ArrayList<String>();


        for (List<String> commandHelpMessage : commandsBasicHelp) {
            resultText.add(String.join("\n", commandHelpMessage));
        }

        //uiStore.setTasks(resultText);
    }

    public HashMap<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();
        command = command.trim();

        String[] listOfParameters = command
                .split(COMMAND_SPLITTER_REGEX, NUMBER_OF_SPLITS_FOR_COMMAND_PARSE);
        try {
            tokens.put(PARAMETER_COMMAND, listOfParameters[SECTION_COMMAND]);
        } catch (Exception e) {
            tokens.put(PARAMETER_COMMAND, "");
        }

        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_REGEX);
    }

    private Collection<Class <? extends Controller>> getHelpControllerClasses() {
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
                ViewAliasController.class,
                UntagController.class,
                FindController.class,
                TagController.class,
                MarkController.class,
                SwitchController.class
        ));
    }

    private Set<String> getControllerKeywords() {
        List<String> keywordList = getHelpControllerClasses()
                .stream()
                .map((Class<? extends Controller> klass) -> {
                    try {
                        final String methodName = METHOD_CONTROLLER_KEYWORDS;
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

    private List<List<String>> getBasicHelpFromClasses() {
        List<List<String>> keywordList = getHelpControllerClasses()
                .stream()
                .map((Class<? extends Controller> klass) -> {
                    try {
                        final String methodName = METHOD_BASIC_HELP;
                        Method method = klass.getMethod(methodName);
                        return Arrays.asList((String[]) method.invoke(null));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        return new ArrayList<String>();
                    }
                })
                .collect(Collectors.toList());
        return keywordList;
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    public static String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_DETAILS, HELP_FORMAT };
    }

    public static String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), null, HELP_EXAMPLES };
    }
}
