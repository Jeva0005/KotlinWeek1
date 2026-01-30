package com.example.week1.view

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week1.model.Task
import com.example.week1.ui.theme.Week1Theme
import com.example.week1.viewmodel.TaskViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, vm: TaskViewModel = viewModel()) {

    val taskList by vm.tasks.collectAsState()

    val filterDone = remember { mutableStateOf<Boolean?>(null) }

    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var dueDateText by remember { mutableStateOf("") }

    val selectedTask = remember { mutableStateOf<Task?>(null) }

    val shownList: List<Task> = when (filterDone.value) {
        null -> taskList
        true -> taskList.filter { it.done }
        false -> taskList.filter { !it.done }
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val contentWidthModifier = if (isLandscape) {
        Modifier.fillMaxWidth().widthIn(max = 600.dp)
    } else {
        Modifier.fillMaxWidth()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = contentWidthModifier.fillMaxSize(),
            horizontalAlignment = if (isLandscape) Alignment.CenterHorizontally else Alignment.Start
        ) {
            item {
                Text(text = "HomeScreen")
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                OutlinedTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    label = { Text("Task title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    label = { Text("Task description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = dueDateText,
                    onValueChange = { dueDateText = it },
                    label = { Text("Due date (dd-MM-yyyy)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        val nextId = (taskList.maxOfOrNull { it.id } ?: 0) + 1
                        val newTask = Task(
                            id = nextId,
                            title = titleText.ifBlank { "Task $nextId" },
                            description = descriptionText.ifBlank { "Description $nextId" },
                            priority = 1,
                            dueDate = dueDateText.ifBlank { "01-01-2026" },
                            done = false
                        )
                        vm.addTask(newTask)
                        titleText = ""
                        descriptionText = ""
                        dueDateText = ""
                    }) { Text("Add task") }

                    Spacer(modifier = Modifier.height(0.dp).padding(6.dp))

                    Button(onClick = { vm.sortByDueDate() }) {
                        Text("Sort by due date")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { filterDone.value = null }) { Text("Show all") }
                    Spacer(modifier = Modifier.height(0.dp).padding(6.dp))
                    Button(onClick = { filterDone.value = true }) { Text("Show done") }
                    Spacer(modifier = Modifier.height(0.dp).padding(6.dp))
                    Button(onClick = { filterDone.value = false }) { Text("Show not done") }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            items(items = shownList, key = { it.id }) { task ->
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
                        Text(text = "${task.id}. ${task.title}")
                        Text(text = task.description)
                        Text(text = "Due: ${task.dueDate}")
                    }

                    Button(onClick = { vm.removeTask(task.id) }) {
                        Text("Delete")
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }

    val taskToEdit = selectedTask.value
    if (taskToEdit != null) {
        DetailDialog(
            task = taskToEdit,
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Week1Theme {
        HomeScreen()
    }
}
