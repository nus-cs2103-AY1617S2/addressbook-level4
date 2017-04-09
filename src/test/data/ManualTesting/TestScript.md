1. Load storage to the test directory, enter
"loadstorage src/test/data/ManualTesting".

2. List the tasklist, enter
"list".

3. Verify the there are 50 Tasks in the list.

4. Undo a command when there's no previous commands, enter
"undo".

5. Verify the exception that there's no command to undo.

6. Add a new Task, enter
"add Write Paper due/15/04/2017 2359 starts/14/04/2017 2100 ends/14/04/2017 2359 t/school t/important".

7. Verify a new Task added at last.

8. Undo a command, enter
"undo".

9. Verify the newly added Task is removed.

10. Redo the last command,enter
"redo".

11. Verify the Task is added again.

12. Add the same Task, enter
"add Write Paper due/15/04/2017 2359 starts/14/04/2017 2100 ends/14/04/2017 2359 t/school t/important".

13. Verify the exception that the task has already been added.
    
14. Add a new Task with no due date, enter 
"add Have Lunch with John starts/01/04/2017 1200 ends/01/04/2017 1300 t/fun".

15. Verify a Task with no due time is added.

16. Add a Task, enter 
"add Go to lab due/tomorrow 1000".

17. Verify a Task which will due tomorrow.

16. Add a Task, enter 
"add Go to lab starts/monday 1000".

18. Verify a Task which will start next Monday.

19. Add a Task with omitted time, enter
"add Buy milk and eggs due/08/09/2017".

20. Verify a Task which will due 2017/8/9 00:00 is added.

21. Add a Task with omitted date, enter
"add Buy milk and eggs due/1400". 

22. Verify a format exception is thrown.

23. List Tasks by due, enter
"list by due".

24. Verify all the Tasks are listed by due time.

25. List Tasks by starts, enter
"list by starts".

26. Verify all the Tasks are listed by start time.

27. List Tasks by ends, enter
"list by ends".

28. Verify all the Tasks are listed by end time.

29. list Tasks by added time, enter:
"list by addded".

30. Verify all the Tasks are listed by add time.

31. Complete a Task, enter
"complete 1".

31. Verify the first Task is marked completed.

32. Complete a Task again, enter
"complete 1".

33. Verify a Task has already been completed exception is thrown.

34. Complete a Task, enter
"complete 2".

35. Verify the second Task is marked completed.

36. Set a Task incomplete, enter
"uncomplete 1".

37. Verify the first Task is marked incomplete.

38. Set an incomplete Task incomplete, enter
"uncomplete 1".

39. Verify an exception that you can't uncomplete an incomplete Task.

40. List all the completed Tasks, enter 
"list complete".

41. Verify all the complete Tasks are listed.

42. List all the Tasks, enter 
"list".

43. Verify all the Tasks are listed.

44. List all the incomplete Tasks, enter 
"list incomplete".

45. Verify all the incomplete Tasks are listed.

46. Find Tasks with certain pattern in discription, enter
"find Alice".

47. Verify all the Tasks having Alice in discription are listed.

48. Find Tasks with any of the certain patterns in discription, enter
"find lecture lab".

49. Verify all the Tasks having lecture or lab in discription are listed.

50. Find Tasks with certain pattern in discription, enter
"find home".

51. Verify all the Tasks having home in tag are listed.
   
52. Find Tasks with any of the certain patterns in tag, enter
"find home important".

53. Verify all the Tasks having home or important in tag are listed.

54. Delete a Task, enter
"delete 1".

55. Verify the first Task is deleted.

56. Delete a Task out of range, enter
"delete 337".

57. Verify an index out of range exception.

58. Edit discription  
"edit 47 Write two papers". 

59. Verify the 47th Task's discription is changed. 

60. Edit due time
"edit 47 due/15/04/2017 2359".

61. Verify the 47th Task's due time is changed. 

62. Edit tag
"edit 47 t/school t/unimportant".
    
63. Verify the 47th Task's tag is changed.     
    
64. Edit start and end time, enter 
"edit 47 ends/17/04/2017 2300".

65. Verify the 47th Task's end time is changed.     

66. Remove parameters, enter
"edit 1 due/".

67. Verify the 47th Task's due time is erased. 

68. Use hybrid parameters, enter
"edit 47 Write three papers due/15/04/2017 2259 t/school t/important". 

69. Verify the 47th Task's due time, tag, discription at the same time. 

70. Move storage file to a different test directory, enter
"setstorage src/test/data/ManualTesting/ChangeDir".

71. Verify that file has been moved to the new directory.

71. clear all the Tasks, enter
"clear".

72. Verify the tasklist is empty.

73. Exit the App, enter
"exit".

74. Verify the App is closed.
