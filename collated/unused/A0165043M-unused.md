# A0165043M-unused
###### \AddCommand.java
``` java
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    private Date combineDateTime(Date date, Date time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mma");
        SimpleDateFormat returnFormat = new SimpleDateFormat("h:mma dd/MM/yy");
        try {
            return returnFormat.parse(timeFormat.format(time) + " " + dateFormat.format(date));
        } catch (ParseException e) {
            throw new ParseException(AddCommand.MESSAGE_INVALID_TIME, 0);
        }
    }
```
###### \AddCommandParser.java
``` java
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    private Date getTimeRecur (Optional<String> time) throws ParseException {
        if (time.get().contains(" ")) {
            throw new ParseException(AddCommand.MESSAGE_INVALID_TIME, 0);
        }
        DateFormat dateFormat = new SimpleDateFormat("h:mma");
        return dateFormat.parse(time.get());
    }
```
###### \AddCommandTest.java
``` java
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */	
    @Test
    public void add_recurringAddTest_success() {
        TestTodo[] currentList = td.getTypicalTodos();
        String name = "recurringAdd";
        String startTime = "11:00AM";
        String endTime = "12:00PM";
        String tags = "recurringtags";
        String startDate = "6/4/17";
        String endMonth = "5/17";
        TestTodo[] addTestTodoList = new TestTodo[8];
        String[] addDate = { "6/4/17", "13/4/17", "20/4/17", "27/4/17", "4/5/17",
            "11/5/17", "18/5/17", "25/5/17" };
        String[] addDateName = { " 06 04 17", " 13 04 17", " 20 04 17", " 27 04 17", " 04 05 17",
            " 11 05 17", " 18 05 17", " 25 05 17" };
        for (int i = 0; i < 8; i++) {
            try {
                addTestTodoList[i] = new TodoBuilder().withName(name + addDateName[i]).
                        withStartTime(startTime + " " + addDate[i]).
                        withEndTime(endTime + " " + addDate[i]).withTags(tags).build();
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        assertRecurringAddSuccess(name + " " + startTime + " " + endTime +
                " " + tags + " " + startDate + " " + endMonth, addTestTodoList, currentList);
    }
```
###### \AddCommandTest.java
``` java
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    @Test
    public void add_invalidDateTimeInputForRecurringAdd_failure() {
        commandBox.runCommand("add invalidRecurringTimeInput s/11:00AM  e/12:00PM  r/4/17 5/17");
        assertResultMessage(AddCommand.MESSAGE_INVALID_TIME);

        commandBox.runCommand("add startTimeAfterEndMonthInput s/11:00AM  e/12:00PM  r/6/4/17 5/16");
        assertResultMessage(AddCommand.MESSAGE_INVALID_RECURRING_DATE);

        commandBox.runCommand("add invalidStartTimeInput s/11:00AM 11/11/11 e/12:00PM r/6/4/17 4/17");
        assertResultMessage(AddCommand.MESSAGE_INVALID_TIME);

        commandBox.runCommand("add invalidEndTimeInput s/11:00AM  e/12:00PM 11/11/11 r/6/4/17 4/17");
        assertResultMessage(AddCommand.MESSAGE_INVALID_TIME);

        commandBox.runCommand("add invalidRecurringTimeInput s/11:00AM  e/12:00PM  r/6/4/17 15/4/17");
        assertResultMessage(AddCommand.MESSAGE_INVALID_RECURRING_DATE);
    }
```
###### \AddCommandTest.java
``` java
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    private void assertRecurringAddSuccess(String commandInfo, TestTodo[] addTestTodoList, TestTodo... currentList) {

        String[] addCommand = commandInfo.split(" ");
        commandBox.runCommand("add" + " " + addCommand[0] + " " + "s/" + addCommand[1] + " " + "e/" +
                addCommand[2] + " " + "t/" + addCommand[3] + " " + "r/" + addCommand[4] + " " + addCommand[5]);
        for (int i = 0; i < addTestTodoList.length; i++) {
            //confirm the new card contains the right data
            TodoCardHandle addedCard = todoListPanel.navigateToTodo(addTestTodoList[i].getName().fullName);
            assertMatching(addTestTodoList[i], addedCard);
            //confirm the list now contains all previous todos plus the new todo
            currentList = TestUtil.addTodosToList(currentList, addTestTodoList[i]);
        }
        assertTrue(todoListPanel.isListMatching(true, currentList));
    }
```
