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
 * Represents a Task in the ToDo List. Guarantees: details are present and not
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
        this.occurrences = occurrences;
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
        //        this.setStartTiming(replacement.getStartTiming());
        //        this.setEndTiming(replacement.getEndTiming());
        this.setOccurrences(replacement.getOccurrences());
        this.setTags(replacement.getTags());
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

    public SimpleDateFormat retriveFormat(String s) {
        SimpleDateFormat format;
        if (s.length() <= 10) {
            format = new SimpleDateFormat("dd/MM/yyyy");
        } else {
            format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        }
        return format;
    }

    @Override
    public void removeOccurrence(int i) {
        this.occurrences.remove(i);
    }

    public void setOccurrences(ArrayList<RecurringTaskOccurrence> occurrences) {
        this.occurrences = occurrences;
    }

    /** If frequency is in hours - support up to 168 hours (1 week)
     * If frequency is in weeks - support up to 24 weeks
     * If frequency is in months - support up to 12 months
     * @param startTime
     * @param endTime
     */
    public void setOccurrences(Timing initialStartTime, Timing initialEndTime) {
        this.occurrences.add(new RecurringTaskOccurrence(initialStartTime, initialEndTime));
        if (isRecurring()) {
            int freqNumber = frequency.getFrequencyNumber();
            String freqCharacter = frequency.getFrequencyCharacter();
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(initialStartTime.getTiming());
            cal2.setTime(initialEndTime.getTiming());
            SimpleDateFormat startTimeFormat = retriveFormat(initialStartTime.toString());
            SimpleDateFormat endTimeFormat = retriveFormat(initialEndTime.toString());
            String tempStartTime;
            String tempEndTime;
            Timing tempStart = null;
            Timing tempEnd = null;
            RecurringTaskOccurrence occurrenceToAdd;
            switch (freqCharacter) {
            case "h":
                for (int i = 1; i < RecurringFrequency.HOUR_LIMIT; i += freqNumber) {
                    cal1.add(Calendar.HOUR_OF_DAY, freqNumber);
                    cal2.add(Calendar.HOUR_OF_DAY, freqNumber);
                    tempStartTime = startTimeFormat.format(cal1.getTime());
                    tempEndTime = endTimeFormat.format(cal2.getTime());
                    try {
                        tempStart = new Timing(tempStartTime);
                    } catch (IllegalValueException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        tempEnd = new Timing(tempEndTime);
                    } catch (IllegalValueException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    occurrenceToAdd = new RecurringTaskOccurrence(tempStart, tempEnd);
                    occurrences.add(occurrenceToAdd);
                }
                break;
            case "d":
                for (int i = 1; i < RecurringFrequency.DAY_LIMIT; i += freqNumber) {
                    cal1.add(Calendar.DATE, freqNumber);
                    cal2.add(Calendar.DATE, freqNumber);
                    tempStartTime = startTimeFormat.format(cal1.getTime());
                    tempEndTime = endTimeFormat.format(cal2.getTime());
                    try {
                        tempStart = new Timing(tempStartTime);
                    } catch (IllegalValueException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        tempEnd = new Timing(tempEndTime);
                    } catch (IllegalValueException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    occurrenceToAdd = new RecurringTaskOccurrence(tempStart, tempEnd);
                    occurrences.add(occurrenceToAdd);
                }
                break;
            case "m":
                for (int i = 1; i < RecurringFrequency.MONTH_LIMIT; i += freqNumber) {
                    cal1.add(Calendar.MONTH, freqNumber);
                    cal2.add(Calendar.MONTH, freqNumber);
                    tempStartTime = startTimeFormat.format(cal1.getTime());
                    tempEndTime = endTimeFormat.format(cal2.getTime());
                    try {
                        tempStart = new Timing(tempStartTime);
                    } catch (IllegalValueException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        tempEnd = new Timing(tempEndTime);
                    } catch (IllegalValueException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    occurrenceToAdd = new RecurringTaskOccurrence(tempStart, tempEnd);
                    occurrences.add(occurrenceToAdd);
                }
                break;
            default:
                break;
            }
        }
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

    public static Comparator<Task> TaskComparator = new Comparator<Task>() {

        @Override
        public int compare(Task task1, Task task2) {
            task1.getStartTiming().setTiming(task1.getStartTiming().toString());
            task1.getEndTiming().setTiming(task1.getEndTiming().toString());
            return task1.compareTo(task2);
        }

    };
    //@@author

}
