
        if (!endDate.isLater(startDate)) {
            throw new IllegalValueException(MESSAGE_EVENT_TASK_CONSTRAINT);
        }
        this.setStartDateTime(startDate);
        this.setEndDateTime(endDate);
    }

  

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" from: ").append(getStartDateTime().toString())
                .append(" to: ").append(getEndDateTime().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" by: ").append(getDeadline().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
