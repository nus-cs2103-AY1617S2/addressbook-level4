package seedu.address.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.WhatsLeftChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Activity;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.person.UniqueActivityList;
import seedu.address.model.person.UniqueActivityList.ActivityNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final WhatsLeft whatsLeft;
    private final FilteredList<ReadOnlyActivity> filteredActivities;

    /**
     * Initializes a ModelManager with the given whatsLeft and userPrefs.
     */
    public ModelManager(ReadOnlyWhatsLeft whatsLeft, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(whatsLeft, userPrefs);

        logger.fine("Initializing with WhatsLeft: " + whatsLeft + " and user prefs " + userPrefs);

        this.whatsLeft = new WhatsLeft(whatsLeft);
        filteredActivities = new FilteredList<>(this.whatsLeft.getActivityList());
    }

    public ModelManager() {
        this(new WhatsLeft(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyWhatsLeft newData) {
        whatsLeft.resetData(newData);
        indicateWhatsLeftChanged();
    }

    @Override
    public ReadOnlyWhatsLeft getWhatsLeft() {
        return whatsLeft;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateWhatsLeftChanged() {
        raise(new WhatsLeftChangedEvent(whatsLeft));
    }

    @Override
    public synchronized void deleteActivity(ReadOnlyActivity target) throws ActivityNotFoundException {
        whatsLeft.removeActivity(target);
        indicateWhatsLeftChanged();
    }

    @Override
    public synchronized void addActivity(Activity activity) throws UniqueActivityList.DuplicateActivityException {
        whatsLeft.addActivity(activity);
        updateFilteredListToShowAll();
        indicateWhatsLeftChanged();
    }

    @Override
    public void updateActivity(int filteredActivityListIndex, ReadOnlyActivity editedActivity)
            throws UniqueActivityList.DuplicateActivityException {
        assert editedActivity != null;

        int addressBookIndex = filteredActivities.getSourceIndex(filteredActivityListIndex);
        whatsLeft.updateActivity(addressBookIndex, editedActivity);
        indicateWhatsLeftChanged();
    }

    //=========== Filtered Activity List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredActivityList() {
        return new UnmodifiableObservableList<>(filteredActivities);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredScheduledActivityList() {
        ObservableList<ReadOnlyActivity> tempActivityList = FXCollections.observableArrayList();
        for (ReadOnlyActivity activity:filteredActivities) {
            if (activity.getByDate() != null || activity.getToDate() != null) {
                tempActivityList.add(activity);
            }
        }
        return new UnmodifiableObservableList<>(tempActivityList);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredUnscheduledActivityList() {
        ObservableList<ReadOnlyActivity> tempActivityList =  FXCollections.observableArrayList();
        for (ReadOnlyActivity activity:filteredActivities) {
            if (activity.getPriority() != null) {
                tempActivityList.add(activity);
            }
        }
        return new UnmodifiableObservableList<>(tempActivityList);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredActivities.setPredicate(null);
    }

    @Override
    public void updateFilteredActivityList(Set<String> keywords) {
        updateFilteredActivityList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredActivityList(Expression expression) {
        filteredActivities.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyActivity activity);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyActivity activity) {
            return qualifier.run(activity);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyActivity activity);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyActivity activity) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(activity.
                    getDescription().description, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredEventList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredDeadlineList() {
        // TODO Auto-generated method stub
        return null;
    }

}
