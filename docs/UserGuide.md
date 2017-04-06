[comment]: # (@@author A0162011A)
# ToLuist - User Guide

By : `Team ToLuist`  &nbsp;&nbsp;&nbsp;&nbsp;

---

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
4. [Command Summary](#4-command-summary)

## 1. Introduction

Have you ever felt overloaded with work? Don't know where to start?<br>
Now you can use ToLuist, the answer to all of your problems!<br>
ToLuist is an application which will help you to manage all your tasks, allowing you to sort out your life.<br>
ToLuist is designed with you in mind, ensuring that you are able to focus on what is important to you.

## 2. Quick Start

1. Double-click the file to start the app. The GUI should appear in a few seconds.

    <img src="images/Ui.png" width="600"><br>
    **Figure 1.** Initial launch screen of ToLuist

2. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
3. Try out some example commands:
   * **`add`**` Try Out Todo List` :
     adds a task named `Try Out Todo List` to the todo list.
   * **`delete`**` 1` : deletes the 1st task shown in the current list.
   * **`exit`** : exits the app.
4. Refer to the [Features](#features) section below for details of each command.<br>

## 3. Features

ToLuist is focused towards users who like to type.<br>
All the features of the application can be accessed through the use of a keyboard, by entering commands into the on-screen text field.<br>

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.
> * Options with '/' allow either word to be used.

### 3.1. Switch Display Task Window : `switch`

For when you want to view different sets of tasks.<br>
Changes the displayed task list.<br>
Format: `switch WINDOWIDENTIFIER`

> * If a number is given for window identifier, that will be the number of the tab from the left which is selected.
> * If a letter is given, it will be the underlined letter in the window list name.
> * If a word is given, it will be the word with the underlined letter in the window list name.
> * You can also use key combination <kbd>Ctrl</kbd> with an underlined letter to switch between different window.

Example:
* `switch 2` <br>
  Switches the displayed view to 'Today'.
* `switch T` <br>
  Switches the displayed view to 'Today'.
* `Press <kbd>Ctrl</kbd> + <kbd>A</kbd>` <br>
  Switches the displayed view to 'All'.

### 3.2. Viewing help : `help`

For when you need assistance for inputting commands.<br>
Shows commands which are in the system.<br>
Format: `help [COMMAND]`

> * The system will prompt you to enter 'help' if you enter an incorrect command.
> * If a command is entered, detailed help for that command will be shown instead.

Examples:

* `help` <br>
  Shows general help information.
* `help add` <br>
  Shows detailed help information for 'add' command.

[comment]: # (@@author A0127545A)
### 3.3. Adding a task: `add`

For when you have a task you need to remember.<br>
Adds a task to the todo list<br>
Format: `add NAME [/from STARTDATE /to ENDDATE] [/by ENDDATE] [/repeat PERIOD(day/week/month)] [/priority PRIORITY(high/low)] [/tags TAGS]`

> * Both 'startdate' and 'enddate' use the same datetime format.
> * 'startdate' requires a valid 'enddate' to be used in the same command.
> * The values entered for 'startdate' and 'enddate' are very flexible:<br>
    Standard dates are parsed, with the month being before the day. i.e. `MM/DD/YY`, `MM/DD/YYYY`, `YYYY/MM/DD`, `YYYY/MM/DD`<br>
    Relaxed dates are parsed as logically as possible. i.e. `Jan 21, '97`, `Sun, Nov 21`, `The 31st of April in the year 2017`<br>
    Relative dates are also allowed. i.e. `Yesterday`, `Today`, `Next Sunday`, `3 Days from now`<br>
    Standard times are parsed in as well. i.e. `0600h`, `8pm`, `noon`, `4:30 p.m.`<br>
    Similar to dates, relative times are also allowed. i.e. `5 minutes from now`, `in 10 minutes`, `5 hours ago`<br>
    For more details, please visit http://natty.joestelmach.com/doc.jsp.

Examples:

* `add Do Homework` <br>
  Adds a task called 'Do Homework'.
* `add Meeting With Boss /from 11-11-2011 17:30 /to 11-11-2011 19:30` <br>
  Adds a task called 'Meeting With Boss', with start date 11-11-2011 17:30, and end date to be 11-11-2011 19:30.
* `add Check Email /by today` <br>
  Adds a task called 'Check Email', and sets the deadline to be today's date.

[comment]: # (@@author A0162011A)
### 3.4. Viewing tasks

For when you need to see your tasks.<br>
View all tasks in the todo list<br>
Format: `list`
> * A list of tasks will always be displayed.
> * When starting the program, the list will show all tasks which are currently not completed.
> * When performing `filter` operations, this list will be updated to show only the results searched for.

[comment]: # (@@author A0127545A)
### 3.5. Updating a task : `update`

For when you need to update or fix a previously entered task.<br>
Updates an existing task in the todo list.<br>
Format: `update INDEX [NAME] [/from STARTDATE /to ENDDATE] [/by ENDDATE] [/repeat PERIOD(day/week/month)] [/stoprepeat] [/priority PRIORITY(high/low)] [/tags TAGS]`

> * Updates the task at the specified `INDEX`. <br>
    The index refers to the index number shown in the last task listing.
> * Only fields entered will be updated.
> * When editing tags, the existing tags of the task will be set to contain the new tags; the old tags will be removed.


Examples:

* `update 2 Assignment 3`<br>
  Updates the name of the 2nd task to be `Assignment 3`.
* `update 3 /from today /to tomorrow` <br>
  Updates the start date and end date of the 3rd task to today and tomorrow respectively.

[comment]: # (@@author A0162011A)
### 3.6. Filter all tasks for a given keyword: `filter`

Finds tasks whose names contain any of the given keywords.<br>
Format: `filter/list/find [KEYWORDS] [/tag] [/name]`

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
  Lists any task with `Assignment` in their names or tags.
* `find Assignment Project Tutorial`<br>
  Returns any task having `Assignment`, `Project`, or `Tutorial` in their names or tags.
* `find school /tag` <br>
  Returns any task with the word 'school' in the tag name.

### 3.7. Sort all tasks in a given order: `sort`

Finds tasks whose names contain any of the given keywords.<br>
Format: `sort [CATEGORY(priority/enddate/startdate/description/overdue/default)]...`

> * The category entered will be given higher preference for sorting.
> * By default, the sorted order is: overdue -> priority -> enddate -> startdate -> description
> * Using 'default' as the category will revert the sorting order to the above mentioned default.
> * Multiple categories can be used. The list will be sorted in order based on which category was entered first.

Examples:

* `sort priority`<br>
  Changes the sorting order to sort by priority first.
* `sort enddate description`<br>
  Changes the sorting order to sort by enddate, then description.
* `sort default`<br>
  Changes the sorting order to the default sort.

[comment]: # (@@author A0127545A)
### 3.8. Deleting a task : `delete`

For when your task is no longer necessary.<br>
Deletes the specified task from the todo list.<br>
Format: `delete INDEX(ES)`

> * Deletes the task at the specified `INDEX`. <br>
> * The index(es) refers to the index number shown in the most recent listing.<br>
> * Supports deletion of multiple indexes in a single command.

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

[comment]: # (@@author A0131125Y)
### 3.9. Complete or Make Incomplete a Task : `mark`

For when you need to indicate that a task is finished or not.<br>
Marks a task to be complete or incomplete. <br>
Format: `mark [complete/incomplete] INDEX(ES)`

> * Using complete as a parameter will mark the selected task(s) as complete.
> * Using incomplete as a parameter will mark the selected task(s) as incomplete.
> * Using neither will default the command to mark as complete.
> * Supports marking of multiple indexes in a single command.

Example:
* `mark complete 1` <br>
  Marks task 1 as complete.
* `mark incomplete 2` <br>
  Marks task 2 as incomplete.
* `mark 3` <br>
  Marks task 3 as complete.
* `mark 3 -`<br>
  Marks tasks from 3rd to last in the todo list as complete.
* `mark incomplete - 5`<br>
  Marks tasks from first to 5th task in the todo list as incomplete.
* `mark 5, - 3, 7-8 10, 12 -`<br>
  Marks tasks from 1st to 3rd, 5th, 7th, 8th, 10th, and from 12th to last task in the todo list as complete.

[comment]: # (@@author A0162011A)
### 3.10. Add a Tag to a Task : `tag`

For when you want to differentiate your tasks by categories.<br>
Adds a tag or multiple tags to an existing task. <br>
Format: `tag INDEX TAG...`

> * If the tag already exists, the command will notify you and do nothing.
> * If multiple tags are used in the command, you will be notified of each one.

Example:
* `tag 1 school` <br>
  Adds the tag 'school' to task 1.
* `tag 2 work home` <br>
  Adds the tags 'work' and 'home' to task 2.

### 3.10. Remove a Tag from a Task : `untag`

For when you want to remove a category from a task.<br>
Removes a tag or multiple tags from an existing task. <br>
Format: `untag INDEX TAG...`

> * If the tag already does not exist, the command will notify you and do nothing.
> * If multiple tags are used in the command, you will be notified of each one.

Example:
* `untag 1 school` <br>
  Removes the tag 'school' from task 1.
* `untag 2 work home` <br>
  Removes the tags 'work' and 'home' from task 2.

### 3.11. Clearing all entries : `clear`

For when you want to start from a clean slate.<br>
Clears all entries from the todo list.<br>
Format: `clear`

[comment]: # (@@author A0131125Y)
### 3.12. Undo a command : `undo`

For when you accidentally entered a wrong command.<br>
Undoes previous commands by the user.<br>
Format: `undo [NUMBER]`

> * `Undo` allows reverting of modifications to the tasks in the todo list. This means that commands like 
`add`, `update`, `delete`, `clear`, `tag`, `untag` and `mark` which modifies the tasks can be reversed with
> `undo`. If loading a different data file with `load` cause the current tasks to be changed, you can go back
> to the previous task using `undo` too (Using `undo`, however, won't revert the change of data storage 
path. It's not considered modifications to the task).
> Changes that are not modifications to the tasks in the todo list cannot be reversed with `undo`. However, 
> you can still easily revert their effects:
>   * Change of storage path location (due to `save` / `load` ) - You can change your storage path to the 
previous location with `save`.
>   * Setting of aliases (due to `alias`) - You can remove the alias with `unalias`.
>   * Removal of aliases (due to `unalias`) - You can set back the alias with `alias`.
>
> * Note that commands that commands that do not modifies the content of the todo list cannot be undone with 
> `undo`. E.g
> * If a number is entered, undoes that amount of previous commands instead.
> * You can also use key combination <kbd>Ctrl</kbd> + <kbd>Z</kbd> to quickly undo one time.

Examples:

* `add Test`<br>
  `undo`<br>
  Undo adding Test to the todo list.
* `add Assignment` <br>
  `add Project` <br>
  `undo 2`<br>
  Undo both commands.
* `add Test`<br>
  `Press <kbd>Ctrl</kbd> + <kbd>Z</kbd>` <br>
  Undo adding Test to the todo list.

### 3.13. Redo a command : `redo`

For when you accidentally undo a correct command.<br>
Redo previously undone commands by the user.<br>
Format: `redo [NUMBER]`

> * Similar to `undo`, `redo` will help you re-apply modifications to the tasks in the todo list that 
> * you mistakenly revoked (using `undo`).<br>
> * If a number is entered, redo that amount of previous commands instead.<br>
> * The number must be less than or equal to the number of commands undone.
> * You can also use key combination <kbd>Ctrl</kbd> + <kbd>Y</kbd> to quickly redo one time.

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
* `add Test`<br>
  `undo`<br>
  `Press <kbd>Ctrl</kbd> + <kbd>Y</kbd>` <br>
  Redo adding Test to the todo list.

[comment]: # (@@author A0162011A)
### 3.14. Viewing previous commands and accessing them : `history`

For when you want to check what you have previously entered.<br>
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

### 3.15. Add alias for any phrase: `alias`

For when you want a shortcut for entering a command.<br>
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

### 3.16. Delete alias for commands: `unalias`

For when you want to remove a shortcut for entering a command.<br>
Removes an alias for a command. <br>
Format: `unalias ALIAS`

Example:
* `alias hs history` <br>
  `unalias hs` <br>
  Removes the alias 'hs'.

### 3.17. View aliases for commands: `viewalias`

For when you want to see all your shortcuts you entered.<br>
Views aliases in the system. <br>
Format: `viewalias`

> * Lists aliases in the format `ALIAS:PHRASE`.

Example:
* `alias hs history` <br>
  `viewalias` <br>
  Shows `hs:history` in the list.

### 3.18. Saving the data

For you to be able to keep your list, even if you close and re-open the application.<br>
Todo list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 3.19. Change storage location for save data: `save`

For when you want to switch the save location of the application, such as if you are moving it to a Dropbox folder.<br>
Changes the location for the storage file used in this system. <br>
Warning: If a file with the requested name already exists, it will be overwritten. <br>
Format: `save NEWFILELOCATION`

> * All data will be moved to the new file location.
> * If the file does not exist, the file will be created.
> * The old file will be removed.

Example:
* `save data/savefile.txt` <br>
  Sets the save storage location to `data/savefile.txt`.

### 3.20. Change storage location for load data: `load`

For when you want to switch the load location of the application, such as when you want to load the data from a file in Dropbox.<br>
Changes the location for the storage file used in this system. <br>
Format: `load NEWFILELOCATION`

> * The new save location will be updated to the location.
> * The program will replace the data in the program with data from the new file location.
> * If the file does not exist, an error message will be displayed.
> * Warning: The old data in the program will stay in the old save file, and will not be updated with new values.

Example:
* `load data/savefile.txt` <br>
  Sets the load storage location to `data/savefile.txt`.

### 3.21. Exiting the program : `exit`

For when you are done with the application.<br>
Exits the program.<br>
Format: `exit/quit`

## 4. Command Summary

**Command** | **Format** | **Examples**
-------- | :-------- | :---------
Add | `add NAME [/from STARTDATE /to ENDDATE] [/by ENDDATE] [/repeat PERIOD(day/week/month)] [/priority PRIORITY(high/low)] [/tags TAGS]` | `add Assigment 1 /by Friday /tags school`
Add a Tag to a Task | `tag INDEX TAG...` | `tag 1 school` <br> `tag 3 work home`
Add Alias | `alias ALIAS PHRASE` | `alias hs history`
Change Load Storage Location | `load FILELOCATION` | `load data/savefile.txt`
Change Save Storage Location | `save FILELOCATION` | `save data/savefile.txt`
Clear | `clear`
Delete | `delete INDEX(ES)` | `delete 3`
Delete Alias | `unalias ALIAS` | `unalias hs`
Exit | `exit/quit`
Filter | `filter/list/find [KEYWORDS] [/tag] [/name]` | `find school tag/`
Help | `help [COMMAND]` | 'help add'
History | `history`
Mark a Task Complete or Incomplete | `mark [complete/incomplete] INDEX(ES)` | `mark complete 1` <br> `mark incomplete 2` <br> `mark 3`
Sort | `sort [CATEGORY(priority/enddate/startdate/overdue/description/default)]...` | 'sort priority'
Switch Display Task Window | `switch WINDOWIDENTIFIER` | `switch 2` <br> `switch T`
Undo | `undo [NUMBER]` | `undo 5` <br> `undo`
Update | `update INDEX [/from STARTDATE /to ENDDATE] [/by ENDDATE] [/repeat PERIOD(day/week/month)] [/stoprepeat] [/priority PRIORITY(high/low)] [/tags TAGS]` | `update 1 /by 11/12/2011`
Remove a Tag from a Task | `untag INDEX TAG...` | `untag 1 school` <br> `untag 3 work home`
Redo | `redo [NUMBER]` | `redo 5` <br> `redo`
View Aliases | `viewalias`
