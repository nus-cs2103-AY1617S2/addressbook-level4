# User Guide

By : `Team CS2103JAN2017-W15-B4`   &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

* [Quick Start](#1-quick-start)
* [Features](#2-features)
* [FAQ](#3-faq)
* [Command Summary](#4-command-summary)

## 1. Quick Start

1. Ensure you have Java version 1.8.0_60 or later installed in your Computer.
	> Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

2. Download the latest taskmanager.jar from the [releases](https://github.com/CS2103JAN2017-W15-B4/main/releases) tab.
3. Copy the file to the folder you want to use as the home folder for your task manager.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
    <br><br>
    <img src="images/Ui.png" width="600">
    <br>

5. Type the command in the command box and press  <kbd>Enter</kbd> to execute it.<br>
e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
6. Some example commands you can try:
    * **`list`** : lists all tasks
    * **`add Complete Progress Report ed/20-3-17 et/2359`** : adds a task titled Complete Progress Report with deadline on 20 March 2017 2359H to the task manager.
    * **`delete 3`** : deletes the 3rd task shown in the current list
    * **`exit`** : exits the app

10. Refer to the [Features](https://github.com/CS2103JAN2017-W15-B4/main/blob/master/docs/UserGuide.md#features) section below for details of each command.

## 2. Features

> **Command Format**
>
> * Words in UPPER_CASE are the parameters.
> * Items in SQUARE_BRACKETS are optional.
> * Items with ... after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task  to the task manager<br>
Format: `add TASK [startdate/START_DATE]  [starttime/START_TIME] [enddate/END_DATE]  [endtime/END_TIME]`

> Acronyms can be used to represent certain commands e.g. `[sd/START_DATE]` is the same as `[startdate/START_DATE]`. `[st/START_TIME]` is the same as `[starttime/START_TIME]`. `[ed/END_DATE]` is the same as `[enddate/END_DATE]`. `[et/END_TIME]` is the same as `[endtime/END_TIME]`.

Examples:

* `add Progress report ed/15-3-17 et/1600`
* `add shop groceries`
* `add Team meeting startdate/15-3-17 st/1500 ed/15-3-17 et/1600`
* `add celebration sd/1-4-17 ed/1-4-17`

### 2.3. Listing all tasks : `list`

Shows a list of all uncompleted tasks in the task manager.<br>
Format: `list`

### 2.4. Listing all completed tasks : `listcompleted`

Shows a list of all completed tasks in the task manager.<br>
Format: `listcompleted`

### 2.5. Editing a task : `update`

Edits an existing task in the task manager.<br>
Format: `update INDEX [TASK] [ed/END_DATE] [et/END_TIME]`

> * Edits the task at the specified INDEX. The index refers to the index number shown in the last task listing.
> * The index must be a positive integer 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.

Examples:

* `update 1 Summary Report et/2359`<br>
 Edits the title of task 1 and updates its ending time to 2359
* `update 2 ed/ et/`<br>
Clears the existing deadlines for task 2

### 2.8. Finding all tasks containing any keyword in their name or on a specific date : `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS] [DATE]`

> * The search is case insensitive.
> * The order of the keywords does not matter. e.g. progress report will match report progress
> * Only the task  name is searched.
> * Substrings will be matched e.g. meet will match meeting
> * Tasks matching at least one keyword will be returned (i.e. OR search). e.g. meeting will match team meeting
> * Specifying the date will narrow the search space to tasks on the day

Examples:

* `find report`<br>
Returns any task having the word report
* `find meet`<br>
Returns any task having the word or substring meet.
* `find meet 17-3-17`<br>
Returns any task having the word or substring meet on 17-3-17

### 2.9. Summary of the tasks for current day: `summary`

Displays a summary of tasks which are due on the current day.<br>
Format: `summary`

Examples:

* `summary`<br>
Returns a list of tasks on the current day

### 2.10. Mark a task as done : `done`

Mark the specified task from the task manager as done.<br>
Format: `done INDEX`

> * Marks the task at the specified INDEX as done.
> * The index refers to the index number shown in the most recent listing.
> * The index must be a positive integer 1, 2, 3, ...

Examples:

* `list`<br>
`done 2`<br>
Marks the 2nd task in the task manager as done.

* `find Report`<br>
`done 1`<br>
Marks the 1st task in the results of the find command as done.

### 2.11. Deleting a task : `delete`

Deletes the specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

> * Deletes the task at the specified INDEX.
> * The index refers to the index number shown in the most recent listing.
> * The index must be a positive integer 1, 2, 3, ...

Examples:

* `list`<br>
`delete 2`<br>
Deletes the 2nd task in the task manager.

* `find Report`<br>
`delete 1`<br>
Deletes the 1st task in the results of the find command.

### 2.12. Undo most recent command : `undo`

Undo the most recent command. Can redo with command redo<br>
Format: `undo`

### 2.13. Redo most recent undo : `redo`

Redo the most recent undo. Can undo with command undo<br>
Format: `redo`

### 2.14. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.15. Saving the data

Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous task manager folder.

## 4. Command Summary

* **Add** `add TASK [sd/START_DATE] [st/START_TIME] [ed/END_DATE] [et/END_TIME]`<br>
 e.g. `add Team meeting sd/15-3-17 st/1500 ed/15-3-17 et/1600`

* **Update** : `update INDEX [TASK] [ed/END_DATE] [et/END_TIME]`<br>
e.g. : `update 1 Summary Report et/2359`

* **Delete** : `delete INDEX`<br>
e.g. `delete 3`

* **Done** : `done INDEX`<br>
e.g. `done 1`

* **Find** : `find KEYWORD [MORE_KEYWORDS] [DATE]`<br>
e.g. `find report`

* **Undo** : `undo`<br>
e.g. `undo`

* **Redo** : `redo`<br>
e.g. `redo`

* **Check** : `summary`<br>
e.g. `summary`

* **List uncompleted tasks** : `list` <br>
e.g. `list`

* **Help** : `help`<br>
e.g. `help`
