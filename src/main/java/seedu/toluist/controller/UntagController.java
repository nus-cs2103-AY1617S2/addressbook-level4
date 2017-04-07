//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Searches the task list for matches in the parameters, and displays the results received
 */
public class UntagController extends TagController {
    private static final String COMMAND_UNTAG_WORD = "untag";

    private static final String MESSAGE_TEMPLATE_SUCCESS = "Sucessfully removed \"%s\".\n";
    private static final String MESSAGE_TEMPLATE_FAIL = "Failed to remove \"%s\".\n";
    private static final String MESSAGE_TEMPLATE_RESULT = "%s%s successfully removed.";

    private static final String HELP_DETAILS = "Removes a tag or multiple tags from an existing task.";
    private static final String HELP_FORMAT = "untag INDEX TAG(S)";
    private static final String[] HELP_COMMENTS = { "Related commands: `tag`",
                                                    "All tags are one word long.",
                                                    "Each word entered after the index will be untagged separately.", };
    private static final String[] HELP_EXAMPLES = { "`untag 1 schoolwork`\n"
                                                        + "Removes the tag `schoolwork` from the task at index 1.",
                                                    "`untag 1 housework groceries`\nRemoves the tags "
                                                        + "`housework` and `groceries` from the task at index 1." };

    private static final Logger logger = LogsCenter.getLogger(UntagController.class);

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass() + "will handle command");

        super.execute(tokens);
    }

    public boolean matchesCommand(String command) {
        String trimmedAndLowercasedCommand = command.trim().toLowerCase();
        return trimmedAndLowercasedCommand.startsWith(COMMAND_UNTAG_WORD);
    }

    protected String[] extractCommandWords(String command) {
        String replacedCommand = Pattern.compile(COMMAND_UNTAG_WORD, Pattern.CASE_INSENSITIVE).matcher(command)
            .replaceFirst(StringUtil.EMPTY_STRING).trim();
        return super.extractCommandWords(replacedCommand);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_UNTAG_WORD };
    }

    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }

    protected void modifyTagsForTask(ArrayList<String> successfulList,
            ArrayList<String> failedList, Task task, String keywords) {
        String[] keywordList = StringUtil.convertToArray(keywords);
        for (String keyword : keywordList) {
            if (task.removeTag(new Tag(keyword))) {
                successfulList.add(keyword);
            } else {
                failedList.add(keyword);
            }
        }
    }

    protected CommandResult formatDisplay(String[] successfulList, String[] failedList, int successCount) {
        String successWords = String.join(StringUtil.QUOTE_DELIMITER, successfulList);
        String failWords = String.join(StringUtil.QUOTE_DELIMITER, failedList);
        String resultMessage = StringUtil.EMPTY_STRING;

        if (successfulList.length > 0) {
            resultMessage += String.format(MESSAGE_TEMPLATE_SUCCESS, successWords);
        }
        if (failedList.length > 0) {
            resultMessage += String.format(MESSAGE_TEMPLATE_FAIL, failWords);
        }

        return new CommandResult(String.format(MESSAGE_TEMPLATE_RESULT, resultMessage,
                StringUtil.nounWithCount(StringUtil.WORD_TAG, successCount)));
    }

    protected void showInvalidFormatMessage() throws InvalidCommandException {
        throw new InvalidCommandException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, COMMAND_UNTAG_WORD));
    }
}
