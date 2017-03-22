# Task Manager - User Guide

By : `W13-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [About](#about)
2. [Quick Start](#quick-start)
3. [Features](#features)
4. [FAQ](#faq)
5. [Command Summary](#command-summary)

## 1. About
Ever had the feeling that you are forgetting to do something important? Tired of always
forgetting events because you forgot to write it down? Docket is the application that 
will solve all your problems. 

Docket is a simple and lightweight application that will keep track of your tasks for you.
It will tell you if a task's due date is coming in a quick and easy to understand manner.

In contrast to other comprenhensive task managers out there, Docket is simple and 
straightforward. A single mode of input and a boiled down interface. Let's get started
on simplifying the way you handle tasks now.

## 2. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
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


## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.
> * Date has to be in the format DD-MM-YYYY

## 3.1. Viewing help : `help`

Brings up user guide<br>
Format: help

> Is also displayed when an incorrect command is entered

## 3.2. Adding a task: `add`

Adds a task to task list<br>
Format: add TASKNAME [dl/DATE] [ds/DESCRIPTION] [-p]
> Tasks includes:
> * Event tasks where deadlines include a specific timing
> * Floating tasks where deadlines are not specified
> * Normal tasks with deadlines

Examples:

* `add Make payment dl/01/08/2017 ds/Pay credit card bills -p`
* `add Lose weight dl/from 12/12/2017 to 24/12/2017`
* `add Performance rehearsal dl/today 10pm`
* `add Complete project dl/12/12/2017`
* `add Buy a cake`

## 3.3. Modifying a task: `edit`

Updates an existing task in the task list<br>
Format: edit INDEX [TASKNAME] [dl/DATE] [ds/DESCRIPTION] [-p]

Examples:

* `edit 3 buy eggs dl/29-02-2017 ds/as soon as possible`
* `edit 1 tie shoelace dl/25-12-2017`

## 3.4. Delete a task: `delete`

Deletes an existing task<br>
Format: delete INDEX

Examples:

* `delete 1`

## 3.5. Undo a command: 'undo'

Undo most recent command<br>
Format: undo

## 3.6. Redo a command: 'redo'

Redo most recent command<br>
Format: redo

## 3.7. Listing all tasks: `list`

Shows a list of all tasks<br>
Format: list

## 3.8. Exiting the program : `exit`

Exits the program.<br>
Format: exit

## 3.9. View tasks : `view`

Changes the different views of the task managers<br>
Format: view all|calendar|done|floating|overdue|today|tomorrow|future

Examples:

* `view all`
* `view floating`

## 3.10. Finding all tasks containing any keyword in the task name or description: `find`

Finds tasks that satisfy given parameters<br>
Format: find KEYWORDS [dl/DATE]

> finds tasks whose task name or description contains any of the given keywords
> ‘find dl/’ finds tasks whose deadline falls on specified date
> 
> The search is case insensitive, the order of the keywords does not matter, only the name is searched, and tasks matching at least one keyword will be returned

Examples:

* `find Project Name/Description`
* `find dl/12-08-2017`

## 3.11. Marking entries as done : `mark`

Marks completed tasks as done<br>
Format: mark INDEX

Examples:

* `mark 3`

## 3.12. Clearing all entries : `clear`

Clears all tasks in task manager<br>
Format: clear

## 3.13. Saving the data

Application data are saved onto hard drive automatically after any commands are executed that changes the data.
There is no need to manually save the data

## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 5. Command Summary

* **Help** : `help`

* **Add**  `add TASKNAME dl/DATE [ds/DESCRIPTION] [-p]` <br>
  e.g. `add Make payment dl/01-08-2017 ds/Pay credit card bills -p`

* **Edit** : `edit INDEX [TASKNAME] [dl/DATE] [ds/DESCRIPTION] [-p]` <br>
  e.g. `edit 3 buy eggs dl/29-02-2017 ds/as soon as possible`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`
   
* **Undo** : `undo` <br>

* **Redo** : `redo` <br> 

* **View** : `view all|calendar|done|floating|overdue|today|tomorrow|future` <br>
  e.g. `view all`
  
* **Find** : `find [KEYWORDS] [ds/KEYWORDS] [dl/DATE]` <br>
  e.g. `find James Jake`

* **List** : `list` <br>

* **Mark** : `mark INDEX` <br>
  e.g. `mark 4`

* **Exit** : `exit` <br>
