# To Do Manager - User Guide

By : `TEAM W09-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp;

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `todomanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your To Do Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Buy groceries d/05032017 p/urgent i/eggs x10, milk x2, bread x2 t/home t/errand` :
     adds a task titled `Buy groceries` to the list.
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

Adds a task to the list<br>
Format: `add TITLE d/DATE p/PRIORITY i/INSTRUCTION [t/TAG]...`

> Tasks can have any number of tags (including 0)

Examples:
* `add Buy groceries d/05032017 p/urgent i/eggs x10, milk x2, bread x2 t/home t/errand`
* `add Watch webcast i/cs2103 t/school p/urgent d/07032017`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the list.<br>
Format: `edit INDEX [TITLE] [d/DATE] [p/PRIORITY] [i/INSTRUCTION] [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 p/relax d/07032017`<br>
  Edits the priority and date of the 1st task to be `relax` and `07032017` respectively.

* `edit 2 Drink Coffee t/`<br>
  Edits the title of the 2nd task to be `Drink Coffee` and clears all existing tags.

### 2.5. Finding all tasks containing any keyword in their title: `find`

Finds tasks with titles containing any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the title is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find groceries Buy`<br>
   Returns `Buy groceries` but not `Groceries`
* `find buy webcast`<br>
   Returns Any tasks having titles `buy` or `webcast`

### 2.6. Deleting a task : `delete`

Deletes the task identified by the index number used in the last task listing.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the list.
* `find 2103T`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Select a task : `select`

Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the title at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the list.
* `find Eat Chips and Pizza` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

### 2.8. Clearing all tasks : `clear`

Clears all tasks from the list.<br>
Format: `clear`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

To-do data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous To Do Manager folder.

## 4. Command Summary

* **Add**  `add TITLE d/DATE p/PRIORITY i/INSTRUCTION [t/TAG]...` <br>
  e.g. `add Buy groceries d/05032017 p/urgent i/eggs x10, milk x2, bread x2 t/home t/errand`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find Play basketball`

* **List** : `list` <br>

* **Help** : `help` <br>

* **Select** : `select INDEX` <br>
  e.g.`select 2`


