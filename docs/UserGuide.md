# AddressBook Level 4 - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `tasklist.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`** add CS2010 PS3 d/problem set 3 t/03-08 p/3 :
     adds a task named CS2010 PS3 to the TaskList.
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
Format: `add NAME d/DESCRIPTION t/TIME p/PRIORITY [tg/TAG]...`

> Persons can have any number of tags (including 0)

Examples:

* `add CS2010 PS3 d/problem set 3 t/03-08-17 p/3 tg/CS2010`
* `add Read handout d/read CS2103 L6 handout t/03-01-17 p/2 tg/CS2103`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the task list.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the task list.<br>
Format: `edit INDEX [NAME] [d/DESCRIPTION] [t/TIME] [p/PRIORITY] [tg/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `tg/` without specifying any tags after it.

Examples:

* `edit 1 p/3 t/10-03-17`<br>
  Edits the priority and time of the 1st task to be `3` and `10-13-17` respectively.

* `edit 3 Project Discussion t/`<br>
  Edits the name of the 3rd task to be `Project Discussion` and clears all existing tags.

### 2.5. Finding all tasks containing any keyword in their name or description: `find`

Finds tasks whose names or descriptions contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `discussion` will match `Discussion`
> * The order of the keywords does not matter. e.g. `Project Discussion` will match `Discussion Project`
> * Only the name and description are searched.
> * Only full words will be matched e.g. `Project` will not match `Projects`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Project` will match `Project Discussion`

Examples:

* `find project`<br>
  Returns `Project Discussion`
* `find project discussion`<br>
  Returns Any tasks having description `project` or `discussion`

### 2.6. Finding all tasks happening on a certain day: `date`

Finds tasks which happen on the given date.<br>
Format: `date DATE`

> * DATE should have format `DD-MM-YY`
> * If only `DD-MM` is entered, year is set to be the current year.
> * If only `DD` is entered, month is set to be the current month.

Examples:

* `date 23-04`<br>
  Returns all tasks happening on 23rd April 2017.

### 2.7. Deleting a task : `delete`

Deletes the specified task from the task list. Undoable.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task list.
* `find exam`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.8. Select a person : `select`

Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the address book.
* `find Betsy` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

### 2.9. Clearing all entries : `clear`

Clears all entries from the task list.<br>
Format: `clear`

### 2.10. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.11. Saving the data

Task list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task List folder.

## 4. Command Summary

* **Add**  `add NAME d/DESCRIPTION t/TIME p/PRIORITY [tg/TAG]...` <br>
  e.g. `add Read handout d/read CS2103 L6 handout t/03-01-17 p/2 tg/CS2103`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
  e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find exam cs2103`

* **List** : `list` <br>

* **Help** : `help` <br>

* **Select** : `select INDEX` <br>
  e.g. `select 2`
