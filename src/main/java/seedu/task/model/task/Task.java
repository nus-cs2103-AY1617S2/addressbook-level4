//@@author A0164212U
package seedu.task.model.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Objects;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the Task Manager. Guarantees: details are present and not
 * null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private Description description;
    private Priority priority;
    private ArrayList<RecurringTaskOccurrence> occurrences;
    private boolean recurring;
    private UniqueTagList tags;
    private RecurringFrequency frequency;
    private ArrayList<Integer> occurrenceIndexList = new ArrayList<Integer>();

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, Priority priority, Timing startTiming, Timing endTiming,
            UniqueTagList tags, boolean recurring, RecurringFrequency frequency) {
        assert !CollectionUtil.isAnyNull(description, priority, startTiming, tags);
        this.description = description;
        this.priority = priority;
        this.occurrences = new ArrayList<RecurringTaskOccurrence>();
        this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
        this.recurring = recurring;
        this.frequency = frequency;
        setOccurrences(startTiming, endTiming);
        occurrenceIndexList.add(0);
    }

    public Task(Description description, Priority priority, ArrayList<RecurringTaskOccurrence> occurrences,
            UniqueTagList tags, boolean recurring, RecurringFrequency frequency) {
        assert !CollectionUtil.isAnyNull(description, priority, occurrences, tags, recurring, frequency);
        this.description = description;
        this.priority = priority;
        this.occurrences = new ArrayList<RecurringTaskOccurrence>(occurrences);
        this.tags = new UniqueTagList(tags);
        this.recurring = recurring;
        this.frequency = frequency;
    }


    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getOccurrences(),
                source.getTags(), source.isRecurring(), source.getFrequency());
    }

    @Override
    public ArrayList<Integer> getOccurrenceIndexList() {
        return occurrenceIndexList;
    };

    @Override
    public void setOccurrenceIndexList(ArrayList<Integer> list) {
        occurrenceIndexList = list;
    };

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void setStartTiming(Timing startTiming) {
        assert startTiming != null;
        this.occurrences.get(0).setStartTiming(startTiming);
    }

    @Override
    public Timing getStartTiming(int i) { //add parameter to index into correct endTime
        if (this.occurrences.size() == 0) {
            try {
                return new Timing(Timing.TIMING_NOT_SPECIFIED);
            } catch (IllegalValueException e) {
                assert false : "Illegal timing value";
            }
        }
        return this.occurrences.get(i).getStartTiming();
    }

    @Override
    public Timing getStartTiming() {
        return getStartTiming(0);
    }

    @Override
    public void setEndTiming(Timing endTiming) { //add parameter to index into correct endTime
        assert endTiming != null;
        this.occurrences.get(0).setEndTiming(endTiming);
    }

    @Override
    public Timing getEndTiming() {
        if (this.occurrences.size() == 0) {
            try {
                return new Timing(Timing.TIMING_NOT_SPECIFIED);
            } catch (IllegalValueException e) {
                assert false : "Illegal timing value";
            }
        }
        return this.occurrences.get(0).getEndTiming();
    }

    public void setComplete() { //add parameter to index into correct endTime
        this.occurrences.get(0).setComplete(true);
    }

    @Override
    public boolean isComplete() { //add parameter to index into correct endTime
        return this.occurrences.get(0).isComplete();
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setPriority(replacement.getPriority());
        this.setOccurrences(replacement.getOccurrences());
        this.setTags(replacement.getTags());
        this.setFrequency(replacement.getFrequency());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(description, priority,
                occurrences.get(0).getStartTiming(), occurrences.get(0).getEndTiming(),
                tags, recurring, frequency);
    }

    @Override
    public RecurringFrequency getFrequency() {
        return frequency;
    }

    @Override
    public void setFrequency(RecurringFrequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public boolean isRecurring() {
        return recurring;
    }

    @Override
    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    @Override
    public ArrayList<RecurringTaskOccurrence> getOccurrences() {
        return occurrences;
    }

    /**
     * @param string
     * @return SimpleDateFormat object of passed in string
     */
    private SimpleDateFormat retriveDateFormat(String s) {
        assert s != null;
        SimpleDateFormat format;
        String basicFormat = "dd/MM/yyyy";
        String extendedFormat = "HH:mm dd/MM/yyyy";
        if (s.length() <= basicFormat.length()) {
            format = new SimpleDateFormat(basicFormat);
        } else {
            format = new SimpleDateFormat(extendedFormat);
        }
        return format;
    }

    @Override
    public void removeOccurrence(int i) {
        this.occurrences.remove(i);
    }

    @Override
    public void setOccurrences(ArrayList<RecurringTaskOccurrence> occurrences) {
        this.occurrences = occurrences;
    }

    /** If frequency is in years - support up to 4 years
     * If frequency is in days - support up to 60 days
     * If frequency is in months - support up to 12 months
     * @param startTime
     * @param endTime
     */
    public void setOccurrences(Timing initialStartTime, Timing initialEndTime) {
        this.occurrences.add(new RecurringTaskOccurrence(initialStartTime, initialEndTime));
        if (isRecurring()) {
            String freqCharacter = frequency.getFrequencyCharacter();
            switch (freqCharacter) {
            case "d":
                int dayLimit = RecurringFrequency.DAY_LIMIT;
                int day = Calendar.DATE;
                setOccurrences(initialStartTime, initialEndTime, dayLimit, day);
                break;
            case "m":
                int monthLimit = RecurringFrequency.MONTH_LIMIT;
                int month = Calendar.MONTH;
                setOccurrences(initialStartTime, initialEndTime, monthLimit, month);
                break;
            case "y":
                int yearLimit = RecurringFrequency.YEAR_LIMIT;
                int year = Calendar.YEAR;
                setOccurrences(initialStartTime, initialEndTime, yearLimit, year);
                break;
            default:
                break;
            }
        }
    }

    /**
     * @param initialStartTime
     * @param initialEndTime
     * @param limit specifies the number of iterations to add to the occurrences list
     * @param offSet specifies the calendar field to be updated
     */
    public void setOccurrences(Timing initialStartTime, Timing initialEndTime, int limit, int offSet) {
        int freqNumber = frequency.getFrequencyNumber();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(initialStartTime.getTiming());
        cal2.setTime(initialEndTime.getTiming());
        SimpleDateFormat startTimeFormat = retriveDateFormat(initialStartTime.toString());
        SimpleDateFormat endTimeFormat = retriveDateFormat(initialEndTime.toString());
        String tempStartTime;
        String tempEndTime;
        Timing tempStart = null;
        Timing tempEnd = null;
        RecurringTaskOccurrence occurrenceToAdd;

        for (int i = 1; i < limit; i += freqNumber) {
            cal1.add(offSet, freqNumber);
            cal2.add(offSet, freqNumber);
            tempStartTime = startTimeFormat.format(cal1.getTime());
            tempEndTime = endTimeFormat.format(cal2.getTime());
            try {
                tempStart = new Timing(tempStartTime);
                tempEnd = new Timing(tempEndTime);
            } catch (IllegalValueException e) {
                assert false : "Illegal Value for timings";
            }
            occurrenceToAdd = new RecurringTaskOccurrence(tempStart, tempEnd);
            occurrences.add(occurrenceToAdd);
        }
    }

    /**
     * @param taskToModify ReadOnlyTask object
     * @return new Task instance with only one occurrence;
     * modifies the parameter by removing the respective occurrence for additional functionality
     */
    public static Task extractOccurrence(ReadOnlyTask taskToModify) {
        Task newTask = null;
        if (taskToModify.getOccurrenceIndexList().size() == 0) {
            taskToModify.getOccurrenceIndexList().add(0);
        }
        int occurrenceIndex = taskToModify.getOccurrenceIndexList().get(0);
        RecurringFrequency freq = null;
        try {
            freq = new RecurringFrequency(RecurringFrequency.NULL_FREQUENCY);
        } catch (IllegalValueException e1) {
            assert false : "Illegal value for frequency";
        }
        newTask = new Task(
                taskToModify.getDescription(),
                taskToModify.getPriority(),
                taskToModify.getOccurrences().get(occurrenceIndex).getStartTiming(),
                taskToModify.getOccurrences().get(occurrenceIndex).getEndTiming(),
                taskToModify.getTags(),
                false,
                freq);
        newTask.getStartTiming().setTiming(newTask.getStartTiming().toString());
        newTask.getEndTiming().setTiming(newTask.getEndTiming().toString());
        taskToModify.removeOccurrence(occurrenceIndex);

        return newTask;
    }

    /**
     * converts readOnlyTask object to Task object
     * @param readOnlyTask
     * @return Task
     */
    public static Task readOnlyToTask(ReadOnlyTask readOnlyTask) {
        assert readOnlyTask != null;
        Task task = new Task(
                readOnlyTask.getDescription(),
                readOnlyTask.getPriority(),
                readOnlyTask.getOccurrences(),
                readOnlyTask.getTags(),
                readOnlyTask.isRecurring(),
                readOnlyTask.getFrequency());
        return task;
    }

    //@@author A0163559U
    /**
     * Results in Tasks sorted by completed state, followed by priority, endTiming, startTiming
     * and lastly by frequency.
     * Note: If a and b are tasks and a.compareTo(b) == 0, that does not imply
     * a.equals(b).
     */
    @Override
    public int compareTo(Task compareTask) {
        int compareToResult = 0;

        if (this.isComplete() && compareTask.isComplete()) {
            compareToResult = 0;
        } else if (this.isComplete()) {
            compareToResult = 1;
        } else if (compareTask.isComplete()) {
            compareToResult = -1;
        }

        if (compareToResult == 0) {
            compareToResult = this.priority.compareTo(compareTask.priority);
        }

        if (compareToResult == 0) {
            compareToResult = this.getEndTiming().compareTo(compareTask.getEndTiming());
        }

        if (compareToResult == 0) {
            compareToResult = this.getStartTiming().compareTo(compareTask.getStartTiming());
        }

        if (compareToResult == 0) {
            compareToResult = this.getDescription().compareTo(compareTask.getDescription());
        }

        return compareToResult;
    }

    public static Comparator<Task> taskComparator = new Comparator<Task>() {

        @Override
        public int compare(Task task1, Task task2) {
            task1.getStartTiming().setTiming(task1.getStartTiming().toString());
            task1.getEndTiming().setTiming(task1.getEndTiming().toString());
            return task1.compareTo(task2);
        }

    };
    //@@author

}
