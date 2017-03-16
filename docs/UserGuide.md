# Werkbook - User Guide

By : `Team W15B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Welcome](#welcome)
2. [Getting Started](#getting-started)
3. [User Interface](#user-interface)
4. [Features](#features)
    1. [Task Management](#task-management)
        1. [Add task](#add-task)
        2. [List task](#list-task)
        3. [Edit task](#edit-task)
        4. [Find task](#find-task)
        5. [Mark task](#mark-task)
        6. [Delete task](#delete-task)
        7. [Clear all tasks](#clear-all-tasks)
    2. [Program Usage](#program-usage)
        1. [Undo/Redo](#undo-redo)
        2. [Save data](#save-data)
        3. [Change save location](#change-save-location)
        4. [Exit program](#exit-program)
5. [FAQ](#faq)
6. [Commands Cheatsheet](#commands-cheatsheet)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `werkbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Werkbook.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : Lists all tasks
   * **`add`**` : Walk the dog d/Take Zelda on a walk around the park s/01/01/2017 1000 e/01/01/2017 1200 t/Important` <br>
     Adds a task named `Walk the dog` into task list.
   * **`delete`**` 3` : Deletes the 3rd task shown in the current list
   * **`exit`** : Exits the app
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

### 2.2. Adding tasks: `add`

Adds a task to the task list<br>
Date and time must be formatted as DD/MM/YYYY HHMM<br>
A task must have a name, the description is optional<br>
The task can also optionally have a End Date/Time, or both a Start Date/Time and End Date/Time<br>
Format: `add NAME [d/DESCRIPTION] [s/START_DATETIME] [e/END_DATETIME] [t/TAG]`

Examples:

* `Walk the dog d/Take Zelda on a walk around the park s/01/01/2017 1000 e/01/01/2017 1200 t/Important`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the task list.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits a task in the task list.<br>
Format: `edit INDEX [NAME] [d/DESCRIPTION] [s/START_DATETIME] [e/END_DATETIME] [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Providing the prefix for start or end date/time without supplying a date/time will remove it from the task
> * Existing values will be updated to the input values.

Examples:

* `edit 1 d/Get the Fish`<br>
  Edits the description of the 1st task to be `Get the Fish`.
* `edit 2 s/`<br>
  Removes the start date/time of the 2nd task.

### 2.5. Finding all tasks containing any keyword in their name or description: `find`

Finds tasks whose name or description contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `milk` will not match `Milk`
> * The order of the keywords does not matter. e.g. `Apple Juice` will match `Juice Apple`
> * Only the name is searched.
> * Only full words will be matched e.g. `Cake` will not match `Cakes`
> * tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Milk` will match `Get the Milk`

Examples:

* `find Milk`<br>
  Returns `Get the Milk` but not `Get the milk`

### 2.6. Marking a task : `mark`

Marks the status of a task in the task list as `Complete` or `Incomplete`.<br>
Format: `mark INDEX`

> * Marks the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * If the task's status is `Incomplete`, the command will update it to `Complete` and vice-versa-

Examples:

* `mark 1`<br>
  Updates the task status to `Complete` if it is `Incomplete`. <br>
  Updates the task status to `Incomplete` if it is `Complete`. <br>

### 2.7. Deleting a task : `delete`

Deletes the specified task from the task list.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task list.
* `find Betsy`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.8. Undo a command : `Undo`

Undo the last command executed. <br>
Format: `undo`

> Undo the last command executed.
> Repeated use of undo will undo the previous command from the last command undone

Examples:

* `delete 2`<br>
  `undo`<br>
  Undo the deletion of the 2nd task in the task list.

### 2.9. Redo a command : `Redo`

Redo the last undo command executed. <br>
Format: `undo`

> Redo the last undo command executed.
> Repeated use of redo will redo the previous undo command from the last undo command redone

Examples:

* `delete 2`<br>
  `undo`<br>
  `redo`<br>
  The final action would be the deletion of the 2nd task in the task list.

### 2.10. Clearing all entries : `clear`

Clears all entries from the task list.<br>
Format: `clear`

### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.12. Saving the data

Saves all changes to disk. <br>
Format: `save [SAVE_LOCATION]`

The first time the task manager is saved without `SAVE_LOCATION` the user would be asked to specify where to save.<br>
Task list data are saved in the hard disk at the last save location automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous task list folder.

## 4. Command Summary

* **Add**  `add NAME [d/DESCRIPTION] [s/START_DATETIME] [e/END_DATETIME] [t/TAG]` <br>
  e.g. `Walk the dog d/Take Zelda on a walk around the park s/01/01/2017 1000 e/01/01/2017 1200 t/Important`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find Milk`

* **Mark** : `mark INDEX` <br>
  e.g. `mark 1`

* **Undo** : `undo INDEX` <br>
  e.g. `undo 4`

* **Redo** : `redo INDEX` <br>
  e.g. `redo 4`

* **List** : `list` <br>
  e.g. `list`

* **Help** : `help` <br>
  e.g. `list`

* **Select** : `select INDEX` <br>
  e.g.`select 2`

* **Exit** : `exit` <br>
  e.g. `exit`
