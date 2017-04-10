# Test Script (for Manual Testing)

## Loading the sample data

1. Ensure that you put `SampleData.xml` inside the `data\` folder (if the `data` directory does not exist inside the directory where the app is located, create it).
2. Rename `SampleData.xml` to `tasklist.xml`.
2. Start `[F14-B3][iManager].jar.` by double clicking it.

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

1. Type `add meeting from 1pm 2 days after to 3pm 2 days after`.

**Result:** A new task "meeting" with start and end date of 2 days later from 1pm to 3pm is added to the bottom of the list.


## Add tags to task

1. Type `add Random t/tag1 t/tag2`.

**Result:** A new task with name "Random" with tags "tag1" and "tag2" is added to the bottom of the task list.

## Select a task in task list

1. Type `select 1`.

**Result:** The first task with task name "CS2103t v0.4" is selected.

## Select the last task in task list

1. Type `select 99`.

**Result:** Task list will jump to last task and select it, showing the task name and tags in result display.

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

1. Type `edit 1 new name"

**Result:** The first task is renamed to "new name".

## Edit the task deadline

1. Type `edit 6 by 6pm`

**Result:** The task with name "class gathering" deadline time is changed to 5pm.

## Edit the task start and end date

1. Type `edit 21 from 17 Dec 12 pm to 17 Dec 3pm`

**Result:** The task with name "a mega long very very..." start and end date is changed to 17 Dec, 12pm to 3pm.

## Edit the task by adding new tags

1. Type `edit 2 t/homework`.

**Result:** The task with name "ma1101 Assignment 2" gets a new tag called "homework".

## Remove the tag in a task

1. Ensure that the second task has a tag "homework".
2. Type `edit 2 t/`.

**Result:** The tag "homework" is removed for the second task.

## Find tasks by name

1. Type `find assignment`.

**Result:** Tasks that contains the word "assignment" will be shown. (3 tasks will be shown)

## Mark a task as completed

2. Type `done 7`.

**Result:** The tab switches to the done tab and shows the task with name "CS2103T v0.5" in the tab.

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
