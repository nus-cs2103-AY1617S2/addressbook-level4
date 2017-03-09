# TaskIt - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

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
   * **`list`** : lists all contacts
   * **`add`**` do SWE project t/school t/CS` :
     adds a task titled `do SWE project` to the TaskIt.
   * **`delete`**` 3` : deletes the 3rd contact shown in the current list
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

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Add a new task to TaskIt<br>
Format: `add <title> [t/TAG]`

Examples:

* `add Group Meeting t/study t/school by Wednesday 8 pm`
* `add Interview t/work`

> Persons can have any number of tags (including 0)



### 2.3. Listing all tasks : `list`

List all the existing tasks in TaskIt.<br>
Format: `list`

List only the undone tasks in TaskIt.<br>
Format: `list undone`

### 2.4. Updating an existing task : `update`

Edits an existing task in TaskIt.<br>
Format: `update index  <name|description|deadline> new`

> * Edits the person at the specified `INDEX`.
    The index refers to the index number shown in the last person listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
> * You can remove all the person's tags by typing `t/` without specifying any tags after it. 

Examples:

* `update 2 name finish SWE HW`<br>
  Update the second task name to finish SWE HW.

* `Update 4 description work on user guide`<br>
  Update the fourth task description to work on user guide.
  
* `Update 1 deadline this Friday 3 pm`<br>
  Update the first task deadline to this Friday 3pm.

### 2.5. Searching all tasks containing any keyword in their name: `search`

Finds tasks which matched name/description/deadline/tag.<br>
Format: ` search <name|description|deadline|tag>`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find do HW`<br>
  Returns tasks with 'do' or 'HW' or both


### 2.6. Deleting a task : `delete`

Deletes the specified task from the TaskIt. 
Format: `delete index <name|description|deadline|tag>`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the address book.
* `find do HW`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Select a task : `select`

Selects the task identified by the index number used in the last person listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the address book.
* `find do HW` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all entries from the address book.<br>
Format: `clear`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

TaskIt data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Add**  `add TITLE [t/TAG]...` <br>
  e.g. `add Lunch with Bob t/friend t/leisure`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find do HW SWE`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`


