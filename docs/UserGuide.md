# TaskBoss - User Guide

By : `Team W14-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [Command Summary](#command-summary)
 
 Our target user is Jim who usually works alone on either his personal computer or office computer. Jim does not share his computer with other employees or his family members. He prefers a command-line approach over a mouse-clicking approach. Jim would like to have an organized set up of his tasks so that he can categorize, sort, and prioritise them for ease of task management.  


## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Run the task manager, TaskBoss. 
2. ADD YOUR FIRST TASK
   Initially, you will have a clear task manager. TaskBoss interface will show you three different panels. All panels should be void of anything at this point with the exception of the panel on the left side of the screen, it should have two categories by default by the names All Tasks and Done. To get started, you can add your first task by entering the correct command in the command panel that you see at the bottom of your screen. Set your first task’s details:
  a. Assign a title for your task
  b. Add in any descriptions you want to specify to your task. 
  c. Specify the task’s deadlines if needed
  d. Decide on the task’s priority level
  e. Set a start and end time if need be.

3. VIEW YOU TASKS 
  Now that you have added in your first task, you should be able to see it in on the panel on the right side of your screen. You can always refer to this panel to see what tasks you have yet to complete. The panel will display your tasks based on the tasks’ deadlines then according to the priority level you’ve assigned the tasks. Notice how the All Tasks category is highlighted in red. This means your are currently seeing all the available tasks according to the default display setting mentioned above.

4. EDIT YOUR TASKS 
  You can edit a task’s details simply by entering the command in the command panel. You should be able to edit and update the task’s title, description, priority level, and deadlines. 

5. MARK DONE OR DELETE YOUR TASKS 
   Now that you’ve added a task and know how to edit it, you can check off the task once you’ve completed it. Enter the correct command prompt in the command panel. Doing so will automatically remove the said task from the All tasks category, the panel on the right, and add it to the list of tasks that have been completed which are all under the Done category. If you wish to move the marked done task back into your pending tasks list, you can do so by editing the task’s category and add it into an existing category. Your task will then appear under the category you have selected and under the All Tasks category.

	In the case you want to delete the task permanently, you can choose to delete it by entering the command in the command panel. Or in the case where you wish to permanently remove all tasks under a specific category, simply enter the command responsible for doing just that, the clear command.

6. CREATE YOUR FIRST CATEGORY 
   TaskBoss gives you the option to create categories in order to organize your tasks. Create your categories by entering the correct command into the command panel. Now, edit the task that you want to put under the new category, and now you should be able to enlist it under the new category you created. You can also choose to edit the category name by entering the correct command prompt. By choosing to view all tasks under a specific category, you will see that the category’s name on the left panel will be highlighted. This will help in letting you know which category you’re viewing.
   
 7. SORT YOUR TASKS
    With the correct command prompt in the command panel, you can view all your tasks in list view or sorted based on deadlines and priority levels. In addition, you can choose to view all the tasks that belong under a given category. Similarly, you can view the tasks that have been marked done or tasks that still need to be completed. 
    The result of these commands should be visible on the main panel, the panel on the right.

 8. VIEW A SPECIFIC TASK
 You can view any particular task by entering the correct command. The task will appear on the main panel, the panel of the right, with all it’s details clearly stated.
 
 9. OVERDUE
 If a pending task was overdue, TaskBoss will automatically highlight the task for you to take note of.
 
 10. UNDO
  If at any point, you entered the wrong command or want to reverse your last command, then you can simply enter the command for undoing your last command. 
  In the case where you enter the wrong format of a command line, an error message will appear above the command panel, instructing you on the correct format and/or let you know if no such command exists.
  
 11. HELP 
  By entering the help command, you will be able to see all the different commands that you can do on your tasks and their descriptions. You should also see how each command should be formatted when entered to produce your intended action. This will be displayed as a pop up message which you can make dissapear after getting the help that you want by simply typing anything in the command panel.

 12. SEARCH FOR A TASK
 If you want to search for a particular task, you can do so with the correct command prompt. This command allows you to search for a task based on title, deadline, or a keyword from the description. The search result will be displayed on the display panel on the right.

 13. EXPORT AND SAVE YOUR TASKS
 At any point while using the TaskBoss, you can choose to export your tasks into a specific file whether it exists or not to save your tasks by entering the correct command in the command panel.

 14. EXIT
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
Format: `add <taskName> i/<info> sd/<MM-DD-YY> st/<hr:min> et/<<hr:min> ed/<MM-DD-YY> c/<CategoryName>  p/<PriorityLvl(1,2,3)>`

> (Abbreviations: 	i - information
			            sd - start date
			            st - start time
			            ed - end date
			            et - end time
			            c - category
			            p - priority level)
> i,sd,st,ed,et,c, and p are optional.
> order does not matter.

Examples:

* `add Project submission i/CS2103T v0.0 ed/03-02-2017 c/study p/1`

### 2.3. Listing all tasks : `ls`

Shows a list of all tasks in the TaskBoss.<br>
Format: `ls`

### 2.4. Editing a task : `edit`

Edits an existing task in the TaskBoss.<br>
Format: `edit <taskIndex>  i/<info> sd/<MM-DD-YY> st/<hr:min> et/<hr:min> ed/<MM-DD-YY> c/<CategoryName>  p/<PriorityLvl(1,2,3)>`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * (Abbreviations: 	i - information
			            sd - start date
			            st - start time
			            ed - end date
			            et - end time
			            c - category
			            p - priority level)
> * i,sd,st,ed,et,c, and p are optional.
> * order does not matter.


Examples:

* `edit 1 i/CS2103T v0.0 ed/03-02-2017 et/23:59 c/study p/1`<br>
  Edits the end time of 1st task.

### 2.5. Finding all tasks by name/deadline/description: `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `find n/<name>` `find d/<MM-DD-YY>` `find kw/<keyWord>`

> * The search is case sensitive. e.g `Project` will not match `project`
> * The order of the keywords does not matter. e.g. `meeting project` will match `project meeting`
> * The name/deadline/description is searched according to the command user enters.
> * Only full words will be matched e.g. `meeting` will not match `meetings`


Examples:

* `find n/project`<br>
  Returns all tasks whose name contains project 
  
* `find d/04-02-2017`<br>
  Returns all tasks with the end day 04-02-2017

### 2.6. Deleting a task : `del`

Deletes the specified task from the TaskBoss. Irreversible.<br>
Format: `del INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the TaskBoss.
  
* `find n/project`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Creating a category : `new`

Creats a new category in the task Boss.<br>
Format: `new categoryName`

Example:

* `new Study`<br>

### 2.8. Clearing tasks by category : `clear`

Clears all tasks under the specified category from the TaskBoss.<br>
Format: `clear categoryName`

> The defualt categories of TaskBoss are AllTasks and Done. <br>

### 2.9. Viewing a task : `view`

Views a task by entering the task index from the TaskBoss.<br>
Format: `view INDEX`

> Views the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

### 2.10. Editing a category name : `name`

Edits a category name in the TaskBoss.<br>
Format: `name <existingCategoryName> <newCategoryName>`

Example:

* `name Study moduleStudy`<br>

### 2.11. Listing tasks by category : `ls`

Lists all tasks under a specified category.<br>
Format: `ls categoryName`

Example:

* `ls moduleStudy`<br>

### 2.12. Marking a task done : `done`

Marks a task done in the TaskBoss.<br>
Format: `done INDEX`

> Marks the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

### 2.13. Undoing a command : `undo`

Undoes a most recent command.<br>
Format: `undo`

### 2.14. Sorting tasks by deadline : `sort`

Sorts tasks in the TaskBoss by their deadlines.<br>
Format: `sort`

> Task with nearerest end date and time will appear on the top

### 2.15. Sorting tasks by priority : `sort p`

Sorts tasks in the TaskBoss by their priorities.<br>
Format: `sort p`

> Task with highest priority will appear on the top

### 2.16. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.17. Saving the data 

TaskBoss data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.18. Exproting the data to existing file : `save e/`

Format: `save e/<filePath>`

### 2.19. Exproting the data to a new file : `save n/`

Format: `save n/<filePath> <newFileName>`

## 3. Command Summary

* **Add**  `add <taskName> i/<info> sd/<MM-DD-YY> st/<hr:min> et/<<hr:min> ed/<MM-DD-YY> c/<CategoryName>  p/<PriorityLvl(1,2,3)>` <br>
  e.g. `add Project submission i/CS2103T v0.0 ed/03-02-2017 c/study p/1`

* **Clear** : `clear`

* **Clear by Category** : `clear <categoryName>`
  e.g. `clear study`

* **Delete** : `del INDEX` <br>
   e.g. `delete 3`

* **Find by Name** : `find n/<name>` <br>
  e.g. `find n/project meeting`

* **Find by deadline** : `find d/<MM-DD-YY>` <br>
  e.g. `find d/03-01-2017`

* **Find by description** : `find kw/<keyWord>` <br>
  e.g. `find kw/meeting room 3`
  
* **Create category** : `new <categoryName>` <br>

* **Edit category** : `name <existingCategoryName> <newCategoryName>` <br>

* **List** : `ls` <br>

* **List by category** : `ls <categoryName>` <br>
  e.g. `ls study`

* **Help** : `help` <br>

* **Edit** : `edit INDEX i/<info> sd/<MM-DD-YY> st/<hr:min> et/<<hr:min> ed/<MM-DD-YY> c/<CategoryName>  p/<PriorityLvl(1,2,3)>` <br>
  e.g.`edit 1 i/CS2103T v0.0 ed/03-06-2017 c/study p/2`

* **Undo** : `undo` <br>

* **Sort by deadline** : `sort` <br>

* **Sort by priority** : `sort p` <br>

* **Mark done** : `done INDEX` <br>

* **View** : `view INDEX` <br>

* **Export to a existing file** : `save e/<filePath>` <br>

* **Export to a new file** : `save n/<filePath> <newFileName>` <br>







