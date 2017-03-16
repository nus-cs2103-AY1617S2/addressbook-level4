# YATS Yet Another Task Scheduler - User Guide

By : `Team T16-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `TaskSceduler.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Scheduler.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add task`**` My task -d July 23 -t 2300` :
     adds a task named `My Task` to the Task Scheduler.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
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

### 2.2. Adding a task : `add task`

Adds a task to the task scheduler <br>

Format: `add TASK_NAME b/DEADLINE [t/TAG d/DESCRIPTION s/POSSIBLE TIMING 1, POSSIBLE TIMING 2 p/PERIODICITY]...`
> Tasks can have any number of tags (including 0)

Examples:

* `add task math homework b/03-03-2017 t/school p/N`
* `add task pay bill b/09032017 t/house p/Y`

### 2.3 Adding an event : `add event`

Adds an event to the task scheduler <br>

Format: `add  EVENT_NAME l/LOCATION p/PERIOD s/START_TIME e/END_TIME d/ DESCRIPTION t/ TAGS`
> Events can have any number of tags (including 0)

Examples:

* `add meeting with boss l/work p/daily s/7:00pm  e/9:00pm  d/ get scolded for being lazy t/kthxbye`


### 2.4. Listing all tasks : `list`

Shows a list of all tasks in the task scheduler.<br>
Format: `list all`

### 2.5. Finding all tasks containing any keyword : `list`

Shows the tasks that are linked to a specific string or a specific date.<br>
Format: `list KEYWORD [MORE_KEYWORDS]...`

Where KEYWORD can be a title, part of a description, tag or a date
> * The list search is case sensitive. e.g `School` will not match `school`
> * The order of the keywords does not matter. e.g. `calling Bob` will match `Bob calling`
> * Titles, descriptions, dates, timings and tags will be searched.
> * Only full words will be matched e.g. `home` will not match `homework`
> * Tasks matching at least one keyword will be returned e.g. `meeting` will match `meeting Bob`

Examples:

* `List My Task`<br>
  Returns `My Task` but not `task`
* `list date1 tag2 Task3`<br>
  Returns any task having any of the keywords `date1` , `tag2`  or `Task3`

### 2.6. Editing a task : `edit`

Edits an existing task in the task scheduler.<br>
Format: `edit INDEX [b/DEADLINE s/TIME d/DESCRIPTION t/TAGS]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 b/02-02-2017 t/school`<br>
  Edits the deadline and tag of the 1st task to be 02-02-2017 and school respectively.

* `edit 2 Math Homework t/`<br>
  Edits the name of the 2nd task to be Math Homework and clears all existing tags.

### 2.7. Deleting a task : `delete`

Deletes the specified task from the task scheduler.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...
> The index must be within the specified list of indexes available

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task scheduler.
* `list Task2`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `list` command.

### 2.8. Clearing all done items : `clear done`

Clears all done tasks from TaskManager <br>
Format: `clear done tasks`
Clears all done events from TaskManager <br>
Format: `clear done events`
Clears all done items from TaskManager <br>
Format: `clear done`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

Task Scheduler data are saved in the hard disk automatically after any command that changes the data.<br>

There is no need to save manually.

### 2.11. Undo : `undo`

Undo the last command that was performed in the task scheduler
Format: `undo`

> Maximum of 5 `undo` can be performed after last save

Example:

* `delete 2`
   Deletes the second task in the task scheduler
* `undo`
   Undoes the delete that was just done

### 2.12. Redo : `redo`

Redo an undone command in the task scheduler
Format: `redo`

> Maximum of 5 `redo` can be performed after 5 `undo`

Example:

* `delete 2`
   Deletes the second task in the task scheduler
* `undo`
   Undoes the delete that was just done
* `redo`
   Redoes the previous undone delete

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Scheduler folder.

## 4. Command Summary

* **Add Task** : `add task NAME b/DEADLINE [t/TAG d/DESCRIPTION s/POSSIBLE TIMING 1, POSSIBLE TIMING 2 p/PERIODICITY]... ` <br>
   e.g. `add task math homework b/03-03-2017 t/school p/N`

* **Add Event** : `add event NAME [s/START TIME,END TIME t/TAG d/DESCRIPTION p/PERIODICITY]` <br>
   e.g. `add event meeting with john s/mon 11.30, mon 13.30 t/work, important  d/meeting about work where I won’t fall asleep`

* **Clear** : `clear done` <br>
   e.g. `clear done tasks`
   e.g. `clear done events`
   e.g. `clear done`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `list KEYWORD [MORE_KEYWORDS]` <br>
   e.g. `list homework`

* **List** : `list` <br>
   e.g. `list all`

* **Help** : `help` <br>
   e.g. `help`

* **Undo** : `undo` <br>
   e.g. `undo`

* **Redo** : `redo` <br>
   e.g. `redo`
