# Task Manager - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`help`** : list all the command can use
   * **`list`** : list all tasks
   * **`add report submit Fri`** : add a task to the task manager
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task to the task manager<br>
Format: `add TASK DEADLINE`

> Tasks can have any number of tags (including 0)

Examples:

* `add submit report Fri #academic`
* `add go gym Sat #personal`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the task manager.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the task manager.<br>
Format: `edit TASK by DEADLINE` 

### 2.5. Finding all tasks containing any keyword in their task description: `find`

Format: `find KEYWORD [MORE_KEYWORDS]`

Examples:

* `find report`<br>
  Returns `submit report`  `print report` etc.

### 2.6. Deleting a task : `delete`

Deletes the specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find report`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Select a task : `select`

Selects the person identified by the index number used in the last task listing.<br>
Format: `select INDEX`

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the task manager.
* `find report` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all entries from the task manager.<br>
Format: `clear`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

Task Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 4. Command Summary

* **Add**  `add TASK` <br>
           `add TASK by DEADLINE` <br>
  e.g. `add submit report by Fri`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
  e.g. `delete 3`

* **Find** : `find KEYWORD` <br>
  e.g. `find report`
  e.g. `find Fri`

* **Edit**  `edit TASK by DEADLINE` <br>
  e.g. `edit submit report by Thu`

* **List** : `list` <br>

* **Help** : `help` <br>

* **History** : `history` <br>

