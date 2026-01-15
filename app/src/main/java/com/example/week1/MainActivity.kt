package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week1.domain.Task
import com.example.week1.domain.addTask
import com.example.week1.domain.filterByDone
import com.example.week1.domain.mockTasks
import com.example.week1.domain.sortByDueDate
import com.example.week1.domain.toggleDone
import com.example.week1.ui.theme.Week1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var taskList by remember { mutableStateOf(mockTasks) }
    var filterDone by remember { mutableStateOf<Boolean?>(null) }

    var toggleIdText by remember { mutableStateOf("") }

    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var dueDateText by remember { mutableStateOf("") }

    val shownList: List<Task> = when (filterDone) {
        null -> taskList
        true -> filterByDone(taskList, true)
        false -> filterByDone(taskList, false)
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "HomeScreen")
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = titleText,
            onValueChange = { titleText = it },
            label = { Text("Task title") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descriptionText,
            onValueChange = { descriptionText = it },
            label = { Text("Task description") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = dueDateText,
            onValueChange = { dueDateText = it },
            label = { Text("Due date (dd-MM-yyyy)") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Button(onClick = {
                val nextId = (taskList.maxOfOrNull { it.id } ?: 0) + 1
                val newTask = Task(
                    id = nextId,
                    title = if (titleText.isBlank()) "Task $nextId" else titleText,
                    description = if (descriptionText.isBlank()) "Description $nextId" else descriptionText,
                    priority = 1,
                    dueDate = if (dueDateText.isBlank()) "01-01-2026" else dueDateText,
                    done = false
                )
                taskList = addTask(taskList, newTask)
                titleText = ""
                descriptionText = ""
                dueDateText = ""
            }) { Text("Add task") }

            Spacer(modifier = Modifier.height(0.dp).padding(6.dp))

            Button(onClick = { taskList = sortByDueDate(taskList) }) { Text("Sort by due date") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = toggleIdText,
            onValueChange = { toggleIdText = it },
            label = { Text("Toggle id (e.g. 1)") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val id = toggleIdText.toIntOrNull()
            if (id != null) taskList = toggleDone(taskList, id)
        }) { Text("Toggle done") }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Button(onClick = { filterDone = null }) { Text("Show all") }
            Spacer(modifier = Modifier.height(0.dp).padding(6.dp))
            Button(onClick = { filterDone = true }) { Text("Show done") }
            Spacer(modifier = Modifier.height(0.dp).padding(6.dp))
            Button(onClick = { filterDone = false }) { Text("Show not done") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        shownList.forEach { task ->
            Text(text = "${task.id} | ${task.title} | due: ${task.dueDate} | done: ${task.done}")
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Week1Theme {
        HomeScreen()
    }
}
