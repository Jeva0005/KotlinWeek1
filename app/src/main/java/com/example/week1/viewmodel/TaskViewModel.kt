package com.example.week1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week1.domain.Task
import com.example.week1.domain.mockTasks

class TaskViewModel : ViewModel() {
    var tasks by mutableStateOf(listOf<Task>())
        private set

    init {
        tasks = mockTasks
    }

    fun addTask(task: Task) {
        tasks = com.example.week1.domain.addTask(tasks, task)
    }

    fun toggleDone(id: Int) {
        tasks = com.example.week1.domain.toggleDone(tasks, id)
    }

    fun removeTask(id: Int) {
        tasks = tasks.filter { task -> task.id != id }
    }

    fun filterByDone(done: Boolean) {
        tasks = com.example.week1.domain.filterByDone(tasks, done)
    }

    fun sortByDueDate() {
        tasks = com.example.week1.domain.sortByDueDate(tasks)
    }
}