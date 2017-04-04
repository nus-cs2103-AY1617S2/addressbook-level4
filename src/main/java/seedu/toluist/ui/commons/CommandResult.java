//@@author A0131125Y-reused
package seedu.toluist.ui.commons;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public enum CommandResultType {
        SUCCESS, FAILURE
    }

    private final String feedbackToUser;
    private final CommandResultType type;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
        this.type = CommandResultType.SUCCESS;
    }

    public CommandResult(String feedbackToUser, CommandResultType type) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
        this.type = type;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public CommandResultType getCommandResultType() {
        return type;
    }
}
