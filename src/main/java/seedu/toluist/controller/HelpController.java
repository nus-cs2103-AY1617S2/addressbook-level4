//@@author A0162011A
package seedu.toluist.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    private static final String HEADING_GENERAL = "Displaying general help. Press any keys to go back.";
    private static final String HEADING_SPECIFIC = "Displaying detailed help for %s. "
                                                    + "Press any keys to go back.";
    private static final String COMMAND_WORD = "help";
    private static final String COMMAND_REGEX = "(?iu)^\\s*help.*";

    private static final String PARAMETER_COMMAND = "command";
    private static final int SECTION_COMMAND = 1;

    private static final int NUMBER_OF_SPLITS_FOR_COMMAND_PARSE = 2;
    private static final String COMMAND_SPLITTER_REGEX = " ";

    private static final String HELP_DETAILS = "Marks a task to be complete or incomplete.";
    private static final String HELP_FORMAT = "mark [complete/incomplete] INDEX(ES)";
    private static final String[] HELP_EXAMPLES = { "`help`\nShows general help for all commands.",
                                                    "`help add`\nShows detailed help for `add` command." };

    //for detailed help
    private static final String STRING_BASIC_INFO = "Basic info:\n";
    private static final String STRING_COMMENTS = "\n\nComments:\n";
    private static final String STRING_EXAMPLES = "\n\nExamples:\n";
    private static final int INDEX_HELP_BASIC = 0;
    private static final int INDEX_HELP_COMMENTS = 1;
    private static final int INDEX_HELP_EXAMPLES = 2;
    private static final String FORMAT_SPACING = "\n";
    //for basic help
    private static final String STRING_COMMAND = "Command: ";
    private static final String STRING_DESCRIPTION = "\n";
    private static final String STRING_FORMAT = "\nFormat: ";
    private static final int INDEX_HELP_COMMAND = 0;
    private static final int INDEX_HELP_DESCRIPTION = 2;
    private static final int INDEX_HELP_FORMAT = 1;
    private static final String FORMAT_LARGESPACING = "\n\n";

    private ControllerLibrary controllerLibrary = new ControllerLibrary();

    public void execute(String command) {
        logger.info(getClass().getName() + " will handle command");
        HashMap<String, String> tokens = tokenize(command);

        String commandWord = tokens.get(PARAMETER_COMMAND);
        if (commandWord.equals("")) {
            showGeneralHelp();
        } else if (controllerLibrary.getCommandControllerKeywords().contains(commandWord.toLowerCase())) {
            showSpecificHelp(commandWord);
        } else {
            uiStore.setCommandResult(new CommandResult(MESSAGE_ERROR));
        }
    }

    private void showSpecificHelp(String commandWord) {
        List<List<String>> detailedHelp =
                Arrays.asList(getControllerFromKeyword(commandWord).getDetailedHelp()).stream()
                    .filter(help -> help != null)
                    .map(help -> Arrays.asList(help))
                    .collect(Collectors.toList());
        uiStore.setHelp(String.format(HEADING_SPECIFIC, commandWord),
                Arrays.asList(convertListListToStringForDetailed(detailedHelp)));
    }

    private Controller getControllerFromKeyword(String commandWord) {
        Collection<Controller> controllers = controllerLibrary.getCommandControllers();
        for (Controller controller : controllers) {
            if (Arrays.stream(controller.getCommandWords())
                    .anyMatch(word -> commandWord.equalsIgnoreCase(word))) {
                return controller;
            }
        }
        return null;
    }

    private void showGeneralHelp() {
        List<List<String>> generalHelp = controllerLibrary.getCommandControllerBasicHelps();
        uiStore.setHelp(HEADING_GENERAL, convertListListToListStringForGeneral(generalHelp));
    }

    private List<String> convertListListToListStringForGeneral(List<List<String>> generalHelp) {
        return generalHelp.stream().map(help -> {
            String finalResult = "";
            finalResult += STRING_COMMAND;
            finalResult += String.join(FORMAT_SPACING, help.get(INDEX_HELP_COMMAND));
            finalResult += STRING_FORMAT;
            finalResult += String.join(FORMAT_SPACING, help.get(INDEX_HELP_FORMAT));
            finalResult += STRING_DESCRIPTION;
            finalResult += String.join(FORMAT_SPACING, help.get(INDEX_HELP_DESCRIPTION));
            return finalResult;
        }).collect(Collectors.toList());
    }

    private String convertListListToStringForDetailed(List<List<String>> detailedHelp) {
        String finalResult = "";
        finalResult += STRING_BASIC_INFO;
        finalResult += String.join(FORMAT_SPACING, detailedHelp.get(INDEX_HELP_BASIC));
        finalResult += STRING_COMMENTS;
        finalResult += String.join(FORMAT_SPACING, detailedHelp.get(INDEX_HELP_COMMENTS));
        if (INDEX_HELP_EXAMPLES < detailedHelp.size()) {
            finalResult += STRING_EXAMPLES;
            finalResult += String.join(FORMAT_SPACING, detailedHelp.get(INDEX_HELP_EXAMPLES));
        }
        return finalResult;
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

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    public String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_FORMAT, HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), null, HELP_EXAMPLES };
    }
}
