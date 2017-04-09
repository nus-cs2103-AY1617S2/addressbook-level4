## Set Up
1. Prepare a folder and place FunTaskTic.jar in it. All extra files that FunTaskTic produces will be in the same folder.
2. In this folder, create another folder `data`and put `SampleData.xml` in `data`
3. Run FunTaskTic and ensure that current tab is in `To Do`

## Test Script
|	Step	|	Intention	|	Input command	|	Expected result	|
|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|
|	Help |		|		|   |
|	1	| **show help** | help | the user guide is displayed in a new window |
|	Load	|		|		|   |
| 1 | **load invalid sample data** | load data/invalid.xml | Error message will be shown |
| 2 | **load sample data** | load data/SampleData.xml | Task manager is refreshed with the data from supplied file with 50 tasks |
|	List	|		|		|   |
| 1 | **go to** `Done` **tab** | list done | Selected view tab is now `Done` |
| 2 | **go to** `To Do` **tab** | list todo | Selected view tab is now `To Do` |
| 3 | **list all tasks in current tab** | list | All tasks will be listed without switching tab |
| 4 | **list with invalid keyword** | list invalid | Alert for invalid keyword is shown. By default, all tasks will be listed in the current tab |
|	Add	|		|		|   |
| | **add a task** <br><br>Every newly added task should be automatically highlighted in the list| |  |
| 1 | add floating task | add Write english essay | Floating task is added |
| 2 | add task | add Prepare for maths test e/next week | Task with end date is added |
| 3 | add repeating daily task | add Buy Lunch s/today 12pm r/day | Task that repeats every day is added |
| 4 | add repeating weekly task | add Buy 4D s/today 4pm e/next month r/week | Task that repeats every week is added |
| 5 | add repeating monthly task | add Cut hair s/today e/tomorrow r/year | Task that repeats every month is added |
| 6 | add repeating yearly task | add Buy textbooks s/1 december e/2 years from now r/year | Task that repeats every year is added |
| 7 | add task with description | add Get car keys from dad d/go to changi | Task with description is added |
| 8 | add task with tag | add Buy airtickets to maldives t/holiday | Task with holiday tag is added |
| 9 | add task with start date after end date | add Finish CS2103 revision s/today e/yesterday | Error message for date constraint will be shown |
| 10 | add new task when in `Done` tab | 1. list done | Selected view is now `Done` |
| 10 | | 2. add Band rehearsal d/gigs are awesome | Auto-switch to `To Do` tab, showing new task added |
| Sort |		|		|		|
|| **sort tasks**<br><br>Sorted order preference will maintain throughout the session, i.e. new tasks added will be inserted according to the sort order | | |
|1 | sort by start date | sort s/ | Tasks are sorted according to earliest start dates first. Tasks without start dates will be listed after tasks with start dates |
|2 | sort by end date | sort e/ | Tasks are sorted according to earliest end dates first. Tasks without end dates will be listed after tasks with end dates |
|3 | default sort by end date | sort | Alert for invalid keyword is shown. Tasks are sorted according to earliest end dates first, tasks without end dates will be listed after tasks with end dates |
|4 | sort by invalid keyword | sort invalid | Alert for invalid keyword is shown. Tasks are sorted according to earliest end dates first, tasks without end dates will be listed after tasks with end dates |
|	Edit	|		|		| |
|| **edit task** <br><br>Editted task will be automatically selected in the list after successful edit| | |
|1| edit title in task:`Write english essay` | edit `index` Write chinese essay | Task is editted |
|2| edit end date in task:`Prepare for maths test` | edit `index` e/next year | Task is editted with its position in the sorted list changed |
|3| edit multiple fields in task `Buy airtickets to maldives` | edit `index` d/with alice t/work | Task description and tag is editted |
|4| edit to remove fields in task `Buy airtickets to maldives` | edit `index` d/ t/ | Task description and tag are removed |
|5| edit a task that is done| 1. list done | Selected view is now `Done` |
|5| | 2. edit 1 t/random | The first task's tag in `Done` tab is edited |
|6| edit task `Buy airtickets to maldives` using alias command | change `index` d/with boss | task description and tag are removed |
|Done|		|		|		|
|1| **done task** | | Tasks that are done will be moved from `To Do` tab to `Done` tab |
|2| mark task `Write chinese essay` as done | done `index` | Task is marked done |
|3| mark recurring task `Buy Lunch` as done | done `index` | A copy of task marked as done is created while the repeating task's start date advances by one day and is still a to do task |
|4| mark last occurence of recurring task `Cut hair` as done | done `index` | Task is marked done and there are no more recurrences of the task |
|5| mark done with an invalid index | done 1000 | Error message will be shown |
|6| mark a done task as done | 1. list done | Selected view tab is now `Done` |
|6| | 2. done `any index` | Error message will be shown |
|Undone|		|		|		|
|1| **undone task**<br><br>Tasks that are undone will be moved from `Done` tab to `To Do` tab | | |
|2| mark task `Write chinese essay` as undone in `Done` tab | undone `index of desired task` | Task is marked incomplete|
|3| mark task `Cut hair` as undone | undone `index of desired task` | task is marked undone, does not add back the recurrence pattern it used to have |
|4| mark an incomplete task as undone | 1. list todo | Selected view tab is now `Done` |
|4| | 2. undone `any index` | Error message will be shown |
||		|		|		|
|| **select task** | | list will automatically scroll to and highlight the selected task |
|| select `Buy Lunch` | select `index` | task is selected |
|| select `Write chinese essay` in `To Do` tab | list todo | selected view tab is now `To Do` |
|| | select `index` | task is selected |
||		|		|		|
|| **delete task ** | | |
|| delete task `Write chinese essay` | delete `index` | task is deleted |
||		|		|		|
|| **undo a command**<br><br>Can only undo actions that changes data, i.e. add, edit, delete.<br><br>Undo history is only maintained for that session, e.g. cannot undo all commands listed above after exiting FunTaskTic and opening it again | | |
|| add a task, edit it, then undo | add Go NTUC d/buy apples | task is added |
|| | edit `index` d/buy oranges | task description is edited |
|| | undo | task description is restored to `buy apples` |
|| | undo | task is un-added, i.e. removed from list |
|	|	|		|		|
|| **redo a command**<br><br>Can only redo actions if the last command was `undo`| | |
|| restore the task `Go NTUC d/buy apples` | redo | task is added back |
|| restore edit description| redo | task becomes `go NTUC d/buy oranges`|
|| check anything else to redo | redo | error given, nothing else to redo |
||		|		|		|
|| **find**<br><br>Search for tasks for the given keyword (non case sensitive), allows partial matching and tolerates some spelling mistakes. Search includes titles, descriptions and tags. | | |
|| find tasks with keyword `school` | find school | shows tasks with `school` |
|| | list | clears the filter and shows all tasks |
|| find tasks with keyword `school` or `home` | find school home | show tasks with `school` or `home` |
|| | list | clears the filter and shows all tasks |
||		|		|		|
|| **find by date**<br><br> Search for tasks that contain the specified date point or date range | | |
|| find tasks on 10 april 2017 | findbydate 10 apr 2017 | tasks listed have start dates <= 10 apr 2017 or end dates >= 10 apr 2017 |
|| | list | clears the filter and shows all tasks |
|| find tasks from 10-11 april 2017 | findbydate 10 apr 2017 to 11 apr 2017 | tasks listed have start dates <= 10 apr 2017 and end dates >= 11 apr 2017 |
|| | list | clears the filter and shows all tasks |
||		|		|		|
|| **save data to another file location** | | |
|| save current FunTaskTic data to data/subfolder/newdata.xml | save data/subfolder/newdata.xml | data saved in specified location |
||		|		|		|
|| **clear data in FunTaskTic** | clear | list is cleared |
||		|		|		|
|| **exit FunTaskTic** | exit | closes FunTaskTic |
