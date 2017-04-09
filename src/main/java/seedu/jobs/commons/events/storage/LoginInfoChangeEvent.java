package seedu.jobs.commons.events.storage;

import seedu.jobs.commons.events.BaseEvent;

public class LoginInfoChangeEvent extends BaseEvent {

    private String email;
    private String password;
    private String credentialFilePath;

    public LoginInfoChangeEvent(String email, String password) {
        this.email = email;
        this.password = password;
        this.credentialFilePath = new java.io.File(System.getProperty("user.home"),
                ".credentials/calendar-java-quickstart/StoredCredential").getAbsolutePath();
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getCredentialFilePath() {
        return this.credentialFilePath;

    }

    @Override
    public String toString() {
        return "new user login info has been set";
    }

}
