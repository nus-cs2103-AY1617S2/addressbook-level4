# Suru - User Guide

By : `W09-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp;

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer. 

   > Having any Java 8 version is not enough.  
   > This app will not work with earlier versions of Java 8.

1. Download the latest `suru.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Type the command in the command box and press <kbd>Enter</kbd> to execute it.  
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
4. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` write essay `**by**` 13/3/17` : Adds a task "write essay" with a due date "13/3/17"
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
5. Refer to the [Features](#features) section below for details of each command. 


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Saving the data

By default, task manager data is saved in the hard disk automatically after any command that changes the data. 
There is no need to save manually.

### 2.2. Viewing usage instructions : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.3. Adding a task with no specified completion date: `add`

Adds a task to the task manager 

Format: `add DESCRIPTION`

> Tasks will be added to the task manager with no due date or duration.

Examples:

* `add write essay`

### 2.4. Adding a task with a due date: `add ... by ...`

Adds a task with a due date to the task manager 

Format: `add DESCRIPTION by DUE_DATE`

> Tasks with a due date will be added to the task manager.

Examples:

* `add write essay by 13/7/17`

### 2.5. Adding a task with a start date: `add ... from ...`

Adds a task with a start date to the task manager 

Format: `add DESCRIPTION from START_DATE`

> Tasks with a start date will be added to the task manager.

Examples:

* `add write essay from 13/7/17`

### 2.6. Adding a task with a duration: `add ... from ... to ...`

Adds a task to the task manager 

Format: `add DESCRIPTION from START_DATE to END_DATE`

> Tasks will be added to the task manager with a duration.

Examples:

* `add write essay from 13/7/17 to 15/7/17`

### 2.7. Adding a task with tags: `add ... #TAGNAME ...`

Adds tags to the task  

Format: `add DESCRIPTION #TAGNAME`

> Tasks will be added to the task manager with a tags.

Examples:

* `add write essay from 13/7/17 to 15/7/17 #school #homework`
* `add write essay from 13/7/17 #school #homework`
* `add write essay by 15/7/17 #blogging`
* `add write essay #blogging`


### 2.8. Listing all tasks : `list`

Shows a list of all tasks in the task manager. 

Format: `list`

### 2.9. Listing undone tasks : `list undone`

Shows a list of all undone tasks in the task manager. 

Format: `list`

### 2.10. Listing done tasks : `list done`

Shows a list of all done tasks in the task manager. 

Format: `list`

### 2.11. Edit task : `edit INDEX`
> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last tasks listing.
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields of the task must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by editing the tag to an empty value. 

Examples:

* `edit 1 buy groceries by 13/5/17` 
  Edits the description of the task to `buy groceries` and due date to `13/5/17` respectively.

* `edit 2 buy groceries` 
  Edits the description of the 2nd task to be `buy groceries`.

### 2.12. Deleting a task : `delete`

Deletes the specified task from the task manager. 
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`.  
> The index refers to the index number shown in the most recent listing. 
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list` 
  `delete 2` 
  Deletes the 2nd task in the list.
* `find Essay` 
  `delete 1` 
  Deletes the 1st task in the results of the `find` command.

### 2.13. Undo : `undo`

Reverses the previous action done by the user 

Format: `undo`

> * Only reverses operations that manipulate data, i.e. `add`, `edit` and `delete`


### 2.14. Redo : `redo`

Reverses the previous undo operation 

Format: `redo`

> * Only reverses the previous `undo` operation

### 2.15. Finding all tasks containing the keyword in any of their properties: `find`

Finds tasks whose properties contain any of the given keywords. 

Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is **NOT** case sensitive. e.g `write` will match `Write`
> * The order of the keywords does not matter. e.g. `Write Essay` will match `Essay Write`
> * All properties are searched e.g. `DUE_DATE`, `TAGS`, `DESCRIPTION` etc.
> * Only full words will be matched e.g. `Ess` will not match `Essay`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Essay` will match `Essay Writing Competition`

Examples:

* `find Essay` 
  Returns `Write Essay` and `essay writing`
* `find Essay Writing Competition` 
  Returns any task having keywords `Essay`, `Writing`, or `Competition`
* `find 17/7/17`  
  Returns any task having the date `17/7/17`
  
### 2.16. Find undone tasks : `find undone`

Finds undone tasks whose properties contain any of the given keywords. 

Format: `find undone KEYWORD [MORE_KEYWORDS]`

> * Similar to regular `find` feature, but only searches through `undone` tasks

### 2.17. Find done tasks: `find done`
Finds done tasks whose properties contain any of the given keywords. 

Format: `find done KEYWORD [MORE_KEYWORDS]`

> * Similar to regular `find` feature, but only searches through `done` tasks

### 2.18. Check off task to mark as done: `check`
Checks off a task to mark as done.  
> Checks off the task at the specified `INDEX`.  
> The index refers to the index number shown in the most recent listing. 
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list` 
  `check 2` 
  Checks off the 2nd task in the listing as done.
* `find Essay` 
  `check 1` 
  Checks off the 1st task in the results of the `find` command.

### 2.19. Uncheck task to mark as undone: `uncheck`
Uncheck a task to mark as undone.  
> Unchecks the task at the specified `INDEX`.  
> The index refers to the index number shown in the most recent listing. 
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list` 
  `uncheck 2` 
  Unchecks the 2nd task in the listing marking it as undone.
* `find Essay` 
  `uncheck 1` 
  Unchecks the 1st task in the results of the `find` command.

### 2.20. See usage instructions: `help`
Shows a list of commands that Suru accepts.

### 2.21. See specific command's usage instructions: `help COMMAND`
Shows usage instractions for a specific command.

Examples:

* `help add`
* Shows usage instructions for the `add` command.

### 2.22. Define save destination: `saveto`
Allows user to define destination to save to.

Examples:

* `saveto` `FILE_PATH`

### 2.23. Define target to load from: `loadfrom`
Allows user to define target to load from.

Examples:

* `loadfrom` `FILE_PATH`

### 2.24. Add reminders to task: `add DESCRIPTION reminders: ...`
Adds reminders to the task  

Format: `add DESCRIPTION reminders:`

> Tasks will be added to the task manager with reminders.

Examples:

* `add write essay from 13/7/17 to 15/7/17 reminders: 1 day, 1 hour`

### 2.25. Edit reminders of a task: `edit`
Use the `edit` command to edit reminders.

Examples:

* `edit 2 buy groceries reminders: 18/7/17` 
  Edits the description of the 2nd task to be `buy groceries` and changes reminders to `18/7/17`

### 2.26. Removing reminders from a task: `edit`
Use the `edit` command to edit reminders.

* `edit 2 buy groceries reminders:` 
  Edits the description of the 2nd task to be `buy groceries` and clears all existing reminders.

### 2.27. Add a recurring task: `add DESCRIPTION recurring:`
Add a recurring task.

Format: `add DESCRIPTION recurring:`

> Tasks will be added to the task manager with recurrences.

Examples:

* `add write essay recurring: daily`
* `add write essay recurring: weekly`
* `add write essay recurring: every 2 weeks`

### 2.28. Sort tasks: `sort by`
Sort tasks by property.

Format: `sort by PROPERTY`

> Tasks will be sorted and listed by the property

Examples:

* `sort by description`
* `sort by due date`
* `sort by #TAGNAME`

### 2.29. Hotkeys: `-HOTKEY`
Suru recognizes a hotkey combination and executes the required operation.

Format: `-HOTKEY`

Examples:

* `-a write essay`: adds new task with description `write essay`
* `-s due date`: sorts by `due date`
* `-ls`: lists all tasks

### 2.30. Add subtask to a task: `add subtask`
User can add a subtask to an existing task:

Format: `add subtask ...`

> * Select the task using relevant `INDEX`.
    The index refers to the index number shown in the last tasks listing.
    The index **must be a positive integer** 1, 2, 3, ...
> * Subtask can be added to selected task

Examples: 
> * `add subtask write first draft by 18/7/17 #pre-production`
> * `add subtask write first draft`

### 2.31. View subtasks of a task: `list subtasks`
Suru displays subtasks of selected task.

Format: `list subtasks`

### 2.32. Edit subtask of a task: `edit subtask INDEX`
User can edit a subtask of an existing task:

Format: `edit subtask INDEX`

> * Select the subtask using relevant `INDEX`.
    The index refers to the index number shown in the last subtasks listing.
    The index **must be a positive integer** 1, 2, 3, ...
> * Selected subtask can be edited.


Examples:
> * `edit subtask 1 write second draft`: edits the description of the first subtask to `write second draft`

### 2.33. Remove subtasks of a task: `delete subtask INDEX`
User can delete subtask of a selected task.

Format: `delete subtask INDEX`

> * Select the subtask using relevant `INDEX`.
    The index refers to the index number shown in the last subtasks listing.
    The index **must be a positive integer** 1, 2, 3, ...
> * Selected subtask can be deleted.

Examples:
> * `delete subtask 1`

### 2.34. Tutorial: `tutorial`
User can view step-by-step tutorial of common features.

Format: `tutorial`

### 2.35. Voice Recognition: `voice-mode`
User can perform operations through voice commands.

Format: `voice-mode`

### 2.36. Parse Email Automatically: `auto-parse-email`
User can toggle automatic parsing of his email.

Format: `auto-parse-email`

### 2.37. Add task to Google Calendar: `google-cal`
User can add tasks to Google Calendar.

Format: `google-cal`



## 3. FAQ

**Q**: How do I transfer my data to another Computer? 

**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Task Manager folder.

## 4. Command Summary

* **Add**  `add DESCRIPTION from START_DATE to END_DATE #TAGNAME... recurring:... reminders:...`  
Examples:
> * `add Write essay`
> * `add Write essay by 24/12/17`
> * `add Write essay from 20/12/17`
> * `add Write essay from 20/12/17 to 24/12/17`
> * `add Write essay #school #homework`
> * `add Write essay from 20/12/17 recurring: weekly`
> * `add Write essay reminders: 1 day, 30 mins`

* **Edit** `edit INDEX`
Examples:
> * `edit 2 buy groceries`
> * `edit 1 buy groceries by 13/5/17`

* **Delete** : `delete INDEX`  
Examples:
> * `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]`  
> * `find Essay`

* **List** : `list`  
Examples:
> * `list undone`
> * `list done`

* **Help** : `help`  

* **Select** : `select INDEX`
Examples:
> * `list`, `select 2`
> * `find Essay`, `select 1`

* **Clear** : `clear`

* **Exit** : `exit`
