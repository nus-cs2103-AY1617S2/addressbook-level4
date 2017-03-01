# Awesome ToDo - User Guide

By : `T11-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `awesometodo.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for AwesomeToDo.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all ToDos
   * **`add`**` Take the dog for a walk s/11-11-2017 e/11-11-2017/17:30` :
   * **`delete`**` 3` : deletes the 3rd ToDo shown in the current list
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

### 2.2. Adding a todo: `add`

Adds a task<br>
Format: `add task TODO

Adds an event<br>
Format: 'add event TODO s/STARTTIME e/ENDTIME'

Adds a deadline<br>
Format: 'add deadline TODO d/DEADLINE'

Examples:

* `add task Finish math homework`
* `add event Take the dog for a walk s/11-11-2017/17:00 e/11-11-17/17:30`
* `add deadline Finish programming project d/11-11-2017/17:00`

### 2.3. Listing all todos : `list`

Shows a list of all todos.<br>
Format: `list`

Shows a list of all uncompleted todos.<br>
Format: `list -u`

Shows a list of all completed todos.<br>
Format: `list -c`

Shows a list of all tasks.<br>
Format: `list -t`

Shows a list of all deadlines.<br>
Format: `list -d`

Shows a list of all events.<br>
Format: `list -e`

### 2.4. Editing a todo : `edit`

Edits an existing event.<br>
Format: `edit INDEX [TASK] [s/STARTTIME] [e/ENDTIME]`

Edits an existing deadline.<br>
Format: `edit INDEX [TASK] [d/DEADLINE]`

Edits an existing task.<br>
Format: `edit INDEX [TASK]`


> * Edits the todo at the specified `INDEX`.
    The index refers to the index number shown in the last todo listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> 
Examples:

* `edit 1 Play with dog d/11-11-2017/16:00`<br>
  Edits the task description and deadline of the 1st todo to be listed

### 2.5. Finding all todos containing any keyword in their description: `find`

Finds todos whose descriptions contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive.
> * The order of the keywords does not matter.
> * Only the task description is searched.
> * Only full words will be matched e.g.
> * Todos matching at least one keyword will be returned (i.e. `OR` search).

Examples:

* `find Dog`<br>
* `find dog math`<br>
  Returns Any todo containing words `dog` or `math`

### 2.6. Deleting a todo : `delete`

Deletes the specified todo. Irreversible.<br>
Format: `delete INDEX`

> Deletes the todo at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd todo in the most recent listing.
* `find Dog`<br>
  `delete 1`<br>
  Deletes the 1st todo in the results of the `find` command.

### 2.7. Select a todo : `select`

Selects the todo identified by the index number used in the last todo listing.<br>
Format: `select INDEX`

> Selects the todo
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd todo in the listing.
* `find Dog` <br>
  `select 1`<br>
  Selects the 1st todo in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all todos. Irreversible.<br>
Format: `clear`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

Todo data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Awesome ToDo folder.

## 4. Command Summary

* **Add Task**  `add task TODO` <br>
  e.g. `add taskFinish math homework`

* **Add Event**  `add event TODO s/STARTTIME e/ENDTIME`
  e.g. `add event Take the dog for a walk s/11-11-2017/17:00 e/11-11-17/17:30`

* **Add Deadline**  `add deadline TODO d/DEADLINE`
  e.g. `add deadline Finish programming project d/11-11-2017/17:00`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find Dog`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`