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
//@@author A0097141H
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
        String keywords = matcher.group("keywords");
        final String[] groupedKeywords = parseKeywords(keywords);
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(groupedKeywords));

        return new FindCommand(keywordSet);
    }


    //@@author A0097141H
    /**
     * Method to separate keywords by whitespace or double inverted commas
     * Assume only 1 set of wrapped keywords are allowed.
     * @param matcher
     * @return String[] keywords of
     */
    private String[] parseKeywords(String str){
        String[] keywords = {"keyword"}; //will eventually be overridden

        String keywordsStr = str;

        int[] invCommaIdx = findInvCommasIndexes(keywordsStr);
        int idxOpenInvComma =	 invCommaIdx[0];
        int idxCloseInvComma = 	 invCommaIdx[1];

        if(idxOpenInvComma < idxCloseInvComma){ //found a pair of inverted commas!
            //extract inverted commas
            keywords[0] = keywordsStr.substring(idxOpenInvComma+1, idxCloseInvComma);

            //create new substring by removing keywords in inverted commas
            String subStrKeywords = keywordsStr.replace(keywordsStr.substring(idxOpenInvComma,idxCloseInvComma+1), "");

            //if subStrKeywords is not empty string
            if(!subStrKeywords.trim().equals("")){
                String[] keywordsToAdd = subStrKeywords.trim().split("\\s+");
                keywords = concatStringArrays(keywords, keywordsToAdd);
            }
        } else{//invalid or don't have inverted commas, just split normally
            keywords = keywordsStr.split("\\s+");
        }
        return keywords;
    }

  //@@author A0097141H
    /**
     * returns indexes of occurrences of '\"'
     * @param str
     * @return int[] {openInvCommaIndex,closeInvCommaIndex,invCommaCount}
     * invCommaCount not in use for now, but future releases maybe
     */
    private int[] findInvCommasIndexes(String str){

        int[] idx = {0,0,0};

        int idxOpenInvComma = 0;
        int idxCloseInvComma = 0;
        boolean foundInvComma = false;

        for (int i=0;i<str.length();i++){
            if (str.charAt(i) == '\"'){
                if(!foundInvComma){
                    idxOpenInvComma = i;
                    foundInvComma = true;
                }else{
                    //found a matching inverted comma
                    idxCloseInvComma = i;
                    foundInvComma = false;
                }
                idx[2]++;
            }
        }
        idx[0] = idxOpenInvComma;
        idx[1] = idxCloseInvComma;
        return idx;
    }

  //@@author A0097141H
    /**
     * simple method to return a String[] array based on 2 String[] arrays
     * @param strArr1
     * @param strArr2
     * @return
     */
    private String[] concatStringArrays(String[] strArr1, String[] strArr2){

        Object[] objArr = ArrayUtils.addAll(strArr1, strArr2);
        String[] keywords = Arrays.copyOf(objArr, objArr.length, String[].class);

        return keywords;
    }

}
