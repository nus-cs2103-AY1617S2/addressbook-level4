package seedu.task.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;

import seedu.task.logic.commandlibrary.CommandLibrary;
import seedu.task.logic.commandlibrary.CommandLibrary.CommandInstance;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.parser.AddCommandParser;

public class CommandLibraryTest {
    
    private static HashMap<String,CommandInstance> hm = CommandLibrary.getInstance().getCommandTable();
    
    @Test
    public void getHashTable(){
        assertNotNull(hm);
    }
    @Test
    public void getCommandFromTable(){
        assertEquals(CommandLibrary.getCommandTable().get("Add".toLowerCase()).getCommandParser().getClass(),AddCommandParser.class);
        assertEquals(CommandLibrary.getCommandTable().get("add").getCommandKey(),AddCommand.COMMAND_WORD_1);
    }
    

}
