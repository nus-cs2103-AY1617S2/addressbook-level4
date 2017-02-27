# TuLuist - User Guide

By : `Team TuLuist`  &nbsp;&nbsp;&nbsp;&nbsp;

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [Command Summary](#command-summary)

## 1. Quick Start

1. Double-click the file to start the app. The GUI should appear in a few seconds.

2. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
3. Some example commands you can try:
   * **`add`**` Try Out Todo List  d/11-12-2011` :
     adds a task named `Try Out Todo List` to the todo list.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
4. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.0. Viewing tasks

> * A list of tasks will always be displayed.
> * When starting the program, the list will show all tasks which are currently not completed.
> * When performing find operations, this list will be updated to show only the results searched for.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task to the todo list<br>
Format: `add NAME [d/ENDDATE] [sd/STARTDATE] [r/PERIOD(DAY/WEEK/MONTH)] [t/TAG]...`

> Tasks can have any number of tags (including 0)

Examples:

* `add Do Homework`
* `add Meeting With Boss d/11-11-2011 19:30 sd/11-11-2011 17:30`
* `add Check Email d/today r/day`

### 2.3. Updating a task : `update`

Updates an existing task in the todo list.<br>
Format: `update INDEX [NAME] [sd/STARTDATE] [d/ENDDATE] [r/PERIOD(DAY/WEEK/MONTH)] [t/TAG]...`

> * Updates the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * If no optional fields are added, task will be set to completed if not completed.
> * Otherwise, if task is completed, the task will be set to not completed
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed if it already exists.
> * Otherwise, if the tag does not exist, the tag will be added.

Examples:

* `update 1`<br>
  Updates the 1st task to be 'completed' if not completed. If it was already completed, update the task to 'not completed'.

* `update 2 Assignment 3 t/schoolwork`<br>
  Updates the name of the 2nd task to be `Assignment 3` and adds the tag 'schoolwork' if it did not exist. If it already existed, removes the tag 'schoolwork'.

### 2.4. Finding all tasks containing any keyword in their name or tag or date: `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `find [KEYWORDS] [t/] [d/] [n/]`

> * The search is case insensitive. e.g `hans` will match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * By default only the name is searched.
> * Adding 't/' will search by tag instead.
> * Adding 'd/' will search by end date instead.
> * Adding 'n/' will still search by name.
> * If no keyword is entered, the list of all tasks is displayed.
> * 't/', 'd/' and 'n/' can be combined to search by multiple fields
> * Partial words will be matched e.g. `Han` will match `Hans`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find Assignment`<br>
  Returns `Assignment 1` but not `assignment 2`
* `find Assignment Project Tutorial`<br>
  Returns any task having names `Assignment`, `Project`, or `Tutorial`
* 'find school t/' <br>
  Returns any task with tags with 'school' in the name

### 2.5. Deleting a task : `delete`

Deletes the specified task from the todo list. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the todo list.
* `find Project`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.6. Clearing all entries : `clear`

Clears all entries from the todo list.<br>
Format: `clear`

### 2.7. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.8. Undo a command : `undo`

Undoes previous commands by the user.<br>
Format: `undo [NUMBER]`

> Undoes the last data-mutating command inputted by the user.<br>
> If a number is entered, undoes that ammount of previous commands instead.<br>
> The number **must be a positive integer** 1, 2, 3, ...

Examples:

* `add Test`<br>
  `undo`<br>
  Undoes adding Test to the todo list.
* `add Assignment` <br>
  `add Project`
  `undo 2`<br>
  Undoes both commands.

### 2.9. Redo a command : `redo`

Redoes previously undone commands by the user.<br>
Format: `redo [NUMBER]`

> Redoes the last data-mutating command inputted since the undone point.<br>
> If a number is entered, redoes that ammount of previous commands instead.<br>
> The number **must be a positive integer** 1, 2, 3, ...
> The number must be less than or equal to the number of commands undone.

Examples:

* `add Test`<br>
  `undo`<br>
  `redo`
  Redoes adding Test to the todo list.
* `add Assignment` <br>
  `add Project`<br>
  `undo 2`<br>
  `redo`<br>
  Redoes `add Assignment`.

### 2.10. Saving the data

todo list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.11. Viewing previous commands and accessing them : 'history'

Shows previous commands entered
Format: 'history'

> * previous commands are listed in order from latest command to earlier command
> * alternatively, pressing on the 'up' and 'down' arrow keys on the keyboard will cycle through the commands previously entered

Examples:
* 'add Test' <br>
  'history' <br>
  Shows 'add Test' in the list.
* 'add Test' <br>
  you press on the 'up' arrow key. <br>
  Shows 'add Test' in your input text field.

### 2.12. Add alias for commands: 'add alias'

Adds an alias for a command
Format: 'addalias COMMAND ALIAS'

> * Once added, alias can be used instead of the command to perform operations.

Example:
* 'addalias history hs' <br>
  'hs' <br>
  Shows 'addalias history hs' in the list.

### 2.13. Delete alias for commands: 'delete alias'

Removes an alias for a command
Format: 'deletealias ALIAS'

### 2.14. View aliases for commands: 'view alias'

Views aliases in the system
Format: 'viewalias [COMMAND]'

> * Inputting a value for command will only show all aliases for that command.
> * Otherwise will show all aliases.
> * Lists aliases in the format 'COMMAND:ALIAS'.

Example:
* 'addalias history hs' <br>
  'viewaliases' <br>
  Shows 'history:hs' in the list.

### 2.15. Change storage location for data: 'change storage location'

Changes the location for the storage file used in this system
Format: 'save NEWFILELOCATION'

> * All data will be moved to the new file location.
> * If the file does not exist, the file will be created.
> * The old file will be removed.

Example:
* 'setstoragelocation data/savefile.txt'
  Sets the storage location to 'data/savefile.txt' 

## 3. Command Summary

* **Add**  `add NAME [d/ENDDATE] [sd/STARTDATE] [r/PERIOD(DAY/WEEK/MONTH)] [t/TAG]...` <br>
  e.g. `add Assigment 1 d/Friday t/school`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find [KEYWORDS] [t/] [d/] [n/]` <br>
  e.g. `find school t/`

* **List** : `list`

* **Help** : `help`

* **Select** : `select INDEX` <br>
  e.g.`select 2`

* **Update** : '`update INDEX [NAME] [d/ENDDATE] [sd/STARTDATE] [r/PERIOD(DAY/WEEK/MONTH)] [t/TAG]...` <br>
  e.g. `update 1 d/11/12/2011`

* **Exit** : `exit`

* **Undo** : `undo [NUMBER]` <br>
  e.g. `undo 5`

* **Redo** : `redo [NUMBER]` <br>
  e.g. `redo 5`

* **History** : `history`

* **Add Alias** : `addalias COMMAND ALIAS` <br>
  e.g. `addalias history hs`

* **Delete Alias** : `deletealias ALIAS` <br>
  e.g. `deletealias hs`

* **View Aliases** : `viewalias [COMMAND]` <br>
  e.g. `view alias history`

* **Change Storage Location** : `save FILELOCATION`