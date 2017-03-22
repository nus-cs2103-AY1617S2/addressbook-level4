# Docket - User Guide

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

When you are unsure about the commands used by the program, 
use this command to bring up a list of commands you can use. 
This is also displayed when an incorrect command is entered.<br>
Format: help

> Is also displayed when an incorrect command is entered

## 3.2. Adding a task: `add`

This command adds a task to your task list to keep track of it. 
Enter the name, the deadline, and a brief description of the 
task you want to keep track of.<br>
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

## 3.3. Listing all tasks: `list`

Shows you a list of all tasks with an index number attached, 
you will need this index number to use some of the tasks below.<br>
Format: list

## 3.4. View tasks : `view`

Changes the different views of the task managers so that you can 
better differentiate the time that the task is due.<br>
Format: view all|calendar|done|floating|overdue|today|tomorrow|future

Examples:

* `view all`
* `view floating`

## 3.5. Modifying a task: `edit`

You can change the details of a task that you are currently 
keeping track of with the edit command.<br>
Format: edit INDEX [TASKNAME] [dl/DATE] [ds/DESCRIPTION] [-p]

Examples:

* `edit 3 buy eggs dl/29-02-2017 ds/as soon as possible`
* `edit 1 tie shoelace dl/25-12-2017`

## 3.6. Delete a task: `delete`

No longer need to do a task? Removes it from the task list 
to forget about it.<br>
Format: delete INDEX

Examples:

* `delete 1`

## 3.7. Undo a command: `undo`

Made a mistake? Use this command to quickly revert the 
changes made with the last command used. <br>
Format: undo

## 3.8. Redo a command: `redo`

Changed your mind? Use this command to quickly reapply 
the changes that were removed with undo command. 
This can only be used after an undo command. <br>
Format: redo

## 3.9. Finding all tasks using keyword or deadline: `find`

Helps you quickly finds tasks whose name contain any of the 
given keywords. The results will come with an index number 
attached.<br>
Format: find KEYWORDS [dl/DATE]

> finds tasks whose task name or description contains any of the given keywords
> ‘find dl/’ finds tasks whose deadline falls on specified date
> 
> The search is case insensitive, the order of the keywords does not matter, only the name is searched, and tasks matching at least one keyword will be returned

Examples:

* `find Project Name/Description`
* `find dl/12-08-2017`

## 3.10. Marking entries as done : `mark`

After completing a task, use this command to mark a task as completed<br>
Format: mark INDEX

Examples:

* `mark 3`

## 3.11. Clearing all entries : `clear`

Clears your list of tasks to start from a clean slate.<br>
Format: clear

## 3.12. Exiting the program : `exit`

When you are tired and want to stop tracking tasks for the day, 
use this command to exit the program. <br>
Format: exit

## 3.13. Saving the data

Application data are saved onto hard drive automatically after any commands are executed that changes the data.
There is no need to manually save the data

## 4. FAQ

**Q**: Where do I download the Task Manager Application?
**A**: The Application can be found at https://github.com/CS2103JAN2017-W13-B2/main to download.

**Q**: Why are there no tasks displayed after I added some tasks?
**A**: Use the command `list` to display all tasks.

**Q**: Where do I get the INDEX number?
**A**: The INDEX number is obtained when the “list” or “find” command is used.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 5. Command Summary

* **Help** : `help`

* **Add**  `add TASKNAME dl/DATE [ds/DESCRIPTION] [-p]` <br>
  e.g. `add Make payment dl/01-08-2017 ds/Pay credit card bills -p`

* **List** : `list` <br>

* **View** : `view all|calendar|done|floating|overdue|today|tomorrow|future` <br>
  e.g. `view all`
  
* **Edit** : `edit INDEX [TASKNAME] [dl/DATE] [ds/DESCRIPTION] [-p]` <br>
  e.g. `edit 3 buy eggs dl/29-02-2017 ds/as soon as possible`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`
   
* **Undo** : `undo` <br>

* **Redo** : `redo` <br> 

* **Find** : `find [KEYWORDS] [ds/KEYWORDS] [dl/DATE]` <br>
  e.g. `find James Jake`

* **Mark** : `mark INDEX` <br>
  e.g. `mark 4`

* **Clear** : `clear` <br>

* **Exit** : `exit` <br>
