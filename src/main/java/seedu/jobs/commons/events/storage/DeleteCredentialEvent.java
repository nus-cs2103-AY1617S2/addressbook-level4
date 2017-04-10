package seedu.jobs.commons.events.storage;

import seedu.jobs.commons.events.BaseEvent;

public class DeleteCredentialEvent extends BaseEvent {
    private String credentialFilePath;

    public DeleteCredentialEvent(String credentialFilePath) {
        this.credentialFilePath = credentialFilePath;
    }

    public String getCredentialFilePath() {
        return credentialFilePath;
    }

    @Override
    public String toString() {
        return "deleting old credentials file";
    }

}
