# Savvy To-Do - User Guide

By : `Team F12-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)
5. [Keyboard Shortcuts](#5-keyboard-shortcuts)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `SavvyToDo.jar` from the [releases](../../../releases) tab.

2. Copy the file to the folder you want to use as the home folder for your Savvy To-Do.

3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">
   > (Mockup of Savvy To-Do app. Final design subject to changes.)

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.

5. Some example commands you can try:
   * **`add`**  `collect parcels from mailbox` : adds a new task
   * **`list`** : lists all tasks
     adds a new task
   * **`delete`** `3` : deletes the 3rd task shown in the current list
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
Adds a task to the Savvy To-Do<br>
Format: `add TASK_NAME [dt/START_DATE END_DATE] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]`

> Parameters | Description
> -------- | :--------
> TASK_NAME | `Mandatory` Specifies the name of the task.
> START_DATE (See [DATE](#date)) | `Optional` Specifies the starting date and time of the task.
> END_DATE (See [DATE](#date)) | `Optional` Specifies the ending date and time of the task.
> LOCATION | `Optional` Specifies the location where the task happens.
> PRIORITY_LEVEL | `Optional` Specifies the priority level of the task.<br>`Accepts` values `low`, `medium`, `high`<br>`Defaults` to `medium`
> RECURRING_TYPE | `Optional` Specifies the recurring type of the task.<br>`Accepts` values `none`, `daily`, `weekly`, `monthly`, `yearly`<br>`Defaults` to `none`
> NUMBER_OF_RECURRENCE | `Optional` Specifies the number of times the task recurs. <br>`Defaults` to `0`<br>`Ignored` if RECURRING_TYPE is `none`
> CATEGORY | `Optional` Specifies a custom category for the task. This can be used for keeping track of similar tasks.
> DESCRIPTION | `Optional` Describes the task.

##### Date

> If only the DATE is specified, the TIME defaults to starting at 12am or ending at 11:59pm.<br>If only the TIME is specified, the DATE defaults to today.<br><br>If only `START_DATE` is supplied, the task will be a 1-day event starting from the specified `START_DATE` and ending on the same day at 11:59pm.<br>If only `END_DATE` is supplied, the task will start today at 12am.<br><br>The date and time can be entered in a formal format like <i>17-03-2016</i>, or a natural format like <i>next wednesday, 2pm</i>. The formal format follows the system's settings for whether <i>mm-dd-yyyy</i> or <i>dd-mm-yyyy</i> is used.

Examples:
* `add Project Meeting dt/05/10/2017 1400 = 06/10/2017 1800 r/daily n/2 c/CS2103 d/Discuss about roles and milestones` <br>
  Add task named, Project Meeting, under CS2103 category. The task is schedule to take place on 5th and 6th of October 2016 from 2pm to 6pm each day.
* `add NUSSU Leadership Camp s/05-10-2016 2pm e/08-10-2016 6pm c/NUSSU`
  Add task named, NUSSU Leadership Camp, under NUSSU category. The 4 day 3 night is schedule to take place from 5th October, 2pm to 8th of October 2016, 6pm.

[//]: # (@@author jingloon)

### 2.3. Listing all tasks : `list`

Shows a list of tasks in the Savvy To-Do by category or by priority or everything.<br>
Examples: 
* `list c/CS2103`<br>
* `list p/high`<br>
* `list`<br>

### 2.4. Editing a task : `edit`

Edits an existing task in the Savvy To-Do. <br>
Format: `edit INDEX [t/TASK_NAME] [dt/START_DATE = END_DATE] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 c/CS2103 d/Complete group project component`<br>
  Edits the specified task to be tagged under `CS2103` with the description `Complete group project component`.


### 2.5. Finding all tasks containing any keyword in their name: `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find homework`<br>
  Returns `do homework` but not `Homework`
* `find dinner lunch breakfast`<br>
  Returns Any task having `dinner`, `lunch`, or `breakfast` in their names.

### 2.6. Deleting a task : `delete`

Deletes the specified task from the Savvy To-Do. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the Savvy To-Do.
* `find laundry`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Select a task : `select`

Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task at the specified `INDEX` and displays the details.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the Savvy To-Do.
* `find shopping` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

### 2.8. Undo an operation : `undo`

Undo the last operation that changed the task manager.<br>
Format: `undo`

### 2.9. Redo an operation : `redo`

Undo the last undo operation.<br>
Format: `redo`

> Can only be used after an undo operation.<br>

### 2.10. Clearing all entries : `clear`

Clears all displayed entries from the interface<br>
Format: `clear`

### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.12. Saving the data

Savvy To-Do data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Savvy To-Do folder.

## 4. Command Summary

* **Add**  `add TASK_NAME [dt/START_DATE = END_DATE] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]`

* **Edit**  `edit INDEX [TASK_NAME] [dt/START_DATE = END_DATE] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>

* **List** : `list` <br>

* **Undo** : `undo` <br>

* **Redo** : `redo` <br>

* **Help** : `help` <br>

* **Select** : `select INDEX` <br>

## 5. Keyboard Shortcuts

Key Codes | Function | Command Box Input
-------- | :--------  | :--------
<kbd>Esc</kbd> | Toggle to show/hide a list of keyboard shortcuts | -
<kbd>Ctrl</kbd> + <kbd>H</kbd> | [Help](#viewing-help--help) | `help`
<kbd>Ctrl</kbd> + <kbd>Q</kbd> | [Exit](#exiting-the-program--exit) | `exit`
<kbd>Ctrl</kbd> + <kbd>D</kbd> | [Clear](#clearing-all-entries--clear) all entries | `clear`
<kbd>Ctrl</kbd> + <kbd>L</kbd> | [List](#listing-all-tasks-list) all unmarked task by date, earliest task first | `list`
<kbd>Ctrl</kbd> + <kbd>P</kbd> | [List](#listing-all-tasks-list) all unmarked task by priority level, highest to lowest | `list priorityLevel`
<kbd>Ctrl</kbd> + <kbd>S</kbd> | [Storage](#storage-location) Popups a directory chooser dialog box to choose a new filepath | `storage NEW_FILEPATH`
<kbd>Ctrl</kbd> + <kbd>Z</kbd> | [Undo](#undo-the-most-recent-operation--undo) | `undo`
