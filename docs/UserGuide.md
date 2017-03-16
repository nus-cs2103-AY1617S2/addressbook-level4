# User Guide

By : `Team CS2103JAN2017-W15-B4`   &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

Geekeep is a simple Command-Line-Interface (CLI) based Task Manager app created by us,  a 4 man team, for NUS's CS2103 Software Engineering module. You can find out more about our team [here](https://github.com/CS2103JAN2017-W15-B4/main/blob/master/docs/AboutUs.md).

---

## Table of Contents

1. [Quick Start](#1-quick-start)
1. [Features](#2-features)
1. [FAQ](#3-faq)
1. [Command Summary](#4-command-summary)
1. [Contact Us](#5-contact-us)

## 1. Quick Start

1. Ensure you have Java version 1.8.0_60 or later installed in your Computer.
	> Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

2. Download the latest taskmanager.jar from the [releases](https://github.com/CS2103JAN2017-W15-B4/main/releases) tab.
3. Move the file to the folder you want to use as the home folder for your task manager.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
    <br><br>
    <img src="images/Ui.png" width="600">
    <br>

5. Type the command in the command box and press  <kbd>Enter</kbd> to execute it.<br>
e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
6. Some example commands you can try:
    * **`list`** : lists all tasks
    * **`add Complete Progress Report e/20-3-17`** : adds a task titled Complete Progress Report with deadline on 20 March 2017 to the task manager.
    * **`delete 3`** : deletes the task with TaskID 3 shown in the current list
    * **`exit`** : exits the app

10. Refer to the [Features](https://github.com/CS2103JAN2017-W15-B4/main/blob/master/docs/UserGuide.md#features) section below for details of each command or just checkout the [Cheatsheet](#4-command-summary) section.

**[⬆ back to top](#table-of-contents)**

## 2. Features

1.
1.

> **Command Format**
>
> * Words in UPPER_CASE are the parameters.
> * Items in SQUARE_BRACKETS are optional.
> * Items with ... after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Opens a window that displays this User Guide.

Format: `help`

> If your command did not follow the format specified, a short help summary of the command will be shown just below your CLI.

### 2.2. Adding a task: `add`

Adds a task  to the task manager.

Format: `add TASK [start/START_DATETIME] [end/END_DATETIME]  [loc/LOCATION] [tag/TAGS...]`

> Acronyms can be used to represent all parameter prefix<br>
 e.g. `[s/START_DATETIME]` is the same as `[start/START_DATETIME]`. `[e/END_DATETIME]` is the same as `[end/END_DATETIME]`.

Examples:

* `add Progress report e/15-3-17 1600`
* `add Shop groceries`
* `add Team meeting s/15-3-17 1500 e/15-3-17 1600`
* `add Birthday Celebration s/1-4-17 e/1-4-17`

### 2.3. Updating a task : `update`

Updates an existing task in the task manager with new value(s).<br>
Format: `update TASKID [start/START_DATETIME] [end/END_DATETIME]  [loc/LOCATION] [tag/TAGS...]`

> * Updates the task at the specified *TaskID*. 
> * *TaskID* refers to the number shown beside the title of the task.
> * *TaskID* must be a positive integer 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.

Examples:

* `update 1 Summary Report e/1-4-17 2359`<br>
 Edits the title of task with *TaskID 1* and updates its ending time to 2359H
* `update 2 e/`<br>
Clears the existing deadlines for task with *TaskID 2*

### 2.4. Marks a task as done or undone : `done` & `undone`

Marks the task with the specified *TaskID* as done.

Format: `done TASKID`

> * Marks the task with the specified *TaskID* as done.
> * *TaskID* refers to the number shown beside the title of the task.
> * *TaskID* must be a positive integer 1, 2, 3, ...

Examples:

* `done 2`<br>
Marks the task with *TaskID 2* in the task manager as done.

* `find Report`<br>
`done 1`<br>
Marks the task with *TaskID 1* as done after you find it with the [find]() command.


### 2.5. Deleting a task : `delete`

Deletes the specified task from the task manager.

Format: `delete TASKID`

> * Deletes the task with the specified *TaskID*.
> * *TaskID* refers to the number shown beside the title of the task.
> * *TaskID* must be a positive integer 1, 2, 3, ...

Examples:

* `delete 2`<br>
Deletes the task with *TaskID 2* in the task manager.

### 2.3. Listing all tasks : `list`

Shows all tasks, completed or otherwise, in the main window.

Format: `list`

### 2.4. Listing all completed tasks : `listdone`

Shows only completed tasks in the main window.

Format: `listdone`

### 2.5 Listing all uncompleted tasks : `listundone`

Shows only uncompleted tasks in the main window.

Format: `listundone`

### 2.7. Finding all tasks containing any keyword in their title, tag, or on a specific date : `find`

Find and displays only tasks whose title or tag contain any of the given keywords, and/or on the specified date.

Format: `find KEYWORD [MORE_KEYWORDS] [DATE]`

> * The search is case insensitive.
> * The order of the keywords does not matter. e.g. progress report will match report progress
> * Substrings will be matched e.g. meet will match meeting
> * Tasks matching at least one keyword will be returned (i.e. OR search). e.g. meeting will match team meeting
> * Specifying the date will narrow the search space to ongoing tasks on the date.

Examples:

* `find report`<br>
Returns any task having the word report
* `find meet`<br>
Returns any task having the word or substring meet.
* `find meet 17-3-17`<br>
Returns any task having the word or substring meet still ongoing on 17-3-17

### 2.11. Undo most recent command : `undo`

Undo the most recent command. Can redo with command redo

Format: `undo`

### 2.12. Redo most recent undo : `redo`

Redo the most recent undo. Can undo with command undo.

Format: `redo`

### 2.14. Saving the data

Task manager data are saved in the hard disk automatically (in XML format) after any command that changes the data.

> There is no need to save manually.

### 2.13. Exiting the program : `exit`

Exits the program.

> You can exit with Alt-F4 (Windows) or Command-Q (Mac) anytime since all changes are automatically saved.

Format: `exit`

**[⬆ back to top](#table-of-contents)**

## 3. FAQ

**Q**: How do I read the task card presented to me?
<br>
**A**: 

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous task manager folder.

**[⬆ back to top](#table-of-contents)**

## 4. Command Summary

| Command | Format | example |
|--- | :--- | :--- | :--- |
| Help | `help` | `help` |
| Add | `add TASK [s/START_DATETIME] [e/END_DATETIME]` | `add Team meeting s/15-3-17 1500 e/15-3-17 1600` |
| Update | `update TASKID [TASK] [e/END_DATETIME]` | `update 1 Summary Report e/15-3-17 2359` |
| Done | `done TASKID` | `done 1` |
| Delete | `delete TASKID` | `delete 3` |
| List all tasks | `list` | `list` |
| List only completed tasks | `listdone` | `listdone` |
| List only uncompleted tasks | `listundone` | `listundone` |
| Find | `find KEYWORD [MORE_KEYWORDS] [DATE]` | `find report` |
| Undo | `undo` | `undo` |
| Redo | `redo` | `redo` |
| Exit | `exit` | `exit` |


**[⬆ back to top](#table-of-contents)**

## 5. Contact Us

* **Bug reports, Suggestions** : Post in our [issue tracker](https://github.com/CS2103AUG2016-W15-B4/main/issues)
  if you noticed bugs or have suggestions on how to improve.

* **Contributing** : We welcome pull requests. Follow the process described [here](https://github.com/oss-generic/process)

* **Email** : ~~Do not contact us~~ You can reach us at `e0003323@u.nus.edu`

**[⬆ back to top](#table-of-contents)**
