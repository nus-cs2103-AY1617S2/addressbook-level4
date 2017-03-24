package seedu.task.logic.commandlibrary;

import java.util.HashMap;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.FindExactCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.HelpFormatCommand;
import seedu.task.logic.commands.ListByDoneCommand;
import seedu.task.logic.commands.ListByNotDoneCommand;
import seedu.task.logic.commands.ListByTagCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.LoadCommand;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.logic.commands.UnDoneCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.logic.parser.AddCommandParser;
import seedu.task.logic.parser.ClearCommandParser;
import seedu.task.logic.parser.CommandParser;
import seedu.task.logic.parser.DeleteCommandParser;
import seedu.task.logic.parser.EditCommandParser;
import seedu.task.logic.parser.EditIsDoneCommandParser;
import seedu.task.logic.parser.EditUnDoneCommandParser;
import seedu.task.logic.parser.ExitCommandParser;
import seedu.task.logic.parser.FindCommandParser;
import seedu.task.logic.parser.FindExactCommandParser;
import seedu.task.logic.parser.HelpCommandParser;
import seedu.task.logic.parser.HelpFormatCommandParser;
import seedu.task.logic.parser.ListByDoneCommandParser;
import seedu.task.logic.parser.ListByNotDoneCommandParser;
import seedu.task.logic.parser.ListByTagCommandParser;
import seedu.task.logic.parser.ListCommandParser;
import seedu.task.logic.parser.LoadCommandParser;
import seedu.task.logic.parser.SaveCommandParser;
import seedu.task.logic.parser.UndoCommandParser;

public class CommandLibrary {

    private static CommandLibrary instance = null;
    private static HashMap<String, CommandParser> commandParserTable;
    private static commandKeyParserPair[] commandKeyParserPairs;
    
    protected CommandLibrary() {
//        commandKeyParserPairs = new commandKeyParserPair[]{new commandKeyParserPair(AddCommand.COMMAND_WORD_1, new AddCommandParser()),
//                new commandKeyParserPair(ClearCommand.COMMAND_WORD_1,new ClearCommandParser()),
//                new commandKeyParserPair(DeleteCommand.COMMAND_WORD_1, new DeleteCommandParser()),
//                new commandKeyParserPair(DoneCommand.COMMAND_WORD_1, new EditIsDoneCommandParser()),
//                new commandKeyParserPair(DoneCommand.COMMAND_WORD_2, new EditIsDoneCommandParser()),
//                new commandKeyParserPair(EditCommand.COMMAND_WORD_1, new EditCommandParser()),
//                new commandKeyParserPair(ExitCommand.COMMAND_WORD_1, new ExitCommandParser()),
//                new commandKeyParserPair(FindCommand.COMMAND_WORD_1, new FindCommandParser()),
//                new commandKeyParserPair(FindCommand.COMMAND_WORD_2, new FindCommandParser()),
//                new commandKeyParserPair(FindExactCommand.COMMAND_WORD_1, new FindExactCommandParser()),
//                new commandKeyParserPair(FindExactCommand.COMMAND_WORD_2, new FindExactCommandParser()),
//                new commandKeyParserPair(FindExactCommand.COMMAND_WORD_3, new FindExactCommandParser()),
//                new commandKeyParserPair(FindExactCommand.COMMAND_WORD_4, new FindExactCommandParser()),
//                new commandKeyParserPair(HelpCommand.COMMAND_WORD_1, new HelpCommandParser()),
//                new commandKeyParserPair(HelpCommand.COMMAND_WORD_2, new HelpCommandParser()),
//                new commandKeyParserPair(HelpFormatCommand.COMMAND_WORD_1,new HelpFormatCommandParser()),
//                new commandKeyParserPair(HelpFormatCommand.COMMAND_WORD_2,new HelpFormatCommandParser()),
//                new commandKeyParserPair(HelpFormatCommand.COMMAND_WORD_3,new HelpFormatCommandParser()),
//                new commandKeyParserPair(HelpFormatCommand.COMMAND_WORD_4,new HelpFormatCommandParser()),
//                new commandKeyParserPair(ListByDoneCommand.COMMAND_WORD_1, new ListByDoneCommandParser()),
//                new commandKeyParserPair(ListByDoneCommand.COMMAND_WORD_2, new ListByDoneCommandParser()),
//                new commandKeyParserPair(ListByNotDoneCommand.COMMAND_WORD_1, new ListByNotDoneCommandParser()),
//                new commandKeyParserPair(ListByNotDoneCommand.COMMAND_WORD_2, new ListByNotDoneCommandParser()),
//                new commandKeyParserPair(ListByNotDoneCommand.COMMAND_WORD_3, new ListByNotDoneCommandParser()),
//                new commandKeyParserPair(ListByTagCommand.COMMAND_WORD_1, new ListByTagCommandParser()),
//                new commandKeyParserPair(ListByTagCommand.COMMAND_WORD_2, new ListByTagCommandParser()),
//                new commandKeyParserPair(ListByTagCommand.COMMAND_WORD_3, new ListByTagCommandParser()),
//                new commandKeyParserPair(ListByTagCommand.COMMAND_WORD_4, new ListByTagCommandParser()),
//                new commandKeyParserPair(ListCommand.COMMAND_WORD_1, new ListCommandParser()),
//                new commandKeyParserPair(ListCommand.COMMAND_WORD_2, new ListCommandParser()),
//                new commandKeyParserPair(ListCommand.COMMAND_WORD_3, new ListCommandParser()),
//                new commandKeyParserPair(LoadCommand.COMMAND_WORD_1, new LoadCommandParser()),
//                new commandKeyParserPair(SaveCommand.COMMAND_WORD_1, new SaveCommandParser()),
//                new commandKeyParserPair(UndoCommand.COMMAND_WORD_1, new UndoCommandParser()),
//                new commandKeyParserPair(UndoCommand.COMMAND_WORD_2, new UndoCommandParser()),
//                new commandKeyParserPair(UnDoneCommand.COMMAND_WORD_1, new EditUnDoneCommandParser()),
//                new commandKeyParserPair(UnDoneCommand.COMMAND_WORD_2, new EditUnDoneCommandParser()),
//        };
        init();
        
    }

    public static CommandLibrary getInstance() {
        if (instance == null) {
           instance =new CommandLibrary();
        }
        return instance;
    }

    /**
     * To initialize the hashmap and necessary variables
     */
    private static void init() {
        commandParserTable =  new HashMap<>();
        // TODO Auto-generated method stub
        commandParserTable.put(AddCommand.COMMAND_WORD_1, new AddCommandParser());
        
        commandParserTable.put(ClearCommand.COMMAND_WORD_1, new ClearCommandParser());
        
        commandParserTable.put(DeleteCommand.COMMAND_WORD_1, new DeleteCommandParser());
        
        commandParserTable.put(DoneCommand.COMMAND_WORD_1, new EditIsDoneCommandParser());
        commandParserTable.put(DoneCommand.COMMAND_WORD_2, new EditIsDoneCommandParser());
        
        commandParserTable.put(EditCommand.COMMAND_WORD_1, new EditCommandParser());
        
        commandParserTable.put(ExitCommand.COMMAND_WORD_1, new ExitCommandParser());

        commandParserTable.put(FindCommand.COMMAND_WORD_1, new FindCommandParser());
        commandParserTable.put(FindCommand.COMMAND_WORD_2, new FindCommandParser());

        commandParserTable.put(FindExactCommand.COMMAND_WORD_1, new FindExactCommandParser());
        commandParserTable.put(FindExactCommand.COMMAND_WORD_2, new FindExactCommandParser());
        commandParserTable.put(FindExactCommand.COMMAND_WORD_3, new FindExactCommandParser());
        commandParserTable.put(FindExactCommand.COMMAND_WORD_4, new FindExactCommandParser());

        commandParserTable.put(HelpCommand.COMMAND_WORD_1, new HelpCommandParser());
        commandParserTable.put(HelpCommand.COMMAND_WORD_2, new HelpCommandParser());

        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_1, new HelpFormatCommandParser());
        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_2, new HelpFormatCommandParser());
        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_3, new HelpFormatCommandParser());
        commandParserTable.put(HelpFormatCommand.COMMAND_WORD_4, new HelpFormatCommandParser());
        
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
        
        commandParserTable.put(SaveCommand.COMMAND_WORD_1, new SaveCommandParser());
        
        commandParserTable.put(UndoCommand.COMMAND_WORD_1, new UndoCommandParser());
        commandParserTable.put(UndoCommand.COMMAND_WORD_2, new UndoCommandParser());

        commandParserTable.put(UnDoneCommand.COMMAND_WORD_1, new EditUnDoneCommandParser());
        commandParserTable.put(UnDoneCommand.COMMAND_WORD_2, new EditUnDoneCommandParser());
        
//        for(commandKeyParserPair pair: commandKeyParserPairs){
//            commandParserTable.put(pair.getKey(), pair.getParser());
//        }
    }

    /**
     * 
     * @param commandWord
     * @param arguments
     * @return Returns the correct command with the correct arguments
     */
    public Command getCorrectCommand(String commandWord, String arguments){
        return  commandParserTable.get(commandWord).parse(arguments);
    }
    
    protected class commandKeyParserPair{
        private String commandKey;
        private CommandParser commandParser;
        
        protected commandKeyParserPair(String commandKey, CommandParser commandParser){
            this.commandKey = commandKey;
            this.commandParser =(CommandParser) commandParser;
        }
        
        public String getKey(){
            return this.commandKey;
        }
        public CommandParser getParser(){
            return this.commandParser;
        }
    }

//    public CommandParser getCommandParser()
//            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//        String commonPackage = "seedu.task.logic.parser";
//        // String addCommandParser = "AddCommandParser";
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(commonPackage).append(commandWordTable.get("add"));
//
//        Class<?> unknownParser = Class.forName(stringBuilder.toString());
//        return (CommandParser) unknownParser.newInstance();
//    }
}
