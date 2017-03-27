# To-do List - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `todolist.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your ToDoList.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**`Do tutorial /by 14.02.17` :
     adds a deadline task describing `Do tutorial` to the todolist.
   * **`delete`**` f3` : deletes the 3rd task shown in the float list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are can have multiples.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> A new window will open showing the user guide webpage
> e.g. `help`

### 2.2. Adding a task: `add`

Adds a task to the todolist
Format: `add TITLE /venue /from /to /level /description #[TAG]`

> * TITLE represent the task (complusory)
> * /venue represents the location for the task
> * /from represents the starting time of the event task
> * /to represents the ending time of the event task
> * /to without /from would change it to deadline task
> * /level represents the urgency level of the task
> * /description represents the description of the task*
> * #[TAG] represents the tag**

Note
*Tasks can have 4 level of priority (0, 1, 2, 3 with 3 being the highest)
**Tasks can have any number of tags (including 0)

### 2.3.1. Listing all tasks : `list`

Shows a list of all tasks in the todolist<br>
Format: `list`

### 2.3.2. Listing all tags : `listtag`

Shows a list of all tags in the todolist<br>
Format: `listtags`

### 2.4. Editing a task : `edit`

Edits an existing task in the todolist.<br>
Format: `edit INDEX TITLE /venue /from /to /level /description [#TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.
    Index **must include the type of task**, e.g. f1 for 1st float task and **must be followed by a positive integer** 1, 2, 3, ... <br>
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.

Examples:

* `edit d1 Do tutorial /to 17.09.19`<br>
  Edits the TITLE and deadline of the 1st float task to be `Do tutorial` and `17.09.19` respectively.

### 2.5. Finding all tasks containing any keyword in their title: `find`

Finds tasks whose description contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `lunch` will not match `Lunch`
> * The order of the keywords does not matter. e.g. `Lunch Plan` will match `Plan Lunch`
> * Only the title is searched.
> * Only full words will be matched e.g. `Lunch` will not match `Lunchie`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Lunch` will match `Lunch Plan`

Examples:

* `find Lunch`<br>
  Returns `Lunch Plan` but not `lunch`
* `find Breakfast Lunch Dinner`<br>
  Returns any task having names `Breakfast`, `Lunch`, or `Dinner`

### 2.6. Deleting a task : `delete`

Deletes the specified task from the todolist. <br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
    The index refers to the index number shown in the last task listing.
    Index **must include the type of task**, e.g. f1 for 1st float task and **must be followed by a positive integer** 1, 2, 3, ... <br>

Examples:

* `list`<br>
  `delete f2`<br>
  Deletes the 2nd float task in the todolist.

### 2.7. Select a tasks: `select`

Selects the tasks identified by the index number used in the last task listing.<br>
Format: `select INDEX...`

> Selects the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
    The index refers to the index number shown in the last task listing.
    Index **must include the type of task**, e.g. f1 for 1st float task and **must be followed by a positive integer** 1, 2, 3, ... <br>

Examples:

* `list`<br>
  `select e2`<br>
  Selects the 2nd event task in the todolist.

### 2.8. Undoing previous command : `undo`

Undo previous command
Format: `undo`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous todolist folder.

## 4. Command Summary

* **Add**  `add TITLE /venue /from /to /level /description #[TAG]` <br>
  e.g. `add Do Tutorial /venue CLB /from today 3pm /to today 5pm /level 2 /description for week13 #CS2103`

* **Delete** : `delete INDEX` <br>
   e.g. `delete f3`

* **Edit** : `edit INDEX [TITLE] [/venue] [/from] [/to] [/level] [/description] [#TAG]...` <br>
e.g. `edit f7 buy calculator`

* **Exit** : `exit` <br>
e.g.

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find tutorial quiz`

* **Help** : `help` <br>
  e.g.

* **List** : `list` <br>
  e.g.

* **ListTag** : `listtag` <br>
e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`

* **Undo** : `undo` <br>
e.g. 'undo'
