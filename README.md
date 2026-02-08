# Week 4 - Navigation

## Demo video

[Link to Demo video](https://unioulu-my.sharepoint.com/:v:/g/personal/t3vaje00_students_oamk_fi/IQAS1nPEB-QzQ78b8EsvdSIqAc_VnFFUvsg6W75Sq_C1AW8?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=BtTSw8)

- Navigation in Jetpack Compose means moving between different screens inside one activity.
Instead of using multiple activities, the app uses composable screens and switches between them using routes. `NavHost`defines which screens exist in the app and what route opens which screen. `NavController` is used to control navigation actions, such as going from one screen to another. In this app, navigation is implemented with three main screens, which are HomeScreen, CalendarScreen and SettingsScreen. The user can move between Home and Calendar using bottom navigation. The Settings screen is also accessible from the same navigation bar.

- The app uses the **MVVM** architecture. All task data and logic are stored in a single `TaskViewModel`. The same ViewModel is shared between HomeScreen and CalendarScreen. Because the ViewModel is created at the navigation level, it is not recreated when switching screens. This means that tasks added or edited on **HomeScreen** are immediately visible on **CalendarScreen** and both screens always show the same data

- **CalendarScreen** shows the same tasks as **HomeScreen**, but in a calendar-like layout. Tasks are grouped by their due date. Each date is shown as a title, and the tasks belonging to that date are listed under it. Tasks can also be edited or deleted from the CalendarScreen. Adding and editing tasks is done using an `AlertDialog`, not separate screens. Pressing the "+" button opens a new dialog to add a new ask, pressing an existing task opens a dialog allowing to edit the task.
