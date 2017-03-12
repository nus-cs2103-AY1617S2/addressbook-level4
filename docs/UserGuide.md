# AddressBook Level 4 - User Guide

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

3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.
> * Date has to be in the format DD-MM-YYYY

## 2.1. Viewing help : `help`

Format: help

> Is also displayed when an incorrect command is entered

## 2.2. Adding a task: `add`

Adds a task to task list
Format: add TASKNAME -d DATE [-o DESCRIPTION] [-p]

Examples:



Updates an existing task in the task list
Format: update INDEX [-n TASKNAME] [-d DATE] [-o DESCRIPTION] [-p]

Examples:


## 2.4. Delete a task: `delete`

Deletes an existing task
Format: delete INDEX

Examples:

* `delete 1`

## 2.5. Undo a command: 'undo'

Undo most recent command
Format: undo

## 2.6. Listing all tasks: `list`

Shows a list of all tasks
Format: list

## 2.7. Exiting the program : `exit`

Exits the program.
Format: exit

## 2.8. View tasks : `view`

Views the details of a task
Format: view INDEX

Examples:

* `view 1`

## 2.9. Finding all tasks containing any keyword in the task name: `find`

Finds tasks that satisfy given parameters

> 
> The search is case sensitive, the order of the keywords does not matter, only the name is searched, and tasks matching at least one keyword will be returned

Examples:


## 2.10. Clearing all entries : `clear`

Format: clear

## 2.11. Saving the data

Application data are saved onto hard drive automatically after any commands are executed that changes the data.
There is no need to manually save the data

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Help** : `help`



* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

  e.g. `find James Jake`

* **List** : `list` <br>

* **View** : `view INDEX` <br>
  e.g. `view 1`

* **Exit** : `exit` <br>
