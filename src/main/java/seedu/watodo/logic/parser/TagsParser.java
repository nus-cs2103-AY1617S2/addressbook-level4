package seedu.watodo.logic.parser;

import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.watodo.logic.parser.ParserUtil.EXTRACT_ARGS_REGEX;
import static seedu.watodo.logic.parser.ParserUtil.INDEX_FIRST_ARG;
import static seedu.watodo.logic.parser.ParserUtil.NUMFIELD_SPLIT_BY_WHITESPACE;
import static seedu.watodo.logic.parser.ParserUtil.WHITESPACE;

import java.util.HashSet;
import java.util.Set;

//@@author A0143076J
/**
 * Parses input argument for tags specified by the PREFIX_TAG
 */
public class TagsParser {

    private Set<String> tags;
    private String unparsedArgs;

    /**
     * Parses the given {@code String} of arguments for any number of tags
     */
    public void parse(String args) {
        extractTags(args);
        extractUnparsedArgs(args);
    }

    /**
     * Finds all instances of the PREFIX_TAG in the given arg and returns a set of all the tags
     */
    private void extractTags(String args) {
        ArgumentTokenizer tagsTokenizer = new ArgumentTokenizer(PREFIX_TAG);
        tagsTokenizer.tokenize(args);

        Set<String> unparsedTags = ParserUtil.toSet(tagsTokenizer.getAllValues(PREFIX_TAG));
        Set<String> parsedTags = new HashSet<String>();
        for (String tag : unparsedTags) {
            tag = tag.split("[\\s+]", NUMFIELD_SPLIT_BY_WHITESPACE)
                [INDEX_FIRST_ARG];  //tag name is only until the first whitespace
            parsedTags.add(tag);
        }
        this.tags = parsedTags;
    }

    /**
     * Removes all instances of the PREFIX_TAG and the corresponding tag name in the arg
     * to get the unparsed args
     */
    private void extractUnparsedArgs(String args) {
        //tag name is only until the first whitespace
        String tagArgs = String.format(EXTRACT_ARGS_REGEX, PREFIX_TAG.getPrefix() + "(\\S+)", "");
        String trimmedArgs = args.replaceFirst(tagArgs, WHITESPACE).trim(); //to remove excess whitespace
        while (!trimmedArgs.equals(args)) {
            args = trimmedArgs;
            trimmedArgs = args.replaceFirst(tagArgs, WHITESPACE).trim();  //to prevent consecutive whitespaces
        }
        this.unparsedArgs = args;
    }


    public String getUnparsedArgs() {
        return unparsedArgs;
    }

    public Set<String> getTags() {
        return tags;
    }

}
