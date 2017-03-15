# myPotato - User Guide

By : `Team myPotato`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `FEB 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download and install the latest version of myPotato.
2. Double-click the icon to start myPotato. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">
3. Type the command in the command line and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
4. Some example commands you can try:
   * **`list`** : Lists all tasks
   * **`add`** : CS2103 midterm d/27/03 : adds a task to the task list. 
   * **`delete`**` 3` : Deletes the 3rd task shown in the current list
   * **`exit`** : Exits myPotato
5. Refer to the [Features](#features) section below for details of each command.<br>


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

Adds a task to the task list.<br>
Format: `add t/TASK [d/DATE] [#tags]`

> Persons can have any number of tags (including 0)

Examples:

* `add t/CS2103 meeting d/03/03 #Programming Lab 2`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the task list.<br>
Format: `list`

### 2.4. Create a list: `createlist`

Create a new list of tasks with a specified name. <br>
Format: `createlist NAME`

> The name refers to name of the task list.

Examples:
* `createlist Sports` <br>
  Create a new list named Sports.

### 2.5. Editing a person : `edit`

Edits an existing person in the address book.<br>
Format: `edit INDEX [t/TASK] [d/DATE] [#tags]`

> * Edits the person at the specified `INDEX`.
    The index refers to the index number shown in the last person listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
> * You can remove all the person's tags by typing `#` without specifying any tags after it. 

Examples:

* `edit 1  d/22/03`<br>
  Edits the due date of the `1st task` to be `22/03`.

* `edit 2 t/Project meeting #iCube`<br>
  Edits the `2nd task` to Project meeting and add hashtag `iCube..`.

* `edit 2 t/Project meeting #`<br>
  Edits the `2nd task` to Project meeting and clear all existing tags.
  
### 2.6. Finding all tasks containing any keyword in their title or due dates: `find`

Finds tasks containing any of the given keywords or due by given date.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `project` will not match `Project`
> * The order of the keywords does not matter. e.g. `Meeting Project` will match `Project Meeting`
> * Search can based on task name, date or tags.
> * Only full words will be matched e.g. `Project` will not match `Projects`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Project` will match `Project Meeting`

Examples:

* `find Meeting`<br>
  Returns `Project Meeting`
* `find 23/03`<br>
  Returns Any task due by `23/03`.

### 2.7. Deleting a task : `delete`

Deletes the specified task from the task list. <br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task.
* `find Project`<br>
  `delete 1`<br>
  Deletes the 1st task from the results of the `find` command.

### 2.8. Select a task : `select`

Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and display all details at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the `2nd task`.
* `find Project` <br>
  `select 1`<br>
  Selects the `1st task` from the results returned from the `find` command.
  
### 2.9. Sorting the list of tasks: `sort`

Sort all the tasks by due dates, closest to furthest from the current date. <br>
Format : `sort`

### 2.10. Undo previous command: `undo` 

Undo previous add/ delete command. <br>
Format: `undo`

### 2.11. Clearing all entries : `clear`

Clears all entries from the current task list.<br>
Format: `clear`

### 2.12. Setting Priority: `priority`

Set Priority for a task with 1 being the most important and 3 being the least important. <br>
Format: `priority t/TASK p/RANK`

> The `task` refers to the title of the task and the `rank` refers to the ranking of priorities.
> The rank **must be a positive integer** 1, 2, or 3.

Examples:

* `Priority t/meeting p/1`<br>
   Mark the priority of meeting as most important
* `Priority t/CS3230 assignment p/2`<br>
   Mark the priority of CS3230 assignment as important
* `Priority t/housework p/3`<br>
   Mark the priority of housework as the least important

### 2.13. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.14. Saving the data

Tasks data are saved in the hard disk automatically after any command that changes the data..<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous myPotato folder.

## 4. Command Summary

| **Command** | **Format** |
| ----------- | --------------- |
| Help | `help` |
| |e.g. ` help` |
| Add  | `add t/TASK [d/task] [#tags]` |
| |e.g. ` add t/CS2103 meeting d/03/03 #Programming Lab 2` |
| List  | `list` |
| |e.g. `list` |
| Createlist | `createlist NAME` |
| |e.g. ` createlist Sports` |
| Edit | `edit INDEX [t/TASK] [d/DATE] [#tags]` |
| |e.g. ` edit 1 t/CS2101 meeting d/04/03 #Progress Report` |
| Find  | `find KEYWORD [MORE_KEYWORDS]` |
| |e.g. ` find CS2101 meeting` |
| |e.g. ` find #Programming Lab 2` |
| Delete | `delete INDEX` |
| |e.g. ` delete 3` |
| Select | `select INDEX` |
| |e.g.` select 2` |
| Sort | `sort` |
| |e.g.` sort` |
| Undo | `undo`   |
| |e.g. `undo` |
| Clear | `clear` |
| |e.g. `clear` |
| Priority | `priority t/TASK p/RANK` |
| |e.g. ` priority t/Project Meeting p/1` |
| Exit | `exit` |
| |e.g. `exit` |
