package seedu.task.logic.commandlibrary;

import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.FindDateCommand;
import seedu.task.logic.commands.FindExactCommand;
import seedu.task.logic.commands.GetGoogleCalendarCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.HelpFormatCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListByDoneCommand;
import seedu.task.logic.commands.ListByNotDoneCommand;
import seedu.task.logic.commands.ListByTagCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.LoadCommand;
import seedu.task.logic.commands.PostGoogleCalendarCommand;
import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.ThemeChangeCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.logic.commands.UndoneCommand;
import seedu.task.logic.parser.AddCommandParser;
import seedu.task.logic.parser.ClearCommandParser;
import seedu.task.logic.parser.CommandParser;
import seedu.task.logic.parser.DeleteCommandParser;
import seedu.task.logic.parser.DoneCommandParser;
import seedu.task.logic.parser.EditCommandParser;
import seedu.task.logic.parser.ExitCommandParser;
import seedu.task.logic.parser.FindCommandParser;
import seedu.task.logic.parser.FindDateParser;
import seedu.task.logic.parser.FindExactCommandParser;
import seedu.task.logic.parser.GetGoogleCalendarCommandParser;
import seedu.task.logic.parser.HelpCommandParser;
import seedu.task.logic.parser.HelpFormatCommandParser;
import seedu.task.logic.parser.ListByDoneCommandParser;
import seedu.task.logic.parser.ListByNotDoneCommandParser;
import seedu.task.logic.parser.ListByTagCommandParser;
import seedu.task.logic.parser.ListCommandParser;
import seedu.task.logic.parser.LoadCommandParser;
import seedu.task.logic.parser.PostGoogleCalendarCommandParser;
import seedu.task.logic.parser.RedoCommandParser;
import seedu.task.logic.parser.SaveCommandParser;
import seedu.task.logic.parser.SelectCommandParser;
import seedu.task.logic.parser.ThemeChangeCommandParser;
import seedu.task.logic.parser.UndoCommandParser;
import seedu.task.logic.parser.UndoneCommandParser;

//@@author A0142487Y
public class CommandLibrary {

    private static CommandLibrary instance = null;
    private static HashMap<String, CommandParser> commandParserTable;
    private static HashMap<String, Command> commandTable;
    // private static commandKeyParserPair[] commandKeyParserPairs;

    protected CommandLibrary() {
        init();
    }

    public static CommandLibrary getInstance() {
        if (instance == null) {
            instance = new CommandLibrary();
        }
        return instance;
    }

    /**
     * To initialize the hashmap and necessary variables
     */
    private static void init() {
        commandParserTable = new HashMap<>();
        commandTable = new HashMap<>();
        // TODO Auto-generated method stub
        commandParserTable.put(AddCommand.COMMAND_WORD_1, new AddCommandParser());

        commandParserTable.put(ClearCommand.COMMAND_WORD_1, new ClearCommandParser());

        commandParserTable.put(DeleteCommand.COMMAND_WORD_1, new DeleteCommandParser());

        commandParserTable.put(DoneCommand.COMMAND_WORD_1, new DoneCommandParser());
        commandParserTable.put(DoneCommand.COMMAND_WORD_2, new DoneCommandParser());

        commandParserTable.put(EditCommand.COMMAND_WORD_1, new EditCommandParser());

        commandParserTable.put(ExitCommand.COMMAND_WORD_1, new ExitCommandParser());

        commandParserTable.put(FindCommand.COMMAND_WORD_1, new FindCommandParser());
        commandParserTable.put(FindCommand.COMMAND_WORD_2, new FindCommandParser());

        commandParserTable.put(FindDateCommand.COMMAND_WORD_1, new FindDateParser());

        commandParserTable.put(FindExactCommand.COMMAND_WORD_1, new FindExactCommandParser());
        commandParserTable.put(FindExactCommand.COMMAND_WORD_2, new FindExactCommandParser());
        commandParserTable.put(FindExactCommand.COMMAND_WORD_3, new FindExactCommandParser());
        commandParserTable.put(FindExactCommand.COMMAND_WORD_4, new FindExactCommandParser());

        commandParserTable.put(GetGoogleCalendarCommand.COMMAND_WORD_1, new GetGoogleCalendarCommandParser());
        commandParserTable.put(GetGoogleCalendarCommand.COMMAND_WORD_2, new GetGoogleCalendarCommandParser());

        commandParserTable.put(HelpCommand.COMMAND_WORD_1, new HelpCommandParser());
        commandParserTable.put(HelpCommand.COMMAND_WORD_2, new HelpCommandParser());

        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_1, new HelpFormatCommandParser());
        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_2, new HelpFormatCommandParser());
        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_3, new HelpFormatCommandParser());
        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_4, new HelpFormatCommandParser());
        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_5, new HelpFormatCommandParser());
        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_6, new HelpFormatCommandParser());

        commandParserTable.put(ListByDoneCommand.COMMAND_WORD_1, new ListByDoneCommandParser());
        commandParserTable.put(ListByDoneCommand.COMMAND_WORD_2, new ListByDoneCommandParser());

        commandParserTable.put(ListByNotDoneCommand.COMMAND_WORD_1, new ListByNotDoneCommandParser());
        commandParserTable.put(ListByNotDoneCommand.COMMAND_WORD_2, new ListByNotDoneCommandParser());
        commandParserTable.put(ListByNotDoneCommand.COMMAND_WORD_3, new ListByNotDoneCommandParser());

        commandParserTable.put(ListByTagCommand.COMMAND_WORD_1, new ListByTagCommandParser());
        commandParserTable.put(ListByTagCommand.COMMAND_WORD_2, new ListByTagCommandParser());
        commandParserTable.put(ListByTagCommand.COMMAND_WORD_3, new ListByTagCommandParser());
        commandParserTable.put(ListByTagCommand.COMMAND_WORD_4, new ListByTagCommandParser());

        commandParserTable.put(ListCommand.COMMAND_WORD_1, new ListCommandParser());
        commandParserTable.put(ListCommand.COMMAND_WORD_2, new ListCommandParser());
        commandParserTable.put(ListCommand.COMMAND_WORD_3, new ListCommandParser());

        commandParserTable.put(LoadCommand.COMMAND_WORD_1, new LoadCommandParser());

        commandParserTable.put(PostGoogleCalendarCommand.COMMAND_WORD_1, new PostGoogleCalendarCommandParser());
        commandParserTable.put(PostGoogleCalendarCommand.COMMAND_WORD_2, new PostGoogleCalendarCommandParser());

        commandParserTable.put(RedoCommand.COMMAND_WORD_1, new RedoCommandParser());

        commandParserTable.put(SaveCommand.COMMAND_WORD_1, new SaveCommandParser());

        commandParserTable.put(SelectCommand.COMMAND_WORD_1, new SelectCommandParser());
        commandParserTable.put(SelectCommand.COMMAND_WORD_2, new SelectCommandParser());

        commandParserTable.put(ThemeChangeCommand.COMMAND_WORD_1, new ThemeChangeCommandParser());

        commandParserTable.put(UndoCommand.COMMAND_WORD_1, new UndoCommandParser());
        commandParserTable.put(UndoCommand.COMMAND_WORD_2, new UndoCommandParser());

        commandParserTable.put(UndoneCommand.COMMAND_WORD_1, new UndoneCommandParser());
        commandParserTable.put(UndoneCommand.COMMAND_WORD_2, new UndoneCommandParser());

        // for(commandKeyParserPair pair: commandKeyParserPairs){
        // commandParserTable.put(pair.getKey(), pair.getParser());
        // }
    }

    /**
     *
     * @param commandWord
     * @param arguments
     * @return Returns the correct command with the correct arguments
     */
    public Command getCorrectCommand(String commandWord, String arguments) {
        if (!commandParserTable.containsKey(commandWord)) {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
        return commandParserTable.get(commandWord).parse(arguments);
    }
    // protected class commandKeyParserPair{
    // private String commandKey;
    // private CommandParser commandParser;
    //
    // protected commandKeyParserPair(String commandKey, CommandParser
    // commandParser){
    // this.commandKey = commandKey;
    // this.commandParser =(CommandParser) commandParser;
    // }
    //
    // public String getKey(){
    // return this.commandKey;
    // }
    // public CommandParser getParser(){
    // return this.commandParser;
    // }
    // }
}
