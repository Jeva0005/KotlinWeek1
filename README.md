# Week 3 - MVVM

## Demo video

[Link to Demo video](https://unioulu-my.sharepoint.com/:v:/g/personal/t3vaje00_students_oamk_fi/IQBC7yCJ5v3wQpF7UMv3BIj0AZPIam9FZLgbxes9cmGpFns)

- **MVVM** stands for **Model–View–ViewModel**. The **Model** is yout data, the **View* is the UI, and the **ViewModel** is in between. It holds the app state and actions that change it. This is useful in Compose because Compose redraws the UI when a state changes.

- **StateFlow** is a state holder that always has a current value. The ViewModel updates the value when something changes and the UI observes it, so basically StateFlow is a way to safely hold and share UI state in observable form.
