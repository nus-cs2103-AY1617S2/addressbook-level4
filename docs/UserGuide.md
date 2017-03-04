# TypeTask - User Guide

By : `Team T09-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `JAN 2016`  &nbsp;&nbsp;&nbsp;&nbsp;

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `TypeTask.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` CS2103T Meeting d/12022107 t/11:00am` :
     adds a task named `CS2103T Meeting` to the Task Manager.
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

Adds a person to the address book<br>
Format: `add TASK d/DATE t/TIME [f/TAG]...`

> Tasks can have any number of tags (including 0)

Examples:

* `add CS2103T Meeting d/12022107 t/11:00am`
* `add Buy Notebook f/Shopping t/11:00am`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the Task Manager.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the address book.<br>
Format: `edit INDEX [TASK] [d/DATE] [t/TIME] [f/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it. 

Examples:

* `edit 1 d/02122017 t/11:00am`<br>
  Edits the date and time of the 1st task to be `02122017` and `11:00am` respectively.

* `edit 2 CS2013T Meeting t/`<br>
  Edits the label of the 2nd task to be `CS2013T Meeting` and clears all existing tags.

### 2.5. Finding all tasks containing any keyword in their label: `find`

Finds tasks whose labels contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `meeting` will not match `Meeting`
> * The order of the keywords does not matter. e.g. `Meeting Tutor` will match `Tutor Meeting`
> * Only the name is searched.
> * Only full words will be matched e.g. `Meeting` will not match `Meetings`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Meeting` will match `Meeting Tutor`

Examples:

* `find Study`<br>
  Returns `Study Math` but not `Study`
* `find Meeting Study Shop`<br>
  Returns Any tasks having labels `Meeting`, `Study`, or `Shop`

### 2.6. Deleting a task : `delete`

Deletes the specified task from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `find Meeting`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.
* `list`<br>
  `delete 2 3`<br>
  Deletes the 2nd and 3rd task in the Task Manager.

### 2.7. Complete a task : `done`

Marks the task as done identified by the index number used in the last task listing.<br>
Format: `Done INDEX`

> Completes the task at the specified `INDEX` and removes it from the list.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `done 2`<br>
  Removes the 2nd task in the Task Manager.
* `find Meeting` <br>
  `done 1`<br>
  Removes the 1st task in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all entries from the Task Manager.<br>
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
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary
Command | Shortcut | Format | Example
-------- | :-------- | :--------- | :-----------

* **Add** |   | `add TASK d/DATE t/TIME [f/TAG]...` <br> | e.g. `add Shop Shoes d/20082017 t/4:30pm t/Shopping t/Party`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find Study Math`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Complete** : `done INDEX` <br>
  e.g.`done 2`


