# Watodo - User Guide

By : `Team CS2103 T15-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

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
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` school camp from 3pm today to 5pm tomorrow :
     adds a school camp task to the Task Manager.
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

### 2.2. Adding a task: `add`

Adds a task to the Task Manager<br>
Format: `add [TASK-NAME] d/[DD/MM/YYYY] c/[HH:MM] t/[KEY-WORD]...` <br>

> * Task can have any number of tags (including 0)
> * Date should follow DD/MM/YYYY format
> * Clock time is 24-hour format
> * the sequence of input should strictly follow the format.

Examples:

* `add school camp from 3pm today to 5pm tomorrow`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the Task Manager.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the Task Manager.<br>
Format: `edit [TASK-NAME] d/[DD/MM/YYYY] c/[HH:MM] t/[KEY-WORD]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.

Examples:

* `edit 1 -name go home`<br>
  Change name of task at index 1 to "go home"

* `edit 2 -start 3pm 5 sept -end 4pm 6 sept`<br>
  Change start and end times of timed tasks


### 2.5. Finding all tasks containing any keyword in their name: `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `search [KEYWORD1]  ...`

> * Only the name is searched.
> * Only full words will be matched e.g. `Go Home` will not match `Going Home`

Examples:

* `search birthday`<br>
  Search for tasks with "birthday" in description
* `search birthday family`<br>
  Search for tasks with "birthday" and "family" in description

### 2.6. Deleting a task : `delete`

Deletes the specified task from the Task Manager. Irreversible, meaning this operation cannot be undone.<br>
Format: `delete [INDEX]`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `delete 2`<br>
  Deletes the 2nd task in the Task Manager.

### 2.7. Clearing all entries : `clear`

Clears all entries from the Task Manager.<br>
Format: `clear`

> Irreversible.

### 2.8. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.9. Saving the data

Task Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 4. Command Summary

* **Add**  `add NAME from [s/START_TIME] [s/START_DATE]  [e/END_TIME] [e/END_DATE]...` <br>
  e.g. `add school camp from 3pm today to 5pm tomorrow`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Search** : `search KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `search birthday party`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Edit** : `edit INDEX [NAME] [PARMAETER] [NEW VALUE]...` <br>
  e.g. `edit 1 -name go home`

* **Done** : `done [INDEX]` <br>
  e.g. `done 1`

* **Undone** : `undone [INDEX]` <br>
  e.g. `undone 1`
