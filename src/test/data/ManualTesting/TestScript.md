<!---@@author A0141011J->
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
- 2. Copy and paste `SampleData.xml` into that folder.
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
> **Command:** `add submit project by tomorrow noon priority high tag school` <br>
> **Result:** <br>
- Added a deadlie named 'submit project' due at tomorrow noon with tag 'school' and high priority
- Task list panel navigates to and displays newly added task card.

### 1.3 Add an event
Add a simple event
> **Command:** `add cs3223 last lecture from 13th Apr at 4pm to 13th Apr at 6pm `<br>
> **Result:**<br>
- Added an event named 'cs3223 last lecture' starting from 13th April 4PM and ending at 13th April 6PM
- TaskList panel navigates to and displays newly added task card.

Add an event with tag and priority
> **Command:** `add cs3223 exam from 25th April 9am to 25th Apr 11am prioroity high`<br>
> **Result:**<br>
- Added an event named 'cs3223 exam' starting from 25th April 9AM and ending at 25th April 11AM with high priority
- TaskList panel navigates to and displays newly added task card.

------
## 2. List Command
------
### 2.1 List all tasks
> **Command:** `list all`<br>
> **Result:**<br>
- Task list panel list all tasks and events in TaskIt
- Tasks are sorted based on priority

### 2.2 List all deadlines
> **Command:** `list deadline`<br>
> **Result:**<br>
- Task list panel list all deadlines in TaskIt
- Tasks are sorted based on priority

### 2.3 List all events
> **Command:** `list event`<br>
> **Result:**<br>
- Task list panel list all events in TaskIt
- Tasks are sorted based on priority

### 2.4 List all completed tasks
> **Command:** `list done`<br>
> **Result:**<br>
- Task list panel list all finished tasks and events in TaskIt
- Tasks are sorted based on priority

### 2.5 List all uncompleted tasks
> **Command:** `list undone`<br>
> **Result:**<br>
- Task list panel list all undone tasks and events in TaskIt
- Tasks are sorted based on priority

### 2.6 List tasks due today
> **Command:** `list today`<br>
> **Result:**<br>
- Task list panel list all deadlines due today and events ending today
- Tasks are sorted based on priority

### 2.7 List all overdue tasks
> **Command:** `list overdue`<br>
> **Result:**<br>
- Task list panel list all tasks and events past due and not completed yet
- Tasks are sorted based on priority

### 2.8 List by priority
> **Command:** `list high`<br>
> **Result:**<br>
- Task list panel list all tasks and events with high priority

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
`Undid the most recent action.`
- TaskList panel displays 3 task cards, including the task card `cs2103 lecture 11.11.2016 to 11.11.2016`

> 5.
- Result display panel posts message:<br>
`Undid the most recent action.`
- TaskList panel displays 2 task cards, without the task card `cs2103 testing`

> 6.
- Result display panel posts message:<br>
`Redid the most recent action that is undone.`<br>
`New task added: cs2103 testing Deadline: 07.11.2016-20 Priority Level: 3`
- TaskList panel displays 3 task cards, including the task card `cs2103 testing`

> 7.
- Result display panel posts message:<br>
`Redid the most recent action that is undone.`<br>
`Deleted Event: cs2103 lecture Event Date: 11.11.2016 to 11.11.2016 recurring weekly Priority Level: 1`
- TaskList panel displays 2 task cards, without the task card `cs2103 lecture 11.11.2016 to 11.11.2016`



------
## 9. Find Command
------
### 9.1 Find by name
> **Command:** `find project meet`<br>
> **Result:**
- Result display panel posts message:<br>
`10 events and tasks listed!`
- TaskList panel lists all events and tasks whose name contains project, meet or meeting (whose stem word is meet)<br>

### 9.2 Find by name and `AND` parameter
> **Command:** `find project AND meet`<br>
> **Result:**
- Result display panel posts message:<br>
`2 events and tasks listed!`
- TaskList panel lists all events and tasks whose name contains project and meet or meeting (whose stem word is meet)<br>

### 9.3 Find by name and `exact!` parameter
> **Command:** `find meet exact!`<br>
> **Result:**
- Result display panel posts message:<br>
`0 events and tasks listed!`
- TaskList panel lists all events and tasks whose name contains the exact form of `meet`<br>

------
## 10. Filter Command
------
### 10.1 Filter in filter panel
> **Command:** <br>
1. `s`<br>
2. `22.04.2016`<br>
> **Result:**<br>
> 1.
- Result display panel post message:<br>
`Enter start date:`
- The start date text field in filter panel is focused

> 2.
- Result display panel posts message:<br>
`Filter the todoList`
- TaskList panel shows one event `earth day cca event` whose start day is on 22.04.2016
- Enter `d`, `s`, `e`, `r`, `t` and `p` in command box to jump to deadline, start date, end date, recurring, tag and priority text fields/choice box respectively. Press enter in those text fields/choice box to jump back to command box.

### 10.2 Filter by deadline and recurring
> **Command:** `filter d/Nov 11 2016 r/weekly`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`2 events and tasks listed!`
- TaskList panel lists all events and tasks whose deadline is on 11.11.2016 and recurring frequency is weekly
- The deadline text field in filter panel shows `11.11.2016`

### 10.3 Filter by start date and end date
> **Command:** `filter s/08.3.2016 e/08.3.2016`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`1 events and tasks listed!`
- TaskList panel lists all events whose start date and end date are on 08.03.2016
- The start date and end date text fields in filter panel shows `08.03.2016`

------
## 11. Change Directory Command
------
### 11.1 Changes the default storage location of the app
> **Command:** `change data/newFile.xml`<br>
> **Result:**
- Result display panel posts message:<br>
`Storage location changed: data/newFile.xml`
- Status Foot Bar updates to show the new location.
- Open the data folder and the `SampleData.xml` and `newFile.xml` should be there.
- Note: If key in invalid path, result display will post `The file path provided is invalid. It must end with the file type extension, .xml`.

### 11.2 Changes the default storage location of the app and deletes the previous storage file
> **Command:** `change data/toDoList.xml clear`<br>
> **Result:**
- Result display panel posts message:<br>
`Storage location changed: data/toDoList.xml`
- Status Foot Bar updates to show the new location.
- Open the data folder and `toDoList.xml` should be there. `newFile.xml` should not be there.

------
## 12. Undo/Redo change Directory Command
------
### 12.1 Undo changes the default storage location of the app
> **Command:** `undochange`<br>
> **Result:**
- Result display panel posts message:<br>
`Storage location changed back.`
- Status Foot Bar updates to show `data/newFile.xml`
- Open the data folder and the `toDoList.xml` and `newFile.xml` should be there.

### 12.2 Undo changes the default storage location of the app and deletes the new storage file
> **Command:** `undochange clear`<br>
> **Result:**
- Result display panel posts message:<br>
`Storage location changed back.`
- Status Foot Bar updates to show the `data/SampleData.xml`
- Open the data folder and `SampleData.xml` should be there and `newFile.xml` should not be there.

### 12.3 Redo change the default storage location of the app
> **Command:** 1. `redochange` 2. `redochange` (enter twice)<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Storage location changed.`
- Status Foot Bar updates to show the `toDoList.xml`
- Open the data folder and `toDoList.xml` should be there. `newFile.xml` should not be there.(It should be in the same status as 11.2)
- Reboot the app, it will load the file under the path `data/toDoList.xml`

### 12.4 Changes the default storage location back to SampleData.xml
> **Command:** `change data/SampleData.xml clear`<br>
> **Result:**<br>
- Result display panell posts message:<br>
`Storage location changed: data/SampleData.xml`

------
## 13. Exit Command
------
### 14.1 Exit the app
> **Command:** `exit`<br>
> **Result:**<br>
- toDoList closes and quits.

------
## End
------
