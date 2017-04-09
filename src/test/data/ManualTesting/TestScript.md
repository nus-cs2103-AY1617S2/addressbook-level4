<!-- @@author A0141011J -->
------
# Testscript
------

> Note:
- The actual date and time displayed by the app depends on the day the test is performed.

------
## Getting Started
------
> **Instructions:**
- 1. Create a folder called `data` at the same directory as `TaskIt.jar` executable.
- 2. Copy and paste `SampleData.xml` into that folder and rename as `taskmanager.xml`.
- 3. Run `TaskIt.jar`.

------
## 0. Help Command
------
### 0.1 Open help window
> **Type:** `help` <br>
> **Result:**
- Result display panel posts message: <br>
`Opened help window.`
- Help window pops up and shows user guide.

------
## 1. Add Command
------
### 1.1 Add a floating task
Add a simple floating task
> **Command:** `add prepare dinner for family` <br>
> **Result:** <br>
- Added a task named 'prepare dinner for family'
- Task list panel navigates to and displays newly added task card.

Add a floating task with tag
> **Command:** `add finish homework tag school` <br>
> **Result:** <br>
- Added a task named 'finish homework' with tag 'school'
- Task list panel navigates to and displays newly added task card.

Add a floating task with tag and priority
> **Command:** `add finish software engineering testscript priority high tag school` <br>
> **Result:** <br>
- Added a task named 'finish software engineering testscript' with tag 'school' and high priority
- Task list panel navigates to and displays newly added task card

### 1.2 Add a deadline
Add a simple deadline
> **Command:** `add finish FYP by friday` <br>
> **Result:** <br>
- Added a deadlie named 'finish FYP' due this coming friday
- Task list panel navigates to and displays newly added task card.

Add a deadline with tag and priority
> **Command:** `add submit project by tomorrow noon priority medium tag school` <br>
> **Result:** <br>
- Added a deadlie named 'submit project' due at tomorrow noon with tag 'school' and medium priority
- Task list panel navigates to and displays newly added task card.

### 1.3 Add an event
Add a simple event
> **Command:** `add cs3223 last lecture from 13th Apr at 4pm to 13th Apr at 6pm `<br>
> **Result:**<br>
- Added an event named 'cs3223 last lecture' starting from 13th April 4PM and ending at 13th April 6PM
- TaskList panel navigates to and displays newly added task card.

Add an event with tag and priority
> **Command:** `add cs3223 exam from 25th April 9am to 25th Apr 11am priority is high`<br>
> **Result:**<br>
- Added an event named 'cs3223 exam' starting from 25th April 9AM and ending at 25th April 11AM with high priority
- TaskList panel navigates to and displays newly added task card.


### 1.4 Add a task with keyword in title
Add a simple event
> **Command:** `add "change priority to high for all my tasks" priority medium by friday`<br>
> **Result:**<br>
- Added an event named 'change priority to high for all my tasks' by friday
- Contains keywords 'priority' and 'to' in title
- TaskList panel navigates to and displays newly added task card.

------
## 2. List Command
------
### 2.1 List all tasks
> **Command:** `list all`<br>
> **Result:**<br>
- Task list panel list all tasks and events in TaskIt
- Tasks are sorted based on priority


### 2.2 List all floating tasks
> **Command:** `list floating`<br>
> **Result:**<br>
- Task list panel list all floating in TaskIt
- Tasks are sorted based on priority

### 2.3 List all deadlines
> **Command:** `list deadline`<br>
> **Result:**<br>
- Task list panel list all deadlines in TaskIt
- Tasks are sorted based on priority and date

### 2.4 List all events
> **Command:** `list event`<br>
> **Result:**<br>
- Task list panel list all events in TaskIt
- Tasks are sorted based on priority and date

### 2.5 List all completed tasks
> **Command:** `list done`<br>
> **Result:**<br>
- Task list panel list all finished tasks and events in TaskIt
- Tasks are sorted based on priority and date

### 2.6 List all uncompleted tasks
> **Command:** `list undone`<br>
> **Result:**<br>
- Task list panel list all undone tasks and events in TaskIt
- Tasks are sorted based on priority and date

### 2.7 List tasks due today
> **Command:** `list today`<br>
> **Result:**<br>
- Task list panel lists all deadlines due today and events ending today
- Tasks are sorted based on priority and date

### 2.8 List all overdue tasks
> **Command:** `list overdue`<br>
> **Result:**<br>
- Task list panel lists all tasks and events past due and not completed yet
- Tasks are sorted based on priority and date

### 2.9 List by priority
> **Command:** `list high`<br>
> **Result:**<br>
- Task list panel lists all tasks and events with high priority

### 2.10 List tasks from GUI
> **Action:** Click buttons on left menu bar<br>
> **Result:**<br>
- Task list panel lists tasks or/and events according description on the menu bar

------
## 3. Edit Command
------
### 3.1 Edit title of task
> **Command:** `edit 1 title write test cases`<br>
> **Result:**<br>
- Newly edited task has new title 'write test cases'.
- Task list panel shows the newly edited task card with task title changed.

### 3.2 Edit time of task to make it an event
> **Command:** `edit 1 from tuesday to wednesday `<br>
> **Result:**<br>
- Newly edited task has been converted to an event starting coming tuesday and ending wednesday
- Task list panel shows the newly edited task card with both start and end time changed.

### 3.3 Edit time of task to make it a deadline
> **Command:** `edit 1 from null to wednesday `<br>
> **Result:**<br>
- Newly edited task has been converted to a deadline due on wednesday
- Task list panel shows the newly edited task card with due date changed.

#### 3.4 Edit the priority level of task/event
> **Command:** `edit 1 priority low`<br>
> **Result:**<br>
- Newly edited task assigned low priority.
- Task list panel shows the newly edited task card with priority level changed.

#### 3.5 Edit multiple fields
> **Command:** `edit 1 title run test cases from null to null tag school priority low`<br>
> **Result:**<br>
- Newly edited task assigned low priority.
- Task list panel shows the newly edited task card with priority level changed.

#### 3.6 Edit title with keyword
> **Command:** `edit 1 title "get hw from friend"`<br>
> **Result:**<br>
- Newly edited task given title with 'from' keyword in it.
- Task list panel shows the newly edited task card with title properly changed.

------
## 4. Mark Command
------
### 4.1 Mark as done
Mark a task as done
> **Command:** `list all`<br> `mark 1 done`<br>
> **Result:**<br>
- The first task is marked as completed
- Task list panel shows the task with 'done' status

Mark a deadline as done
> **Command:** `list deadline`<br> `mark 1 done`<br>
> **Result:**<br>
- The first deadline task is marked as completed
- Task list panel removes the completed deadline task from view.

### 4.2 Mark as undone
Mark a task as undone
> **Command:** `list all`<br> `mark 1 undone`<br>
> **Result:**<br>
- The first task, which was marked as 'done' previously, is marked as uncompleted
- Task list panel shows the marked task and removes 'done' status

Mark a done task as undone
> **Command:** `list done`<br> `mark 1 undone`<br>
> **Result:**<br>
- The first done task is marked as uncompleted
- Task list panel removes the uncompleted task from view

## 5. Delete Command
------
### 5.1 Delete task by index
> **Command:** `delete 1`<br>
> **Result:**<br>
- Deleted the first task as displayed in task list panel.
- Task list panel removes the task.

------
## 6. Clear Command
------
### 6.1 Delete all data
> **Command:** `clear`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Tasks have been cleared!`
- TaskIt removes all tasks.

------
## 7. Undo/Redo Command
------
### 7.1 Undo/Redo clear command
> **Command:** <br>
1. `undo`<br>
2. `redo`<br>
3. `undo`<br>
> **Result:**<br>
> 1.
- Result display panel posts message:<br>
`Previous action undone.`<br>
- TaskList panel displays the list of tasks before executing clear command.

> 2.
- Result display panel posts message:<br>
`Action redone.`<br>
- TaskList panel removes all tasks.

> 3.
The result is the same as `1`

### 7.2 Undo/Redo add and delete command
> **Command:** <br>
1. `add cs2103 presentation by now priority high`<br>
2. `delete 1` <br>
3. `undo`<br>
4. `undo`<br>
5. `redo`<br>
6. `undo`<br>

> **Result:**<br>
1 and 2: normal add task and delete event results<br>

3.
- Result display panel posts message:<br>
`Previous action undone..`
- Task list panel add back the previously delete task

> 4.
- Result display panel posts message:<br>
`Previous action undone.`
- Task list panel remove the newly added task with title 'cs2103'

> 5.
- Result display panel posts message:<br>
`Action redone.`<br>
- Task list panel readd the task task card `cs2103 presentation`

> 6.
- Result display panel posts message:<br>
`Action redone.`<br>
- Task list panel re-delete the first task

### 7.3 Undo/Redo edit command
> **Command:** <br>
1. `edit 1 from now to tomorrow now`<br>
2. `undo` <br>
3. `redo` <br>

------
## 8. Find Command
------
### 8.1 Find by keyword
> **Command:** `find new pillow`<br>
> **Result:**
- Task list panel lists all events and tasks whose title contains `new` or `clothes`

### 8.2 Find by group of words
> **Command:** `find "new clothes"`<br>
> **Result:**
- Task list panel lists all events and tasks whose title contains `new clothes`

### 8.3 Find by substring
> **Command:** `find cloth`<br>
> **Result:**
- TaskList panel lists all events and tasks whose title contains `cloth` as a substring<br>

------
## 9. Change Path Command
------
### 9.1 Changes the default storage location of the app
> **Command:** `path newFolder`<br>
> **Result:**
- Result display panel posts message:<br>
`File path changed to newFolder/taskit.xml`
- Status Foot Bar updates to show the new location.
- Open the new folder and the `taskit.xml` should be there.
- Note: If key in invalid path such as `path \`, result display will post `The new file path specified is invalid`.

------
## 10. Save Command
------
### 10.1 Save the current task manager data to a file
> **Command:** `save newFoler/newFile.xml`<br>
> **Result:**
- Result display panel posts message:<br>
`Saved to  newFolder/file`
- `newFile.xml` should be at the specified location in the project folder.
- Note: this command only save a new copy of the task manager, but does not modify the storage file path of the application.

------
## 11. Exit Command
------
### 14.1 Exit the app
> **Command:** `exit`<br>
> **Result:**<br>
- TaskIt closes and quits.

------
## End
------
