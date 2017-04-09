# TaskIt - User Guide

By : `Team B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `February 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [User Interface] (#2-ui)
2. [Features](#3-features)
3. [FAQ](#4-faq)
4. [Command Summary](#5-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `TaskIt.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. Refer to [User Interface](#2-ui) for a more detailed explanations of various UI components. 
   > <img src="images/Ui_demo.gif" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`**` all`: lists all tasks
   * **`add`**` do SWE project tag school tag CS` :
     adds a task titled `do SWE project` to the TaskIt.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#3-features) section below for details of each command.<br>

<!-- @@author A0141872E -->
## 2. User Interface
#### Menu Bar : update the task list to display all relevant tasks based on the selection for fast search <br>
Home will display all tasks inside, other buttons will only display incompleted tasks with the specified type.
For example, click on deadline, only incompleted tasks with deadline will be displayed:<br>
<img src="images/menubar.png" width="300">
<br>

#### Task Card : display all details about a task   
An incompleted task card is shown with a white color bar:
<img src="images/incompleted_task.png" width="500"> 
<br>
A completed task card is shown with a green color bar:
<img src="images/completed_task.png" width="500"> 
<br>
A overdued task card is shown with a red red bar:<br>
<img src="images/overdued_task.png" width="500"> 
<br>
There are three priorities associated with task, and they are highlighted using red, orange and yellow circles at the end of the task card:<br>
<img src="images/pri_high.png" height="50" width="100"> <img src="images/pri_medium.png" height="50",width="100"> <img src="images/pri_low.png" height="50",width="100"> 
<br>

## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 3.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
> Help is also shown for specific command if command is entered with incorrect arguments e.g.  `delete asdf`

### 3.2. Adding a task: `add`

Add a new task to TaskIt<br>
Format: `add <TITLE> [tag TAG|by DATE|from DATE to DATE|priority <high|medium|low>]`

Examples:

* `add finish hw tag study tag school by April 5th`
* `add Interview tag work priority high`
* `add friend's party tag leisure tag friend from next Wednesday 8 pm to next Wednesday at 11pm`


> Tasks can have any number of tags (including 0)

<!-- @@author A0141872E -->
### 3.3. Listing all tasks: `list`

Listing all relevant tasks based on given parameters<br>
Format: `list <all|undone|done|today|overdue|floating|event|deadline|high|medium|low>`

List all the existing tasks in TaskIt.<br>
Format: `list all`

List only the undone tasks in TaskIt.<br>
Format: `list undone`

List only the completed tasks in TaskIt.<br>
Format: `list done`

List only the overdued tasks in TaskIt.<br>
Format: `list overdue`

List all the tasks dued today in TaskIt.<br>
Format: `list today`

### 3.4. Editing an existing task : `edit` `e`

Edits an existing task in TaskIt.<br>
Format: `e/edit <INDEX>  <[title]|[from]|[to]|[priority]|[tag]> <NEW>`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * Existing values will be updated to the input values.
> * Can remove start or end dates by typing `null` or `none`
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `null`.

Examples:

* `e/edit 2 title finish SWE HW`<br>
  Edit the second task title to finish SWE HW.

* `e/edit 1 from null`<br>
  Remove the first task start time.

* `e/edit 2 title attend meeting from Apr 7 at 2pm to 4pm priority high`<br>
  Edit the second task title to attend meeting, start time to Apr 7th 2pm, end time to 4pm and prioirty to high.

* `e/edit 1 tag null`<br>
  Remove all the tags of first task.

### 3.5. Marks a task as done or undone: `mark` `m`

Mark an existing task as done or undone based on a given valid index.<br>
Format: `m/mark <INDEX> <undone|done>`

> Marks the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list all`<br>
  `m/mark 2 done`<br>
  Marks the 2nd task in the task manager as done.
  
* `find do HW` <br>
  `m/mark 1 undone`<br>
  Marks the 1st task in the results of the `find` command as undone.

### 3.6. Searching all tasks based on keywords or date: `find`

Finds tasks which matched name/deadline/tag.<br>
Format: ` find <[NAME]|[DATE]|[TAG]>`

> * The search is case insensitive. e.g `do HW` will match `do hw`
> * The order of the keywords does not matter. e.g. `HW do` will match `do HW`
> * Substrings will be matched e.g. `HW` will match `HWs`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `HW` will match `do HW`

Examples:

* `find do HW`<br>
  Returns tasks with 'do' or 'HW' or both
* `find monday`<br>
  Returns tasks with dates on monday


### 3.7. Deleting a task : `delete`

Deletes the specified task from the TaskIt.
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list all`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find do HW`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 3.8. Select a task : `select`

Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list all`<br>
  `select 2`<br>
  Selects the 2nd task in the task manager.
* `find do HW` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

### 3.9. Clearing all entries : `clear`

Clears all entries from the task manager.<br>
Format: `clear`

### 3.10. Undo previous action: `undo`

Undo the prevous actions.<br>
Format: `undo`

### 3.11. Redo previous undo: `redo`

Redo the prevous undone action.<br>
Format: `redo`

### 3.12. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 3.13. Saving the data in specified file/folder

Puts all TaskIt storage in the given path to file.
Format: `save FILEPATH`

Example:

* `save ../myFile.txt`<br>

## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous TaskIt folder.


## 5. Command Summary

* **Add**  `add <TITLE> [tag TAG|by DATE|from DATE to DATE|priority <high|medium|low>]` <br>
  e.g. `add Lunch with Bob tag friend priority low tag leisure`
  e.g. `add friend's party tag leisure tag friend from next Wednesday 8 pm to next Wednesday at 11pm`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Edit**  `edit <INDEX>  <[title]|[start]|[end]|[priority]|[tag]> <NEW>` <br>
  e.g. `edit 1 title Movie`
  e.g. `edit 2 from this Friday 3 pm`
  e.g. `edit 2 end none`
  e.g. `edit 2 end null`
  e.g. `edit 1 tag School`
  e.g. `edit 1 tag null`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find do HW SWE`

* **List** : `list` <br>
  e.g. `list all`
  e.g. `list done|undone`
  e.g. `list low|medium|high`
  e.g. `list overdue`
  e.g. `list today`
  e.g. `list floating|event|deadline`

* **Mark** : `mark` <br>
  e.g. `mark 1 undone`
  e.g. `mark 3 done`

* **Undo** : `undo` <br>
  
* **Redo** : `redo` <br>

* **Help** : `help` <br>

* **Select** : `select INDEX` <br>
  e.g.`select 2`

* **Save** : `save FILEPATH` <br>
  e.g.`save taskIt.txt`

