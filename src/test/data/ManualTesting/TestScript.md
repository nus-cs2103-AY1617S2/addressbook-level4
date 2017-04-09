## Set Up
1. Prepare a folder and place FunTaskTic.jar in it. All extra files that FunTaskTic produces will be in the same folder.
2. In this folder, create another folder `data`and put `SampleData.xml` in `data`
3. Run FunTaskTic and ensure that current tab is in `To Do`
4. Load the sample data with 50 tasks with command `load data/SampleData.xml`

## Test Script
|	Intention	|	Input command	|	Expected result	|
|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|
| **show help** | help | the user guide is displayed in a new window |
| **add a task** <br><br>all newly added task should be automatically selected in the list| |  | 
| add floating task | add Write english essay | floating task is added |
| add task | add Prepare for maths test e/next week | task with end date is added |
| add repeating daily task | add Buy Lunch s/today 12pm r/day | that that repeats every day is added |
| add repeating weekly task | add Buy 4D s/today 4pm e/next month r/week | task that repeats every week is added |
| add repeating monthly task | add Cut hair s/today e/tomorrow r/year | task that repeats every month is added |
| add repeating yearly task | add Buy textbooks s/1 december e/2 years from now r/year | task that repeats every year is added |
| add task with description | add Get car keys from dad d/go to changi | task with description is added |
| add task with tag | Buy airtickets to maldives t/holiday | tash with holiday tag is added |
| **edit task** <br><br>editted task will be automatically selected in the list after successful edit| | |
| edit title in task:`Write english essay` | edit `index` Write chinese essay | task is editted |
| edit end date in task:`Prepare for maths test` | edit `index` e/next month | task is editted |
| edit multiple fields in task `Buy airtickets to maldives` | edit `index` d/with alice t/work | task description and tag is editted |
| **done task** | | tasks that are done will be moved from `to do` tab to `done` tab |
| mark task `Write chinese essay` as done | done `index` | task is marked done |
| mark recurring task `Buy Lunch` as done | done `index` | a copy of task marked as done is created while the repeating task's start date advances by one day and is still a to do task |
| mark last occurence of recurring task `Cut hair` as done | done `index` | task is marked done and there are no more recurrences of the task |
| **undone task**<br><br>tasks that are undone will be moved from `done` tab to `to do` tab | | |
| mark task `Write chinese essay` as undone | list done | selected view tab is now `Done` |
| | undone `index` | task is marked undone |
| mark task `Cut hair` as undone | undone `index` | task is marked undone, does not add back the recurrence pattern it used to have |
| **select task** | | list will automatically scroll to and highlight the selected task |
| select `Buy Lunch` | select `index` | task is selected |
