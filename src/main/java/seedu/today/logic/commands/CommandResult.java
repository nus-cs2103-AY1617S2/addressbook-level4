package seedu.today.logic.commands;

/**
 * Represents the result of a command execution.
 */
// @@Author A0144315N
public class CommandResult {

    private final String feedbackToUser;
    private final String statusBarMessage;

    public CommandResult(String feedbackToUser, String statusBarMessage) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
        this.statusBarMessage = statusBarMessage;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getStatusBarMessage() {
        return statusBarMessage;
    }
}
