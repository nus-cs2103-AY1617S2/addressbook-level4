# A0140063X-unused
###### /Date.java
``` java
//this code uses multiple SimpleDateFormat to validate date input.
//this code is Working but decided to use pretty time parser instead which allows natural language parsing.
/**
 * Represents a Task's date in KIT. Guarantees: immutable; is valid as declared
 * in {@link #isValidDate(String)}
 */
public class Date {

    // add to user guide
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date format invalid, use help to see "
            + "allowed formats or try this format: DD-MM-YY hh:mm AM/PM";
    public static final String DEFAULT_DATE = "DEFAULT_DATE";

    public static final ArrayList<SimpleDateFormat> ALLOWED_FORMATS = new ArrayList<>();
    private final java.util.Date value;
    private static SimpleDateFormat format = new SimpleDateFormat("d/M/y h:m a");

    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        if (date.equals(DEFAULT_DATE)) {
            this.value = null;
        } else {
            String trimmedDate = date.trim();

            if (!isValidDate(trimmedDate)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }

            if (!trimmedDate.contains("-") && !trimmedDate.contains("/")
                    && !trimmedDate.matches("[0-9]* [a-zA-Z]{3,}.*")) {
                java.util.Date currentDate = new java.util.Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/y");
                trimmedDate = dateFormat.format(currentDate) + " " + trimmedDate;
            }

            this.value = new java.util.Date(getTime(trimmedDate));
        }
    }

    // date must be valid
    private long getTime(String date) {
        Date.format = getDateFormat(date);
        assert (format) != null;
        try {
            return format.parse(date).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    // allow both datetime, date only, time only = current date,
    // time assume 24HR unless AM/PM specified
    private static void prepareDateFormats() {
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y H:m"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y HHmm"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y h:m a"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y h:mma"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y ha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y hha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y H:m"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y HHmm"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y h:m a"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y h:mma"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y ha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y hha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy H:m"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy HHmm"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy h:m a"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy h:mma"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy ha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy hha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("H:m"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("HHmm"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("h:m a"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("h:mma"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("ha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("hha"));

    }

    private static SimpleDateFormat getDateFormat(String input) {
        for (SimpleDateFormat format : ALLOWED_FORMATS) {
            try {
                ParsePosition position = new ParsePosition(0);
                format.setLenient(false);
                format.parse(input, position).getTime();

                if (position.getIndex() != input.length()) {
                    throw new ParseException(input, 0);
                } else {
                    return format;
                }
            } catch (ParseException e) {
                // check next
            } catch (NullPointerException e) {
                // check next
            }
        }
        return null;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String input) {
        if (input.trim().isEmpty()) {
            return false;
        }

        prepareDateFormats();

        return (getDateFormat(input) != null);
    }

}
```
###### /SmartAddTest.java
``` java
//This test works on local machine, but is unable to pass on travis.
//This is due to the required credentials for Google Calendar is not held by travis.
//Tried to keep the stored credential in a file and read from it but it did not work.
//Suspect that credentials are unique to device.
public class SmartAddTest extends TaskManagerGuiTest {

    @Test
    public void smartAdd() throws DuplicateTagException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        GoogleCalendar.setTestGoogleCredentialFilePath();

        //add task with all details
        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Meet friends for dinner 6pm-8pm"
                + " in Clementi r/Eat stingray t/friends");
        TestTask taskToAdd = createTask("Meet friends for dinner in Clementi", "6pm", "8pm",
                "Eat stingray", "Clementi", "friends", false, "");
        assertSmartAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add task without remark or tag
        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Discuss presentation with group 10am-12pm"
                + " in Meeting Room");
        taskToAdd = createTask("Discuss presentation with group in Meeting Room", "10am", "12pm",
                "", "Meeting Room", "", false, "");
        assertSmartAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add task without location, remark or tag, specify end time by stating duration
        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Celebration Party fri 11pm 4 hours");
        taskToAdd = createTask("Celebration Party", "fri 11pm", "sat 3am", "", "", "", false, "");
        assertSmartAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Celebration Party fri 11pm 4 hours");
        taskToAdd = createTask("Celebration Party", "fri 11pm", "sat 3am", "", "", "", false, "");
        assertResultMessage(SmartAddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add task without end date = assume duration 1hr
        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Think about life sat 12am");
        taskToAdd = createTask("Think about life", "sat 12am", "sat 1am", "", "", "", false, "");
        assertSmartAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add to empty list
        commandBox.runCommand("clear");

        //add task without start date = assume given date is start date and duration 1hr
        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Meet with client 3pm");
        taskToAdd = createTask("Meet with client", "3pm", "4pm", "", "", "", false, "");
        assertSmartAddSuccess(taskToAdd);

        //invalid command
        commandBox.runCommand("smart add Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private TestTask createTask(String name, String startDate, String endDate, String remark,
            String location, String tags, boolean isDone, String eventId)
                    throws DuplicateTagException, IllegalValueException {
        if (tags.trim().equals("")) {
            return new TestTask(new Name(name), new Date(startDate), new Date(endDate),
                    new Remark(remark), new Location(location),
                    new UniqueTagList(), isDone, eventId);
        } else {
            return new TestTask(new Name(name), new Date(startDate), new Date(endDate),
                    new Remark(remark), new Location(location),
                    new UniqueTagList(tags.trim()), isDone, eventId);
        }
    }

    private void assertSmartAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
```
