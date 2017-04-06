# A0163962X-reused
###### /java/project/taskcrusher/logic/LogicManagerTest.java
``` java
    private void assertEventIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);
        List<Event> eventList = helper.generateEventList(2);

        // set UserInbox state to 2 tasks
        model.resetData(new UserInbox());
        for (Task t : taskList) {
            model.addTask(t);
        }

        for (Event e : eventList) {
            model.addEvent(e);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

```
