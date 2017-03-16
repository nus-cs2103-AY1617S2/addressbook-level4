# Opus - User Guide

By : `Team W15-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

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
   * **`list`** : lists all tasks in the main application window
   * **`add`**` Do laundry` : adds a task named `Do laundry` to the task manager.
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
Format: `add NAME [n/NOTES] [d/DEADLINE] [p/PRIORITY] [t/TAG]...`

> Task can have a deadline
> Task can have a priority ranking from 1 - 5
> Task can have any number of tags (including 0)

Examples:

* `add Do laundry`
* `add Finish v0.0 documentation t/CS2103T`

### 2.3. Sort tasks : `sort`

Sorts the list of task currently being shown according to the parameters.<br>
Format: `sort [s/STATUS] [d/DEADLINE] [p/PRIORITY] [t/TAG]...`

### 2.4. Editing a task : `edit`

Edits an existing task in Opus.<br>
Format: `edit INDEX [NAME] [n/NOTES] [d/DEADLINE] [p/PRIORITY] [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 Finish tutorial exercises`<br>
  Edits the name of the 1st task to `Finish tutorial exercises`.

### 2.5. Mark task

Marks task as complete.<br>
Format: `mark [INDEX]...`

> * Marks the task at all the specified `INDEX` as complete.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `mark 3 4 5`<br>
Marks tasks at index 3, 4 and 5 as complete.

### 2.6. Unmark task

Marks task as incomplete.<br>
Format: `unmark INDEX...`

> * Marks the task at all the specified `INDEX` as incomplete.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `unmark 3 4 5`<br>
Marks tasks at index 3, 4 and 5 as incomplete.

### 2.7. Set deadline for task

Sets a new deadline for task.<br>
Format: `schedule INDEX DEADLINE`

> * Updates the task deadline at the specified `INDEX` as `DEADLINE`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

### 2.8. Undo

Undo the latest command.<br>
Format: `undo`

> * Reverts the change done by the command that was run previously.

### 2.9. Redo

Reverts the previous undo.<br>
Format: `redo`

> * Reverts the change done by the previous undo.

### 2.10. Find

Displays a list of tasks based on keywords and/or tags.<br>
Format: `find [NAME] [s/STATUS] [d/DEADLINE] [p/PRIORITY] [t/TAG]...`

> * Displays the list of tasks matching the search parameters
> * `find` without parameters will display the help section for the command

### 2.11. Autocomplete

Autocomplete the user's command on the CLI.

> * Example:
    User enters `f` and presses `TAB`, `find` command will appear in the input box.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Add**  `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...` <br>
  e.g. `add James Ho p/22224444 e/jamesho@gmail.com a/123, Clementi Rd, 1234665 t/friend t/colleague`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find James Jake`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`


