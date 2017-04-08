package t09b1.today.logic.commands;

/**
 * Represents the result of a command execution.
 */
// @@Author A0144315N
public class CommandResult {

    public final String feedbackToUser;
    public final String statusBarMessage;

    public CommandResult(String feedbackToUser, String statusBarMessage) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
        this.statusBarMessage = statusBarMessage;
    }
}
