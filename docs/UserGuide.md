# YATS Yet Another Task Scheduler - User Guide

By : `Team T16-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

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

### 2.2. Adding a floating task : `add`

Adds a task to the task scheduler <br>

Format: `add TASK_NAME`
> Tasks can have any number of tags (including 0)

Examples:

* `add task math homework`
* `add task pay bill`

### 2.3 Adding an event : `add`

Adds an event to the task scheduler <br>

Format: `add  EVENT_NAME [l/LOCATION p/PERIOD s/START_TIME e/END_TIME d/DESCRIPTION t/TAGS]`
> Events can have any number of tags (including 0)

Examples:

* `add meeting with boss l/work p/daily s/7:00pm  e/9:00pm  d/ get scolded for being lazy t/kthxbye`


### 2.4. Listing all tasks : `list`

Shows a list of all tasks in the task scheduler.<br>
Format: `list all`

### 2.5. Listing tasks containing any keyword in attributes: `list by`

Shows the tasks that are linked to a specific string or a specific date.<br>
Format: `list by ATTRIBUTE KEYWORD [MORE_KEYWORDS]...`

Where ATTRIBUTE can be location, date, timing, done, tag
Where KEYWORD can be any string that the user wants to list
> * The list search is not case sensitive. e.g `School` will match `school`
> * The order of the keywords does not matter. e.g. `singapore Work` will match `Work singapore`
> * Only location will be searched.
> * Only full words will be matched e.g. `Singa` will not match `Singapore`
> * Tasks matching at least one keyword will be returned e.g. `meeting` will match `meeting MarinaSands`

Examples:

* `list by location My School`<br>
  Returns `School`, `My`
* `list by tag date1 tag2 Task3`<br>
  Returns any task that has the tags of the keywords `date1` , `tag2`  or `Task3`

### 2.6 Finding tasks containing any keyword in title and description: `find`

Shows the tasks that are linked to a specific string. <br>
Format: `find KEYWORD [MORE KEYWORD]`

Where KEYWORD can be a title or part of a description
> * The find search is not case sensitive. e.g `School` will match `school`
> * The order of the keywords does not matter. e.g. `calling Bob` will match `Bob calling`
> * Titles and description will be searched.
> * Only full words will be matched e.g. `home` will not match `homework`
> * Tasks matching at least one keyword will be returned e.g. `meeting` will match `meeting Bob`

Examples:

* `find My Task`<br>
  Returns `My Task` but not `task`
* `find date1 tag2 Task3`<br>
  Returns any task having any of the keywords `date1` , `tag2`  or `Task3`

### 2.7. Editing a task : `edit`

Edits an existing task in the task scheduler.<br>
Format: `edit INDEX [b/DEADLINE s/TIME e/TIME d/DESCRIPTION t/TAGS]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 b/02-02-2017 t/school`<br>
  Edits the deadline and tag of the 1st task to be 02-02-2017 and school respectively.

* `edit 2 Math Homework t/`<br>
  Edits the name of the 2nd task to be Math Homework and clears all existing tags.

### 2.8. Deleting a task : `delete`

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

### 2.9. Mark task as done : `mark`

Marks the specified task from the task scheduler as done.
Format: `mark INDEX`

> Marks the task at the specified `INDEX` as done. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive interger** 1, 2, 3, ...
> The index must be within the specified list of indexes available

Examples:

* `mark 1`<br>
  Marks the 1st task in the task scheduler as done.

### 2.10. Mark task as not done : `unmark`

Marks the specified task from the task scheduler as done.
Format: `unmark INDEX`

> Marks the task at the specified `INDEX` as not done. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive interger** 1, 2, 3, ...
> The index must be within the specified list of indexes available

Examples:

* `unmark 1`<br>
  Marks the 1st task in the task scheduler as not done.

### 2.11. Clearing all done tasks : `clear`

Clears all done tasks from TaskManager <br>
Format: `clear`

### 2.12. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.13. Saving the data

Task Scheduler data are saved in the hard disk automatically after any command that changes the data.<br>

There is no need to save manually.

### 2.14. Changing save location : `save`

Changes the file directory of the save location to a file directory specified by the user.<br>
Format: `save`

> File directory specified must be exisiting file directory in disk
> File directory specified cannot be same as current save location file directory

Example:

* `save default`
   Changes save location to the default file directory
* `save /Users/main/Desktop/data`
   Saves the data file of the task scheduler to /Users/main/Desktop/data/YATS.xml


### 2.15. Undo : `undo`

Undo the last command that was performed in the task scheduler
Format: `undo`

> Maximum of 5 `undo` can be performed after last save

Example:

* `delete 2`
   Deletes the second task in the task scheduler
* `undo`
   Undoes the delete that was just done

### 2.16. Redo : `redo`

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

**Q**: How do I transfer my data to another Computer? <br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Scheduler folder.

## 4. Command Summary

* **Add Floating Task** : `add task NAME ` <br>
   e.g. `add task math homework`

* **Add Event** : `add event NAME [s/START TIME,END TIME t/TAG d/DESCRIPTION p/PERIODICITY]` <br>
   e.g. `add event meeting with john s/mon 11.30, mon 13.30 t/work, important  d/meeting about work where I won’t fall asleep`

* **Mark as Done** : `mark INDEX` <br>
   e.g. `mark 1`

* **Mark as Not Done** : `unmark INDEX` <br>
   e.g. `unmark 1`

* **Clear Done Task** : `clear` <br>
   e.g. `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `list ATTRIBUTE KEYWORD [MORE_KEYWORDS]` <br>
   e.g. `list by tag homework`
   e.g. `list by timing 9:00pm`

* **List** : `list` <br>
   e.g. `list`

* **Change Save Location** : `save` <br>
   e.g. `save default`
   e.g. `save /Users/main/Desktop/data`

* **Help** : `help` <br>
   e.g. `help`

* **Undo** : `undo` <br>
   e.g. `undo`

* **Redo** : `redo` <br>
   e.g. `redo`
