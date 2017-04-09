## Set Up
1. Prepare a folder and place FunTaskTic.jar in it. All extra files that FunTaskTic produces will be in the same folder.
2. In this folder, create another folder `data`and put `SampleData.xml` in `data`
3. Run FunTaskTic and ensure that current tab is in `To Do`

## Test Script
|	Intention	|	Input command	|	Expected result	|
|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|
| **load sample data** | load data/SampleData.xml | task manager is refreshed with the data from supplied file with 50 tasks |
|		|		|		|
| **show help** | help | the user guide is displayed in a new window |
|		|		|		|
| **go to** `Done` **tab** | list done | selected view tab is now `Done` |
| **go to** `To Do` **tab** | list todo | selected view tab is now `To Do` |
|		|		|		|
| **add a task** <br><br>all newly added task should be automatically selected in the list| |  | 
| add floating task | add Write english essay | floating task is added |
| add task | add Prepare for maths test e/next week | task with end date is added |
| add repeating daily task | add Buy Lunch s/today 12pm r/day | that that repeats every day is added |
| add repeating weekly task | add Buy 4D s/today 4pm e/next month r/week | task that repeats every week is added |
| add repeating monthly task | add Cut hair s/today e/tomorrow r/year | task that repeats every month is added |
| add repeating yearly task | add Buy textbooks s/1 december e/2 years from now r/year | task that repeats every year is added |
| add task with description | add Get car keys from dad d/go to changi | task with description is added |
| add task with tag | Buy airtickets to maldives t/holiday | tash with holiday tag is added |
|		|		|		|
| **edit task** <br><br>editted task will be automatically selected in the list after successful edit| | |
| edit title in task:`Write english essay` | edit `index` Write chinese essay | task is editted |
| edit end date in task:`Prepare for maths test` | edit `index` e/next month | task is editted |
| edit multiple fields in task `Buy airtickets to maldives` | edit `index` d/with alice t/work | task description and tag is editted |
|		|		|		|
| **done task** | | tasks that are done will be moved from `to do` tab to `done` tab |
| mark task `Write chinese essay` as done | done `index` | task is marked done |
| mark recurring task `Buy Lunch` as done | done `index` | a copy of task marked as done is created while the repeating task's start date advances by one day and is still a to do task |
| mark last occurence of recurring task `Cut hair` as done | done `index` | task is marked done and there are no more recurrences of the task |
|		|		|		|
| **undone task**<br><br>tasks that are undone will be moved from `done` tab to `to do` tab | | |
| mark task `Write chinese essay` as undone in `Done` tab | list done | selected view tab is now `Done` |
| | undone `index` | task is marked undone |
| mark task `Cut hair` as undone | undone `index` | task is marked undone, does not add back the recurrence pattern it used to have |
|		|		|		|
| **select task** | | list will automatically scroll to and highlight the selected task |
| select `Buy Lunch` | select `index` | task is selected |
| select `Write chinese essay` in `To Do` tab | list todo | selected view tab is now `To Do` |
| | select `index` | task is selected |
|		|		|		|
| **delete task ** | | |
| delete task `Write chinese essay` | delete `index` | task is deleted |
|		|		|		|
| **sort tasks**<br><br>Sorted order preference will maintain throughout the session, i.e. new tasks added will be inserted according to the sort order | | |
| sort by start date | sort s/ | tasks are sorted according to earliest start dates first, tasks without start dates will be listed after tasks with start dates |
| sort by end date | sort e/ | tasks are sorted according to earliest end dates first, tasks without end dates will be listed after tasks with end dates |
|		|		|		|
| **undo a command**<br><br>Can only undo actions that changes data, i.e. add, edit, delete.<br><br>Undo history is only maintained for that session, e.g. cannot undo all commands listed above after exiting FunTaskTic and opening it again | | |
| add a task, edit it, then undo | add Go NTUC d/buy apples | task is added |
| | edit `index` d/buy oranges | task description is edited |
| | undo | task description is restored to `buy apples` |
| | undo | task is un-added, i.e. removed from list |
|		|		|		|
| **redo a command**<br><br>Can only redo actions if the last command was `undo`| | |
| restore the task `Go NTUC d/buy apples` | redo | task is added back |
| restore edit description| redo | task becomes `go NTUC d/buy oranges`|
| check anything else to redo | redo | error given, nothing else to redo |
|		|		|		|
| **find**<br><br>Search for tasks for the given keyword (non case sensitive), allows partial matching and tolerates some spelling mistakes. Search includes titles, descriptions and tags. | | |
| find tasks with keyword `school` | find school | shows tasks with `school` |
| | list | clears the filter and shows all tasks |
| find tasks with keyword `school` or `home` | find school home | show tasks with `school` or `home` |
| | list | clears the filter and shows all tasks |
|		|		|		|
| **find by date**<br><br> Search for tasks that contain the specified date point or date range | | |
| find tasks on 10 april 2017 | findbydate 10 apr 2017 | tasks listed have start dates <= 10 apr 2017 or end dates >= 10 apr 2017 |
| | list | clears the filter and shows all tasks |
| find tasks from 10-11 april 2017 | findbydate 10 apr 2017 to 11 apr 2017 | tasks listed have start dates <= 10 apr 2017 and end dates >= 11 apr 2017 |
| | list | clears the filter and shows all tasks |
|		|		|		|
| **save data to another file location** | | |
| save current FunTaskTic data to data/subfolder/newdata.xml | save data/subfolder/newdata.xml | data saved in specified location |
|		|		|		|
| **clear data in FunTaskTic** | clear | list is cleared |
|		|		|		|
| **exit FunTaskTic** | exit | closes FunTaskTic |
