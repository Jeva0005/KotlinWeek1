# Week 2 - ViewModel

## Demo video

[Link to Demo video](https://unioulu-my.sharepoint.com/:v:/g/personal/t3vaje00_students_oamk_fi/IQA9S99M17woQZQ3FzQd9FSxAb_lIOspXkqpkiuYtmL-BY8?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=a7fPVh)

- In Jetpack Compose, the UI is driven by state, which means when a state value changes, Compose will automatically recompose affected parts of the UI.

- ViewModel is better than only using remember because remember stores state only for the lifetime of a composable, and so the data can be easily lost on configuration changes such as screen rotation. Meanwhile, a ViewModel will not be affected by these kind of changes and can keep the state separate from the UI.
