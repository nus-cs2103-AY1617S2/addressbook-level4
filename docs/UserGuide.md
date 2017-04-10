# TaskIt - User Guide

By : `Team T15-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `February 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [User Interface](#2-user-interface)
3. [Features](#3-features)
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `TaskIt.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. Refer to [User Interface](#2-user-interface) section for a more detailed explanations of various UI components. 
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
For example, click on overdue, only incompleted tasks which are overdued will be displayed:<br>
<img src="images/menubar.png" width="500">
<br>

#### Task Card : display all details about a task and the date will shown in different languages based on system languages  
An incompleted task card is shown with a white color bar:
<img src="images/incompleted_task.png" height="80" width="600"> 
<br>
A completed task card is shown with a green color bar:
<img src="images/completed_task.png" height="80" width="600"> 
<br>
A overdued task card is shown with a red color bar:<br>
<img src="images/overdued_task.png" height="80" width="600"> 
<br>
There are three priorities associated with task: low, medium and high, and they are highlighted using red, orange and yellow circles at the end of the task card:<br>
<img src="images/pri_high.png" height="50" width="100"> <img src="images/pri_medium.png" height="50" width="100"><img src="images/pri_low.png" height="50" width="100"> 
<br>

## 3. Features

<!-- @@author A0141872E -->
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

> * Title can add at the back using quote.<br>
> * Quotes can be used to add title with keywords. <br>
> * Tasks can have any number of tags (including 0)<br>

Examples:

* `add finish hw tag study tag school by April 5th`
* `add Interview tag work priority high`
* `add friend's party tag leisure tag friend from next Wednesday 8 pm to next Wednesday at 11pm`
* `add tag school tag assignment by 11pm "CS2103 revision"`
* `add "tag friends on facebook" priority high tag friends tag facebook` 


### 3.3. Listing all tasks: `list`

Listing all relevant tasks based on given parameters<br>
Format: `list <all|undone|done|today|overdue|floating|event|deadline|high|medium|low>`

> * All parameters are case insensitive.<br>

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

### 3.4. Editing an existing task : `edit` 

Edits an existing task in TaskIt.<br>
Format: `e/edit <INDEX>  <[title]|[from]|[to]|[priority]|[tag]> <NEW>`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * Existing values will be updated to the input values.
> * When new title contains keywords such as from, to, please quote the new value to avoid error.
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

* `e/edit 5 title "today top priority is to do homework"`<br>
  Edit the fifth task title to today top priority is to do homework.
  
* `e/edit 1 tag null`<br>
  Remove all the tags of first task.

### 3.5. Marks a task as done or undone: `mark` 

Mark an existing task as done or undone based on a given valid index.<br>
Format: `m/mark <INDEX> <undone|done>`

> * The undone|done parameters are case insensitive.<br>
> * Marks the task at the specified `INDEX`.<br>
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list all`<br>
  `mark 2 done`<br>
  Marks the 2nd task in the task manager as done.
  
* `find do HW` <br>
  `mark 1 undone`<br>
  Marks the 1st task in the results of the `find` command as undone.

### 3.6. Searching all tasks based on keywords or date: `find`

Finds tasks which matched name/deadline/tag.<br>
Format: ` find <[NAME]|[DATE]|[TAG]>`

> * The search is case insensitive. e.g `do HW` will match `do hw`
> * The order of the keywords does not matter. e.g. `HW do` will match `do HW`
> * Substrings will be matched e.g. `HW` will match `HWs`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `HW` will match `do HW`
> * For exact match, name/deadline/tag should be inside a quote	

Examples:

* `find do HW`<br>
  Returns tasks with 'do' or 'HW' or both
* `find monday`<br>
  Returns tasks with dates on monday
* `find "home"`<br>
  Returns tasks with title|deadline/tag being home


### 3.7. Deleting a task : `delete`

Deletes the specified task from the TaskIt.
Format: `delete INDEX`

> * Deletes the task at the specified `INDEX`. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...

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

> * Selects the task at the specified `INDEX`.<br>
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...

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

<!-- @@author A0141011J -->
### 3.10. Undo previous action: `undo`

Undo the prevous actions.<br>
Format: `undo`

### 3.11. Redo previous undo: `redo`

Redo the prevous undone action.<br>
Redo can only work after a successful undo command<br>
Format: `redo`

### 3.12. Saving data: `save`

Puts all TaskIt data to the file in the specified path.
Format: `save FILEPATH`

Example:

* `save myFolder/myFile.xml`<br>

### 3.13. Change storage file path: `path`

Change the storage folder to the specified folder.
Format: `path FILEPATH`

Example:
* `path myFolder`<br>
### 3.14. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`



<!-- @@author A0141872E -->
## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous TaskIt folder.


## 5. Command Summary

* **Add**  `add <TITLE> [tag TAG|by DATE|from DATE to DATE|priority <high|medium|low>]` <br>
  e.g. `add Lunch with Bob tag friend priority low tag leisure`<br>
  e.g. `add friend's party tag leisure tag friend from next Wednesday 8 pm to next Wednesday at 11pm`<br>
  e.g. `add tag school tag assignment by 11pm "CS2103 revision"`<br>

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Edit**  `edit <INDEX>  <[title]|[start]|[end]|[priority]|[tag]> <NEW>` <br>
  e.g. `edit 1 title Movie`<br>
  e.g. `edit 2 from this Friday 3 pm`<br>
  e.g. `edit 2 end none`<br>
  e.g. `edit 2 end null`<br>
  e.g. `edit 1 tag School`<br>
  e.g. `edit 1 tag null`<br>
  e.g. `edit 1 title "today"`<br>

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find do HW SWE`

* **List** : `list` <br>
  e.g. `list all`<br>
  e.g. `list done|undone`<br>
  e.g. `list low|medium|high`<br>
  e.g. `list overdue`<br>
  e.g. `list today`<br>
  e.g. `list floating|event|deadline`<br>

* **Mark** : `mark` <br>
  e.g. `mark 1 undone`
  e.g. `mark 3 done`

* **Undo** : `undo` <br>
  
* **Redo** : `redo` <br>

* **Help** : `help` <br>

* **Select** : `select INDEX` <br>
  e.g.`select 2`
  
* **Save** : `save FILEPATH` <br>
  e.g.`save taskIt.xml`
  
* **Path** : `path FILEPATH` <br>
  e.g.`path newFolder`


