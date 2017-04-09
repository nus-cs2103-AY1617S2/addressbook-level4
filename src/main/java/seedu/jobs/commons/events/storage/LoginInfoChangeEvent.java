package seedu.jobs.commons.events.storage;

import seedu.jobs.commons.events.BaseEvent;

public class LoginInfoChangeEvent extends BaseEvent {

    private String email;
    private String password;

    public LoginInfoChangeEvent(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "new user login info has been set";
    }

}
