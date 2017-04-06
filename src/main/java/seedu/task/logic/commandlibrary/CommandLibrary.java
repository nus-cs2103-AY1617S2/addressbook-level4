package seedu.task.logic.commandlibrary;

import static seedu.task.commons.core.Messages.MESSAGE_COMMAND_DOES_NOT_EXIST;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.QueryUnknownCommandEvent;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
//import seedu.task.logic.commands.FindDateCommand;
import seedu.task.logic.commands.FindExactCommand;
import seedu.task.logic.commands.GetGoogleCalendarCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.HelpFormatCommand;
import seedu.task.logic.commands.IncorrectCommand;
//import seedu.task.logic.commands.ListByDoneCommand;
//import seedu.task.logic.commands.ListByNotDoneCommand;
import seedu.task.logic.commands.ListByTagCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.LoadCommand;
import seedu.task.logic.commands.PostGoogleCalendarCommand;
import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.SmartAddCommand;
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
//import seedu.task.logic.parser.FindDateCommandParser;
import seedu.task.logic.parser.FindExactCommandParser;
import seedu.task.logic.parser.GetGoogleCalendarCommandParser;
import seedu.task.logic.parser.HelpCommandParser;
import seedu.task.logic.parser.HelpFormatCommandParser;
//import seedu.task.logic.parser.ListByDoneCommandParser;
//import seedu.task.logic.parser.ListByNotDoneCommandParser;
import seedu.task.logic.parser.ListByTagCommandParser;
import seedu.task.logic.parser.ListCommandParser;
import seedu.task.logic.parser.LoadCommandParser;
import seedu.task.logic.parser.PostGoogleCalendarCommandParser;
import seedu.task.logic.parser.RedoCommandParser;
import seedu.task.logic.parser.SaveCommandParser;
import seedu.task.logic.parser.SelectCommandParser;
import seedu.task.logic.parser.SmartAddCommandParser;
import seedu.task.logic.parser.ThemeChangeCommandParser;
import seedu.task.logic.parser.UndoCommandParser;
import seedu.task.logic.parser.UndoneCommandParser;

//@@author A0142487Y
public class CommandLibrary {

    private static CommandLibrary instance = null;
    private static HashMap<String, CommandInstance> commandTable;

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

        commandTable = new HashMap<>();
        commandTable.put(AddCommand.COMMAND_WORD_1, new CommandInstance(AddCommand.COMMAND_WORD_1,
            new AddCommandParser() , AddCommand.MESSAGE_USAGE));

        commandTable.put(ClearCommand.COMMAND_WORD_1, new CommandInstance(ClearCommand.COMMAND_WORD_1,
                new ClearCommandParser() , ClearCommand.MESSAGE_USAGE));

        commandTable.put(DeleteCommand.COMMAND_WORD_1, new CommandInstance(DeleteCommand.COMMAND_WORD_1,
                new DeleteCommandParser() , DeleteCommand.MESSAGE_USAGE));

        commandTable.put(DoneCommand.COMMAND_WORD_1, new CommandInstance(DoneCommand.COMMAND_WORD_1,
                new DoneCommandParser() , DoneCommand.MESSAGE_USAGE));
        commandTable.put(DoneCommand.COMMAND_WORD_2, new CommandInstance(DoneCommand.COMMAND_WORD_1,
                new DoneCommandParser() , DoneCommand.MESSAGE_USAGE));

        commandTable.put(EditCommand.COMMAND_WORD_1, new CommandInstance(EditCommand.COMMAND_WORD_1,
                new EditCommandParser() , EditCommand.MESSAGE_USAGE));

        commandTable.put(ExitCommand.COMMAND_WORD_1, new CommandInstance(ExitCommand.COMMAND_WORD_1,
                new ExitCommandParser() , ExitCommand.MESSAGE_USAGE));

        commandTable.put(FindCommand.COMMAND_WORD_1, new CommandInstance(FindCommand.COMMAND_WORD_1,
                new FindCommandParser() , FindCommand.MESSAGE_USAGE));
        commandTable.put(FindCommand.COMMAND_WORD_2, new CommandInstance(FindCommand.COMMAND_WORD_1,
                new FindCommandParser() , FindCommand.MESSAGE_USAGE));

        commandTable.put(FindExactCommand.COMMAND_WORD_1, new CommandInstance(FindExactCommand.COMMAND_WORD_1,
                new FindExactCommandParser(), FindExactCommand.MESSAGE_USAGE));
        commandTable.put(FindExactCommand.COMMAND_WORD_2, new CommandInstance(FindExactCommand.COMMAND_WORD_1,
                new FindExactCommandParser(), FindExactCommand.MESSAGE_USAGE));
        commandTable.put(FindExactCommand.COMMAND_WORD_3, new CommandInstance(FindExactCommand.COMMAND_WORD_1,
                new FindExactCommandParser(), FindExactCommand.MESSAGE_USAGE));
        commandTable.put(FindExactCommand.COMMAND_WORD_4, new CommandInstance(FindExactCommand.COMMAND_WORD_1,
                new FindExactCommandParser(), FindExactCommand.MESSAGE_USAGE));

        commandTable.put(GetGoogleCalendarCommand.COMMAND_WORD_1,
                new CommandInstance(GetGoogleCalendarCommand.COMMAND_WORD_1,
                new GetGoogleCalendarCommandParser(), GetGoogleCalendarCommand.MESSAGE_USAGE));
        commandTable.put(GetGoogleCalendarCommand.COMMAND_WORD_2,
                new CommandInstance(GetGoogleCalendarCommand.COMMAND_WORD_1,
                new GetGoogleCalendarCommandParser(), GetGoogleCalendarCommand.MESSAGE_USAGE));

        commandTable.put(HelpCommand.COMMAND_WORD_1, new CommandInstance(HelpCommand.COMMAND_WORD_1,
                new HelpCommandParser(), HelpCommand.MESSAGE_USAGE));
        commandTable.put(HelpCommand.COMMAND_WORD_2, new CommandInstance(HelpCommand.COMMAND_WORD_1,
                new HelpCommandParser(), HelpCommand.MESSAGE_USAGE));
        commandTable.put(HelpCommand.COMMAND_WORD_3, new CommandInstance(HelpCommand.COMMAND_WORD_1,
                new HelpCommandParser(), HelpCommand.MESSAGE_USAGE));
        commandTable.put(HelpCommand.COMMAND_WORD_4, new CommandInstance(HelpCommand.COMMAND_WORD_1,
                new HelpCommandParser(), HelpCommand.MESSAGE_USAGE));

        commandTable.put(HelpFormatCommand.COMMAND_WORD_1, new CommandInstance(HelpFormatCommand.COMMAND_WORD_1,
                new HelpFormatCommandParser(), HelpFormatCommand.MESSAGE_USAGE));
        commandTable.put(HelpFormatCommand.COMMAND_WORD_2, new CommandInstance(HelpFormatCommand.COMMAND_WORD_1,
                new HelpFormatCommandParser(), HelpFormatCommand.MESSAGE_USAGE));
        commandTable.put(HelpFormatCommand.COMMAND_WORD_3, new CommandInstance(HelpFormatCommand.COMMAND_WORD_1,
                new HelpFormatCommandParser(), HelpFormatCommand.MESSAGE_USAGE));
        commandTable.put(HelpFormatCommand.COMMAND_WORD_4, new CommandInstance(HelpFormatCommand.COMMAND_WORD_1,
                new HelpFormatCommandParser(), HelpFormatCommand.MESSAGE_USAGE));

//        commandTable.put(ListByDoneCommand.COMMAND_WORD_1, new CommandInstance(ListByDoneCommand.COMMAND_WORD_1,
//                new ListByDoneCommandParser(), ListByDoneCommand.MESSAGE_USAGE));
//        commandTable.put(ListByDoneCommand.COMMAND_WORD_2, new CommandInstance(ListByDoneCommand.COMMAND_WORD_1,
//                new ListByDoneCommandParser(), ListByDoneCommand.MESSAGE_USAGE));
//
//        commandTable.put(ListByNotDoneCommand.COMMAND_WORD_1, new CommandInstance(ListByNotDoneCommand.COMMAND_WORD_1,
//                new ListByNotDoneCommandParser(), ListByNotDoneCommand.MESSAGE_USAGE));
//        commandTable.put(ListByNotDoneCommand.COMMAND_WORD_2, new CommandInstance(ListByNotDoneCommand.COMMAND_WORD_1,
//                new ListByNotDoneCommandParser(), ListByNotDoneCommand.MESSAGE_USAGE));
//        commandTable.put(ListByNotDoneCommand.COMMAND_WORD_3, new CommandInstance(ListByNotDoneCommand.COMMAND_WORD_1,
//                new ListByNotDoneCommandParser(), ListByNotDoneCommand.MESSAGE_USAGE));

        commandTable.put(ListByTagCommand.COMMAND_WORD_1, new CommandInstance(ListByTagCommand.COMMAND_WORD_1,
                new ListByTagCommandParser(), ListByTagCommand.MESSAGE_USAGE));
        commandTable.put(ListByTagCommand.COMMAND_WORD_2, new CommandInstance(ListByTagCommand.COMMAND_WORD_1,
                new ListByTagCommandParser(), ListByTagCommand.MESSAGE_USAGE));
        commandTable.put(ListByTagCommand.COMMAND_WORD_3, new CommandInstance(ListByTagCommand.COMMAND_WORD_1,
                new ListByTagCommandParser(), ListByTagCommand.MESSAGE_USAGE));
        commandTable.put(ListByTagCommand.COMMAND_WORD_4, new CommandInstance(ListByTagCommand.COMMAND_WORD_1,
                new ListByTagCommandParser(), ListByTagCommand.MESSAGE_USAGE));

        commandTable.put(ListCommand.COMMAND_WORD_1, new CommandInstance(ListCommand.COMMAND_WORD_1,
                new ListCommandParser(), ListCommand.MESSAGE_USAGE));
        commandTable.put(ListCommand.COMMAND_WORD_2, new CommandInstance(ListCommand.COMMAND_WORD_1,
                new ListCommandParser(), ListCommand.MESSAGE_USAGE));
        commandTable.put(ListCommand.COMMAND_WORD_3, new CommandInstance(ListCommand.COMMAND_WORD_1,
                new ListCommandParser(), ListCommand.MESSAGE_USAGE));

        commandTable.put(LoadCommand.COMMAND_WORD_1, new CommandInstance(LoadCommand.COMMAND_WORD_1,
                new LoadCommandParser(), LoadCommand.MESSAGE_USAGE));

        commandTable.put(PostGoogleCalendarCommand.COMMAND_WORD_1,
                new CommandInstance(PostGoogleCalendarCommand.COMMAND_WORD_1,
                new PostGoogleCalendarCommandParser(), PostGoogleCalendarCommand.MESSAGE_USAGE));
        commandTable.put(PostGoogleCalendarCommand.COMMAND_WORD_2,
                new CommandInstance(PostGoogleCalendarCommand.COMMAND_WORD_1,
                        new PostGoogleCalendarCommandParser(), PostGoogleCalendarCommand.MESSAGE_USAGE));

        commandTable.put(RedoCommand.COMMAND_WORD_1, new CommandInstance(RedoCommand.COMMAND_WORD_1,
                new RedoCommandParser(), RedoCommand.MESSAGE_USAGE));

        commandTable.put(SaveCommand.COMMAND_WORD_1, new CommandInstance(SaveCommand.COMMAND_WORD_1,
                new SaveCommandParser(), SaveCommand.MESSAGE_USAGE));

        commandTable.put(SelectCommand.COMMAND_WORD_1, new CommandInstance(SelectCommand.COMMAND_WORD_1,
                new SelectCommandParser(), SelectCommand.MESSAGE_USAGE));
        commandTable.put(SelectCommand.COMMAND_WORD_2, new CommandInstance(SelectCommand.COMMAND_WORD_1,
                new SelectCommandParser(), SelectCommand.MESSAGE_USAGE));

        commandTable.put(SmartAddCommand.COMMAND_WORD_1, new CommandInstance(SmartAddCommand.COMMAND_WORD_1,
            new SmartAddCommandParser() , SmartAddCommand.MESSAGE_USAGE));
        commandTable.put(SmartAddCommand.COMMAND_WORD_2, new CommandInstance(SmartAddCommand.COMMAND_WORD_1,
                new SmartAddCommandParser() , SmartAddCommand.MESSAGE_USAGE));

        commandTable.put(ThemeChangeCommand.COMMAND_WORD_1, new CommandInstance(ThemeChangeCommand.COMMAND_WORD_1,
                new ThemeChangeCommandParser(), ThemeChangeCommand.MESSAGE_USAGE));

        commandTable.put(UndoCommand.COMMAND_WORD_1, new CommandInstance(UndoCommand.COMMAND_WORD_1,
                new UndoCommandParser(), UndoCommand.MESSAGE_USAGE));
        commandTable.put(UndoCommand.COMMAND_WORD_2, new CommandInstance(UndoCommand.COMMAND_WORD_1,
                new UndoCommandParser(), UndoCommand.MESSAGE_USAGE));

        commandTable.put(UndoneCommand.COMMAND_WORD_1, new CommandInstance(UndoneCommand.COMMAND_WORD_1,
                new UndoneCommandParser(), UndoneCommand.MESSAGE_USAGE));
        commandTable.put(UndoneCommand.COMMAND_WORD_2, new CommandInstance(UndoneCommand.COMMAND_WORD_1,
                new UndoneCommandParser(), UndoneCommand.MESSAGE_USAGE));

    }

    /**
     *
     * @param commandWord
     * @param arguments
     * @return Returns the correct command with the correct arguments
     */
    public Command getCorrectCommand(String commandWord, String arguments) {
        if (!commandTable.containsKey(commandWord)) {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
        return commandTable.get(commandWord).commandParser.parse(arguments.trim());
    }

    public String getCommandUsage(String commandWord) {
        if (!commandTable.containsKey(commandWord)) {
            EventsCenter.getInstance().post(new QueryUnknownCommandEvent());
            return String.format(MESSAGE_COMMAND_DOES_NOT_EXIST, commandWord);
        }
        return commandTable.get(commandWord).getCommandUsage();
    }

    protected static class CommandInstance {
        private String commandKey;
        private String commandUsage;
        private CommandParser commandParser;

        protected CommandInstance(String commandKey, CommandParser commandParser, String commandUsage) {
            this.commandKey = commandKey;
            this.commandParser = commandParser;
            this.commandUsage = commandUsage;
        }

        public String getCommandKey() {
            return this.commandKey;
        }

        public CommandParser getCommandParser() {
            return this.commandParser;
        }

        public String getCommandUsage() {
            return this.commandUsage;
        }

    }

    public static HashMap<String, CommandInstance> getCommandTable() {
        return commandTable;
    }
}
