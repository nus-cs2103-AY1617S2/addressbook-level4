# Manual Testing TestScript
Welcome to OneTwoDo.
1. Ensure that you have installed **Java version 1.8.0_60** or later.
2. Download **onetwodo.jar** and put it in a new folder. Download [here]().
3. Double click to open.
3. Download our **SampleData.xml** for testing. Download [here](https://github.com/CS2103JAN2017-F14-B1/main/releases/download/sampledata/SampleData.xml).
4. Place this sample data inside the folder "data".
5. Type `import data/SampleData.xml` in the command box of our app.
6. You are now ready for testing.
<br>

---
## Test `help` command
Command | Expected Result |
:-------: | :--------------
`help` | Opens the cheatsheet window.
Press F1 | Opens the cheatsheet window.
`helpug` | Opens the userguide window.

<br>

---
## Test `add` command
Command | Expected Result |
:-------: | :--------------
`add Go clubbing d/wear jeans p/low t/habits` | A **To Do** task will appear at the most right panel with description "wear jeans", a tag "habit" and a green label displaying "low" priority. 
`add Go holiday e/next sunday 10pm r/weekly p/high t/holiday`| A **Deadline** task will appear at the most left panel without description, but with tag "holiday", a deadline of next sunday 10pm, a "weekly" label beside the task name and a red label displaying "high" priority. 
`add Go party s/now e/tmr d/bring beer t/favourite t/habits` | A **Event** task will appear at the centre panel with description "bring beer", two tags "favourite" and "habits", a duration of the current time + 1 hour to tomorrow 2359hr, and without any priority label.

<br>

---
## Test `edit` command
Command | Expected Result |
:-------: | :--------------
`edit e8 Go party and enjoy d/bring milk p/m` | Edits the task at index E8 and change its name to "Go party and enjoy", description to "bring milk" and add a orange "medium" priority label. Other info remains unchanged.  | Opens the cheatsheet window.
`edit d24 s/next sunday 6am e/ next sunday 10pm` | Edits the task at index d24 and change its start date and end date to next sunday 6am to next sunday 10pm, and shift the entire task to the **Event** panel at the centre. Other info remains unchanged.
`edit t22 d/ p/ t/` | Edits the task at index t22 and remove its tag, description and priority. Other info remains unchanged.
<br>

---
## Test `list` command
Command | Expected Result |
:------- | :--------------
`list done` | Shows you all the tasks that have been completed.
`ls all` | Shows you all the tasks that have both been completed and incompleted. "ls" is the shortcut command for "list".
`list today` | List all incompleted tasks for today. Every **To Do** tasks are shown as well because they have no date.
`list s/today e/next week p/high` | List all tasks from today to next week that have high priority.
`list o/priority t/cs2103` | List all tasks that have tag "cs2103" and sort them by priority level.

<br>

---
## Test `find` command
Command | Expected Result |
:-------: | :--------------
`find tutorial` | Display all tasks that have the word "tutorial" from its name, description and tag, regardless of whether the task has been completed or not.
`find cs2103 study buy` | Display all tasks that have the words "cs2103", "study" or "buy" from its name, description and tag, regardless of whether the task has been completed or not.

<br>

---
## Test `select` command
Command | Expected Result |
:-------: | :--------------
`select d1` | Select the task at index d1 and popup a window that shows its name and description (if it has).

<br>

---
## Test `done` command

Command | Expected Result |
:-------: | :--------------
`list`| Firstly, list all incompleted task.
`done d1` | Mark the task at index d1 as completed.
`list done` | List out all the completed tasks and check if the task you just mark as completed is found there in the **Deadline** panel on the left. If you mark a recurring task as done, its *dateTime* will increase by the amount of recurring period, and clone an old one in the *done* section.

<br>

---
## Test `undone` command
Command | Expected Result |
:-------: | :--------------
`list done`| Firstly, list all completed task.
`undone t1` | Mark the task at index t1 as incompleted.
`list` | List out all the incompleted tasks and check if the task you just mark as incompleted is found there in the **To Do** panel on the right. If you try to mark a completed recurring task as undone, it will tells you "recurring tasks cannot be undone". Please use undo instead.

<br>

---
## Test `save` command
Command | Expected Result |
:------- | :--------------
`save data/newStorage.xml` | A new file called "newStorage.xml" is created at the path data/newStorage.xml (relative path to the onetwodo.jar file). The footer at the bottom right will also update its path to *data/newStorage.xml*
`save data/newStorage.xml` | You will see the command feedback of "Warning: File name already exist! If you wish to overwrite, add the word "overwrite". Example: save [overwrite] data/newStorage.xml".
`save overwrite data/newStorage.xml` | You overwrite the existing file with your current storage.

<br>

---
## Test `export` command
Command | Expected Result |
:------- | :--------------
`export data/exportStorage.xml` | A new file called "exportStorage.xml" is created at the path data/exportStorage.xml (relative path to the onetwodo.jar file). The footer at the bottom right will remain unchanged.
`export data/exportStorage.xml` | You will see the command feedback of "Warning: File name already exist! If you wish to overwrite, add the word "overwrite". Example: save [overwrite] data/exportStorage.xml".
`export overwrite data/exportStorage.xml` | You overwrite that existing file with your current storage.

<br>

---
## Test `import` command
Command | Expected Result |
:------- | :--------------
`import data/exportStorage.xml` | Change your current storage to the filepath *data/exportStorage.xml*. The footer at the bottom right will also update its path to *data/exportStorage.xml*

<br>

---
## Test `delete` command
Command | Expected Result |
:-------: | :--------------
`list` | Firstly, display all incompleted tasks.
`delete d1` | Remove the task at index d1 permanently. You can delete any type of tasks.

<br>

---
## Test `clear` command
Command | Expected Result |
:-------: | :--------------
`list done` | Firstly, display all completed tasks.
`clear done` | Permanently removes all completed tasks.
`list` | Display all incompleted tasks to ensure they are still there.
`clear` | Permanently removes all data. You now have a brand new task manager tool.

<br>

---
## Test `undo` and `redo` command
Command | Expected Result |
:-------: | :--------------
`undo` | Undo previous command. Now all your tasks are back from before you typed `clear`.
`redo` | Redo your undo. Now all your tasks are gone again.
Press Ctrl + Z | This is a command shortcut for `undo`. Now all your tasks are back from before you typed `clear`.
Press Ctrl + R | This is a command shortcut for `redo`. Now all your tasks are gone again.

<br>

---
## Test `exit` command
Command | Expected Result |
:-------: | :--------------
`exit` | Close this applicaiton. You can try Ctrl + E to do the same thing.
