// @@author A0144315N
// Similar functions has been combined to a single method at higher level
@Override
    public int compareTo(ReadOnlyTask task2) {
        if (task2 instanceof TaskWithDeadline) {
            return -MAX_TIME_DIFF;
        } else {
            return 0;
        }
    }
