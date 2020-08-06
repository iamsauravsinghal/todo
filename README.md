# Task API

## PORT
Works on PORT 4000

## API Calls
For adding and updating task use

	[POST/PUT]: /task

For retrieving task based on search text. Sub task if any is also displayed. Sorting order is priority, status and expected date respectively.

	[GET]: /task/<search text>

For closing task/sub-task using the task id. If task is closed, subtasks (if any) will also be closed. Similarly, if all sub-tasks are closed, the parent task will be closed by default.

	[POST]: /task/<id> #for closing task
	
For deleting task/sub-task using id. Please note that if the parent task is deleted, the sub tasks (if any) are also deleted.

	[DELETE]: /task/<id>
