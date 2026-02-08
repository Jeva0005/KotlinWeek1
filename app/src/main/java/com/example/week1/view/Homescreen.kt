package com.example.week1.view

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week1.model.Task
import com.example.week1.ui.theme.Week1Theme
import com.example.week1.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, vm: TaskViewModel) {
    val taskList by vm.tasks.collectAsState()

    val filterDone = remember { mutableStateOf<Boolean?>(null) }

    val selectedTask = remember { mutableStateOf<Task?>(null) }
    val isNewTaskDialog = remember { mutableStateOf(false) }

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

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Tasks") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val nextId = (taskList.maxOfOrNull { it.id } ?: 0) + 1
                    selectedTask.value = Task(
                        id = nextId,
                        title = "",
                        description = "",
                        priority = 1,
                        dueDate = "01-01-2026",
                        done = false
                    )
                    isNewTaskDialog.value = true
                }
            ) { Text("+") }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = contentWidthModifier.fillMaxSize(),
                horizontalAlignment = if (isLandscape) Alignment.CenterHorizontally else Alignment.Start
            ) {
                item { Spacer(modifier = Modifier.height(12.dp)) }

                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
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
                            .clickable {
                                selectedTask.value = task
                                isNewTaskDialog.value = false
                            },
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
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }

    val taskToEdit = selectedTask.value
    if (taskToEdit != null) {
        DetailDialog(
            task = taskToEdit,
            isNew = isNewTaskDialog.value,
            onDismiss = { selectedTask.value = null },
            onSave = { updated ->
                if (isNewTaskDialog.value) vm.addTask(updated) else vm.updateTask(updated)
                selectedTask.value = null
            },
            onDelete = { id ->
                vm.removeTask(id)
                selectedTask.value = null
            }
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Week1Theme {
        HomeScreen(vm = TaskViewModel())
    }
}
