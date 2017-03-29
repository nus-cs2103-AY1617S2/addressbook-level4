package seedu.taskit.logic.parser;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskit.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.commons.lang.ArrayUtils;

import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.FindCommand;
import seedu.taskit.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        
        
        //@@author A0097141H
        // keywords delimited by whitespace
        // if keywords are wrapped with double inverted commas, consider whole string as 1 keyword
        // assume only 1 set of wrapped keywords are allowed.
        String[] keywords = {"keyword"};
        final Set<String> keywordSet;
        int idxOpenInvComma = 0;
        int idxCloseInvComma = 0;
        boolean foundInvComma = false;
        String keywordsStr = matcher.group("keywords");
        for (int i=0;i<keywordsStr.length();i++){
        	if (keywordsStr.charAt(i) == '\"'){
        		if(!foundInvComma){
        			idxOpenInvComma = i;
        			foundInvComma = true;
        		}else{
        			//found a matching inverted comma
        			idxCloseInvComma = i;
        			foundInvComma = false;
        		}
        	}
        }
        if(idxOpenInvComma < idxCloseInvComma && !foundInvComma){ //found a pair of inverted commas!
        	//extract inverted commas
        	keywords[0] = keywordsStr.substring(idxOpenInvComma+1, idxCloseInvComma);
        	String subStrKeywords = keywordsStr.replace(keywordsStr.substring(idxOpenInvComma,idxCloseInvComma+1), "");
        	System.out.println(subStrKeywords);
        	if(!subStrKeywords.trim().equals("")){
        		String[] keywordsToAdd = subStrKeywords.trim().split("\\s+");
        		System.out.println("subStrKeywords: " + keywordsToAdd[0]);
        		Object[] objArr = ArrayUtils.addAll(keywords, keywordsToAdd);
        		keywords = Arrays.copyOf(objArr, objArr.length, String[].class);
        		for(String str: keywords){
        			System.out.println("String is: " + str);
        		}
        	}
        } else{
        	keywords = keywordsStr.split("\\s+");
        }
        
//        if(matcher.group("keywords").charAt(0) == '"' && matcher.group("keywords").charAt(matcher.group("keywords").length()-1) == '"')
//        {
//        	keywords[0] = matcher.group("keywords").replaceAll("\"", "");
//        }
//        else{
//        	
//        	
//        }
        keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }
    


}
