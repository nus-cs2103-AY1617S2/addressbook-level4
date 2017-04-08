package seedu.today.ui.util;

import com.jfoenix.validation.base.ValidatorBase;

// @@author A0144315N
public class CommandTextFieldValidator extends ValidatorBase {
    private boolean isErrorMessageShown;

    public CommandTextFieldValidator() {
        super();
        this.isErrorMessageShown = false;
    }

    /**
     * Implements parent class's abstract method. It refreshes the commandBox
     * error message when called.
     */
    @Override
    protected void eval() {
        return;
    }

    /**
     * displays error message under command box Notice: need to call the
     * corresponding JFXTextField's validate() method to update the UI
     */
    public void showErrorMessage() {
        hasErrors.set(true);
        isErrorMessageShown = true;
    }

    public void hideErrorMessage() {
        hasErrors.set(false);
        isErrorMessageShown = false;
    }
}
