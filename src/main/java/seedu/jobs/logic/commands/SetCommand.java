package seedu.jobs.logic.commands;

import java.util.Optional;

import seedu.jobs.commons.exceptions.IllegalValueException;

public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    //@@author A0130979U
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Set email and password to login to the Google Calendar. "
            + "Parameters: set email/EMAIL pwd/PASSWORD \n"
            + "Example: " + COMMAND_WORD
            + " set email/cs2103rocks@gmail.com pwd/abcdefgh123456";

    public static final String MESSAGE_SUCCESS = "Email and Password has been successfully added \n";
    public static final String MESSAGE_INVALID_EMAIL = "Emails should be 2 "
            + "alphanumeric/period strings separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";

    public String email;
    public String password;

    /**
     * Creates a set command using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public SetCommand(Optional<String> email, Optional<String> password)
            throws IllegalValueException {

        if (EMAIL_VALIDATION_REGEX.matches(email.get())) {
            throw new IllegalValueException(MESSAGE_INVALID_EMAIL);
        }
        this.email = email.get();
        this.password = password.get();
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.set(email, password);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
