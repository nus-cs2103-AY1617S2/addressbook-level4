package seedu.geekeep.commons.events.model;

import seedu.geekeep.commons.events.BaseEvent;
import seedu.geekeep.model.Config;
import seedu.geekeep.model.ReadOnlyGeeKeep;

/** Indicates the GeeKeep in the model has changed*/
public class GeekeepFilePathChangedEvent extends BaseEvent {

    public final Config config;
    public final ReadOnlyGeeKeep geekeep;

    public GeekeepFilePathChangedEvent(Config config, ReadOnlyGeeKeep geekeep) {
        this.config = config;
        this.geekeep = geekeep;
    }

    @Override
    public String toString() {
        return "GeeKeep file path = " + config.getGeekeepFilePath() + "\nnumber of tasks "
                + geekeep.getTaskList().size()
                + ", number of tags " + geekeep.getTagList().size();
    }
}
