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

1. Download the latest `addressbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**`Do tutorial d/14.02.17` :
     adds a task describing `Do tutorial` to the Task Manager.
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

> A list of commands available will be shown
> Help is also shown if you enter an incorrect command e.g. `abcd`

Format: `help COMMAND`

> Different type of commands relating to COMMAND is shown
> e.g. `help help`

### 2.2.1. Adding a task: `add`

Adds a task to the task manager
Format: `add DESCRIPTION d/DATE t/TIME f/FREQUENCY bt/TIMERANGE p/PRIORITY [TAG]`

> * DESCRIPTION represent the task (complusory)
> * d/ represents the deadline date*
> * t/ represents the deadline time
> * f/ represents the type to repeat, e.g. w / m / y
> * bt/ represents the timeslot to be blocked, must be given in a range
> * p/ represents the priority**
> * [TAG] represents the tag***

Note
*If DATE is not specified, today's date will be selected
**Tasks can have any level of priority (including 0)
***Tasks can have any number of tags (including 0)

### 2.2.2. Adding tags to existing tasks: `addtag`

Adds tag to existing tasks
Format: `addtag INDEX [TAG]...`
> * Adds tag to task at the specified INDEX
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * More tags will be added to the task


### 2.3.1. Listing all tasks : `list`

Shows a list of all tasks in the task manager<br>
Format: `list d/DATE d/DATE p/PRIORITY [TAG]...`
> * All fields are not compulsory
> * d/ would show the tasks due by specified deadline*
> If two DATE are given, tasks due within the period would be shown
> * p/ would show the tasks of that priority*
> * [TAG] would show tasks under the tag, allow for multiple

Note
*If `d/` or `p/` is given, but no specified DATE or PRIORITY, the list would be sorted by the given command

### 2.3.2. Listing all completed tasks : `listcom`

Shows a list of all completed tasks in the task manager<br>
Format: `listcom`

### 2.3.2. Listing all tags : `listtag`

Shows a list of all tags in the task manager<br>
Format: `listtags`

### 2.4.1. Editing a task : `edit`

Edits an existing task in the task manager.<br>
Format: `edit INDEX [DESCRIPTION] [d/DATE] [t/TIME] [bt/TIMERANGE] [f/FREQUENCY] [p/PRIORITY] [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without
specifying any tags after it.

Examples:

* `edit 1 Do tutorial d/17.09.19`<br>
  Edits the description and deadline of the 1st person to be `Do tutorial` and `17.09.19` respectively.

* `edit 2 t/`<br>
  Clears all existing tags of the 2nd task.

### 2.4.2. Editing a tag : `edittag`
Edits the name of a tag and update all tasks that have the tag to the updated name
Format: `edittag INDEX TAGNAME`

> * Edits the tag at the specified `INDEX`.
    The index refers to the index number shown in the last tag listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `listtag`
`edittag 3 CS2103`
Edits the 3rd tag to be `CS2103`

### 2.5.1. Finding all tasks containing any keyword in their description and tags: `find`

Finds tasks whose description contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the description is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find John`<br>
  Returns `John Doe` but not `john`
* `find Betsy Tim John`<br>
  Returns Any person having names `Betsy`, `Tim`, or `John`

### 2.5.2. Find most urgent tasks: `findurgent`

Finds tasks that has the highest priority and closest deadline
Format: `findurgent`

### 2.5.3. Find all tags containing any keywords: `findtag`

Finds tags whose name contain any of the given keywords.<br>
Format: `findtag KEYWORD [MORE_KEYWORDS]`

### 2.6. Deleting a task : `delete` or `complete`

Marks the specified task as complete and move it to completed list
Format: `complete INDEX`

Deletes the specified task from the task manager. <br>
Format: `delete INDEX`

> Mark as complete / deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...
> Confirmation will be needed if the task is incomplete
> If select is used, INDEX is not needed

Examples:

* `list`<br>
  `complete 2`<br>
  Mark the 2nd task as complete in the task manager.
* `find Betsy`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.
* `list`
`select 2 - 4`
`complete`
Mark selected tasks as complete

### 2.7. Select a / multiple tasks / tags: `select`

Selects the tasks / tags identified by the index number used in the last tasks / tags listing.<br>
Format: `select INDEX...`

> Selects the tasks / tags at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the task manager.
* `find Betsy` <br>
  `select 1 - 4`<br>
  Selects the 1st to the 4th task in the results of the `find` command.
* `listtag` <br>
`select 1, 3`
Selects the 1st and 3rd tag from the list

### 2.8. Changing data location : `storage`

Change the location of the task manager data<br>
Format: `storage FILEPATH`

### 2.9. Undoing previous command : `undo`

Undo previous command
Format: `undo`

### 2.10. Customizing a command : `customize`

Changing the command name to suit the user
Format: `customize [OLDCOMMAND] [NEWCOMMAND]`

### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.12. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 4. Command Summary

* **Add**  `add DESCRIPTION d/DATE t/TIME f/FREQUENCY bt/TIMERANGE p/PRIORITY [TAG]` <br>
  e.g. `add Do Tutorial d/29.07 t/23:59 f/w bt/1500 - 1800 p/10 t/CS2103`


* **AddTag** `addtag INDEX [TAG]`
e.g. `addtag 3 CS2103`

* **Customize** : `customize [OLDCOMMAND] [NEWCOMMAND]`
e.g. `customize complete done`

* **Complete** : `complete INDEX`
e.g.

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Edit** : `edit INDEX [DESCRIPTION] [d/DATE] [t/TIME] [bt/TIMERANGE] [p/PRIORITY] [t/TAG]...`
e.g. `edit 7 buy calculator`

* **EditTag** : `edittag INDEX TAGNAME`
e.g. `edittag 2 CS2103`

* **Exit** : `exit`
e.g.

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find tutorial quiz`

* **FindUrgent** : `findurgent`
e.g.

* **FindTag** : `findtag`
e.g.

* **Help** : `help` <br>
  e.g.

* **List** : `list d/DATE d/DATE p/PRIORITY [TAG]` <br>
  e.g. `list` `list d/03.03.17` `list d/03.03.17 d/10.03.17` `list p/5` `list t/CS2103`

* **ListCom** : `listcom`
e.g.

* **ListTag** : `listtag`
e.g.

* **Select** : `select INDEX...` <br>
  e.g.`select 2`

* **Storage** : `storage FILEPATH`
e.g. `storage C:\user\docs\taskmanager.txt`

* **Undo** : `undo`
e.g. 'undo'
