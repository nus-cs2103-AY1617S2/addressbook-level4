# Manual Testing

## Quick Start
1. Rename the SampleData.xml file to YATS.xml file
2. Copy the renamed file over to /main/data/
3. Start up the YATS
> * The YATS should now be pre-loaded with the supplied sample data.

## Test Cases

### List
`list`
1. YATS will show all the tasks in the primary list
> Result in the result display
> Listed all tasks
> ** Detailed list is not shown in the TestScript.md**

`list by start 03:45PM`
1. YATS will show one result
> Result (if task is unmarked)
> 1. Benzoic Aptitude Test
> Organic revision
> Benzoic acid is a very acidic compount that requires intense alcoholic exchange. Revision has to be based on ethanol and focus on malloc.
> School
> 03:45PM 12/12/2017 - 07:00PM 12/12/2017

`list by end 12/12/2017`
1. YATS will show one result
> Result (if task is unmarked)
> 1. Benzoic Aptitude Test
> Organic revision
> Benzoic acid is a very acidic compount that requires intense alcoholic exchange. Revision has to be based on ethanol and focus on malloc.
> School
> 03:45PM 12/12/2017 - 07:00PM 12/12/2017

`list by deadline 10/01/2019`
1. YATS will show one result
> Result (if task is unmarked)
> 1. Inductive Reasoning Based Philosophy
> books armaggedon reading home
> Have to finish reading this book by 10th January in preparation for Armaggedon
> Home
> by 10:20AM 10/01/2019

`list by location NUS`
1. YATS will show four results
> Search is not case-sensitive
> Result
> 1. Seeing is believeing
> wild gone
> NUS Wifi is kind of a finicky subject, you don't want to touch it, but you have to use it to survive. Do what you will with it, but I'm leaving.
> NUS
> 2. Remove mine documentation
> techies WOAW
> Did we put the right fuse on that one?
> NUS
> 08:05AM
> 3. Sallen pointer
> never give up you
> Sallen key low pass filtering seems to have failed.
> NUS
> 02:11PM 22/04/2017 - 02:45PM 22/04/2017
> 4. Test configuration file of reverberating system
> recovering system
> System harmonics project failing test cases
> NUS
>  by 11:59PM 15/03/2017

`list by tag tag1`
1. YATS will show one result, with the task title "Test event 2"

`list by start`

`list by end`

`list by deadline`

`list by done`

### Find
`find TASK_TITLE`
`find TASK_DESCRIPTION`

### Add
`add test floating task`<br>
1. YATS will show the details of the added task<br>
> New event added: test floating task<br>
> Location: <br>
> Description:  <br>
> Time:  - <br>
> Completed: No<br>
> Tags: <br>

`add new test -s 4 july 2017 9pm -e 5 july 2017 10pm -l school -T great`<br>
1. YATS will show the details of the added task<br>
> New event added: new test<br>
> Location: school<br>
> Description:  <br>
> Time: 09:00PM 04/07/2017 - 10:00PM 05/07/2017<br>
> Completed: No<br>
> Tags: [great]<br>

`add more tests -t from 5 july 2017 10am to 10 july 2017 11pm -d so many things to do -T work`<br>
1. YATS will show the details of the added task<br>
> New event added: more tests<br>
> Location: <br>
> Description: so many things to do<br>
> Time: 10:00AM 05/07/2017 - 11:00PM 10/07/2017<br>
> Completed: No<br>
> Tags: [work]<br>

`add deadline test -by 5 july 2017 10pm -l ivle -d due date for CS -T finally`
1. YATS will show the details of the added task<br>
> New event added: deadline test<br>
> Location: ivle<br>
> Description: due date for CS<br>
> Time:  - (SHOWN IN GUI) [by 10:00PM 05/07/2017]<br>
> Completed: No<br>
> Tags: [finally]<br>

### Delete
`delete 2`
1. YATS will delete the task at the given index<br>
> Deleted Task: Grocery Shopping<br>
> Location: FoodCourt<br>
> Description: Buy some food<br>
> Time: 08:05PM 07/04/2017 - 09:05PM 07/04/2017<br>
> Completed: No<br>
> Tags: [necessitires]<br>

`delete 100`
1. Index does not exist<br>
2. YATS will output invalid index message<br>
> The task index provided is invalid<br>

`delete` 
1. Invalid command because index is required<br>
2. invalid message will be displayed along with command usage message<br>
> Invalid command format! <br>
> delete: Deletes the task identified by the index number used in the last task listing.<br>
> Parameters: INDEX (must be a positive integer)<br>
> Example: delete 1<br>

`delete a` 
1. Invalid command because index is required, non-aplanumeric character not accepted<br>
2. invalid message will be displayed along with command usage message<br>
> Invalid command format! <br>
> delete: Deletes the task identified by the index number used in the last task listing.<br>
> Parameters: INDEX (must be a positive integer)<br>
> Example: delete 1<br>

`delete 1 2 3`
1. YATS will delete the tasks at the specified indexes
> Deleted 3 tasks<br>

### Edit
`edit 1 new name for this`<br> 
1. YATS will edit the title of the task<br>
> Edited Task: new name for this<br>
> Location: School<br>
> Description: Benzoic acid is a very acidic compound that requires intense alcoholic exchange. Revision has to be based on ethanol and focus on malloc.<br>
> Time: 03:45PM 12/12/2017 - 07:00PM 12/12/2017<br>
> Completed: No<br>
> Tags: [Organic][revision]<br>

`edit 1 -T revision`
1. YATS will edit and remove the tag 'revision'<br>
> Edited Task: new name for this<br>
> Location: School<br>
> Description: Benzoic acid is a very acidic compound that requires intense alcoholic exchange. Revision has to be based on ethanol and focus on malloc.<br>
> Time: 03:45PM 12/12/2017 - 07:00PM 12/12/2017<br>
> Completed: No<br>
> Tags: [Organic]<br>

`edit 1 -s 5 july 2017 10pm -e 6 july 2017 -l nowhere -d great things come for good people`
1. YATS edits and updates the details of the task<br>
> Edited Task: new name for this<br>
> Location: nowhere<br>
> Description: great things come for good people<br>
> Time: 10:00PM 05/07/2017 - 11:32AM 06/07/2017<br>
> Completed: No<br>
> Tags: [Organic]<br>

`edit 1 -T great`
1. YATS adds the tag to the task
> Edited Task: new name for this<br>
> Location: nowhere<br>
> Description: great things come for good people<br>
> Time: 10:00PM 05/07/2017 - 11:32AM 06/07/2017<br>
> Completed: No<br>
> Tags: [Organic][great]<br>

`edit 1 -T`
1. YATS clears all the tags of the task
> Edited Task: new name for this<br>
> Location: nowhere<br>
> Description: great things come for good people<br>
> Time: 10:00PM 05/07/2017 - 11:32AM 06/07/2017<br>
> Completed: No<br>
> Tags: <br>

### Mark and Unmark

`mark 1`
1. YATS marks task at index 1 of task list as done
2. YATS shifts the task to the done task list
> Task marked as done: new name for this<br>
> Location: nowhere<br>
> Description: great things come for good people<br>
> Time: 10:00PM 05/07/2017 - 11:32AM 06/07/2017<br>
> Completed: Yes<br>
> Tags: <br>

`unmark 1` 
1. YATS unmarks task at index 1 of done task list as not done
2. YATS shifts the task to the task list 
> Task marked as not done: new name for this<br>
> Location: nowhere<br>
> Description: great things come for good people<br>
> Time: 10:00PM 05/07/2017 - 11:32AM 06/07/2017<br>
> Completed: No<br>
> Tags: <br>

`unmark 1`
1. YATS output invalid index message because there is no task in done list
> The task index provided is invalid<br>

`list done`
1. return to list of undone tasks

`mark 1 2 3` 
1. YATS marks the tasks specified as done
2. YATS shifts the tasks to the done list
> 3 tasks marked as done<br>

`unmark 1 2 3`
1. YATS marks the tasks specified in done list as not done
2. YATS shifts the tasks to the task list
> 3 tasks marked as not done<br>

`mark`
1. YATS returns invalid message
> Invalid command format! <br>
> mark: Marks the task identified as done by the index number used in the last task listing. <br>
> Parameters: INDEX (must be a positive integer) <br>
> Example: mark 1<br>

### Select

`select 1`
1. YATS selects and shows details of task at index 1
> Selected task: new name for this<br>
> Location: nowhere<br>
> Description: great things come for good people<br>
> Time: 10:00PM 05/07/2017 - 11:32AM 06/07/2017<br>
> Completed: No<br>
> Tags: <br>

`select 90`
1. Invalid index provided message outputed
> The task index provided is invalid<br>

### Schedule
`schedule TASK`
`schedule TASK -h HOUR -m MINUTE`
`schedule TASK -l LOCATION -d DESCRIPTION -h HOUR -m MINUTE`

`schedule meeting -l meeting room 1`
1. YATS will schedule the meeting at the closest possible 1 hour weekday slot betwen 8am and 6pm and scroll down to it. Results will be sorted by start date.<br>
> Result in the result display<br>
> New event scheduled: meeting<br>
> Location: meeting room 1<br>
> Description:  <br>
> Time: 12:00PM 19/04/2017 - 01:00PM 19/04/2017<br>
> Completed: No<br>
> Tags: <br>

`schedule call -d with japanese folks -h 2 -m 30`
1. YATS will schedule the call at the closest possible 2 and a half hour weekday slot betwen 8am and 6pm and scroll down to it. Results will be sorted by start date.<br>
> Result in the result display<br>
> New event scheduled: meeting<br>
> Location: <br>
> Description: with japanese folks<br>
> Time: 01:00PM 19/04/2017 - 03:30PM 19/04/2017<br>
> Completed: No<br>
> Tags: <br>

### Undo
`undo`
1. YATS will restore the previous state if the previous command mutates the list 
> Undo completed!<br>

### Redo
`redo`
1. YATS will restore the last `undo` command executed
> Redo completed!<br>

### Clear

`mark 1 2 3`
1. YATS marks the 3 tasks as done

`clear`
1. YATS will clear all done task in the task list, both primary and secondary
> All done tasks have been cleared!<br>

### Reset
`reset`
1. YATS will clear all tasks in the task list, both primary and secondary
> Task Manager has been reset!<br>

### Save
`save default`
1. YATS will change the save location to the default save location

`save FILE_LOCATION`
1. YATS will change the save location to the specified save location on user's computer

### Help
`help`
1. YATS will open up the YATS help web page

### Exit
`exit`
1. YATS will exit
