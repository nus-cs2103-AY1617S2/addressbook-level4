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

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Meeting dl/15-05-2017 ds/Meeting Room 1 t/important` :
     adds a task named `Meeting` to the Task Manager.
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
> * Date has to be in the format DD-MM-YYYY

## 2.1. Viewing help : `help`

Format: help

> Is also displayed when an incorrect command is entered

## 2.2. Adding a task: `add`

Adds a task to task list
<<<<<<< HEAD
Format: add TASKNAME -d DATE [-o DESCRIPTION] [-p]
=======
Format: add TASKNAME dl/DATE [ds/DESCRIPTION] [-p]
>>>>>>> origin/master

Examples:

* `add Make payment dl/01/08/2017 ds/Pay credit card bills -p`
* `add Complete project dl/12/12/2017`

## 2.3. Modifying a task: `edit`

Updates an existing task in the task list
Format: update INDEX [-n TASKNAME] [-d DATE] [-o DESCRIPTION] [-p]
Format: edit INDEX [TASKNAME] [dl/DATE] [ds/DESCRIPTION] [-p]

Examples:

* `edit 3 buy eggs dl/29-02-2017 ds/as soon as possible`
* `edit 1 tie shoelace dl/25-12-2017`

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
Format: find KEYWORDS [dl/DATE] [ds/KEYWORDS] 

> finds tasks whose task name contain any of the given keywords
> ‘find dl/’ finds tasks whose deadline falls on specified date
> ‘find ds/’ finds tasks whose task description contain any of the given keywords
> 
> The search is case sensitive, the order of the keywords does not matter, only the name is searched, and tasks matching at least one keyword will be returned

Examples:

* `find Project Name`
* `find ds/Some description of the project`
* `find dl/12-08-2017`

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

* **Add**  `add TASKNAME dl/DATE [ds/DESCRIPTION] [-p]` <br>
  e.g. `add Make payment dl/01-08-2017 ds/Pay credit card bills -p`

* **Update** : `update INDEX [TASKNAME] [dl/DATE] [ds/DESCRIPTION] [-p]` <br>
  e.g. `update 3 buy eggs dl/29-02-2017 ds/as soon as possible`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find [KEYWORDS] [ds/KEYWORDS] [dl/DATE]` <br>
  e.g. `find James Jake`

* **List** : `list` <br>

* **View** : `view INDEX` <br>
  e.g. `view 1`

* **Exit** : `exit` <br>
