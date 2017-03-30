# Bullet Journal - User Guide

By : `Team F12-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `addressbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Pick up laundry` :
     adds a task named `Pick up laundry` to the Task List.
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

Adds a task to the address book<br>
Format: `add TASK...`

> Tasks can have any number of tags (including 0)

Examples:

* `add Pick up laundry`
* `add Do CS2103 V0.0 t/urgent`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the Task List.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the address book.<br>
Format: `edit INDEX [TASK] [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 Pick up laundry`<br>
  Edits the name of the 1st task to be `Pick up laundry`.

* `edit 2 Do CS2103 V0.0 t/`<br>
  Edits the name of the 2nd task to be `Do CS2103 V0.0` and clears all existing tags.

### 2.5. Finding all tasks containing any keyword in their name: `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `pick` will not match `Pick`
> * The order of the keywords does not matter. e.g. `Pick Up` will match `Up Pick`
> * Only the name is searched.
> * Only full words will be matched e.g. `Pic` will not match `Pick`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Pick` will match `Pick Up`

Examples:

* `find Pick`<br>
  Returns `Pick Up` but not `pick up`
* `find Pick Do`<br>
  Returns Any tasks having names `Pick`, or `Do`

### 2.6. Showing done/undone tasks: `show`

Shows tasks that are done or undone.<br>
Format: `show done/undone`

> * This command is case insensitive. e.g `DONE` works the same as `done`
> * Only the status is searched.
> * Only full words will be matched e.g `do` will not match `done`

Examples:

* `show done`<br>
  Returns tasks that are done
* `show undone`<br>
  Returns tasks that are undone

### 2.7. Deleting a task : `delete`

Deletes the specified task from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the address book.
* `find Pick`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.8. Select a task : `select`

Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the address book.
* `find Pick up` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

### 2.9. Clearing all entries : `clear`

Clears all entries from the Task List.<br>
Format: `clear`

### 2.10. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.11. Saving the data

Task List data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task List folder.

## 4. Command Summary

* **Add**  `add TASK [t/TAG]...` <br>
  e.g. `add Pick up laundry t/chores`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find Pick Do`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`


