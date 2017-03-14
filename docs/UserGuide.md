# ToLuist - User Guide

By : `Team ToLuist`  &nbsp;&nbsp;&nbsp;&nbsp;

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [Command Summary](#command-summary)

## 1. Quick Start

1. Double-click the file to start the app. The GUI should appear in a few seconds.

    <img src="images/Ui.png" width="600"><br>
    **Figure 1.** Initial launch screen of ToLuist

2. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
3. Some example commands you can try:
   * **`add`**` Try Out Todo List` :
     adds a task named `Try Out Todo List` to the todo list.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list.
   * **`exit`** : exits the app.
4. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.
> * Options with '/' allow either word to be used.

### 2.0. Viewing tasks
Viewing all tasks in the todo list<br>
Format: `list`
> * A list of tasks will always be displayed.
> * When starting the program, the list will show all tasks which are currently not completed.
> * When performing find operations, this list will be updated to show only the results searched for.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command. e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task to the todo list<br>
Format: `add NAME [startdate/STARTDATE] [enddate/ENDDATE]`

Examples:

* `add Do Homework` <br>
  Adds a task called 'Do Homework'.
* `add Meeting With Boss startdate/11-11-2011 17:30 enddate/11-11-2011 19:30` <br>
  Adds a task called 'Meeting With Boss', set to begin 11-11-2011 17:30, and the end date to be 11-11-2011 19:30.
* `add Check Email enddate/today` <br>
  Adds a task called 'Check Email', sets the end date to be today's date.

### 2.3. Updating a task : `update`

Updates an existing task in the todo list.<br>
Format: `update INDEX [NAME] [startdate/STARTDATE] [enddate/ENDDATE]`

> * Updates the task at the specified `INDEX`. <br>
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * Existing values will be updated to the input values.

Examples:

* `update 2 Assignment 3`<br>
  Updates the name of the 2nd task to be `Assignment 3`.
* `update 3 startdate/today enddate/tomorrow`
  Updates the start date and end date of the 3rd task to today and tomorrow respectively.

### 2.4. Filter all tasks containing any keyword in their name or tag or date: `filter`

Finds tasks whose names contain any of the given keywords.<br>
Format: `filter/list/find [KEYWORDS] [tag/] [name/]`

> * The search is case insensitive. e.g `hans` will match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * By default only the name is searched.
> * Adding 'tag/' will search by tag instead.
> * Adding 'name/' will still search by name.
> * If no keyword is entered, the list of all tasks is displayed.
> * 'tag/' and 'name/' can be combined to search by multiple fields.
> * Partial words will be matched e.g. `Han` will match `Hans`.
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find Assignment`<br>
  Returns `Assignment 1` and also `assignment 2`.
* `find Assignment Project Tutorial`<br>
  Returns any task having `Assignment`, `Project`, or `Tutorial` in their names.
* `find school tag/` <br>
  Returns any task with tags with 'school' in the name.

### 2.5. Deleting a task : `delete`

Deletes the specified task from the todo list. Reversible with `undo` command.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...<br>
> Supports deletion of multiple indexes in a single command, by best effort matching.

Examples:

* `delete 2`<br>
  Deletes the 2nd task in the todo list.
* `delete 3 - 6`<br>
  Deletes the 3rd, 4th, 5th, and 6th task in the todo list.<br>
  Note that the system is whitespace insensitive so `delete 3-6` or `delete 3- 6` would work as well.
* `delete 3 -`<br>
  Deletes from 3rd to last in the todo list.
* `delete - 5`<br>
  Deletes from first to 5th task in the todo list.
* `delete 5, - 3, 7-8 10, 12 -`<br>
  Deletes from 1st to 3rd, 5th, 7th, 8th, 10th, and from 12th to last task in the todo list.

### 2.6. Clearing all entries : `clear`

Clears all entries from the todo list.<br>
Format: `clear`

### 2.7. Exiting the program : `exit`

Exits the program.<br>
Format: `exit/quit`

### 2.8. Undo a command : `undo`

Undoes previous commands by the user.<br>
Format: `undo [NUMBER]`

> Undo the last data-mutating command inputted by the user.<br>
> If a number is entered, undoes that ammount of previous commands instead.<br>
> The number **must be a positive integer** 1, 2, 3, ...

Examples:

* `add Test`<br>
  `undo`<br>
  Undo adding Test to the todo list.
* `add Assignment` <br>
  `add Project` <br>
  `undo 2`<br>
  Undo both commands.

### 2.9. Redo a command : `redo`

Redo previously undone commands by the user.<br>
Format: `redo [NUMBER]`

> Redo the last data-mutating command inputted since the undone point.<br>
> If a number is entered, redo that ammount of previous commands instead.<br>
> The number **must be a positive integer** 1, 2, 3, ...
> The number must be less than or equal to the number of commands undone.

Examples:

* `add Test`<br>
  `undo`<br>
  `redo` <br>
  Redo adding Test to the todo list.
* `add Assignment` <br>
  `add Project`<br>
  `undo 2`<br>
  `redo`<br>
  Redo `add Assignment`.

### 2.10. Saving the data

Todo list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.11. Viewing previous commands and accessing them : `history`

Shows previous commands entered. <br>
Format: `history`

> * Previous commands are listed in order from latest command to earlier command.
> * Alternatively, pressing on the <kbd>up</kbd> and <kbd>down</kbd> arrow keys on the keyboard will cycle through the commands previously entered.

Examples:
* `add Test` <br>
  `history` <br>
  Shows `add Test` in the list.
* `add Test` <br>
  You press on the <kbd>up</kbd> arrow key. <br>
  Shows `add Test` in your input text field.

### 2.12. Add alias for any phrase: `alias`

Adds an alias for a phrase. <br>
Format: `alias ALIAS PHRASE`

> * Once added, alias can be used instead of the phrase to perform operations.
> * The phrase can be multiple words long.

Example:
* `alias hs history` <br>
  `hs` <br>
  Shows `alias hs history` in the list.
* `alias addTaskNamedTest add Test` <br>
  `addTaskNamedTest` <br>
  Performs the command `add Test` which will add a new task called 'Test'.


### 2.13. Delete alias for commands: `unalias`

Removes an alias for a command. <br>
Format: `unalias ALIAS`

Example:
* `alias hs history` <br>
  `unalias hs` <br>
  Removes the alias 'hs'.

### 2.14. View aliases for commands: `viewalias`

Views aliases in the system. <br>
Format: `viewalias`

> * Lists aliases in the format `ALIAS:PHRASE`.

Example:
* `alias hs history` <br>
  `viewalias` <br>
  Shows `hs:history` in the list.

### 2.15. Change storage location for save data: `save`

Changes the location for the storage file used in this system. <br>
Warning: If a file with the requested name already exists, it will be overwritten. <br>
Format: `save NEWFILELOCATION`

> * All data will be moved to the new file location.
> * If the file does not exist, the file will be created.
> * The old file will be removed.

Example:
* `save data/savefile.txt` <br>
  Sets the save storage location to `data/savefile.txt`.

### 2.16. Change storage location for load data: `load`

Changes the location for the storage file used in this system. <br>
Format: `load NEWFILELOCATION`

> * The new save location will be updated to the location.
> * The program will replace the data in the program with data from the new file location.
> * If the file does not exist, an error message will be displayed.
> * Warning: The old data in the program will stay in the old save file, and will not be updated with new values.

Example:
* `load data/savefile.txt` <br>
  Sets the load storage location to `data/savefile.txt`.

### 2.17. Switch Display Task Window : `switch`

Changes the displayed task list.
Format: `switch WINDOWIDENTIFIER`

> * If a number is given for window identifier, that will be the number of the tab from the left which is selected.
> * If a letter is given, it will be the underlined letter in the window list name.
> * If a word is given, it will be the word with the underlined letter in the window list name.

Example:
* `switch 2` <br>
  Switches the displayed view to 'Today'.
* `switch T` <br>
  Switches the displayed view to 'Today'.

### 2.18. Complete or Make Incomplete a Task : `mark`

Marks a task to be complete or incomplete.
Format: `mark [complete/incomplete] INDEX`

> * Using complete as a parameter will mark the selected task as complete.
> * Using incomplete as a parameter will mark the selected task as incomplete.
> * Using neither will default the command to mark as complete.

Example:
* `mark complete 1` <br>
  Marks task 1 as complete.
* `mark incomplete 2` <br>
  Marks task 2 as incomplete.
* `mark 3` <br>
  Marks task 3 as complete.

### 2.19. Add a Tag to a Task : `tag`

Adds a tag or multiple tags to an existing task.
Format: `tag INDEX TAG...`

> * If the tag already exists, the command will notify you and do nothing.
> * If multiple tags are used in the command, you will be notified of each one.

Example:
* `tag 1 school` <br>
  Adds the tag 'school' to task 1.
* `tag 2 work home` <br>
  Adds the tags 'work' and 'home' to task 2.

### 2.20. Remove a Tag from a Task : `untag`

Removes a tag or multiple tags from an existing task.
Format: `untag INDEX TAG...`

> * If the tag already does not exist, the command will notify you and do nothing.
> * If multiple tags are used in the command, you will be notified of each one.

Example:
* `untag 1 school` <br>
  Removes the tag 'school' from task 1.
* `untag 2 work home` <br>
  Removes the tags 'work' and 'home' from task 2.

## 3. Command Summary

* **Add** : `add NAME [enddate/ENDDATE] [startdate/STARTDATE] [recurring/PERIOD(day/week/month)] [priority/PRIORITY] [tag/TAG]...` <br>
  e.g. `add Assigment 1 enddate/Friday tag/school`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Filter** : `filter/list/find [KEYWORDS] [tag/] [name/]` <br>
  e.g. `find school tag/`

* **Help** : `help`

* **Update** : `update INDEX [name/NAME] [enddate/ENDDATE] [startdate/STARTDATE] [recurring/PERIOD(day/week/month)] [priority/PRIORITY] [tag/TAG]...` <br>
  e.g. `update 1 enddate/11/12/2011`

* **Exit** : `exit/quit`

* **Undo** : `undo [NUMBER]` <br>
  e.g. `undo 5` <br>
  e.g. `undo`

* **Redo** : `redo [NUMBER]` <br>
  e.g. `redo 5` <br>
  e.g. `redo`

* **History** : `history`

* **Add Alias** : `alias ALIAS PHRASE` <br>
  e.g. `alias hs history`

* **Delete Alias** : `unalias ALIAS` <br>
  e.g. `unalias hs`

* **View Aliases** : `viewalias`

* **Change Save Storage Location** : `save FILELOCATION` <br>
  e.g. `save data/savefile.txt`

* **Change Load Storage Location** : `load FILELOCATION` <br>
  e.g. `load data/savefile.txt`

* **Switch Display Task Window** : `switch WINDOWIDENTIFIER` <br>
  e.g. `switch 2` <br>
  e.g. `switch T`

* **Complete or Make Incomplete a Task** : `mark [complete/incomplete] INDEX` <br>
  e.g. `mark complete 1` <br>
  e.g. `mark incomplete 2` <br>
  e.g. `mark 3`

* **Add a Tag to a Task** : `tag INDEX TAG...` <br>
  e.g. `tag 1 school` <br>
  e.g. `tag 3 work home`

* **Remove a Tag from a Task** : `untag INDEX TAG...` <br>
  e.g. `untag 1 school` <br>
  e.g. `untag 3 work home`
