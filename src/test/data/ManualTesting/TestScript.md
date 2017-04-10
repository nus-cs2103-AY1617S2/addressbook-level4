**Test Script**

1. Click on YTomorrow.jar to run.
2. No need to load SampleData.xml! The program randomly generates new sample data on the first open.
3. Type "add brisk jog from tomorrow at 10am to tomorrow at noon"</br>
    * Task should appear in task list with correct values. Check clock to make sure values are correct.
4. Type "add compsci hw to friday"</br>
    * Task should appear with end date on Friday, but no start date.
5. Type "add work out from saturday"</br>
    * Error should appear. If a task has a start date, it needs an end date.
6. Type "add float in the pool in pool activities"</br>
    * Task should appear with no end date or start date, but with pool activites as a group tag.
7. Type "clear all"</br>
    * List should clear.
8. Type "undo"</br>
    * List should reappear
 9. Type "redo"</br>
    * List should clear again
 10. Type "undo"</br>
    * List should reappear again
 11. Type "delete 3"</br>
    *  Third task in the list should delete
 12. Type "mark 1"</br>
    * 1st task should turn green if it wasn't already green
 13. Type "unmark 1"</br>
    * 1st task should turn red
 14. Type "unmark 2"</br>
    * 2nd task should turn red if it wasn't already red
 15. Type "mark 2"</br>
    * 2nd task should turn green
 16. Type "lc"</br>
    * All complete tasks should be listed (the green ones)
 17. Type "li"</br>
    * All incomplete tasks should be listed (the red ones)
 18. Type "list"</br>
    * All tasks should be listed
 19. Type "find with"</br>
    * All tasks with 'with' in their title should be listed
 20. Type "list"</br>
    * All tasks should be listed
 21. Type "theme" and cycle through themes using UP and DOWN arrow keys. Press ENTER when you find one you like.</br>
    * Theme manager should pop up, and theme should change.
 22. Type "select 7"</br>
    * The 7th command should be highlighted
 23. Type "edit 7 working hard s/Apr 22 d/05/25/2017 g/wow"</br>
    * Name, start date, end date, and group tag should update to reflect your changes
 24. Type "undo"</br>
    * Task 7 should be reset to previous values
 25. Type "help"</br>
    * Help page should appear
 26. Press ENTER</br>
    * Help page should close
 27. Type "clear complete"</br>
    * All complete tasks should clear
 28. Type "add old task from jan 1 2016"
    * Task block should appear filled in red (meaning task end date has passed).
 29. Type "clear passed"
    * Tasks with an end date in the past should be cleared.
 30. Close the program.
   
  
    
