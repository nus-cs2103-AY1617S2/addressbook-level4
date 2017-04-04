// @@author A0144315N
// Similar functions has been combined to a single method at higher level
@Override
public int compareTo(ReadOnlyTask task2) {
    if (task2 instanceof TaskWithoutDeadline) {
        return MAX_TIME_DIFF;
    } else {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(this.getDeadline().getDate());
        cal2.setTime(task2.getDeadline().getDate());
        // Compares in UNIX time
        return (int) ((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / 1000);
    }
}