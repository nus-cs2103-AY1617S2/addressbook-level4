# DoTomorrow - User Guide

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

1. Download the latest `doTomorrow.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your TODO list.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all TODOs
   * **`add`** : `Email manager due/08/01/2017 0900`: adds a task "Email manager" to the TODO list and set the due date to January 8th at 9am.
   * **`complete`** `2` : marks the 2nd task as completed
   * **`delete`** `3` : deletes the 3rd task shown in the current list
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

Shows information about how to use DoTomorrow.<br>
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task : `add`

Adds a task to the list<br>
Format: `add TASK_DESCRIPTION [due/DATE_AND_TIME] [starts/DATE_AND_TIME ends/DATE_AND_TIME]`

Examples:

* `add CS2103 assignment due/23/03/2017 1600 starts/22/03/2017 ends/23/03/2017 1800`
* `add CS2013 assignment due/23/03/2017`

### 2.3. Listing: `list`

Shows a list of all tasks.<br>
Format: `list`

### 2.3.1. Listing completed tasks: `list complete`

Shows a list of all completed tasks.<br>
Format: `list complete`

### 2.3.2. Listing incomplete tasks: `list incomplete`

Shows a list of all incomplete tasks.<br>
Format: `list incomplete`

### 2.3.3. Sort current list : `list by`

Sort the current task list in a particular order.<br>
Format: `list by SORTING_CRITERIA`<br>
`SORTING_CRITERIA` may be:

* `added`
* `due`
* `starts`
* `ends`

Examples:

* `list by added`
* `list by due`

### 2.4. Editing a task : `edit`

Edits an existing task in the list.<br>
Format: `edit INDEX [NEW_TASK_DESCRIPTION] [PARAMETER1/VALUE1] [PARAMETER2/VALUE2] ...` <br>

> * Edits the task at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, ...
> * New description must follow index if necessary
> * New value should follow the parameter and separated by a '/'
> * Existing value will be updated to the input value.
> * Existing value removed if no value follow parameter
> * Can update multiple parameters in one command (see examples below)
> * New values must be different from existing value
> * At least one property (description, due date, start date, etc.) must be specified
> * If a task does not have a start and end time, both start and end times must be edited at the same time

Examples:

* `edit 1 due/20/03/2017`<br>
  Edits the due date of the 1st item to 20/03/2017.

* `edit 2 buy milk t/groceries`<br>
  Edits the description of the 2nd to read “buy milk” and change tags to "groceries".

* `edit 1 t/hipri starts/01/01/2016 1230 ends/01/01/2017 1230` <br>
Edits the tag, duration start, and duration end for the 1st task.

* `edit 1 t/` <br>
  Removes all tags from the 1st task

### 2.5. Completing a task : `complete`

Marks a task as completed.<br>
Format: `complete INDEX` <br>

> * Completes the task at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, ...
> * Marks the task as complete.

Examples:

* `complete 1`<br>
  Marks the 1st task as complete.
  
### 2.6. Uncompleting a task : `uncomplete`

Marks a task as incomplete.<br>
Format: `uncomplete INDEX` <br>

> * Uncompletes the task at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, ...
> * Marks the task as incomplete.

Examples:

* `uncomplete 1`<br>
  Marks the 1st task as incomplete.

### 2.7. Deleting a task : `delete`

Deletes the specified task. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `delete 2`<br>
  Deletes the 2nd task.

### 2.8. Delete all tasks : `clear`

Deletes all tasks.<br>
Format: `clear`

### 2.9. Finding all tasks containing any keyword in their description: `find`

Finds tasks whose descriptions contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The order of the keywords does not matter. e.g. `buy milk` will match `milk buy`
> * The description and tags will be searched.
> * Partial words will be matched e.g. `day` will match `monday`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `milk` will match `buy milk`

Examples:

* `find milk`<br>
  Returns `Buy milk` but not `Milk`
* `find home school hipri`<br>
  Returns Any task having description or tag `home`, `school`, or `hipri`

### 2.10. Undo : `undo`

Undoes the previous action. This command will return the program to the state it was in before the previous action was executed<br>
Format: `undo`

### 2.11. Redo : `redo`

Re-executes the previous undo action. This command will return the program to the state it was in before the previous undo was executed<br>
Format: `redo`

### 2.12. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.13. Saving the data

DoTomorrow data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.14. Changing storage file path: `setstorage`

Change storage path to user specifed path<br>
Format: `setstorage PATH`

Example:

* `setstorage /Users/Jim/Documents` will store the to-do list file to `/Users/Jim/Documents`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous DoTomorrow folder.

**Q**: I found a bug in the program, how do I report it?<br>
**A**: Please create a new issue via this [link](https://github.com/CS2103JAN2017-T11-B4/main/issues) with a detailed title and description. Please also include [BUG] in the beginning of the title.

**Q**: I am a developer. How can I contribute to the project?<br>
**A**: Please feel free to open a pull request with implementation of new features. Our only request is that you review the [developer guide](https://github.com/CS2103JAN2017-T11-B4/main/blob/developer-guide/docs/DeveloperGuide.md) and abide to all the requirements.

**Q**: Is it possible for me to change the appearance of the application?<br>
**A**: At the moment, there are no ways to edit the theme of the application.

## 4. Command Summary

* **Help**: `help` <br>
  e.g. `help`

* **Add**  `add TASK_DESCRIPTION [due/DATE_AND_TIME] [starts/DATE_AND_TIME ends/DATE_AND_TIME]` <br>
  e.g. `add CS2103 assignment due/23/03/2017 1600 starts/22/03/2017 ends/23/03/2017 1800`

* **List All Tasks**: `list` <br>
  e.g. `list`

* **List Completed Tasks**: `list complete` <br>
  e.g. `list complete`
  
* **List Incomplete Tasks**: `list incomplete` <br>
  e.g. `list incomplete`

* **List By (i.e. Sorting)**: `list by SORTING_CRITERIA` <br>
  e.g. `sort by due`

* **Edit**  `edit INDEX PARAMETER/NEW_VALUE` <br>
  e.g. `edit 2 due/23/03/2017 1200`

* **Complete** `complete INDEX` <br>
  e.g. `complete 2`
  
* **Uncomplete** `uncomplete INDEX` <br>
  e.g. `uncomplete 2`

* **Delete** `delete INDEX` <br>
  e.g. `delete 3`

* **Delete all** `clear` <br>
  e.g. `clear`

* **Find**: `find KEYWORD [MORE_KEYWORDS]...` <br>
  e.g. `find James Jake`

* **Undo**: `undo` <br>
  e.g. `undo`

* **Redo**: `redo` <br>
  e.g. `redo`

* **Set storage path**: `setstorage PATH` <br>
  e.g. `setstorage /Users/Jim/Documents`

* **Exit**: `exit` <br>
  e.g. `exit`
