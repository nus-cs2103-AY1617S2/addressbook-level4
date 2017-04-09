# Yesterday's Tomorrow - User Guide

By : `Team CS2103JAN2017-F11-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `YTomorrow.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your task list.
3. Double-click the file to start the app. The GUI should appear in a few seconds.

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Econ hw d/2017/05/22 0700 : adds a task "Econ hw" to the task list and set the due date to May 22nd at 7am.
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

Shows information about how to use Yesterday's Tomorrow.
The page is a real-time html version of this file.
Format: `help`
Hotkey: `CTRL+H`

### 2.2. Opening the theme manager : `theme`

Displays the theme manager of the program.
Format: `theme`
Hotkey: `CTRL+T`

### 2.3. Adding a task: `add`

Adds a task to the task list<br>
Format: `add TASK_NAME [from START TIME] [to END TIME] [in GROUP]`

> Persons can add a task without defining either start time or end time.
> local time will be set as default if start time and end time parameters do not specify time .
> TASK_NAME should only contain alphanumeric characters and spaces, and it should not be blank.

Examples:

* **Normal task**: `add CS2103 assignment from Feb 3 to Apr 6 in study`
* **Task with deadline**: `add CS2013 assignment to tomorrow in study`
* **Task with deadline**: `add CS2013 assignment from Monday in study`
* **Floating task**: `add CS2013 assignment in study`

### 2.4. Listing all tasks : `list`

Shows a list of all tasks (**completed** or **incompleted**).<br>
Format: `list`

> The task list will be ordered from earliest to latest end date.

### 2.5. Listing complete tasks : `lc`

Shows a list of all **completed** tasks.<br>
Format: `lc`

> The task list will be ordered from earliest to latest end date.

### 2.6. Listing incomplete tasks : `li`

Shows a list of all **incomplete** tasks.<br>
Format: `li`

> The task list will be ordered from earliest to latest end date.

### 2.7. Editing a task : `edit`

Edits an existing task in the task list.<br>
Format: `edit INDEX PARAMETER NEW_VALUE` <br>

> * Edits the task at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, ...
> * One parameter and updated value needs to be provided.
> * Existing value will be updated to the input value.

Examples:

* `edit 1 newName`<br>
  Edits the name of the 1st item to "newName".

* `edit 2 g/weekend`<br>
  Edits the group of the 2nd task to "weekend".

### 2.8. Marking a task as complete : `mark`

Changes the status of one task to **complete**.
Format: `mark INDEX` <br>

> * Mark the task at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `mark 1`<br>
  Marks the 1st task as completed.
  
### 2.9. Marking a task as incomplete : `unmark`

Marks a task in current task list as incomplete.
Format: `unmark INDEX` <br>

> * Unmark the task at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `unmark 1`<br>
  Marks the 1st task as incomplete.

### 2.10. Deleting a task : `delete`

Deletes the specified task. Reversible with `undo` <br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task.
* `find milk`<br>
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.
  
### 2.11. Clear all tasks : `clear`

Clear all tasks or only completed task in the list. <br>
Format: `clear [KEYWORD]`

> Keyword can just be **all**, **passed** or **complete**. <br>

Examples:

* `clear`<br>
  Clear all tasks.
* `clear all`, <br>
  `clear incomplete`, <br>
  `clear complete`<br>
  Clear all completed tasks.

### 2.12. Finding all tasks containing keyword in their task name: `find`

Finds tasks whose name contain any of the given keywords.<br>
Format: `find KEYWORD`

> * The search is not case-sensitive. e.g `milk` **will** match `Milk`
> * The order of the keywords does not matter. e.g. `buy milk` will match `milk buy`
> * The title and description is searched.
> * Only full words will be matched e.g. `day` will not match `monday`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `milk` will match `buy milk`

Examples:

* `find John`<br>
  Returns `John Doe` and `john`
* `find Betsy Tim John`<br>
  Returns Any person having names `Betsy`, `Tim`, or `John`

### 2.13. Undo : `undo`

Undoes the previous action. This command will return the program to the state it was in before the previous action was executed<br>
Format: `undo`

### 2.14. Redo : `redo`

Does the undo function again. This command will return the program to the state it was in before the previous undo action was executed<br>
Format: `redo`

### 2.15. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`
Hotkey: `CTRL+Q`

### 2.16. Saving data

Prompts a new window to select a file to save, and all further changes are saved to this file.
Hotkey: `CTRL+S`

### 2.16. Opening data

Prompts a new window to select a file to load from, and all further changes are saved to this file.
Hotkey: `CTRL+O`

### 2.16. Exporting data

Prompts a new window to select a file to save the current data to.
Hotkey: `CTRL+E`

### 2.16. Importing data

Prompts a new window to select a file to import data from. 
This adds any new tasks to the current file, and updates any with the same name.
Hotkey: `CTRL+I`

### 2.17 Hotkeys

* `CTRL+S` - Save As
* `CTRL+O` - Open
* `CTRL+E` - Export
* `CTRL+I` - Import
* `CTRL+Q` - Exit
* `CTRL+H` - Help
* `CTRL+T` - Theme Manager
* `ENTER` - In the Help and Theme Manager windows, closes them
* `UP`, DOWN` In the theme manager window to cycle between themes

### 2.18 Data Initialization
Upon starting for the first time, the program randomly generates and displays 50 tasks.
Upon saving, loading, or modifying the data, the program saves the file location and loads this on startup if found.

### 2.19 Themes
YTomorrow has a built in theme manager that displays choosable css themes for the program and sets the selected one.
Hotkey: `CTRL+T`

### 2.20 Clearing Data 

Command clears all tasks that are either:
* `clear all`: all tasks
* `clear passed`: all tasks past deadline
* `clear complete`: all completed tasks
Format: `clear [all/complete/passed]`

### 2.21 Completion Indication

Whether a task is complete or not is indicated by a light green or red border around the task, respectively.

### 2.22 Past Deadline Indication

A task's deadline has passed when the task's background is red.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and ask the program to load the transferred file.
 **Q**: I found a bug in the program, how do I report it?<br>
 **A**: Please create a new issue via this [link](https://github.com/CS2103JAN2017-T11-B4/main/issues) with a detailed title and description. Please also include [BUG] in the beginning of the title.

**Q**: I am a developer. How can I contribute to the project?<br>
**A**: Please feel free to open a pull request with implementation of new features. Our only request is that you review the [developer guide](https://github.com/CS2103JAN2017-T11-B4/main/blob/developer-guide/docs/DeveloperGuide.md) and abide to all the requirements.

**Q**: Is it possible for me to change the appearance of the application?<br>
**A**: Yes, using the theme manager via the `theme` command or `CTRL+T`.

## 4. Command Summary

* **Help**: `help` <br>
  e.g. `help`, `CTRL+H`
  
  **Theme**: `theme` <br>
  e.g. `theme`, `CTRL+T`

* **Add**  `add TASK_NAME [s/START TIME] [d/END TIME] e/EMAIL g/GROUP`
  e.g. `add CS2103 assignment from Feb 3 to Apr 6 in study` <br>
       `add CS2013 assignment to tomorrow in study` <br>
       `add CS2013 assignment from Monday in study` <br>
       `add CS2013 assignment in study` <br>
	   
* **List All**: `list` <br>
  e.g. `list`
  
* **List Complete**: `lc` <br>
  e.g. `lc`
  
* **List Incomplete**: `li` <br>
  e.g. `li`

* **Clear All**: `clear all` <br>
  e.g. `clear all`
  
* **Clear Complete**: `clear complete` <br>
  e.g. `clear all`
  
* **Clear Passed**: `clear passed` <br>
  e.g. `clear passed`

* **Edit**: `edit INDEX PARAMETER NEW_VALUE` <br>
  e.g. `edit 2 g/learning`
  
* **Mark**: `mark INDEX` <br>
  e.g. `mark 1`

* **Unmark**: `unmark INDEX` <br>
  e.g. `unmark 2`

* **Delete**: `delete INDEX` <br>
  e.g. `delete 1`

* **Find**: `find KEYWORD ` <br>
  e.g. `find tutorial`

* **Undo**: `undo` <br>
  e.g. `undo`

* **Redo**: `redo` <br>
  e.g. `redo`
  
* **Exit**: `exit` <br>
  e.g. `exit`, `CTRL+Q`
