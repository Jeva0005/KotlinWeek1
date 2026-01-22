# Week 2 - ViewModel

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

[Link to Demo video](https://unioulu-my.sharepoint.com/:v:/g/personal/t3vaje00_students_oamk_fi/IQA9S99M17woQZQ3FzQd9FSxAb_lIOspXkqpkiuYtmL-BY8?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=a7fPVh)
