# ToLuist - User Guide

By : `Team ToLuist`  &nbsp;&nbsp;&nbsp;&nbsp;

---

1. [Introduction](#introduction)
1. [Quick Start](#quick-start)
2. [Features](#features)
3. [Command Summary](#command-summary)

## 1. Introduction

Have you ever felt overloaded with work? Don't know where to start?<br>
Introducing ToLuist, the answer to all of your problems!<br>
ToLuist is an application which will help you to manage all your tasks, allowing you to sort out your life.<br>
ToLuist is designed with you in mind, ensuring that you are able to focus on what is important to you.

## 1. Quick Start

1. Double-click the file to start the app. The GUI should appear in a few seconds.

    <img src="images/Ui.png" width="600"><br>
    **Figure 1.** Initial launch screen of ToLuist

2. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
3. Try out some example commands:
   * **`add`**` Try Out Todo List` :
     adds a task named `Try Out Todo List` to the todo list.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list.
   * **`exit`** : exits the app.
4. Refer to the [Features](#features) section below for details of each command.<br>

## 2. Features

ToLuist is focused towards users who like to type.<br>
All the features of the application can be accessed through the use of a keyboard, by entering commands into the on-screen text field.<br>

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.
> * Options with '/' allow either word to be used.

### 2.0. Viewing tasks

View all tasks in the todo list<br>
Format: `list`
> * A list of tasks will always be displayed.
> * When starting the program, the list will show all tasks which are currently not completed.
> * When performing `filter` operations, this list will be updated to show only the results searched for.

### 2.1. Viewing help : `help`

Shows commands which are in the system.<br>
Format: `help`

> * Help is also shown if you enter an incorrect command.

### 2.2. Adding a task: `add`

Adds a task to the todo list<br>
Format: `add NAME [startdate/STARTDATE] [enddate/ENDDATE] [recurring/PERIOD(day/week/month)] [priority/PRIORITY] [tag/TAG]...`

> * Both 'startdate' and 'enddate' use the same datetime format.
> * 'startdate' requires a valid 'enddate' to be used in the same command.
> * The values entered for 'startdate' and 'enddate' use the Natty parser to determine the final value:<br>
    Formal dates are parsed, with the month being before the day. i.e. `MM/DD/YY`, `MM/DD/YYYY`, `YYYY/MM/DD`, `YYYY/MM/DD`<br>
    Relaxed dates are parsed as logically as possible. i.e. `Jan 21, '97`, `Sun, Nov 21`, `The 31st of April in the year 2017`<br>
    Relative dates are also allowed. i.e. `Yesterday`, `Today`, `Next Sunday`, `3 Days from now`<br>
    Formal times are parsed in as well. i.e. `0600h`, `8pm`, `noon`, `4:30 p.m.`<br>
    Similar to dates, relative times are also allowed. i.e. `5 minutes from now`, `in 10 minutes`, `5 hours ago`<br>
    For more details, please visit http://natty.joestelmach.com/doc.jsp.

Examples:

* `add Do Homework` <br>
  Adds a task called 'Do Homework'.
* `add Meeting With Boss startdate/11-11-2011 17:30 enddate/11-11-2011 19:30` <br>
  Adds a task called 'Meeting With Boss', with start date 11-11-2011 17:30, and end date to be 11-11-2011 19:30.
* `add Check Email enddate/today` <br>
  Adds a task called 'Check Email', sets the end date to be today's date.

### 2.3. Updating a task : `update`

Updates an existing task in the todo list.<br>
Format: `update INDEX [NAME] [startdate/STARTDATE] [enddate/ENDDATE] [recurring/PERIOD(day/week/month)] [priority/PRIORITY] [tag/TAG]...`

> * Updates the task at the specified `INDEX`. <br>
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be set to contain the new tags; the old tags will be removed.

Examples:

* `update 2 Assignment 3`<br>
  Updates the name of the 2nd task to be `Assignment 3`.
* `update 3 startdate/today enddate/tomorrow` <br>
  Updates the start date and end date of the 3rd task to today and tomorrow respectively.

### 2.4. Filter all tasks for a given keyword: `filter`

Finds tasks whose names contain any of the given keywords.<br>
Format: `filter/list/find [KEYWORDS] [tag/] [name/]`

> * The search is case insensitive. e.g `hans` will match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * By default the name and tag is searched.
> * Adding 'tag/' will search by only tag.
> * Adding 'name/' will search by only name.
> * If no keyword is entered, the list of all tasks is displayed.
> * Partial words will be matched. e.g. `Han` will match `Hans`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find Assignment`<br>
  Returns `Assignment 1` and also `assignment 2`.
* `find Assignment Project Tutorial`<br>
  Returns any task having `Assignment`, `Project`, or `Tutorial` in their names or tags.
* `find school tag/` <br>
  Returns any task with tags with 'school' in the name.

### 2.5. Deleting a task : `delete`

Deletes the specified task from the todo list. Reversible with `undo` command.<br>
Format: `delete INDEX(ES)`

> * Deletes the task at the specified `INDEX`. <br>
> * The index(es) refers to the index number shown in the most recent listing.<br>
> * The index(es) **must be a positive integer** 1, 2, 3, ...<br>
> * Supports deletion of multiple indexes in a single command, by best effort matching.
> * The system is whitespace insensitive i.e. `delete 3-6` is the same as `delete 3 - 6`.

Examples:

* `delete 2`<br>
  Deletes the 2nd task in the todo list.
* `delete 3 - 6`<br>
  Deletes the 3rd, 4th, 5th, and 6th task in the todo list.<br>
* `delete 3 -`<br>
  Deletes from 3rd to last in the todo list.
* `delete - 5`<br>
  Deletes from first to 5th task in the todo list.
* `delete 5, - 3, 7-8 10, 12 -`<br>
  Deletes from 1st to 3rd, 5th, 7th, 8th, 10th, and from 12th to last task in the todo list.

### 2.6. Clearing all entries : `clear`

Clears all entries from the todo list.<br>
Useful for when you want to start from a clean slate.<br>
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
> The number **must be a positive integer** 1, 2, 3, ... <br>
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

Marks a task to be complete or incomplete. <br>
Format: `mark [complete/incomplete] INDEX(ES)`

> * Using complete as a parameter will mark the selected task(s) as complete.
> * Using incomplete as a parameter will mark the selected task(s) as incomplete.
> * Using neither will default the command to mark as complete.
> * Supports marking of multiple indexes in a single command, by best effort matching.
> * The system is whitespace insensitive i.e. `delete 3-6` is the same as `delete 3 - 6`.

Example:
* `mark complete 1` <br>
  Marks task 1 as complete.
* `mark incomplete 2` <br>
  Marks task 2 as incomplete.
* `mark 3` <br>
  Marks task 3 as complete.

### 2.19. Add a Tag to a Task : `tag`

Adds a tag or multiple tags to an existing task. <br>
Format: `tag INDEX TAG...`

> * If the tag already exists, the command will notify you and do nothing.
> * If multiple tags are used in the command, you will be notified of each one.

Example:
* `tag 1 school` <br>
  Adds the tag 'school' to task 1.
* `tag 2 work home` <br>
  Adds the tags 'work' and 'home' to task 2.

### 2.20. Remove a Tag from a Task : `untag`

Removes a tag or multiple tags from an existing task. <br>
Format: `untag INDEX TAG...`

> * If the tag already does not exist, the command will notify you and do nothing.
> * If multiple tags are used in the command, you will be notified of each one.

Example:
* `untag 1 school` <br>
  Removes the tag 'school' from task 1.
* `untag 2 work home` <br>
  Removes the tags 'work' and 'home' from task 2.

## 3. Command Summary

**Command** | **Format** | **Examples**
-------- | :-------- | :---------
Add | `add NAME [enddate/ENDDATE] [startdate/STARTDATE] [recurring/PERIOD(day/week/month)] [priority/PRIORITY] [tag/TAG]...` | `add Assigment 1 enddate/Friday tag/school`
Clear | `clear`
Delete | `delete INDEX(ES)` | `delete 3`
Filter | `filter/list/find [KEYWORDS] [tag/] [name/]` | `find school tag/`
Help | `help`
Update | `update INDEX [name/NAME] [enddate/ENDDATE] [startdate/STARTDATE] [recurring/PERIOD(day/week/month)] [priority/PRIORITY] [tag/TAG]...` | `update 1 enddate/11/12/2011`
Exit | `exit/quit`
Undo | `undo [NUMBER]` | `undo 5` <br> `undo`
Redo | `redo [NUMBER]` | `redo 5` <br> `redo`
History | `history`
Add Alias | `alias ALIAS PHRASE` | `alias hs history`
Delete Alias | `unalias ALIAS` | `unalias hs`
View Aliases | `viewalias`
Change Save Storage Location | `save FILELOCATION` | `save data/savefile.txt`
Change Load Storage Location | `load FILELOCATION` | `load data/savefile.txt`
Switch Display Task Window | `switch WINDOWIDENTIFIER` | `switch 2` <br> `switch T`
Mark a Task Complete or Incomplete | `mark [complete/incomplete] INDEX(ES)` | `mark complete 1` <br> `mark incomplete 2` <br> `mark 3`
Add a Tag to a Task | `tag INDEX TAG...` | `tag 1 school` <br> `tag 3 work home`
Remove a Tag from a Task | `untag INDEX TAG...` | `untag 1 school` <br> `untag 3 work home`
