# A0139161J-reused
###### \java\seedu\task\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> deadline} into an {@code Optional<Deadline>} if {@code deadline} is present.
     */
    public static Optional<Deadline> parseDeadline(Optional<String> deadline) throws IllegalValueException {
        assert deadline != null;
        Parser parser = new Parser();
        String fromDate = new String("");
        String fromTime = null;
        String toDate = new String("");
        String toTime = null;
        if (deadline.isPresent()) {
            String deadlineString = deadline.toString();
            List <DateGroup> groups = parser.parse(deadlineString);
            List dates = null;
            int line;
            int column;
            String matchingValue;
            String syntaxTree;
            Map parseMap;
            boolean isRecurring;
            Date recursUntil;

            for (DateGroup group: groups) {
                dates = group.getDates();
                line = group.getLine();
                column = group.getPosition();
                matchingValue = group.getText();
                syntaxTree = group.getSyntaxTree().toStringTree();
                parseMap = group.getParseLocations();
                isRecurring = group.isRecurring();
                recursUntil = group.getRecursUntil();
            }

            if (dates != null) {
                fromDate = dates.get(0).toString();
                fromTime = getTime(fromDate);
                if (dates.size() != 1) {
                    toDate = dates.get(1).toString();
                    toTime = getTime(toDate);
                    isEvent = true;
                }
            }
            StringTokenizer st = new StringTokenizer(fromDate);
            List<String> listDeadline = new ArrayList<String>();
            while (st.hasMoreTokens()) {
                listDeadline.add(st.nextToken());
            }
            List<String> endOfEvent = new ArrayList<String>();
            if (isEvent) {
                st = new StringTokenizer(toDate);
                while (st.hasMoreTokens()) {
                    endOfEvent.add(st.nextToken());
                }
            }
            StringBuilder deadlineStringBuilder = new StringBuilder();
            deadlineStringBuilder.append(listDeadline.get(2) + "-" + listDeadline.get(1)
                + "-" + listDeadline.get(5) + " @ " + fromTime);
            if (isEvent) {
                deadlineStringBuilder.append(" to " + endOfEvent.get(2) + "-" + endOfEvent.get(1)
                    + "-" + endOfEvent.get(5) + " @ " + toTime);
            }
            fromDate = deadlineStringBuilder.toString();
        }
        return deadline.isPresent() ? Optional.of(new Deadline(fromDate)) : Optional.empty();
    }
```
