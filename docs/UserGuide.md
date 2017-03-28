# Awesome Todo - User Guide

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
2. Copy the file to the folder you want to use as the home folder for AwesomeTodo.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all Todos
   * **`add`**` Take the dog for a walk s/5:00PM 11/11/2011 e/6:00PM 11/11/2011` :
   * **`delete`**` 3` : deletes the 3rd Todo shown in the current list
   * **`edit`**` 3 take the cat for a walk e/6:00am 11/11/2017` : edits the 3rd Todo's title and end time shown in the current list.
   * **`complete`**` 3`: marks the 3rd Todo shown in the current list as complete
   * **`uncomplete`**` 3`: marks the 3rd Todo shown in the current list as uncomplete
   * **`savefile`** ` data/newsave.xml`: changes the location of the save file to data/newsave.xml
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

Adds a floating task<br>
Format: `add TASK [t/TAG]`

Adds an event<br>
Format: `add EVENT s/STARTTIME e/ENDTIME [t/tag]`

Adds a deadline<br>
Format: `add DEADLINE e/DEADLINE [t/tag]`

Examples:

* `add Finish math homework t/school`
* `add Take the dog for a walk s/5:00PM 11/11/2011 e/6:00PM 11/11/2011`
* `add Finish programming project d/5:00PM 11/11/2011`

### 2.3. Listing all todos : `list`

Shows a list of all todos.<br>
Format: `list`

Shows a list of all uncompleted todos.<br>
Format: `list u`

Shows a list of all completed todos.<br>
Format: `list c`

Shows a list of all floating tasks.<br>
Format: `list f`

Shows a list of all deadlines.<br>
Format: `list d`

Shows a list of all events.<br>
Format: `list e`

### 2.4. Editing a todo : `edit`

Edits a Todo's title, start time and endtime.<br>
Format: `edit INDEX [TASK] [s/STARTTIME] [e/ENDTIME] [t/TAG]`

<<<<<<< HEAD
Edits a Todo's name and deadline.<br>
Format: `edit INDEX [TASK] [e/DEADLINE] [t/TAG]`

Adds a deadline.<br>
Format: `edit INDEX [e/DEADLINE]`

Adds a start time and endtime.<br>
Format: `edit INDEX [s/STARTTIME] [e/ENDTIME]`

Edits a todo title.<br>
=======
Edits a deadline.<br>
Format: `edit INDEX [TASK] [e/DEADLINE] [t/TAG]`

Edits a task.<br>
>>>>>>> 38691468cdfcc9582389a61fb2b8bcdb194d3dff
Format: `edit INDEX [TASK] [t/TAG]`

Adds a tag to a task.<br>
Format: `edit [ta/TAG]`

> * Edits the todo at the specified `INDEX`.
    The index refers to the index number shown in the last todo listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least a new title or one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * Edit with "t/" may remove the existing tags and replace them with the tags in command line.
> * Edit with "s/" "e/" may update the task with default Datetime.

Examples:

* `edit 1 Play with dog e/5:00PM 11/11/2011`<br>
  Edits the task description and deadline of the 1st todo to be listed

### 2.5. Finding all todos containing any keyword in their description: `find`

Finds todos whose descriptions contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive.
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

### 2.7. Mark todo as completed : `complete`

Marks the specified todo as completed.<br>
Format: `complete INDEX [COMPLETETIME]`

> * If COMPLETETIME is not specified, COMPLETETIME will be automatically set to the current time

Examples:

* `complete 1`<br>
* `complete 2 17-03-17T8:00`<br>

### 2.8. Mark todo as uncompleted : `uncomplete`

Marks the specified todo as uncompleted.<br>
Format: `uncomplete INDEX`

Example:

* `uncomplete 1`<br>

### 2.9. Undo last command : `undo`

Undoes last command if it involves changing a todo.<br>
Format: `undo`

### 2.10. Clearing all entries : `clear`

Clears all todos. Irreversible.<br>
Format: `clear`

### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.12. Saving the data

Todo data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.13 Setting the data save location : `savefile`

Sets the save file location containing todo data.<br>
Format: `savefile PATH_TO_FILE`

> Changes the file save location to specified `PATH_TO_FILE`. <br>

Examples:

* `savefile Documents\savefiles\todofile.xml`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Awesome Todo folder.

## 4. Command Summary

* **Add Task** : `add TASK` <br>
  e.g. `add Finish math homework`

* **Add Event** : `add TODO s/STARTTIME e/ENDTIME` <br>
  e.g. `add Take the dog for a walk s/6:00PM 11/11/2017 e/7:00PM 11/11/2017`

* **Add Deadline** : `add TODO e/DEADLINE` <br>
  e.g. `add Finish programming project e/17-03-17T8:00`

* **Edit** : `edit INDEX [TASK] s/STARTTIME e/ENDTIME t/TAGS` <br>
  e.g. `edit 1 Take cat for a walk s/11:11am 11/11/2011 e/12:12pm 11/11/2011 t/cat`

* **Clear** : `clear` <br>
  e.g. `clear`

* **Delete** : `delete INDEX` <br>
  e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find Dog`

* **Complete** : `complete INDEX [COMPLETETIME]` <br>
  e.g. `complete 1 17-03-17T8:00`

  * **Uncomplete** : `uncomplete INDEX` <br>
  e.g. `uncomplete 1`

* **Undo** : `undo` <br>
  e.g. `undo`

* **List** : `list` <br>
  e.g. `list`

* **Set Save File Location** : `savefile FILE_PATH` <br>
  e.g. `savefile ~/User/Documents/todolist.xml`

* **Help** : `help` <br>
  e.g. `help`

* **Exit** : `exit` <br>
  e.g. `exit`

