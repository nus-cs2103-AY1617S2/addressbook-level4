package savvytodo.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

//@@A0147827U
/**
 * Class to generate dictionaries (as Collections<String>) for auto-complete feature in CommandBox
 * 
 * @author jingloon
 *
 */
public class AutoCompleteDictionaryFactory {

    public static final String[] keywords = { "add", "delete", "list", "edit",
            "clear", "find", "undo", "redo", "help",
            "select" };
    
    public static final String[] commonWords = { "breakfast", "lunch", "dinner",
            "class", "homework", "assigment" };
    
    public static Collection<String> getDictionary(){
        ArrayList<String> dictionary = new ArrayList<String>();
        dictionary.addAll(Arrays.asList(keywords));
        dictionary.addAll(Arrays.asList(commonWords));
        
        return dictionary;
    }
    
}
