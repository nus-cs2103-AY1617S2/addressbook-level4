# User Guide

This product is not meant for end-users and therefore there is no user-friendly installer. 
Please refer to the [Setting up](DeveloperGuide.md#setting-up) section to learn how to set up the project.

## Starting the program

1. Locate the .jar file
2. Right click on the project
3. Click `Run As` > `Java Application`
4. The GUI should appear in a few seconds.

## Getting Help : `help`
Format: `help`

> Typing `help` will create a pop up of this User Guide on the user's computer. Internet connection is required. 

## Listing All Tasks : `list`
Shows a list of all active tasks.<br>
Format: `list`
 
## Adding a Task : `add`
Adds a task to the task manager<br>
Format: `add TASKNAME [d/DATE] [s/STARTTIME] [e/ENDTIME] [m/MESSAGE]` 
 
> Words in `UPPER_CASE` are the parameters, items in `[SQUARE_BRACKETS]` are optional. 
> `add` and `TASKNAME` must be in the order shown, but there is not set order for all other parameters.
> A format hint will be added if the user types in an incorrect command. 

> `d/DATE` can be entered in the format `12/12/12` or `12-12-12`. `s/STARTTIME` and `e/ENDTIME` can be entered in the format `12:12` or `1212`. Time is formated using a 24 hour clock. 

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
Deletes a specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

> Deletes the name of the tasks at the specified `INDEX`. 
  The index refers to the task number shown in the list. 
  `INDEX` must be an exact match in order for the function to work.

Examples: 
* `delete 2`<br>
  
## Editing a Task : `edit`
Edits a specified tasks from the task manager. Irreversible.<br>
Format: `edit INDEX [TASKNAME] [d/DATE] [s/STARTTIME] [e/ENDTIME] [m/MESSAGE]`

> Edits the task at the specified `INDEX`. The index refers to the 
task number shown in the list. `INDEX` must be an exact match in order 
for the function to work.

Examples: 
* `edit 2` m/Go to Ralph's

## Googling a Task or Phrase: `google [INDEX] [KEYWORDS]` 
> Googles the task name at the specified index or Googles the key words that the user enters.  

## Clearing All Entries : `clear`
Clears all tasks from the task manager.<br>
Format: `clear`  

## Exiting the Program : `exit`
Exits the program.<br>
Format: `exit`  

## Saving the Data 
Tasks manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## Save Location
Tasks manager data are saved in a file called `TasksManagerData.xml` in the project root folder.
