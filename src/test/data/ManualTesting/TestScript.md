**Test Script**

| **No.** | **Command** | **General Observation** | **Result Display** | **Task List Panel (Task Card Selected)** | **Timed Count** | **Float Count** | **Calendar** <br>Blue = Pending,<br>Red = Overdue,<br>Grey = Completed |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | load &lt;directory\_to\_file&gt;\taskmanager.xml | - | Data file loaded successfully from &lt;directory\_to\_file&gt;\taskmanager.xml | Showing all tasks | 63 | 5 | Month view <br> Showing current month |
| 2 | a | Autocompletes to add | - | - | - | - | - |
| 3 | add Pay school fees<br> (a Pay school fees) | - | New task added: <br>Name: &quot;Pay school fees&quot; Tags: | 6. Pay school fees | 63 | 6 | - |
| 3 | add Project proposal by 13 apr 2pm | - | New task added: <br>Name: &quot;Project proposal&quot; End Date and Time: (relative duration from 13/4/17 2 p.m.) Tags: | 54. Project proposal<br>End Date: 13/04/2017<br>End Time: 14:00:00 | 64 | 6 | Showing April <br>Newly added task showing in calendar |
| 4 | add sushi restaurant promotion from 25 apr 10am to 28 apr 9pm | - | New task added: <br>Name: &quot;sushi restaurant promotion&quot; Start Date and Time: (relative duration from 25/4/17 10 a.m.) End Date and Time: (relative duration from 28/4/17 9 p.m.) Tags: | 67. sushi restaurant promotion<br>Start Date: 25/04/2017<br>Start Time: 10:00:00<br>End Date: 28/04/2017<br>End Time: 21:00:00 | 65 | 6 | Showing April <br>Newly added task showing in calendar |
| 5 | add buy toys by 2 hours later | Notification would appear after 2 seconds | New task added: <br>Name: &quot;buy toys&quot; End Date and Time: &quot;2 hours from now&quot; Tags:   | 53. buy toys<br>End Date: (today&#39;s date)<br>End Time: (current time + 2 hours) | 66 | 6 | Showing current month <br>Newly added task showing in calendar (blue in color) |
| 5 | add practise piano from 10 april to 30 april every week | - | New task added: <br>Name: &quot;practise piano&quot; Start Date and Time: (relative duration from 10/4/17 12 a.m.) End Date and Time: (relative duration from 30/4/17 12 a.m.) Tags:   | (with R symbol) 74. practise piano<br>Start Date: 10/04/2017<br>Start Time: 00:00:00<br>End Date: 30/04/2017<br>End Time: 00:00:00 | 70 | 6 | 4 new entries in the calendar corresponding to the recurring tasks |
| 6 | edit 5 change name to Pay David 25 for cab<br>(e 5 change name to Pay David 25 for cab) | - | Edited Task: <br>Name: &quot;Pay David 25 for cab&quot; Tags: [HighPriority] | 5. Pay David 25 for cab <br>**HighPriority** | - | - | - |
| 7 | edit 72 change enddate to 25 apr and endtime to 3pm | - | Edited Task: <br>Name: &quot;Clear out old clothes&quot; End Date and Time: (relative duration from 25/4/17 3 p.m.) Tags: | 68. Clear out old clothes<br>End Date: 25/04/2017<br>End Time: 15:00:00 | - | - | Showing April |
| 8 | edit 63; sd 15 apr; st 9am | - | Edited Task: <br>Name: &quot;Study till you drop&quot;Start Date and Time: (relative duration from 15/4/17 9 a.m.) End Date and Time: (relative duration from 21/4/17 11.59 p.m.) Tags: | 63. Study till you drop<br>Start Date: 15/04/2017<br>Start Time: 09:00:00<br>End Date: 21/04/2017<br>End Time: 23:59:00 | - | - | Showing April |
| 9 | edit 74 change recurinterval to day |   | Edited Task:<br>Name: &quot;practise piano&quot; Start Date and Time: (relative duration from 10/4/17 12 a.m.) End Date and Time: (relative duration from 30/4/17 12 a.m.) Tags: | (with R symbol) 92. practice piano<br>Start Date: 10/04/2017<br>Start Time: 00:00:00<br>End Date: 30/04/2017<br>End Time: 00:00:00 | 88 | 6 | 21 new entries in the calendar corresponding to the recurring tasks |
| 10 | delete 41<br>(d 41) | Disappear from calendar | Deleted Task:<br>Name: &quot;Karaoke with Relatives&quot;Start Date and Time: (relative duration from 1/4/17 5 p.m.) End Date and Time: (relative duration from 1/4/17 8 p.m.) Tags: [LowPriority] | Task card disappears from panel | 87 | 6 | - |
| 11 | find study<br>(f study) | Finds all tasks that have &quot;study&quot; in their names | 4 task(s) listed!  | Tasks:<br>1. Study with Bernard<br>2. Study with Joel<br>3. Study with David<br>4. Study till you drop | 4 | 0 | - |
| 12 | find exam | Finds all tasks that have the tag &quot;exam&quot; | 2 task(s) listed! | Tasks:<br>1. CS2103 Final Exam<br>2. ST2334 Final Exam | 2 | 0 | - |
| 13 | find 23:59:00 | Finds all tasks that have start or end time as &quot;23:59:00&quot; | 2 task(s) listed! | Tasks:<br>1. Intensive Revision<br>2. Study till you drop | 2 | 0 | - |
| 14 | find 28/04/2017 | Finds all tasks that have start or end date as &quot;28/04/2017&quot; | 2 task(s) listed! | Tasks:<br>1. ST2334 Final Exam<br>2. sushi restaurant promotion | 2 | 0 | - |
| 15 | list | - | Uncompleted tasks listed. | - | 48 | 5 | - |
| 16 | list all<br>(l all) | - | All tasks listed. | - | 87 | 6 | - |
| 17 | list timed | - | Timed tasks listed. | - | 48 | 0 | - |
| 18 | list floating | - | Floating tasks listed. | - | 0 | 5 | - |
| 19 | list overdue | - | Overdue tasks listed. | Subjected to date and time this command is run | - |
| 20 | list today | - | Today tasks listed. | Subjected to date and time this command is run | Day view |
| 21 | list this week | - | This week tasks listed. | Subjected to date and time this command is run | Week view |
| 22 | list completed | - | Completed tasks listed. | - | 39 | 1 | - |
| 23 | list uncompleted | - | Uncompleted tasks listed. | - | 48 | 5 | - |
| 24 | select 6<br>(s 6) | - | Selected Task: 6 | 6. Replenish Milk in Fridge<br>End Date: 16/03/2017<br>End Time: 14:00:00 | - | - | Showing March |
| 25 | complete 6(c 6) | - | Completed Task(s): <br>Name: &quot;Replenish Milk in Fridge&quot; End Date and Time: (relative duration from 16/3/17 2 p.m.) Tags: | Task card disappears from panel | 47 | 5 | Completed task updated (grey in color) |
| 26 | complete 6,7,8 | - | Completed Task(s): <br>Name: &quot;Read Book about Software Engineering&quot; End Date and Time: (relative duration from 31/3/17 9 a.m.) Tags: [LowPriority]<br>Name: &quot;Meetup Dinner and Watch Movie with Cousins&quot; Start Date and Time: (relative duration from 16/3/17 7 p.m.) End Date and Time: (relative duration from 16/3/17 10.30 p.m.) Tags: <br>Name: &quot;MacRitchie Reservoir with Dad&quot; Start Date and Time: (relative duration from 9/4/17 9 a.m.) End Date and Time: (relative duration from 9/4/17 11 a.m.) Tags: | Task cards disappear from panel | 44 | 5 | Completed tasks updated (grey in color) |
| 27 | undo(u) | - | Undone: Completed Task(s): <br>[Name: &quot;Read Book about Software Engineering&quot; End Date and Time: (relative duration from 31/3/17 9 a.m.) Tags: [LowPriority],Name: &quot;Meetup Dinner and Watch Movie with Cousins&quot; Start Date and Time: (relative duration from 16/3/17 7 p.m.) End Date and Time: (relative duration from 16/3/17 10.30 p.m.) Tags: , Name: &quot;MacRitchie Reservoir with Dad&quot; Start Date and Time: (relative duration from 9/4/17 9 a.m.) End Date and Time: (relative duration from 9/4/17 11 a.m.) Tags: <br>] | Previously completed task cards reappear in panel | 47 | 5 | Completed tasks undone |
| 27 | view day(v day) | - | Calendar view switched to day view. | - | - | - | Day view |
| 28 | view week | - | Calendar view switched to week view. | - | - | - | Week view |
| 29 | view month | - | Calendar view switched to month view. | - | - | - | Month view |
| 30 | next(n) | - | Next day/week/month displayed at the calendar. | - | - | - | Next day/week/month |
| 31 | prev(p) | - | Previous day/week/month displayed at the calendar. | - | - | - | Previous day/week/month |
| 32 | Press on hotkey CTRL+ALT+D | App Window minimized | - | - | - | - | - |
| 33 | Press on hotkey CTRL+ALT+D | App Window restored | - | - | - | - | - |
| 34 | Press up and down keys | Show previous commands | - | - | - | - | - |
| 35 | save &lt;directory&gt; | - | Save location changed to: &lt;directory&gt;\taskmanager.xml | - | - | - | - |
| 36 | help | Help window opened | Opened help window. | - | - | - | - |
| 37 | help find(h find) | - | find: Finds all tasks whose names contain any of the specified keywords (case-sensitive) and displays them as a list with index numbers.Parameters: KEYWORD [MORE\_KEYWORDS]...Example: find meeting with | - | - | - | - |
| 38 | exit | App exits | - | - | - | - | - |
