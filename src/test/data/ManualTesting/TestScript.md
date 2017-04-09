# Manual Testing TestScript
Welcome to OneTwoDo.
1. Ensure that you have installed **Java version 1.8.0_60** or later.
2. Download **onetwodo.jar** and put it in a new folder. Download [here](https://github.com/CS2103JAN2017-F14-B1/main/releases/download/V0.5/onetwodo.jar).
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
`help ug` | Opens the userguide window.

<br>

---
## Test `add` command
Command | Expected Result |
:-------: | :--------------
`add Go clubbing d/wear jeans p/low t/habits` | A **To Do** task will appear at the most right panel with description "wear jeans", a tag "habit" and a green label displaying "low" priority. 
`add Go holiday e/next sunday 10pm r/weekly p/high t/holiday`| A **Deadline** task will appear at the most left panel without description, but with tag "holiday", a deadline of next sunday 10pm, a "weekly" label beside the task name and a red label displaying "high" priority. 
`a Go party s/now e/tmr d/bring beer t/favourite t/habits` | The "a" is our add shortcut. A **Event** task will appear at the centre panel with description "bring beer", two tags "favourite" and "habits", a duration of the current time + 1 hour to tomorrow 2359hr, and without any priority label.
`a Go swimming s/tmr e/today d/bring watergun t/favourite t/habits` | Error message will appear due to start date later than end date. The command box will turn red.The message also shows an example of how to add task. Of course, the task is not added


<br>

---
## Test `edit` command
Command | Expected Result |
:-------: | :--------------
`edit e8 Go party and enjoy d/bring milk p/m` | Edits the task at index E8 and change its name to "Go party and enjoy", description to "bring milk" and add a orange "medium" priority label. Other info remains unchanged.  | Opens the cheatsheet window.
`edit d24 s/next sunday 6am e/ next sunday 10pm` | Edits the task at index d24 and change its start date and end date to next sunday 6am to next sunday 10pm, and shift the entire task to the **Event** panel at the centre. Other info remains unchanged.
`e t22 d/ p/ t/` | The "e" is our edit shortcut. Edits the task at index t22 and remove its tag, description and priority. Other info remains unchanged.
`edit t21 e/yesterday 10pm` | The task initially at `t21` index will now swap to the **Deadline** panel at the left. It also has a date added in red, displaying  yesterday's date at 10pm.
<br>

---
## Test `list` command
Command | Expected Result |
:------- | :--------------
`list done` | Shows you all the tasks that have been completed. Completed tasks are have strikethrough and greyed out design.
`list all` | Shows you all the tasks that have both been completed and incompleted.
`list today` | List all incompleted tasks for today. Every **To Do** tasks are shown as well because they have no date.
`list s/today e/next week p/high` | List all tasks from today to next week that have high priority.
`ls o/priority t/cs2103` | The "ls" is our list shortcut. List all tasks that have tag "cs2103" and sort them by priority level.
`list o/alphanumeric o/datetime o/priority` | The tasks have been firstly ordered by alphanumeric, then by date time. Lastly, they are ordered by priority.

<br>

---
## Test `find` command
Command | Expected Result |
:-------: | :--------------
`find tutorial` | Display all tasks that have the word "tutorial" from its name, description and tag, regardless of whether the task has been completed or not. The message result shows the number of tasks found.
`f buy` | The "f" is our find shortcut. Display all tasks that have the words "buy" from its *name, description and tag*, regardless of whether the task has been completed or not.
`find cs2103 study change go` | Display all tasks that have the words "cs2103", "study", "change" and "go" from its *name, description and tag*, regardless of whether the task has been completed or not.

<br>

---
## Test `select` command
Command | Expected Result |
:-------: | :--------------
`select d1` | Select the task at index d1 and popup a window that shows its name and description (if it has).
`select d1000` | No task is selected. The command box turns red and error message displays "The task index provided is invalid" appears.
`select lalala` | No task is selected. The command box turns red and error message that display the `select` command format appears.

<br>

---
## Test `done` command

Command | Expected Result |
:-------: | :--------------
`list`| Firstly, list all incompleted task.
`done d1` | Mark the task at index d1 as completed. Result message appear, showing you the task name and relevant task information (if it has any).
`list done` | List out all the completed tasks and check if the task you just mark as completed is found there in the **Deadline** panel on the left. 
`find holiday`| Next, we will demonstrate the different when you done a recurring task.
`done e1`| Mark the task at current index e1 as done. Result message appears, showing you the task details that has been done. If you mark a recurring task as done, its *dateTime* will increase by the amount of recurring period, and clone the original one (before dateTime is updated) as a completed task.

<br>

---
## Test `undone` command
Command | Expected Result |
:-------: | :--------------
`list done`| Firstly, list all completed task.
`undone t1` | Mark the task at index t1 as incompleted.
`list` | List out all the incompleted tasks and check if the task you just mark as incompleted is found there in the **To Do** panel on the right. When undone a recurring task, if it is the latest 
`list t/cs2105` | Next, we will demonstrate the different when you undone a recurring task. We will use this *cs2105* task as an example.
`done e1` | Success message result is shown. Tasks remains with date updated by a week.
`done e1` | Success message result is shown. Tasks remains with date updated by a week.
`done e1` | Success message result is shown. Tasks remains with date updated by a week.
`list done` | List all the completed tasks. We are ready to test undone.
`undone e6` | When you undone a latest completed recurring task, it will replace the currently incompleted one, and removed from the *done* list.
`list t/cs2105` | Checks that the task "Chiong cs2105 assignment" datetime is now 15 April 2017 05:00pm - 15 April 2017 11:59pm (which is same as the *datetime* of the task you just undone).
`edit e1 Chiong cs2105 tutorial` | Now let's edit this recurring task and see what will happen if you undone the tasks you previously marked as done.
`list done` | List all done tasks.
`undone e4` | When you undone a non-latest completed recurring task OR if original recurring task has been edited, the task you done will become a non-recurring task and added to the `undone` list.
`list t/cs2105` | There are two tasks. The original recurring one ("Chiong cs2105 tutorial") and a non-recurring one ("chiong cs2105 assignment") that you just *undone*. This is so as to prevent information.
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
`clear done` | Permanently removes all completed tasks. You can also `clear undone`, but we will not test it here to retain the tasks.
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

## Test miscellaneous
Command | Expected Result |
:-------: | :--------------
`add someTask s/now e/tmr` | A task called "someTask" has been added. When adding a task without specifying start time, the default start time will be current time + 1hour. The default end time will be 2359hr.
Press UP and DOWN arrow | *Previous* and *Next* commands will appear in your command box respectively.

---
## Test `exit` command
Command | Expected Result |
:-------: | :--------------
`exit` | Close this applicaiton. You can try Ctrl + E to do the same thing.

Thanks for trying our app! We hope you had some fun.
