# Week 1 - Task list

## Data model

Task Kotlin data class that includes:
- `id: Int` unique identifier
- `title: String` short name
- `description: String` details
- `priority: Int` priority level
- `dueDate: String` date `dd-MM-yyyy`
- `done: Boolean` completion state

## Kotlin functions

- `addTask(list, newtask)`: returns `list + newTask`
- `toggleDone(list, id)`: flips `done` for the task with the given `id`
- `filterByDone(list, done)`: returns only tasks where `task.done == done`
- `sortByDueDate(list)`: sorts tasks by `dueDate`

## Demo video

[Link to Demo video](https://unioulu-my.sharepoint.com/:v:/g/personal/t3vaje00_students_oamk_fi/IQCxc0nTR4yTQ6AJIyMbk7TIAUz7bQUNAp1lDsbZ5iA3F7I)
