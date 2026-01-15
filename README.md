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

<iframe src="https://unioulu-my.sharepoint.com/personal/t3vaje00_students_oamk_fi/_layouts/15/embed.aspx?UniqueId=d34973b1-8c47-4393-a009-23231b93b4c8&embed=%7B%22ust%22%3Atrue%2C%22hv%22%3A%22CopyEmbedCode%22%7D&referrer=StreamWebApp&referrerScenario=EmbedDialog.Create" width="1280" height="720" frameborder="0" scrolling="no" allowfullscreen title="kotlinweek1.webm"></iframe>
