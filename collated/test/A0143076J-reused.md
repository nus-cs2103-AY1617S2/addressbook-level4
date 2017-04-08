# A0143076J-reused
###### \java\guitests\EditCommandTest.java
``` java
    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getDescription().fullDescription);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
```
###### \java\guitests\guihandles\TaskCardHandle.java
``` java
    public boolean equalsOrderInsensitive(List<String> other, List<String> another) {
        return another == other || new HashSet<>(another).equals(new HashSet<>(other));
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
```
###### \java\seedu\watodo\logic\LogicManagerTest.java
``` java
    //similar to EditCommandTest cases under guitests package
    @Test
    public void execute_editInvalidFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertCommandFailure("edit   ", expectedMessage);
        //missing task index
        assertCommandFailure("edit p", expectedMessage);
        assertCommandFailure("edit updated description but missing index", expectedMessage);
        //invalid task index
        assertCommandFailure("edit 0 updatedDescription", expectedMessage);
        assertCommandFailure("edit -1 updatedDescription", expectedMessage);
    }

    @Test
    public void execute_editNoFieldsSpecified_failure() {
        assertCommandFailure("edit 1", EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_editInvalidValues_failure() {
        assertCommandFailure("edit 1 by/invalid date", DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        assertCommandFailure("edit 1 from/thurs", DateTimeParser.MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
        assertCommandFailure("edit 1 #$%^^", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandSuccess("list", ListCommand.MESSAGE_SUCCESS, expectedAB, expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     *
     * @param commandWord to test assuming it targets a single task in the last
     *            shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandFailure(commandWord, expectedMessage); // index missing
        assertCommandFailure(commandWord + " +1", expectedMessage); // index should be unsigned
        assertCommandFailure(commandWord + " -1", expectedMessage); // index should be unsigned
        assertCommandFailure(commandWord + " 0", expectedMessage); // index cannot be 0
        assertCommandFailure(commandWord + " not_a_number", expectedMessage);
    }

```
