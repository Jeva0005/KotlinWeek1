package com.example.week1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week1.model.Task
import com.example.week1.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(modifier: Modifier = Modifier, vm: TaskViewModel) {
    val tasks by vm.tasks.collectAsState()

    val selectedTask = remember { mutableStateOf<Task?>(null) }

    val groups: Map<String, List<Task>> = tasks.groupBy { it.dueDate }
    val sortedDates = groups.keys.sortedBy { toSortableDateKey(it) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Calendar") }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (tasks.isEmpty()) {
                item { Text("No tasks") }
            } else {
                sortedDates.forEach { date ->
                    item {
                        Text(text = date)
                        Spacer(Modifier.height(8.dp))
                    }

                    items(items = groups[date].orEmpty(), key = { it.id }) { task ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTask.value = task },
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { vm.toggleDone(task.id) }
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = task.title)
                                Text(text = task.description)
                            }
                        }

                        Spacer(Modifier.height(10.dp))
                    }

                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }

    val taskToEdit = selectedTask.value
    if (taskToEdit != null) {
        DetailDialog(
            task = taskToEdit,
            isNew = false,
            onDismiss = { selectedTask.value = null },
            onSave = { updated ->
                vm.updateTask(updated)
                selectedTask.value = null
            },
            onDelete = { id ->
                vm.removeTask(id)
                selectedTask.value = null
            }
        )
    }
}

private fun toSortableDateKey(dueDate: String): String {
    val parts = dueDate.split("-")
    val dd = parts.getOrNull(0) ?: "00"
    val mm = parts.getOrNull(1) ?: "00"
    val yyyy = parts.getOrNull(2) ?: "0000"
    return "$yyyy$mm$dd"
}
