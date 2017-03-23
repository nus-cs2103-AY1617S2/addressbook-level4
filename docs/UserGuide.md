# TodoApp - User Guide

By : `Team F12-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `ToDoApp.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your ToDoApp.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/ToDoApp.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Buy printer :
     adds a task named `Buy Printer` to the TodoApp.
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

Adds a task to the task list<br>
Format: `add NAME [s/START_TIME] [d/DEADLINE] [p/PRIORITY] [t/TAG] [n/NOTES] [c/COMPLETION]...`

> * Task can have a start time
> * Task can have a deadline
> * Task can have a priority ranking from 1 - 5
> * Task can have any number of tags (including 0)
> * Task can be completed or uncompleted
> * Task can have a note

Examples:

* `add Buy Printer`
* `add Do CS2103 Homework p/1 t/CS2103 n/Buy from Challenger.`
* `add Do CS2101 Homework s/tomorrow d/next week.`

### 2.3. Listing out uncomplete tasks : `list`

Shows a list of all tasks in the task list.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the TodoApp.<br>
Format: `edit INDEX [NAME] [d/DEADLINE] [p/PRIORITY] [t/TAG] [n/NOTES]...`

> * Edits the task at the specified `INDEX`.<br>
    Index is the index number shown in the last task listing.<br>
    The index **must be a POSITIVE integer** 1, 2, ...
> * Minimally, one of the optional fields must be provided in order to EDIT successfully
> * Optional values provided will be updated.
> * Existing tags of the task will be replaced by the `edit` command.
> * Can choose to remove fields by typing a [tag/] with anything after it.

Examples:

* `edit 1 Finish CS2103 work`<br>
  Edits the name of the 1st task to `Finish CS2103 work`.

* `edit 2 t/`<br>
  Edits clears all existing tags of the task in Index 2.


### 2.5. Mark task

Marks a task as complete.<br>
Format: `mark INDEX`

> * Marks the task at the specified `INDEX` as completed.
> * The index refers to the index number shown in the most recent task listing.
> * The index **must be a positive integer** 1, 2, ...

Examples:

* `mark 2`<br>
Marks tasks at index 2 as completed.

### 2.6. Unmark task

Marks task as incomplete.<br>
Format: `unmark INDEX`

> * Marks the task at the specified `INDEX` as incomplete.
> * The index refers to the index number shown in the most recent task listing.
> * The index **must be a positive integer** 1, 2, ...

Examples:

* `unmark 2`<br>
Marks tasks at index 2 as incomplete.

### 2.7. Clear the list

Deletes all the tasks in the list.<br>
Format: `clear`

> * Will remove ALL tasks in the list
> * Use with **caution**!

Examples:

* `clear`<br>
All tasks in the ToDoApp will be deleted.

### 2.8. Find specific tasks

Retrieve tasks based on various conditions.<br>
Format: `find [name NAME] [deadline DEADLINE] [priority PRIORITY] [completion COMPLETION]`

> * Lists all tasks with the specific optional fields.

Examples:

* `find deadline 5 pm`<br>
Retrieve all tasks with the deadline at 5pm.

### 2.9. Undo a recent command: `undo`

Undo the most recent command<br>
Format: `undo`

> * Can undo commands `add`, `delete`, `edit`, `mark`, `unmark`

### 2.10. Redo a recent command: `redo`

Redo the most recent command<br>
Format: `redo`

> * Can redo commands that were most recently undone
> * Inputting a new undo-able command clears the redo state history


### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.12. Saving the data

ToDoApp data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous ToDoApp folder.

## 4. Command Summary

* **Add**  `add NAME [d/DEADLINE] [p/PRIORITY] [t/TAG] [n/NOTES]` <br>
  e.g. `add Do CS2103 Homework p/1 t/CS2103 n/Buy from Challenger.`

* **Edit**  `edit INDEX [NAME] [d/DEADLINE] [p/PRIORITY] [t/TAG] [n/NOTES]` <br>
  e.g. `edit 1 Finish CS2103 work`

* **Undo** : `undo` <br>
  e.g. `undo`

* **Redo** : `redo` <br>
  e.g. `redo`

* **Find** : `find [deadline DEADLINE]` <br>
  e.g. `find deadline 5 pm`

* **Mark** : `mark INDEX` <br>
  e.g.`mark 2`

* **Unmark** : `unmark INDEX` <br>
  e.g.`unmark 2`

* **List** : `list` <br>
  e.g. `list`

* **Clear** : `clear` <br>
  e.g. `clear`

* **Help** : `help` <br>
  e.g. `help`
