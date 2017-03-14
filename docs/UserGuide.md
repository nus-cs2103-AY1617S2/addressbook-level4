# TaskBoss - User Guide

By : `Team W14-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [Command Summary](#command-summary)
 

## TaskBoss Prototype
<img src="images/TaskBoss (all tasks).png" width="600"> <br>

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Run the task manager, TaskBoss. 

2. **Add your first task**<br>
   Initially, you will have a clear task manager. TaskBoss interface will show you three different panels. All panels should be void of anything at this point with the exception of the panel on the left side of the screen, it should have two categories by default by the names All Tasks and Done. To get started, you can add your first task by entering the correct command in the command panel that you see at the bottom of your screen. Set your first task’s details:
  a. Assign a title for your task
  b. Add in any descriptions you want to specify to your task. 
  c. Specify the task’s deadlines if needed
  d. Decide on the task’s priority level
  e. Set a start and end time if need be.

3. **View your tasks**<br> 
  Now that you have added in your first task, you should be able to see it in on the panel on the right side of your screen. You can always refer to this panel to see what tasks you have yet to complete. The panel will display your tasks based on the tasks’ deadlines then according to the priority level you’ve assigned the tasks. Notice how the All Tasks category is highlighted in red. This means your are currently seeing all the available tasks according to the default display setting mentioned above.

4. **Edit your tasks**<br> 
  You can edit a task’s details simply by entering the command in the command panel. You should be able to edit and update the task’s title, description, priority level, and deadlines. 

5. **Mark done or delete your tasks**<br>
   Now that you’ve added a task and know how to edit it, you can check off the task once you’ve completed it. Enter the correct command prompt in the command panel. Doing so will automatically remove the said task from the All tasks category, the panel on the right, and add it to the list of tasks that have been completed which are all under the Done category. If you wish to move the marked done task back into your pending tasks list, you can do so by editing the task’s category and add it into an existing category. Your task will then appear under the category you have selected and under the All Tasks category.

	In the case you want to delete the task permanently, you can choose to delete it by entering the command in the command panel. Or in the case where you wish to permanently remove all tasks under a specific category, simply enter the command responsible for doing just that, the clear command.

6. **Create your first category**<br>
   TaskBoss gives you the option to create categories in order to organize your tasks. Create your categories by entering the correct command into the command panel. Now, edit the task that you want to put under the new category, and now you should be able to enlist it under the new category you created. You can also choose to edit the category name by entering the correct command prompt. By choosing to view all tasks under a specific category, you will see that the category’s name on the left panel will be highlighted. This will help in letting you know which category you’re viewing.
   
7. **Sort your tasks**<br>
    With the correct command prompt in the command panel, you can view all your tasks in list view or sorted based on deadlines and priority levels. In addition, you can choose to view all the tasks that belong under a given category. Similarly, you can view the tasks that have been marked done or tasks that still need to be completed. 
    The result of these commands should be visible on the main panel, the panel on the right.

8. **View a specific task**<br>
 You can view any particular task by entering the correct command. The task will appear on the main panel, the panel of the right, with all it’s details clearly stated.
 
9. **Overdue**<br>
 If a pending task was overdue, TaskBoss will automatically highlight the task for you to take note of.
 
10. **Undo**<br>
  If at any point, you entered the wrong command or want to reverse your last command, then you can simply enter the command for undoing your last command. 
  In the case where you enter the wrong format of a command line, an error message will appear above the command panel, instructing you on the correct format and/or let you know if no such command exists.
  
11. **Help**<br>
  By entering the help command, you will be able to see all the different commands that you can do on your tasks and their descriptions. You should also see how each command should be formatted when entered to produce your intended action. This will be displayed as a pop up message which you can make dissapear after getting the help that you want by simply typing anything in the command panel.

12. **Search for a task**<br>
 If you want to search for a particular task, you can do so with the correct command prompt. This command allows you to search for a task based on title, deadline, or a keyword from the description. The search result will be displayed on the display panel on the right.

13. **Export and save your tasks**<br>
 At any point while using the TaskBoss, you can choose to export your tasks into a specific file whether it exists or not to save your tasks by entering the correct command in the command panel.

14. **Exit**<br>
 You can exit the program at any point by simply entering the correct exit command

15. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

### 2.2. Adding a task: `add`

Adds a task to the TaskBoss<br>
Format: `add n/TASK_NAME [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]`

> * Date can be written in any format whether using slashes, dashes, or natural language  <br>
> * Time should be in 24-hour clock format or 12 hour format with AM or PM next to it, i.e `hr:min <PM/AM>` <br>
> * Priority level is either `1` (low priority), `2` (medium priority) or `3` (high priority).
> * Order of optional parameters does not matter.
> * All fields are optional except TASK_NAME.

Examples:

* `add n/Buy groceries ed/19-02-2017 c/Home p/3`
* `add n/Dinner with Jim i/@Orchard v0.0 sd/next friday ed/19-02-2017 c/Leisure p/2`
* `add n/Post-exam celebration i/@Zouk sd/tomorrow at 3 PM ed/tomorrow 20.30  c/Leisure p/1`


### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the TaskBoss.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the TaskBoss.<br>
Format: `edit INDEX [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * Order of the optional parameters does not matter.

Examples:

* `edit 1 i/Use Stack ed/23:59`<br>
  Edits the task information and end time of the 1st task to be `Use Stack` and `23:59` respectively.
  
* `edit 3 p/3`<br>
  Edits the priority level of 3rd task to be 3. 

### 2.5. Finding all tasks by Name: `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `find n/TASK_NAME` 

> * The search is case-sensitive. e.g `Project` will not match `project`
> * The order of the keywords does not matter. e.g. `meeting project` will match `project meeting`
> * The name/date/information is searched according to the command user enters.
> * Only full words will be matched e.g. `meeting` will not match `meetings`

Examples:

* `find n/Meeting`<br>
  Returns all tasks whose name contains `Meeting`
  
* `find ed/04-02-2017`<br>
  Returns all tasks with the end day `04-02-2017`

### 2.5. Finding all tasks by Deadline: `find`

Finds tasks whose deadlines contain any of the given keywords.<br>
Format: `find sd/date and time` `find ed/date and time`

> * The date is searched according to the date format user enters.
> * The date format for find date command is restricted either DD-MM-YYYY or DD/MM/YY formats 
or MM-DD-YYYY or MM/DD/YYYY, depending on your system's settings.

Examples:

* `find sd/04-02-2017`<br>
  Returns all tasks with the start date `04-02-2017`
  
* `find ed/05-02-2017`<br>
  Returns all tasks with the end date `05-02-2017`
  
### 2.6. Deleting a task : `delete`

Deletes the specified task from the TaskBoss.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the TaskBoss.
  
* `find n/Meeting`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Creating a category : `new`

Creates a new category in the task Boss.<br>
Format: `new CATEGORY`

Example:

* `new Study`<br>

### 2.8. Clearing tasks by category : `clear`

Clears all tasks under the specified category from TaskBoss.<br>
Format: `clear CATEGORY`

> The default categories of TaskBoss are `All Tasks` and `Done`. <br>

### 2.9. Viewing a task : `view`

Views a task by entering the task index from the TaskBoss.<br>
Format: `view INDEX`

> Views the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

### 2.10. Modifying a category name : `name`

Modifies a category name in TaskBoss.<br>
Format: `name EXISTING_CATEGORY NEW_CATEGORY`

Example:

* `name School ModuleStudy`<br>

### 2.11. Listing tasks by category : `listcategory`

Lists all tasks under a specified category.<br>
Format: `listcategory CATEGORYNAME`

Example:

* `listcategory School`<br>

### 2.12. Marking a task done : `done`

Marks a task done in TaskBoss.<br>
Format: `done INDEX`

> Marks the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

### 2.13. Undoing a command : `undo`

Undoes a most recent command.<br>
Format: `undo`

### 2.14. Sorting tasks by deadline : `sort`

Sorts tasks in TaskBoss by their deadlines.<br>
Format: `sort`

> Tasks are sorted by nearest end date and time

### 2.15. Sorting tasks by priority : `sort p`

Sorts tasks in the TaskBoss by their priorities.<br>
Format: `sort p`

> Tasks are sorted by descending order of priority levels

### 2.16. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.17. Saving the data 

TaskBoss data will automatically be saved in local hard disk after any command that mutates the data.<br>
There is no need to save manually.

### 2.18. Exporting data to an existing file : `save e/`

Format: `save e/FILE_PATH`

### 2.19. Exporting the data to a new file : `save n/`

Format: `save n/FILE_PATH NEW_FILE_NAME`

## 3. Command Summary

* **Add**  `add n/TASK_NAME [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]` <br>
  e.g. `add n/Post-exam celebration i/@Zouk sd/10-03-2017 ed/11-03-2017 c/Leisure p/1`

* **Clear by Category** : `clear CATEGORY`
  e.g. `clear School`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find by name** : `find n/TASK_NAME` <br>
  e.g. `find n/Meeting`

* **Find by end date** : `find sd/END_DATE` <br>
  e.g. `find d/03-01-2017`

* **Find by task information** : `find i/INFORMATION` <br>
  e.g. `find i/Meeting room 3`
  
* **Create category** : `new CATEGORY` <br>

* **Edit category** : `name EXISTING_CATEGORY NEW_CATEGORY` <br>

* **List** : `list` <br>

* **List by category** : `listcategory` <br>
  e.g. `listcategory study`

* **Help** : `help` <br>

* **Edit** : `edit INDEX [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]` <br>
  e.g.`edit 1 i/Use Stack et/23:59`

* **Undo** : `undo` <br>

* **Sort by deadline** : `sort` <br>

* **Sort by priority** : `sort p` <br>

* **Mark done** : `done INDEX` <br>

* **View** : `view INDEX` <br>

* **Export to a existing file** : `save e/FILE_PATH` <br>

* **Export to a new file** : `save n/FILE_PATH NEW_FILE_NAME` <br>







