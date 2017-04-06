# Yesterday's Tomorrow - User Guide

By : `Team CS2103JAN2017-F11-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `ytomorrow.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your TODO list.
3. Double-click the file to start the app. The GUI should appear in a few seconds.

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all TODOs
   * **`add`**` Email manager due 2017/01/08 0900 : adds a task "Email manager" to the TODO list and set the due date to January 8th at 9am.
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

Shows information about how to use Yesterday's Tomorrow.
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task to the task list<br>
Format: `add TASK_NAME [s/START TIME] [d/END TIME] e/EMAIL g/GROUP`

> Persons can add a task without defining either start time or end time.
> local time will be set as default if start time and end time parameters do not specify time .
> TASK_NAME should only contain alphanumeric characters and spaces, and it should not be blank.

Examples:

* **Normal task**: `add CS2103 assignment s/Feb 3 d/Apr 6 e/example@gmail.com g/study`
* **Task with deadline**: `add CS2013 assignment d/tomorrow e/example@gmail.com g/study`
* **Floating task**: `add CS2013 assignment e/example@gmail.com g/study`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks (**completed** or **incompleted**).<br>
Format: `list`

> The task list will be ordered by ascending end date. 

### 2.4. Editing a task : `edit`

Edits an existing task in the task list.<br>
Format: `edit INDEX PARAMETER NEW_VALUE` <br>

> * Edits the task at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, ...
> * One parameter and updated value needs to be provided.
> * Existing value will be updated to the input value.

Examples:

* `edit 1 newName`<br>
  Edits the name of the 1st item to "newName".

* `edit 2 g/weekend`<br>
  Edits the group of the 2nd task to "weekend".

### 2.5. Marking a task as complete : `Mark`

Changes the status of one task to **complete**.
Format: `mark INDEX` <br>

> * Mark the task at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `mark 1`<br>
  Marks the 1st task as completed.
  
### 2.6. Marking a task as incomplete : `Unmark`

Marks a task in current task list as incomplete.
Format: `unmark INDEX` <br>

> * Unmark the task at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `unmark 1`<br>
  Marks the 1st task as incomplete.

### 2.7. Deleting a task : `delete`

Deletes the specified task. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task.
* `find milk`<br>
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

### 2.8. Finding all tasks containing keyword in their task name: `find`

Finds tasks whose name contain any of the given keywords.<br>
Format: `find KEYWORD`

> * The search is not case-sensitive. e.g `milk` **will** match `Milk`
> * The order of the keywords does not matter. e.g. `buy milk` will match `milk buy`
> * The title and description is searched.
> * Only full words will be matched e.g. `day` will not match `monday`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `milk` will match `buy milk`

Examples:

* `find John`<br>
  Returns `John Doe` and `john`
* `find Betsy Tim John`<br>
  Returns Any person having names `Betsy`, `Tim`, or `John`

### 2.9. Undo : `undo`

Undoes the previous action. This command will return the program to the state it was in before the previous action was executed<br>
Format: `undo`

### 2.9. Redo : `redo`

Does the undo function again. This command will return the program to the state it was in before the previous undo action was executed<br>
Format: `redo`

### 2.10. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.13. Saving the data
Yesterday's Tomorrow data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Yesterday's Tomorrow folder.
 **Q**: I found a bug in the program, how do I report it?<br>
 **A**: Please create a new issue via this [link](https://github.com/CS2103JAN2017-T11-B4/main/issues) with a detailed title and description. Please also include [BUG] in the beginning of the title.

**Q**: I am a developer. How can I contribute to the project?<br>
**A**: Please feel free to open a pull request with implementation of new features. Our only request is that you review the [developer guide](https://github.com/CS2103JAN2017-T11-B4/main/blob/developer-guide/docs/DeveloperGuide.md) and abide to all the requirements.

**Q**: Is it possible for me to change the appearance of the application?<br>
**A**: At the moment, there are no ways to edit the theme of the application.


## 4. Command Summary

* **Help**: `help` <br>
  e.g. `help`

* **Add**  `add TASK_NAME [s/START TIME] [d/END TIME] e/EMAIL g/GROUP`
  e.g. `add CS2103 assignment s/Feb 3 d/Apr 6 e/example@gmail.com g/study` <br> 
       `add CS2103 assignment d/Apr 6 e/example@gmail.com g/study` <br>
       `add CS2103 assignment e/example@gmail.com g/study` <br>
	   
* **List All**: `list` <br>
  e.g. `list`

* **Edit**: `edit INDEX PARAMETER NEW_VALUE` <br>
  e.g. `edit 2 g/learning`
  
* **Mark**: `mark INDEX` <br>
  e.g. `mark 1`

* **Unmark**: `unmark INDEX` <br>
  e.g. `unmark 2`

* **Delete**: `delete INDEX` <br>
  e.g. `delete 1`

* **Find**: `find KEYWORD ` <br>
  e.g. `find tutorial`

* **Undo**: `undo` <br>
  e.g. `undo`

* **Redo**: `redo` <br>
  e.g. `redo`
  
* **Exit**: `exit` <br>
  e.g. `exit`
