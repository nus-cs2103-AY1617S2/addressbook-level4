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
| 1 | **load nonexistent sample data** | load data/new.xml | Alert is shown for nonexistent data and new xml with given file name is created |
| 2 | **load invalid sample data** | load invalid | Error message will be shown |
| 3 | **load sample data** | load data/sampledata.xml | Task manager is refreshed with the data from supplied file with 50 tasks |
|	List	|		|		|   |
| 1 | **go to** `Done` **tab** | list done | Selected view tab is now `Done` |
| 2 | **go to** `To Do` **tab** | list todo | Selected view tab is now `To Do` |
| 3 | **list all tasks in current tab** | list | All tasks will be listed without switching tab |
| 4 | **list with invalid keyword** | list invalid | Alert for invalid keyword is shown. By default, all tasks will be listed in the current tab |
|	Add	|		|		|   |
| | **add a task** <br><br>Every newly added task should be automatically highlighted in the list| |  |
| 1 | add floating task | add Write english essay | Floating task is added |
| 2 | add task | add Prepare for maths test e/today | Task with end date is added |
| 3 | add repeating daily task | add Buy Lunch s/today 12pm r/day | Task that repeats every day is added |
| 4 | add repeating weekly task | add Buy 4D s/today 4pm e/next month r/week | Task that repeats every week is added |
| 5 | add repeating monthly task | add Cut hair s/today e/tomorrow r/year | Task that repeats every month is added |
| 6 | add repeating yearly task | add Buy textbooks s/1 december e/2 years from now r/year | Task that repeats every year is added |
| 7 | add task with description | add Get car keys from dad d/go to changi | Task with description is added |
| 8 | add task with tag | add Buy airtickets to maldives t/holiday | Task with holiday tag is added |
| 9 | add task with start date after end date | add Finish CS2103 revision s/today e/yesterday | Error message for date constraint will be shown |
| 10 | add new task when in `Done` tab | list done | Selected view is now `Done` |
| | | add Band rehearsal d/gigs are awesome | Auto-switch to `To Do` tab, showing new task added |
| Sort |		|		|		|
|| **sort tasks**<br><br>Sorted order preference will maintain throughout the session, i.e. new tasks added will be inserted according to the sort order | | |
|1 | sort by start date | sort s/ | Tasks are sorted according to earliest start dates first. Tasks without start dates will be listed after tasks with start dates |
|2 | sort by end date | sort e/ | Tasks are sorted according to earliest end dates first. Tasks without end dates will be listed after tasks with end dates |
|3 | default sort by end date | sort | Alert for invalid keyword is shown. Tasks are sorted according to earliest end dates first, tasks without end dates will be listed after tasks with end dates |
|4 | sort by invalid keyword | sort invalid | Alert for invalid keyword is shown. Tasks are sorted according to earliest end dates first, tasks without end dates will be listed after tasks with end dates |
|	Edit	|		|		| |
|| **edit task** <br><br>Editted task will be automatically selected in the list after successful edit| | |
|1| edit title in task:`Write English Essay` | edit 1 Write Chinese Essay| Task is edited |
|2| edit end date in task:`Prepare for maths test` | edit `index` e/next year | Task is edited with its position in the sorted list changed |
|3| edit multiple fields in task `Buy airtickets to maldives` | edit `index` d/with alice t/work | Task description and tag is editted |
|4| edit to remove fields in task `Buy airtickets to maldives` | edit `index` d/ t/ | Task description and tag are removed |
|5| edit a task that is done| list done | Selected view is now `Done` |
|| |  edit 1 t/random | The first task's tag in `Done` tab is edited |
|6| edit task `Buy airtickets to maldives` using alias command | change `index` d/with boss | task description and tag are removed |
|Done|		|		|		|
|| **done task** | | Tasks that are done will be moved from `To Do` tab to `Done` tab |
|1| mark task `Write chinese essay` as done | done `index` | Task is marked done |
|2| mark recurring task `Buy Lunch` as done | done `index` | A copy of task marked as done is created while the repeating task's start date advances by one day and is still a to do task |
|3| mark last occurence of recurring task `Cut hair` as done | done `index` | Task is marked done and there are no more recurrences of the task |
|4| mark done with an invalid index | done 1000 | Error message will be shown |
|5| mark a done task as done | list done | Selected view tab is now `Done` |
|| | done `any index` | Error message will be shown |
|Undone|		|		|		|
|| **undone task**<br><br>Tasks that are undone will be moved from `Done` tab to `To Do` tab | | |
|1| mark task `Write chinese essay` as undone in `Done` tab | undone `index of desired task` | Task is marked incomplete|
|2| mark task `Cut hair` as undone | undone `index of desired task` | task is marked undone, does not add back the recurrence pattern it used to have |
|3| mark undone with an invalid index | undone 1000 | Error message will be shown |
|4| mark an incomplete task as undone | list todo | Selected view tab is now `To Do` |
|| | undone `any index` | Error message will be shown |
|Select|		|		|		|
|| **select task** | | list will automatically scroll to and highlight the selected task |
|1| select `Buy Lunch` | select `index` | Task with given index is selected |
|2| select invalid index | select 1000 | Error message will be shown |
||		|		|		|
|| **delete task ** | | |
|1| delete task `Write chinese essay` | delete `index` | Task is deleted |
|2| delete task with invlaid index | delete 1000 | Error message will be shown |
|3| delete task with alias command remove | remove `index` | Task is deleted |
|Undo|		|		|		|
|| **undo a command**<br><br>Can only undo actions that changes data, i.e. add, edit, delete.<br><br>Undo history is only maintained for that session, e.g. cannot undo all commands listed above after exiting FunTaskTic and opening it again | | |
|1| undo previous delete | undo | Deleted task is restored |
|2| add a task, edit it, then undo | add Go to NTUC d/buy apples | Task is added |
|| | edit `index` d/buy oranges | Task description is edited |
|| | undo | Task description is restored to `buy apples` |
|| | undo | Task is un-added, i.e. removed from list |
|	Redo|	|		|		|
|| **redo a command**<br><br>Can only redo actions if the last command was `undo`| | |
|1| restore the task `Go NTUC d/buy apples` | redo | Task is added back |
|2| restore edit description| redo | Task becomes `go NTUC d/buy oranges`|
|3| check for anything else to redo | redo | Error message will be shown as there is nothing else to redo |
|Find|		|		|		|
|| **find**<br><br>Search for tasks for the given keyword (non case sensitive), allows partial matching and tolerates some spelling mistakes. Search includes titles, descriptions and tags. | | |
|1| find tasks with keyword `school` | find school | Shows tasks with `school` |
|| | list | Clears the filter and shows all tasks |
|2| find tasks with keyword `school` or `home` | 1. find school home | Shows tasks with `school` or `home` |
|| | list | Clears the filter and shows all tasks |
|3| find tasks with partial keyword `sch` | 1. find sch | Show tasks with `school`|
|| | list | Clears the filter and shows all tasks |
|4| find tasks with typo in keyword | 1. find prisentation | Shows tasks with `presentation` |
|| | list | Clears the filter and shows all tasks |
||		|		|		|
|| **find by date**<br><br> Search for tasks that contain the specified date point or date range | | |
|1| find tasks on 10 april 2017 | findbydate 10 apr 2017 | Tasks listed have start dates <= 10 apr 2017 or end dates >= 10 apr 2017 |
|1| | list | Clears the filter and shows all tasks |
|2| find tasks from 10-11 april 2017 | findbydate 10 apr 2017 to 11 apr 2017 | Tasks listed have start dates <= 10 apr 2017 and end dates >= 11 apr 2017 |
|| | list | Clears the filter and shows all tasks |
|Save|		|		|		|
|| **save data to another file location** | | |
|1| save current FunTaskTic data to data/subfolder/newdata.xml | save data/subfolder/newdata.xml | Data saved in specified location |
|2| save current FunTaskTic data to an invalid location | save invalid/newdata.xml | Error message will be shown |
|3| save current FunTaskTic data to an data/subfolder/newdata2.xml using alias command saveas | saveas data/subfolder/newdata2.xml | Data saved in specified location |
|Clear|		|		|		|
|| **clear data in FunTaskTic** | clear | list is cleared |
|Exit|		|		|		|
|| **exit FunTaskTic** | exit | closes FunTaskTic |
