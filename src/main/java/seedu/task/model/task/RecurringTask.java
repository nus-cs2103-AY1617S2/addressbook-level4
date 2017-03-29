////@@author A0164212U
//package seedu.task.model.task;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Objects;
//
//import seedu.task.commons.exceptions.IllegalValueException;
//import seedu.task.commons.util.CollectionUtil;
//import seedu.task.model.tag.UniqueTagList;
//
///**
// * Represents a Task in the ToDo List. Guarantees: details are present and not
// * null, field values are validated.
// */
//public class RecurringTask implements ReadOnlyTask {
//
//    private Description description;
//    private Priority priority;
//    private UniqueTagList tags;
//    private RecurringFrequency frequency;
//
//    private ArrayList<RecurringTaskOccurrence> occurrences;
//
//    /**
//     * Every field must be present and not null.
//     */
//    public RecurringTask(Description description, Priority priority, Timing startTiming,
//                          Timing endTiming, UniqueTagList tags, RecurringFrequency frequency) {
//        assert !CollectionUtil.isAnyNull(description, priority, startTiming, tags, frequency);
//        this.description = description;
//        this.priority = priority;
//        this.tags = new UniqueTagList(tags); // protect internal tags from
//        // changes in the arg list
//        this.frequency = frequency;
//        this.occurrences = new ArrayList<RecurringTaskOccurrence>();
//        setOccurences(this.frequency, startTiming, endTiming);
//
//    }
//
//    /**
//     * Creates a copy of the given ReadOnlyTask.
//     */
//    public RecurringTask(ReadOnlyTask source) {
//        this(source.getDescription(), source.getPriority(), source.getStartTiming(), source.getEndTiming(),
//                source.getTags(), source.getFrequency());
//    }
//
//    public void setDescription(Description description) {
//        assert description != null;
//        this.description = description;
//    }
//
//    @Override
//    public Description getDescription() {
//        return description;
//    }
//
//    public void setPriority(Priority priority) {
//        assert priority != null;
//        this.priority = priority;
//    }
//
//    @Override
//    public Priority getPriority() {
//        return priority;
//    }
//
//    @Override
//    public UniqueTagList getTags() {
//        return new UniqueTagList(tags);
//    }
//
//    /**
//     * Replaces this task's tags with the tags in the argument tag list.
//     */
//    public void setTags(UniqueTagList replacement) {
//        tags.setTags(replacement);
//    }
//
//    @Override
//    public RecurringFrequency getFrequency() {
//        return frequency;
//    }
//    public ArrayList<RecurringTaskOccurrence> getOccurrences() {
//        return occurrences;
//    }
//
//    public SimpleDateFormat retriveFormat(String s) {
//        SimpleDateFormat format;
//        if (s.length() <= 10) {
//            format = new SimpleDateFormat("dd/MM/yyyy");
//        } else {
//            format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
//        }
//        return format;
//    }
//
//    /** If frequency is in hours - support up to 168 hours (1 week)
//     * If frequency is in weeks - support up to 24 weeks
//     * If frequency is in months - support up to 12 months
//     * @param frequency
//     * @param startTime
//     * @param endTime
//     */
//    public void setOccurences(RecurringFrequency frequency, Timing initialStartTime, Timing initialEndTime) {
//        int freqNumber = frequency.getFrequencyNumber();
//        String freqCharacter = frequency.getFrequencyCharacter();
//        Calendar cal1 = Calendar.getInstance();
//        Calendar cal2 = Calendar.getInstance();
//        cal1.setTime(initialStartTime.getTiming());
//        cal2.setTime(initialEndTime.getTiming());
//        SimpleDateFormat startTimeFormat = retriveFormat(initialStartTime.toString());
//        SimpleDateFormat endTimeFormat = retriveFormat(initialEndTime.toString());
//        String tempStartTime, tempEndTime;
//        Timing tempStart = null, tempEnd = null;
//        RecurringTaskOccurrence occurenceToAdd;
//
//        switch (freqCharacter) {
//        case "h":
//            for (int i = 0; i < RecurringFrequency.HOUR_LIMIT; i += freqNumber) {
//                cal1.add(Calendar.HOUR_OF_DAY, freqNumber);
//                cal2.add(Calendar.HOUR_OF_DAY, freqNumber);
//                tempStartTime = startTimeFormat.format(cal1.getTime());
//                tempEndTime = endTimeFormat.format(cal2.getTime());
//                try {
//                    tempStart = new Timing(tempStartTime);
//                } catch (IllegalValueException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                try {
//                    tempEnd = new Timing(tempEndTime);
//                } catch (IllegalValueException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                occurenceToAdd = new RecurringTaskOccurrence(tempStart, tempEnd);
//                occurrences.add(occurenceToAdd);
//            }
//            break;
//        case "d":
//            for (int i = 0; i < RecurringFrequency.DAY_LIMIT; i += freqNumber) {
//                cal1.add(Calendar.DATE, freqNumber);
//                cal2.add(Calendar.DATE, freqNumber);
//                tempStartTime = startTimeFormat.format(cal1.getTime());
//                tempEndTime = endTimeFormat.format(cal2.getTime());
//                try {
//                    tempStart = new Timing(tempStartTime);
//                } catch (IllegalValueException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                try {
//                    tempEnd = new Timing(tempEndTime);
//                } catch (IllegalValueException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                occurenceToAdd = new RecurringTaskOccurrence(tempStart, tempEnd);
//                occurrences.add(occurenceToAdd);
//            }
//            break;
//        case "m":
//            for (int i = 0; i < RecurringFrequency.MONTH_LIMIT; i += freqNumber) {
//                cal1.add(Calendar.MONTH, freqNumber);
//                cal2.add(Calendar.MONTH, freqNumber);
//                tempStartTime = startTimeFormat.format(cal1.getTime());
//                tempEndTime = endTimeFormat.format(cal2.getTime());
//                try {
//                    tempStart = new Timing(tempStartTime);
//                } catch (IllegalValueException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                try {
//                    tempEnd = new Timing(tempEndTime);
//                } catch (IllegalValueException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                occurenceToAdd = new RecurringTaskOccurrence(tempStart, tempEnd);
//                occurrences.add(occurenceToAdd);
//            }
//            break;
//        }
//    }
//
//    /**
//     * Updates this task with the details of {@code replacement}.
//     */
//    public void resetData(ReadOnlyTask replacement) {
//        assert replacement != null;
//
//        this.setDescription(replacement.getDescription());
//        this.setPriority(replacement.getPriority());
//        this.setOccurences(replacement.getFrequency(), replacement.getStartTiming(), replacement.getEndTiming());
//        this.setTags(replacement.getTags());
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof ReadOnlyTask // instanceof handles nulls
//                        && this.isSameStateAs((ReadOnlyTask) other));
//    }
//
//    @Override
//    public int hashCode() {
//        // use this method for custom fields hashing instead of implementing
//        // your own
//        return Objects.hash(description, priority, getStartTiming(), getEndTiming(), tags);
//    }
//
//    @Override
//    public String toString() {
//        return getAsText();
//    }
//
//
//    //returns initial start time
//    @Override
//    public Timing getStartTiming() {
//        return getOccurrences().get(0).getStartTiming();
//    }
//
//    //returns initial start time
//    @Override
//    public Timing getEndTiming() {
//        return getOccurrences().get(0).getEndTiming();
//    }
//
//    @Override
//    public boolean isComplete() {
//        return getOccurrences().get(0).isComplete();
//    }
//
//    @Override
//    public boolean isRecurring() {
//        return true;
//    }
//}
