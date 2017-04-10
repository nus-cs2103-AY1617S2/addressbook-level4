# Test Script (for Manual Testing)

## Loading the sample data

1. Ensure that you put `SampleData.xml` inside the `data\` folder (if the `data` directory does not exist inside the directory where the app is located, create it).
2. Rename `SampleData.xml` to `tasklist.xml`.
2. Start `iManager.jar` by double clicking it.
3. Type `switchlist SampleData`.

**Result:** The task list is loaded.

## Open help

1. Type `help`

**Result:** A new window appears with a user guide.

## Add new floating task

1. Type `add Download song stand by me`

**Result:** A new floating task for "Download song stand by me" is created at the bottom of the list.

## Add new task with deadline

1. Type `add proposal by tmr 9am`

**Result:** A new task "proposal" with deadline at tomorrow 9am is added to the bottom of the list.

## Add new event

1. Type `add meeting from 2 days after 1pm to 2 days after 3pm`.

**Result:** A new task "meeting" with start and end date of 2 days later from 1pm to 3pm is added to the bottom of the list.


## Add tags to task

1. Type `add "Random" t/tag1 t/tag2`.

**Result:** A new task "Random" with tags "tag1" and "tag2" is added to the bottom of the task list.

## Select a task in task list

1. Type `select 1`.

**Result:** TODO

## Select the last task in task list

1. Type `select 1000`.

**Result:** Task list will scroll to last task and select it, showing the task name and tags in result display.

## View all tasks

1. Type `view`.

**Result:** The task list will show all tasks.

## View all tasks that needs to be completed

1. Type `view t`.

**Result:** The task list will show all tasks that has a deadline and has yet to be completed.

## View all tasks that are upcoming and yet to be completed

1. Type `view p`.

**Result:** The task list will show all tasks that has a deadline, start and end date and has yet to be completed. 

## View all completed tasks 

1. Type `view d`.

**Result:** The task list will show all tasks that are completed.
 
1. Type `view f`.

**Result:** Only floating tasks are shown (TODO).

## View overdue tasks

1. Type `view o`.

**Result:** Only overdue tasks are shown. 

## Edit the task name

1. Type `update 1 new name"

**Result:** The first task is renamed to "new name".

## Update the task deadline

1. Type `edit 1 by 5pm`

**Result:** The first task's deadline time is changed to 5pm.

## Update the task period

1. Type `update 3 from 17 Dec 12 pm to 3pm`

**Result:** The third task's start and end date is changed to 17 Dec, 12pm to 3pm.

## Update the task by adding new tags

1. Type `edit 1 t/ tagged`.

**Result:** The first task gets a new tag called "tagged".

## Remove the tag in a task

1. Ensure that the first task has a tag "tagged".
2. Type `edit 1 t/`.

**Result:** The tag "tagged" is removed for the first task.

## Find tasks by name

1. Type `find go`.

**Result:** Tasks that contains the word "go" will be shown. (TODO)

## Mark a task as completed

2. Type `done 7`.

**Result:** The tab switches to the done tab and shows the task in the tab.

## Undo a previous action

1. Type `add "Wrong Task"`.
2. Type `undo`.

**Result:** The task list is restored back to the state before step 1 was executed (i.e. "Wrong Task" should not exist after step 2).

## Undo when we first start the program

1. Ensure that we just started the program and did not do anything yet.
2. Type `undo`.

**Result:** The task list is unaffected. A message "No more command to undo" is shown.

## Redo something that we previously undo

1. Type `add Something`.
2. Type `undo`.
3. Type `redo`.

**Result:** "Something" is added back to the list after it went away in step 2.

## Redo when we did not undo anything

1. Ensure that we never execute any undo.
2. Type `redo`.

**Result:** The task list is unaffected. A message "No more command to redo" is shown.

## Delete a certain task

1. Type `delete 3`

**Result:** The third task is deleted.

## Clear the entire list

1. Type `clear`

**Result:** The entire task list is empty.

## Backup file to a new location

1. Type `save C:\Dropbox\test.xml`

**Result:** A new file called test.xml should be created in the folder location. 
