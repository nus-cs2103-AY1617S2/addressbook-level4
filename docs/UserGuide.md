# User Guide

This product is a tool to accept natural language commands via keyboards to manage the user's schedule and todo tasks.

## Start the program

1. Find the project in the `Project Explorer` or `Package Explorer` (usually located at the left side)
2. Right click on the project
3. Click `Run As` > `Java Application` and choose the `Main` class.
4. The GUI should appear in a few seconds.

## View help : `help`
Format: `help`

1. Help will show the command list with brief descriptions.
2. Help is also shown if you enter an incorrect command e.g. `abcd`

## Add a task: `add`
Add is the command word to add a task to the task handler application.
Format: `add "<task_name>" ,<optional/deadline> ,<optional/task type>`
>The following format is expected when user is adding a specific task to the task handler:

Example :
* `add "do laundry" ,2012-12-02 20:00, recurring`
>If no deadline and task-type supplied, the task will be considered a floating task

## Delete a task: `delete`
Delete, Del or Remove are the command words that can be used to delete a task.
Format: `delete <optional/index>, <optional/keywords>, <optional/tag>`
> Specify the task number or the task description in the command bar along with the deleting keyword.

Example:
* `Delete Meeting for CS2103`
* `Delete 1`

## List tasks : `list`
List is the command word to list down all the existing tasks
Formats: `list [optional\tag] [optional\name]`
> Specify the filter after the keyword in the command line to filter the task based on the tags

Example:
* `list floating (List down all tasks with “floating” tag)`
* `list (List down all existing tasks irrespective of their tags)`
* `list cs2103 (List down a task with a task name cs2103) `

## Edit a task: `edit`
Edit is the command word to edit a specific tasks
Format : `edit <task name> {<optional/attribute> : value}+`
>User edits a specific task by specifying the new entry they wish to be reflected on the existing task list

Example:
* `edit cs2103 deadline : 2/3/2017 , name : cs2103rocks`
> (This will change the deadline of task cs2103 to 2/3/2017 and change the task name of cs2103 to cs2103rocks)

## Clear all entries : `clear`
Clears all entries from the address book.
Format: `clear`  

## Exit the program : `exit`
Exits the program.
Format: `exit`  

## Save the data 
Task data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.



