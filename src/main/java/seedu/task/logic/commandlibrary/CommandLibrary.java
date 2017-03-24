package seedu.task.logic.commandlibrary;

import java.util.HashMap;

import seedu.task.logic.parser.CommandParser;

public class CommandLibrary {
    
    private static HashMap<String,String> commandKeyTable;
        
    public CommandParser getCommandParser() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        String commonPackage = "seedu.task.logic.parser";
//        String addCommandParser = "AddCommandParser";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(commonPackage).append(commandKeyTable.get("add"));
        
        Class<?> unknownParser = Class.forName(stringBuilder.toString());
        return  (CommandParser) unknownParser.newInstance();
    }
}
