# User Guide

Author: Jacob Levy

1. [Starting the Program](#Starting-the-Program)
2. [Features](#Features)
3. [Command Summary](#Command-Summary)

## Starting the Program

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `fasttask.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/UIFinal.png" width="600">
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**`add Groceries Shopping d/030117 s/09:00 e/12:00 m/Go to Cold Storage, Buy Extra milk` :
     adds a task called `Groceries Shopping` to the Task Manager.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

## Getting Help : `help`
Format: `help`

> Typing `help` will create a pop up of this User Guide on the user's computer. Internet connection is required. 

## Listing All Tasks : `list`
Shows a list of all active tasks.<br>
Format: `list`
 
## Adding a Task : `add`
Adds a task to the task manager<br>
Format: `add TASKNAME [d/DATE1 [DATE2]] [s/STARTTIME] [e/ENDTIME] [m/MESSAGE]` 
 
> Words in `UPPER_CASE` are the parameters, items in `[SQUARE_BRACKETS]` are optional. 
> `add` and `TASKNAME` must be in the order shown, but there is no set order for all other parameters.
> A format hint will be added if the user types in an incorrect command. 

> `s/STARTTIME` and `e/ENDTIME` can be entered in the format `mm:hh` or `mmhh`. Time is formated using a 24 hour clock. Additionally, many natural language options are available. A list of some possible natural language options are listed below.

* 06:00 hours
* 5:30 a.m.
* noon
* afternoon
* midnight

>`[d/DATE1 [DATE2]` can be entered in the format `ddmmyy`. Additionally, many natural language options are available. A list of some possible natural language options are listed below.

* 1-02-2017
* 2/28/79
* Fri, 21 Nov 2017
* Jan 21
* next thursday
* today
* tomorrow

Example: 
* `add Groceries Shopping d/030117 s/09:00 e/12:00 m/ Go to Cold Storage, Buy Extra milk`

## Finding a Task: `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is not case sensitive, the order of the keywords does not matter, only the name is searched, 
and tasks matching at least one keyword will be returned (i.e. `OR` search).

Examples: 
* `find groceries` returns `Groceries Shopping`

## Deleting a Task : `delete`
Deletes a specified task or tasks from the task manager. Irreversible.<br>
Format: `delete INDEX [MORE INDECIES]`

> Deletes the tasks at the specified `INDEX` or indecies. 
  The index refers to the task number shown in the list. 
  `INDEX` must be an exact match in order for the function to work.

Examples: 
* `delete 2`<br>
  
## Editing a Task : `edit`
Edits a specified task from the task manager.<br>
Format: `edit INDEX [TASKNAME] [d/DATE] [s/STARTTIME] [e/ENDTIME] [m/MESSAGE]`

> Edits the task at the specified `INDEX`. The index refers to the 
task number shown in the list. `INDEX` must be an exact match in order 
for the function to work.

Examples: `edit 2 m/Go to Ralphs`

## Clearing all Tasks : `clear`
Clears all tasks from the task manager.<br>
Format: `clear` 

## Undoing a Task : `undo`
Undos the most recent action up to 10 times.
Format: `undo`

## Completing a Task : `done`
Completes the specified tasks from the task manager and changes the task status from `Ongoing` to `Completed`.<br>
Format: `done INDEX [MORE INDECIES]`

## Filtering the Tasks : `filter`
Filters the list of tasks to only see `Ongoing` or `Completed` tasks.<br>
Format: `filter`

## Sorting the Tasks : `sort`
Sorts the tasks by date or by alphabet. 
Format: `sort name` or `sort time`
> Sorts tasks in either alphabetical order or by upcoming tasks. `sort time` will sort the tasks by the date that is closest to the current date. 
> To return to the unsorted list of tasks, type and enter `list`.

## Googling the Tasks: `google` 
Format: `google [INDEX]`
> Googles the task name at the specified index. The user can additionally Google a task name by single clicking on a task. 

## Changing the Save Path: `path`
Changes the save path for Fast Task data. Must specify the name of the save file and mark it as a .xml file.<br>
Format: `path PATHNAME`<br>
Example for Windows: `path C:\Desktop\MyTasks.xml`<br>
Example for Mac: `path /Users/jlevy/Desktop/MyTasks.xml`

## Loading Fast Task Data: `load`
Loads a save file for Fast Task data. Can be used for loading different sets of task data. Must specify the name of the save file and mark it as a .xml file.<br>
Format: `load PATHNAME`<br>
Example for Windows: `path C:\Desktop\MyTasks.xml`<br>
Example for Mac: `path /Users/jlevy/Desktop/MyTasks.xml`

## Exiting the Program : `exit`
Exits the program.<br>
Format: `exit`  

## Saving the Data 
Tasks manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## Save Location
Fast Task data are saved in a file called `TasksManagerData.xml` in the project root folder.

## 4. Command Summary

* **Help** : `help` <br>

* **Add**  `add TASKNAME [d/DATE1 [DATE2]] [s/STARTTIME] [e/ENDTIME] [m/MESSAGE]` <br>
  e.g. `add Groceries Shopping d/030117 s/09:00 e/12:00 m/Go to Cold Storage, buy extra milk`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find groceries`

* **List** : `list` <br>

* **Clear** : `clear`

* **Undo** : `undo` <br>

* **Done** : `done` <br>
e.g. `done 3`

* **Filter** : `filter` <br>

* **Sort** : `sort time` or `sort name`<br>

* **Path** : `path PATHNAME` <br>
 e.g. `path C:\Desktop\MyTasks.xml`
 
 * **Load** : `load PATHNAME` <br>
 e.g. `load C:\Desktop\MyTasks.xml`

* **Google** : `google INDEX` <br>
  e.g.`google 2`
