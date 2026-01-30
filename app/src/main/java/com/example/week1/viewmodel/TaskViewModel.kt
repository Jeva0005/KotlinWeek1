package com.example.week1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.week1.model.Task
import com.example.week1.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow(mockTasks)
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }

    fun toggleDone(id: Int) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == id) task.copy(done = !task.done) else task
        }
    }

    fun removeTask(id: Int) {
        _tasks.value = _tasks.value.filter { task -> task.id != id }
    }

    fun updateTask(updated: Task) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == updated.id) updated else task
        }
    }

    fun sortByDueDate() {
        _tasks.value = _tasks.value.sortedBy { task ->
            val parts = task.dueDate.split("-") // dd-MM-yyyy
            val dd = parts.getOrNull(0) ?: "00"
            val mm = parts.getOrNull(1) ?: "00"
            val yyyy = parts.getOrNull(2) ?: "0000"
            "$yyyy$mm$dd"
        }
    }
}