# A0142418L-reused
###### \java\seedu\taskmanager\testutil\TestUtil.java
``` java
    /**
     * Compares the starting date and time of 2 event tasks.
     *
     * @return true if 1st event task is earlier than the 2nd event task based
     *         on the startDate and startTime
     * @return false, if otherwise.
     */
    private static boolean isAddEventEarlierAddListIndex(TestTask toAdd, TestTask task) {
        if (toAdd.getStartDate().value.substring(toAdd.getStartDate().value.length() - 2)
                .compareTo(task.getStartDate().value.substring(task.getStartDate().value.length() - 2)) < 0) {
            return true;
        } else {
            if (toAdd.getStartDate().value.substring(toAdd.getStartDate().value.length() - 2)
                    .compareTo(task.getStartDate().value.substring(task.getStartDate().value.length() - 2)) == 0) {
                if (toAdd.getStartDate().value
                        .substring(toAdd.getStartDate().value.length() - 5, toAdd.getStartDate().value.length() - 3)
                        .compareTo(task.getStartDate().value.substring(task.getStartDate().value.length() - 5,
                                task.getStartDate().value.length() - 3)) < 0) {
                    return true;
                } else {
                    if (toAdd.getStartDate().value
                            .substring(toAdd.getStartDate().value.length() - 5, toAdd.getStartDate().value.length() - 3)
                            .compareTo(task.getStartDate().value.substring(task.getStartDate().value.length() - 5,
                                    task.getStartDate().value.length() - 3)) == 0) {
                        if (toAdd.getStartDate().value.substring(0, toAdd.getStartDate().value.length() - 6).compareTo(
                                task.getStartDate().value.substring(0, task.getStartDate().value.length() - 6)) < 0) {
                            return true;
                        } else {
                            if (toAdd.getStartDate().value.substring(0, toAdd.getStartDate().value.length() - 6)
                                    .compareTo(task.getStartDate().value.substring(0,
                                            task.getStartDate().value.length() - 6)) == 0) {
                                return (toAdd.getStartTime().value.compareTo(task.getStartTime().value) < 0);
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Compares the due date of 2 deadline tasks.
     *
     * @return true if 1st deadline task is earlier than the 2nd deadline task
     *         based on the endDate and endTime
     * @return false, if otherwise.
     */
    private static boolean isAddDeadlineEarlierAddListIndex(TestTask toAdd, TestTask task) {
        if (toAdd.getEndDate().value.substring(toAdd.getEndDate().value.length() - 2)
                .compareTo(task.getEndDate().value.substring(task.getEndDate().value.length() - 2)) < 0) {
            return true;
        } else {
            if (toAdd.getEndDate().value.substring(toAdd.getEndDate().value.length() - 2)
                    .compareTo(task.getEndDate().value.substring(task.getEndDate().value.length() - 2)) == 0) {
                if (toAdd.getEndDate().value
                        .substring(toAdd.getEndDate().value.length() - 5, toAdd.getEndDate().value.length() - 3)
                        .compareTo(task.getEndDate().value.substring(task.getEndDate().value.length() - 5,
                                task.getEndDate().value.length() - 3)) < 0) {
                    return true;
                } else {
                    if (toAdd.getEndDate().value
                            .substring(toAdd.getEndDate().value.length() - 5, toAdd.getEndDate().value.length() - 3)
                            .compareTo(task.getEndDate().value.substring(task.getEndDate().value.length() - 5,
                                    task.getEndDate().value.length() - 3)) == 0) {
                        if (toAdd.getEndDate().value.substring(0, toAdd.getEndDate().value.length() - 6).compareTo(
                                task.getEndDate().value.substring(0, task.getEndDate().value.length() - 6)) < 0) {
                            return true;
                        } else {
                            if (toAdd.getEndDate().value.substring(0, toAdd.getEndDate().value.length() - 6).compareTo(
                                    task.getEndDate().value.substring(0, task.getEndDate().value.length() - 6)) == 0) {
                                return (toAdd.getEndTime().value.compareTo(task.getEndTime().value) < 0);
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Finds the sorted position to add new task to the existing list of task.
     * List of tasks is sorted firstly based on type of task and then by
     * chronological order of the task
     *
     * Event tasks sorted by startDate startTime. Deadline tasks sorted by
     * endDate endTime. Floating tasks are just added to the bottom of the list
     * as there is no time element within a floating task.
     *
     * @return The sorted position index to add the new task in the sorted list
     *         of tasks.
     */
    private static int findSortedPositionToAdd(TestTask toAdd, List<TestTask> taskList) {
        int addIndex = 0;
        if (!taskList.isEmpty()) {
            if (toAdd.isEventTask()) {
                while (taskList.get(addIndex).isEventTask()) {
                    if (isAddEventEarlierAddListIndex(toAdd, taskList.get(addIndex))) {
                        break;
                    }
                    addIndex++;
                    if (addIndex == taskList.size()) {
                        break;
                    }
                }
            }

            if (toAdd.isDeadlineTask()) {
                while (taskList.get(addIndex).isEventTask()) {
                    addIndex++;
                    if (addIndex == taskList.size()) {
                        break;
                    }
                }
                while ((addIndex != taskList.size()) && taskList.get(addIndex).isDeadlineTask()) {
                    if (isAddDeadlineEarlierAddListIndex(toAdd, taskList.get(addIndex))) {
                        break;
                    }
                    addIndex++;
                    if (addIndex == taskList.size()) {
                        break;
                    }
                }
            }

            if (toAdd.isFloatingTask()) {
                addIndex = taskList.size();
            }
        }
        return addIndex;
    }

}
```
