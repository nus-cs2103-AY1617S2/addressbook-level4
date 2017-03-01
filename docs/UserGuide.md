# Suru - User Guide

By : `W09-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp;

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `suru.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` write essay `**by**` 13/3/17` : Adds a task "write essay" with a due date "13/3/17"
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

### 2.1. Viewing usage instructions : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task with no specified completion date: `add`

Adds a task to the task manager<br>
Format: `add DESCRIPTION`

> Tasks will be added to the task manager with no due date or duration.

Examples:

* `add write essay`

### 2.3. Adding a task with a due date: `add ... by ...`

Adds a task with a due date to the task manager<br>
Format: `add DESCRIPTION by DUE_DATE`

> Tasks with a due date will be added to the task manager.

Examples:

* `add write essay by 13/7/17`
* 
### 2.4. Adding a task with a start date: `add ... from ...`

Adds a task with a start date to the task manager<br>
Format: `add DESCRIPTION from START_DATE`

> Tasks with a start date will be added to the task manager.

Examples:

* `add write essay from 13/7/17`

### 2.5. Adding a task with a duration: `add ... from ... to ...`

Adds a task to the task manager<br>
Format: `add DESCRIPTION from START_DATE to END_DATE`

> Tasks will be added to the task manager with a duration.

Examples:

* `add write essay from 13/7/17 to 15/7/17`

### 2.6. Adding a task with tags: `add ... tags: ...`

Adds tags to the task <br>
Format: `add DESCRIPTION tags:`

> Tasks will be added to the with a tags.

Examples:

* `add write essay from 13/7/17 to 15/7/17 tags: school, homework`
* `add write essay tags: blogging`


### 2.6. Listing all tasks : `list`

Shows a list of all tasks in the task manager.<br>
Format: `list`

### 2.7. Listing undone tasks : `list undone`

Shows a list of all undone tasks in the task manager.<br>
Format: `list`

### 2.8. Listing done tasks : `list done`

Shows a list of all done tasks in the task manager.<br>
Format: `list`

### 2.9. Edit task : `edit INDEX`
> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last tasks listing.
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields of the task must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by editing the tag to an empty value. 

Examples:

* `edit 1 buy groceries by 13/5/17`<br>
  Edits the description of the task to `buy groceries` and due date to `13/5/17` respectively.

* `edit 2 buy groceries tags:`<br>
  Edits the name of the 2nd person to be `buy groceries` and clears all existing tags.

### 2.5. Finding all persons containing any keyword in their name: `find`

Finds persons whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find John`<br>
  Returns `John Doe` but not `john`
* `find Betsy Tim John`<br>
  Returns Any person having names `Betsy`, `Tim`, or `John`

### 2.6. Deleting a person : `delete`

Deletes the specified person from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the person at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd person in the address book.
* `find Betsy`<br>
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

### 2.7. Select a person : `select`

Selects the person identified by the index number used in the last person listing.<br>
Format: `select INDEX`

> Selects the person and loads the Google search page the person at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd person in the address book.
* `find Betsy` <br>
  `select 1`<br>
  Selects the 1st person in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all entries from the address book.<br>
Format: `clear`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Add**  `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...` <br>
  e.g. `add James Ho p/22224444 e/jamesho@gmail.com a/123, Clementi Rd, 1234665 t/friend t/colleague`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find James Jake`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`


