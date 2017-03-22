# TaskManager - User Guide

By : `Team CS2103JAN2017-T11-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `NUS`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `TaskManager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`** : add Study for midterm sd/02/03/17 ed/04/03/17 t/study t/midterm :
     adds the task Study for Midterm, starting from 02/03/17 to 04/03/17 with tags "study" and "midterm" to the task manager.
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

Opens a side window that displaying all commands and syntax.<br>
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task to the task manager<br>
Format: `add TASK_NAME p/PRIORITY_LEVEL sd/DATETIME ed/DATETIME [t/TAG]...`
Date Format: HH:mm dd/MM/yyyy
Note: HH:MM is optional

> Tasks can have any number of tags (including 0).

Examples:

* `add Study for midterm p/1 ed/04/03/2017 t/study t/midterm`
* `add Attend CS2103 tutorial p/1 ed/02/03/2017 t/lesson t/school t/tutorial`

### 2.3. Listing tasks : `list`

Shows a list of all tasks in the task manager on that day.<br>
Format: `list`<br>
Shows a list of all tasks in the task manager on a particular day.<br>
Format: `list d/dd/mm/yy`<br>
Shows a list of all tasks in the task manager with a particular tag.<br>
Format: `list t/TAG`<br>
Shows a list of all tasks in the task manager<br>
Format: `list t/ALL<br>

> The list of tasks will be sorted accoding to priority level from 1 to 5

Examples:
* `list t/complete`<br>
  Shows a list of all completed tasks. The `complete` tag is a reserved tag.

* `list ed/04/03/17`
  Shows a list of all tasks due on 04/03/17.

### 2.4. Editing a task : `edit`

Edits an existing task in the task manager.<br>
Format: `edit INDEX [NAME] p/PRIORITY sd/START_DATE ed/END_DATE [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 sd/03/03/17` <br>
  Edits the start date of task 1 as 03/03/17.

* `edit 2 Do Algorithm Assignment t/`<br>
  Edits the name of the 2nd task to be `Do Algorithm Assignment` and clears all existing tags.

### 2.5. Finding all tasks containing any keyword in their name: `find`

Finds tasks whose names contain any of the given keywords or tag names.<br>
Format 1: `find KEYWORD [MORE_KEYWORDS]`
Format 2: `find [t/TAG]`

> * The search is case insensitive. e.g `assignment` will match `AssIGNmEnt`
> * The order of the keywords does not matter. e.g. `do assignment to` will match `assignment to do`
> * Only full words will be matched e.g. `assign` will not match `assignment`
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `assignemnt` will match `do algorightm assignment`
> * The tag name is case sensitive and must be exact match

Examples:

* `find midterm`<br>
  Returns `Study for midterm`
* `find t/Tutorial`<br>
  Returns nothing because the correct tag name is `tutorial`

### 2.6. Deleting a task : `delete`

Deletes the specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index  number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list t/ALL`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find t/tutorial`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Complete a task : `complete`

Marks the specified task as `Completed`. The task is automatically added with a `complete` tag.<br>
Format: `complete INDEX`

> Mark the task at the specified `INDEX` as `Completed`.
> The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list t/ALL`<br>
  `complete 2`<br>
  Selects the 2nd task in the task manager.
* `find t/tutorial` <br>
  `complete 1`<br>
  Selects the 1st task in the results of the `find` command.

### 2.8. Allocate priority to a task: `prioritize`

Puts a priority level to a task.<br>
Format: `prioritize INDEX PRIORITY_LEVEL`

> Allocates a priority leve of `PRIORITY_LEVEL` to the task at the specified `INDEX`.
> The index **must be a positive interger** 1, 2, 3,...
> The priority level **must be a positive integer from 1 to 3**, 1 being the highest priority and 3 being the least.

Examples:
* `list t/ALL`<br>
  `prioritize 2 3`
  Puts a priority level of 3 to the 2nd task in the task manager

### 2.9. Clearing all entries : `clear`

Clears all entries from the task manager.<br>
Format: `clear`

### 2.10. Undo the last change : `undo`

Undos the last modification done by the user.
Format: `undo`

### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.12. Saving the data

Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.13. Viewing command history : `history`

Displays the past 10 commands entered by the user.<br>
Format: `history`,

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 4. Command Summary

* **Add**  `add TASK_NAME p/1 sd/START_DATE ed/DUE_DATE [t/TAG]...` <br>
   e.g. `add Study for midterm p/1 sd/04/03/2017 ed/04/04/2017 t/study t/midterm`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Edit** : `edit INDEX` <br>
   e.g. `edit 1 ed/03032017`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
   e.g. `find assignment`
	      `find t/tutorial`

* **List** : `list` <br>
   e.g. `list`
        `list t/ALL`

* **Help** : `help` <br>

* **Complete** : `complete INDEX` <br>
   e.g. `complete 2`

* **Prioritze** : `prioritize INDEX PRIORITY_LEVEL` <br>
   e.g. `priority 2 3`

* **Undo** : `undo` <br>

* **Command History** : `history` <br>

* **Exit ** : `exit` <br>
