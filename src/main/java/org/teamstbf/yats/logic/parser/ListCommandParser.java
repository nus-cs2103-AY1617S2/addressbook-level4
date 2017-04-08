package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.logic.commands.AddCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.IncorrectCommand;
import org.teamstbf.yats.logic.commands.ListCommand;
import org.teamstbf.yats.logic.commands.ListCommandDeadline;
import org.teamstbf.yats.logic.commands.ListCommandDone;
import org.teamstbf.yats.logic.commands.ListCommandEndTime;
import org.teamstbf.yats.logic.commands.ListCommandLocation;
import org.teamstbf.yats.logic.commands.ListCommandStartTime;
import org.teamstbf.yats.logic.commands.ListCommandTag;

/**
 * Parses input arguments and creates a new ListCommand object
 */
// @@author A0138952W
public class ListCommandParser {

    private final int LIST_COMMAND_SUFFIX = 2;
    private final String COMMAND_EXTENSION_END = "by end";
    private final String COMMAND_EXTENSION_START = "by start";
    private final String COMMAND_EXTENSION_DEADLINE = "by deadline";
    private final String COMMAND_EXTENSION_LOCATION = "by location";
    private final String COMMAND_EXTENSION_DONE = "done";
    private final String COMMAND_EXTENSION_TAG = "by tag";
    private final String KEYWORD_DONE = "Yes";
    private final String KEYWORD_PERIOD = " ";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListCommand and returns a ListCommand object that is according to the
     * suffix for execution.
     */
    public Command parse(String args) {
        try {
            if (args.contains(ListCommand.COMMAND_WORD_EXTENTION)) {
                String[] commandTextArray = stringTokenizer(args);
                switch (commandTextArray[LIST_COMMAND_SUFFIX]) {
                case (ListCommand.COMMAND_WORD_SUFFIX_END):
                    return new ListCommandEndTime(internalParser(args, COMMAND_EXTENSION_END, KEYWORD_PERIOD));
                case (ListCommand.COMMAND_WORD_SUFFIX_START):
                    return new ListCommandStartTime(internalParser(args, COMMAND_EXTENSION_START, KEYWORD_PERIOD));
                case (ListCommand.COMMAND_WORD_SUFFIX_DEADLINE):
                    return new ListCommandDeadline(internalParser(args, COMMAND_EXTENSION_DEADLINE, KEYWORD_PERIOD));
                case (ListCommand.COMMAND_WORD_SUFFIX_LOCATION):
                    return new ListCommandLocation(internalParser(args, COMMAND_EXTENSION_LOCATION, KEYWORD_PERIOD));
                case (ListCommand.COMMAND_WORD_SUFFIX_TAG):
                    return new ListCommandTag(internalParser(args, COMMAND_EXTENSION_TAG, KEYWORD_PERIOD));
                }
            } else if (args.contains(ListCommand.COMMAND_WORD_SUFFIX_DONE)) {
                return new ListCommandDone(internalParser(args, COMMAND_EXTENSION_DONE, KEYWORD_DONE));
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand();
    }

    /**
     * Parses a {@code String} to remove the Command Extensions
     *
     * @param args
     *            String to parse
     * @param toReplace
     *            Command Extension to replace
     * @return {@code Set<String>} for the ListCommand variants to process
     */
    private Set<String> internalParser(String args, String toReplace, String toReplaceWith) {
        String[] keywords = args.replaceFirst(toReplace, toReplaceWith).trim().split(" ");
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return keywordSet;
    }

    /**
     * Returns a {@code String[]} of the command text tokens
     *
     * @param commandText
     */
    private String[] stringTokenizer(String commandText) {
        return commandText.split(" ");
    }
}
