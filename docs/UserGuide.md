# Task Manager - User Guide

By : `CS2103JAN2017-T16-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/UiPrototype.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list all`** : lists all tasks
   * **`add `**` send budget proposal by next Thurs noon to boss #project A` :
     adds a task to the task manager.
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

> **DATETIME Format**
>
> Watodo uses the external library Natty Date Parser to parse date and time options.
> Some examples of the acceptable formats are:
> * `2017-02-23` (yyyy-mm-dd) <br>
> * `04/05` (mm/dd) <br>
> * `today` <br>
> * `tomorrow` or `tmr` <br>
> * `next tues` <br>
> * `3 weeks from now` <br>
> * `9pm` or `21:00` <br>
> * `noon` <br>

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task : `add`

Adds a task to the task manager<br>
> Task can have any number of tags (including 0)

### 2.2.1. Floating task

Format: `add TASK [#TAG]...`

Examples:

* `add read Lord of The Rings #personal`

### 2.2.2. Adding a task with a deadline

Format: `add TASK by/ DATETIME [#TAG]...` <br>
     OR `add TASK on/ DATETIME [#TAG]...`

Examples:

* `add prepare meeting slides by/ tomorrow 9am #impt #work`
* `add send budget proposal on/ Thurs noon to boss #project A`

### 2.2.3. Adding an event

Format: `add TASK from/ START_DATETIME to/ END_DATETIME [#TAG]...` <br>
     OR `add TASK on/ START_DATETIME to/ END_DATE_TIME [#TAG]...`

Examples:

* `add attend skills upgrading workshop from/next mon to/ 05/16`
* `add meeting at board room 4 from/ 10am to/ 11am #project A #meetings`

### 2.3. Listing tasks by type : `list LIST_TYPE`

Format: `list`<br>
Shows a list of all overdue tasks and upcoming tasks set for the next day.<br>

Format: `list all`<br>
Shows a list of all tasks.<br>

Format: `list float`<br>
Shows a list of all floating tasks.<br>

Format: `list deadline`<br>
Shows a list of all tasks with deadlines.<br>

Format: `list event`<br>
Shows a list of all events.<br>

Format: `list day`<br>
Shows a list of deadlines and events scheduled on the current day.<br>

Format: `list week`<br>
Shows a list of deadlines and events scheduled for the week.<br>

Format: `list month`<br>
Shows a list of deadlines and events scheduled for the month.<br>

Format: `list #TAG`<br>
Shows a list of tasks labeled with the given TAG.<br>

Format: `list marked`<br>
Shows a list of all tasks that have been marked as completed.<br>

### 2.4. Finding all tasks containing any keyword in their description: `find`

Finds tasks which contain any of the given keywords in their description.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * Only the task description is searched.
> * The search is case insensitive. e.g `Report` will match `report`
> * The order of the keywords does not matter. e.g. `proposal for boss` will match `for boss proposal`
> * Partial words will be matched e.g. `meet` will match `meeting`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `lunch` will match `lunch appointment`

Examples:

* `find file filing`<br>
  Returns any tasks with `file`, `files`, `filing` etc. as part of its description

### 2.5. Editing a task : `edit`

Edits an existing task in the task manager.<br>
Format: `edit INDEX <[TASK] [by DATETIME] [from START_DATETIME to END_DATETIME] [#TAG]...>`

> * Edits the person at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * If changing the DATETIME, format must match the chosen task type (either deadline or event)
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the tasks will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `#` without specifying any tags after it.

Examples:

* `list today`
  `edit 5 meeting at board room 2`<br>
  Edits the task description of the 5th task today to be `meeting at board room 2`.

* `list`
  `edit 2 by mon #`<br>
  Edits the deadline of the 2nd task listed to be `mon` and clears all existing tags.


### 2.6. Deleting a task : `delete`

Deletes the specified task from the task manager. <br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list all`<br>
  `delete 2`<br>
  Deletes the 2nd task in the list of all tasks.

### 2.7. Mark a task : `mark`

Marks the task identified by the index number used in the last task listing.<br>
Format: `mark INDEX`

_**Alternative keyword: check, finish**_

> Marks the task as completed and hides it from view.
> Task is added to a list of completed tasks that can be viewed by calling `list marked`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list today`<br>
  `mark 2`<br>
  Marks the 2nd task in the list of task today.

### 2.8. Unmark a task : `unmark`

Unmarks the task identified by the index number used in the marked task listing.<br>
Format: `unmark INDEX`

_**Alternative keyword: uncheck, unfinish**_

> Unmarks a previously marked task and restores it back into its original location and view.
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list marked`<br>
  `unmark 2`<br>
  Unmarks the 2nd task in the list.

### 2.9. Undoing previous step : `undo`

Undo the previous command and restore the data to one step before.<br>
Format: `undo`

### 2.10. Clearing all entries : `clear`

Clears all entries from the task manager.<br>
Format: `clear`

### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.12. Saving the data

Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 4. Command Summary

* **Add**
> * `add TASK [#TAG]...` <br>
> * `add TASK by DATETIME [#TAG]...`
> * `add TASK from START_DATETIME to END_DATETIME [#TAG]...`

* **List** : `list` <br>
> * `list`<br>
> * `list all`<br>
> * `list float`<br>
> * `list deadline`<br>
> * `list event`<br>
> * `list day`<br>
> * `list week`<br>
> * `list month`<br>
> * `list #TAG`<br>
> * `list marked`<br>

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>

* **Edit** :
> `edit INDEX [TASK] [by DATETIME] [from START_DATETIME to END_DATETIME] [#TAG]...`

* **Mark** : `mark INDEX` <br>

* **Unmark** : `unmark INDEX` <br>

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Clear** : `clear`

* **Undo** : `undo` <br>

* **Help** : `help` <br>
