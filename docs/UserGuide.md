# User Guide

This product is not meant for end-users and therefore there is no user-friendly installer. 
Please refer to the [Setting up](DeveloperGuide.md#setting-up) section to learn how to set up the project.

## Starting the program

1. Locate the .jar file
2. Right click on the project
3. Click `Run As` > `Java Application`
4. The GUI should appear in a few seconds.

## Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
## Adding a task : `add`
Adds a task to the task manager<br>
Format: `add TASKNAME [d/DATE] [s/STARTTIME] [e/ENDTIME] [m/MESSAGE]` 
 
> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> A format hint will be added to the command line so users know how to input the correct format

Examples: 
* `add groceries shopping 030117 0900 1200 Cold Storage Buy Extra milk`

## Listing all tasks : `list`
Shows a list of all active tasks.<br>
Format: `list`

## Finding all tasks containing any keyword in their name: `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is not case sensitive, the order of the keywords does not matter, only the name is searched, 
and tasks matching at least one keyword will be returned (i.e. `OR` search).

Examples: 
* `find groceries`<br>
  Returns `groceries shopping` but not `groceries`

## Deleting a task : `delete`
Deletes a specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

> Deletes the name of the tasks at the specified `INDEX`. 
  The index refers to the task number shown in the list. 
  `INDEX` must be an exact match in order for the function to work.

Examples: 
* `list`<br>
  `delete groceries shopping`<br>
  Deletes groceries shopping in the task manager.
  
## Editing a task : `edit`
Edits a specified tasks from the task manager. Irreversible.<br>
Format: `edit INDEX [TASKNAME] [d/DATE] [s/STARTTIME] [e/ENDTIME] [m/MESSAGE]`

> Edits the task at the specified `INDEX`. The index refers to the 
task number shown in the list. `INDEX` must be an exact match in order 
for the function to work.
  
## View description of a task : `view`
Displays details of the specified task.<br>
Format: `view TASKNAME`

> Views the task at the specified `TASKNAME`. 
  The TASKNAME refers to the name shown in the list.
  Fast Task will show all tasks that match with `TASKNAME`.

Examples: 
* `list groceries shopping`<br>
  Views the desrciption in the task manager.

## Clearing all entries : `clear`
Clears all tasks from the task manager.<br>
Format: `clear`  

## Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

## Saving the data 
Tasks manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## Changing the save location
Tasks manager data are saved in a file called `Tasksmanager.txt` in the project root folder.
You can change the location by specifying the file path as a program argument.<br>

> The file name must end in `.txt` for it to be acceptable to the program.
>
> When running the program inside Eclipse, you can 
  [set command line parameters before running the program](http://stackoverflow.com/questions/7574543/how-to-pass-console-arguments-to-application-in-eclipse).
